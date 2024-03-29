import java.time.LocalDate;

public class MedTree {
    private Medicine root;

    private void insertMed(Medicine current, Medicine inserted) {
        if (root == null) {
            current = inserted;
            root = current;
        } else if (inserted.getName().compareTo(current.getName()) < 0) {
            Medicine left = current.getLeft();
            if (left == null) {
                current.setLeft(inserted);
            } else {
                insertMed(current.getLeft(), inserted);
            }
        } else if (inserted.getName().compareTo(current.getName()) > 0) {
            Medicine right = current.getRight();
            if (right == null) {
                current.setRight(inserted);
            } else {
                insertMed(current.getRight(), inserted);
            }
        } else {
            return;
        }

        current.setHeight(1 + Math.max(nodeHeight(current.getLeft()), nodeHeight(current.getRight())));
    }

    public void removeMed(Medicine remove) {
        removeMed(root, remove, null, "");
    }

    private void removeMed(Medicine current, Medicine remove, Medicine parent, String relation) {
        if (current.equals(remove)) {
            if (relation.equals("left")) {
                parent.setLeft(null);
            } else if (relation.equals("right")) {
                parent.setRight(null);
            }
        } else {
            int comparison = remove.getName().compareTo(current.getName());
            if (comparison < 0) {
                removeMed(current.getLeft(), remove, current, "left");
            } else {
                removeMed(current.getRight(), remove, current, "right");
            }
        }
    }

    public void insertMed(Medicine inserted) {
        insertMed(root, inserted);
    }

    // If found, returns medicine, else returns parent node where to insert new node
    private Medicine search(String name, Medicine med) {
        if (med == null) {
            return null;
        }
        int comparison = name.compareTo(med.getName());
        if (comparison > 0) {
            if (med.getRight() != null) {
                search(name, med.getRight());
            }
        } else if (comparison < 0) {
            if (med.getLeft() != null) {
                search(name, med.getLeft());
            }
        } else {
            return med;
        }
        return med;
    }

    public Medicine search(String name) {
        return search(name, root);
    }

    public boolean contains(String name) {
        Medicine search = search(name);
        if (search == null) {
            return false;
        } else {
            return search.getName().equals(name);
        }
    }

    public int nodeHeight(Medicine med) {
        if (med == null) {
            return -1;
        } else {
            return med.getHeight();
        }
    }

    public Medicine getRoot() {
        return root;
    }

    public String printStocks(Medicine med) {
        String output = "";
        if (med != null) {
            String left = "";
            String right = "";
            if (med.getLeft() != null) {
                left = printStocks(med.getLeft());
            }
            String medName = med.getName();
            String middle = "";
            for (Medicine.Shipment shipment : med.getShipments()) {
                middle += medName + "\t" + shipment.getStock() + "\t" + shipment.getExpiryDate() + "\n";
            }
            if (med.getRight() != null) {
                right = printStocks(med.getRight());
            }
            output += left + middle + right;
        }
        return output;
    }

    private void removeExpired(LocalDate currentDate, Medicine med) {
        if (med != null) {
            if (med.getLeft() != null) {
                removeExpired(currentDate, med.getLeft());
            }
            for (Medicine.Shipment shipment : med.getShipments()) {
                if (currentDate.compareTo(shipment.getExpiryDate()) > 0) {
                    med.removeShipment(shipment);
                }
            }
            if (med.getRight() != null) {
                removeExpired(currentDate, med.getRight());
            }
        }
    }

    public void removeExpired(LocalDate currentDate) {
        removeExpired(currentDate, root);
    }
}
