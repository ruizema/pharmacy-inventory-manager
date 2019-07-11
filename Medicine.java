import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Medicine {
    private String name;
    private Medicine left;
    private Medicine right;
    private int height;
    private LinkedList<Shipment> shipments = new LinkedList<>();
    private int totalStock = 0;

    public Medicine(String name, int stock, LocalDate expiryDate) {
        this.name = name;
        this.height = 0; // A leaf has height 0
        addShipment(stock, expiryDate);
    }

    public void addShipment(int stock, LocalDate expiryDate) {
        shipments.add(new Shipment(stock, expiryDate));
        shipments.sort(Comparator.comparing(Shipment::getExpiryDate));
        totalStock += stock;
    }

    public void removeShipment(Shipment shipment) {
        totalStock -= shipment.getStock();
        shipments.remove(shipment);
    }

    public LinkedList<Shipment> getShipments() {
        return shipments;
    }

    public String getName() {
        return name;
    }

    public Medicine getLeft() {
        return left;
    }

    public Medicine getRight() {
        return right;
    }

    public void setLeft(Medicine left) {
        this.left = left;
    }

    public void setRight(Medicine right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public class Shipment {
        private int stock;
        private LocalDate expiryDate;

        Shipment(int stock, LocalDate expiryDate) {
            this.stock = stock;
            this.expiryDate = expiryDate;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            totalStock += stock - this.stock;
            this.stock = stock;
        }

        public LocalDate getExpiryDate() {
            return expiryDate;
        }
    }
}