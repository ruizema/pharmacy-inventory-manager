import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Tp2 {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];

        Scanner scanner = new Scanner(new File(input));
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        MedTree medicines = new MedTree();
        LinkedList<Order> orders = new LinkedList<>();
        int prescriptionCount = 0;
        String currentDate = "YYYY-MM-DD";

        while (scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            switch (line[0]) {
                case "DATE":
                    currentDate = line[1];
                    if (orders.isEmpty()) {
                        writer.write(currentDate + " OK" + "\n");
                    } else {
                        writer.write(currentDate + " COMMANDES :" + "\n");
                        HashMap<String, Integer> orderOutput = new HashMap<>();
                        for (Order order: orders) {
                            int quantity = order.getQuantity();
                            if (orderOutput.containsKey(order.getMedName())) {
                                quantity += orderOutput.get(order.getMedName());
                            }
                            orderOutput.put(order.getMedName(), quantity);
                        }
                        for (String orderName : orderOutput.keySet()) {
                            writer.write(orderName + '\t' + orderOutput.get(orderName) + '\n');
                        }
                        orders.clear();
                    }
                    medicines.removeExpired(LocalDate.parse(currentDate));
                    break;
                case "APPROV":
                    do {
                        String[] medElement = scanner.nextLine().split(" ");
                        String name = medElement[0];
                        int stock = Integer.parseInt(medElement[1]);
                        LocalDate expiryDate = LocalDate.parse(medElement[2]);
                        Medicine search = medicines.search(name);
                        if (!medicines.contains(name)) {
                            medicines.insertMed(new Medicine(name, stock, expiryDate));
                        } else {
                            search.addShipment(stock, expiryDate);
                        }
                    } while (!scanner.hasNext(";"));
                    writer.write("APPROV OK" + "\n");
                    break;
                case "STOCK":
                    writer.write("STOCK " + currentDate + "\n");
                    writer.write(medicines.printStocks(medicines.getRoot()));
                    break;
                case "PRESCRIPTION":
                    prescriptionCount += 1;
                    writer.write("PRESCRIPTION " + prescriptionCount + '\n');
                    do {
                        String medPres = scanner.nextLine();
                        String[] presElement = medPres.split("\\s+");
                        if (medicines.contains(presElement[0])) {
                            int toTake = Integer.parseInt(presElement[1]) * Integer.parseInt(presElement[2]);
                            Medicine med = medicines.search(presElement[0]);
                            LinkedList<Medicine.Shipment> shipments = med.getShipments();
                            Iterator<Medicine.Shipment> iterator = shipments.listIterator();
                            Medicine.Shipment shipment = iterator.next();
                            while (toTake > 0) {
                                int existing = shipment.getStock();
                                int taken = Math.min(existing, toTake);
                                shipment.setStock(existing - taken);
                                toTake -= taken;
                                if (shipment.getStock() == 0) {
                                    shipments.remove(shipment);
                                }
                                if (shipments.isEmpty()) {
                                    medicines.removeMed(med);
                                }
                                if (iterator.hasNext()) {
                                    shipment = iterator.next();
                                } else {
                                    break;
                                }
                            }
                            if (toTake > 0) {
                                // TODO: Make an order
                            }
                            writer.write(medPres.replace('\t', ' ') + " OK\n");
                        } else {
                            orders.add(new Order(presElement[0], presElement[1], presElement[2]));
                            writer.write(medPres.replace('\t', ' ') + " COMMANDE\n");
                        }
                    } while (!scanner.hasNext(";"));
                    break;
            }
        }
        writer.close();
    }
}