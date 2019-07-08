import java.time.LocalDate;
import java.util.LinkedList;

public class Medicine {
    private String name;
    private Medicine left;
    private Medicine right;
    private int height;
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

    private class Shipment {
        private int stock;
        private LocalDate expiryDate;

        Shipment(int stock, LocalDate expiryDate) {
            this.stock = stock;
            this.expiryDate = expiryDate;
        }
    }
}
