import java.time.LocalDate;
import java.util.LinkedList;

/****
 *Need a remove stock function
 *Need a display the medicine function : "medicine-name stock expiry-date"
 *----> to write the stock in main
 */

public class Medicine {
    private String name;
    private Medicine left;
    private Medicine right;
    private int height;
    private int heightDiff;
    private LinkedList<Shipment> shipments = new LinkedList<>();

    public Medicine(String name, int stock, LocalDate expiryDate) {
        this.name = name;
        this.height = 0; // A leaf has height 0
        addShipment(stock, expiryDate);
    }

    public void addShipment(int stock, LocalDate expiryDate) {
        // TODO: add shipment to appropriate spot in the list according to expiry date
        shipments.add(new Shipment(stock, expiryDate));
    }

    public void removeShipment(String currentDate) {
    	//TODO: remove the shipments that are inferior expirydate to the current date
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

        public LocalDate getExpiryDate() {
            return expiryDate;
        }
    }
}