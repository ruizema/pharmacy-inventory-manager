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
            String[] line = scanner.nextLine().split(" "); // DOUBT
            switch (line[0]) {
                case "DATE":
                    currentDate = line[1];
                    if (orders.isEmpty()) {
                        writer.write(currentDate + " OK" + "\n");
                    } else {
                        writer.write(currentDate + " COMMANDES :" + "\n");
                        for (Order order: orders) {
                            writer.write(order.getMedName() + " " + order.getQuantity() + "\n");
                        }
                        // TODO: Merge orders
                        orders.clear();
                    }
                    // TODO: Get rid of expiring medicine
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
                            // TODO: Remove appropriate quantity from earlier shipment
                            writer.write(medPres.replace('\t', ' ') + " OK\n");
                        } else {
                            System.out.println(Arrays.toString(presElement));
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