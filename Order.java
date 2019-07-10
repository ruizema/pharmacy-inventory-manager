public class Order {
    private String medicineName;
    private int quantity;

    public Order(String medName, String dose, String repeat) {
    	medicineName = medName;
    	quantity = Integer.parseInt(dose) * Integer.parseInt(repeat);
    }

    public String getMedName() {
    	return medicineName;
    }

    public int getQuantity() {
    	return quantity;
    }
}
