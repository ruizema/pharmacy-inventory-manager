import java.io.*;
import java.time.LocalDate;
import java.util.*;

/*****
 *TODO: Case STOCK and PRESCRIPTION to complete
 *Need an AVL tree for medicine nodes?
 */

public class Tp2 {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];

        Scanner scanner = new Scanner(new File(input));
        BufferedWriter writer = new BufferedWriter(new FileWriter(output));

        LinkedList<Medicine> medicines = new LinkedList<>();
        LinkedList<Order> orders = new LinkedList<>();
        int prescriptionCount = 0;
        String currentDate = "YYYY-MM-DD";

        while(scanner.hasNext()) {
            String[] line = scanner.nextLine().split(" ");
            
            switch(line[0]) {
                case "DATE":
                    currentDate = line[1];
                    if(orders.isEmpty()) {
                        writer.write(currentDate + " OK" + "\n");
                    }else {
                        writer.write(currentDate + " COMMANDES" + "\n");
                        for(Order order: orders){
                            writer.write(order.getMedName() + " " + order.getQuantity() + "\n");
                        }
                        orders.clear();
                    }
                    break;

                case "APPROV":
                    while(!scanner.hasNext(";")) {
                        writer.write("APPROV OK" + "\n");
                        String[] medElement = scanner.nextLine().split(" ");
                        int stock = Integer.parseInt(medElement[1]);
                        LocalDate expiryDate = LocalDate.parse(medElement[2]);
                        medicines.add(new Medicine(medElement[0], stock, expiryDate));
                    }
                    break;

                case "STOCK":
                    writer.write("STOCK " + currentDate + "\n");
                    for(Medicine med: medicines) { med.removeShipment(currentDate);}
                    //TODO: write 'medicine-name stock expiry-date' of each medicine in medicines  
                    break;

                case "PRESCRIPTION":
                    prescriptionCount += 1;
                    writer.write("PRESCRIPTION " + prescriptionCount);
                    while(!scanner.hasNext(";")) {
                        String medPres = scanner.nextLine();
                        String[] presElement = medPres.split("\t");
                        if(medicines.contains(presElement[0]) == true) {
                            //TODO: remove stock from shipment closes expiring date
                            writer.write(medPres.replace('\t', ' ') + " OK");
                        }else {
                            orders.add(new Order(presElement[0], presElement[1], presElement[2]));
                            writer.write(medPres.replace('\t', ' ') + " COMMANDE");
                        }
                    }
                    break;
            }
        }
        writer.close();

    }

}