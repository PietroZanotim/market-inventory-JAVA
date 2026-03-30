package Program;

import model.dao.CategoryDao;
import model.dao.ProductDao;
import model.dao.SaleDao;
import model.entities.Category;
import model.entities.Product;
import model.entities.Sale;
import model.entities.SaleItem;
import model.exceptions.DbException;
import model.exceptions.InputException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UI {

    public static void clearScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

    public static void waitEnter(Scanner sc) {
        System.out.println("\n=================================");
        System.out.println("Press ENTER to continue...");
        System.out.println("=================================");
        sc.nextLine(); // Aqui ele trava e fica esperando
    }

    public static Integer validateOption(int option, int limit) {
        if(option<1 || option >limit) {
            throw new InputException("Invalid option!\n");
        }
        else {
            return option;
        }
    }

    public static Sale makeSale (Scanner sc, ProductDao prodDao, List<Product> list, SaleDao saleDao) throws InputException {

        clearScreen();

        boolean flagGeneral = true;
        Sale sale = new Sale();

        while(true) {

            for(Product p : list) {
                System.out.println(p.toString());
            }

            boolean flagScope = true;
            Product findProduct = null;

            do {
                System.out.println();
                System.out.printf("Text the Id from the item you do want to sell (text 0 to exit): ");
                int id = sc.nextInt();
                if (id == 0) {
                    flagGeneral = false;
                    break;
                }
                findProduct = list.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
                if (findProduct == null) {
                    System.out.println("Id does not exist, try again.");
                } else {
                    flagScope = false;
                }
            } while (flagScope);
            if(flagGeneral==false) {
                break; //Finish this sale;
            }

            System.out.println(findProduct.toString());

            int quantity;
            do {
                System.out.println();
                System.out.printf("Text the quantity of the item: ");
                quantity = sc.nextInt();

                if (quantity > findProduct.getQuantity()) {
                    System.out.println("This quantity is not valid, try again.");
                }
            } while (quantity > findProduct.getQuantity());
            sc.nextLine();

            findProduct.setQuantity(findProduct.getQuantity() - quantity);

            SaleItem item = new SaleItem(findProduct, quantity, findProduct.getPrice());
            sale.addItem(item);
        }

        if(sale.getItems().isEmpty()) {
            return null;
        }

        sale.setDate(LocalDateTime.now());

        clearScreen();
        return sale;
    }

    public static void reportSales(List<Sale> saleList) {

        if(saleList.isEmpty()){
            System.out.println("No sales found...");
        }
        else {
            DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            for(Sale sl : saleList) {

                for(SaleItem si : sl.getItems()) {
                    System.out.printf("Id: %d | Date: %s | Total: %.2f | ", sl.getId(), dt.format(sl.getDate()), sl.getTotal());
                    System.out.printf("Quantity: %d | UnitPrice: %.2f | ProductName: %s\n", si.getQuantity(), si.getUnitPrice(), si.getProduct().getName());
                }
            }
        }
    }

    public static LocalDateTime[] filterDates(Scanner sc) {

        System.out.println();
        String stringDate;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.print("Type the inital date to filter(dd/mm/yyyy): ");
        stringDate = sc.nextLine();
        stringDate = stringDate.concat(" 00:00:00");

        LocalDateTime initialDate = LocalDateTime.parse(stringDate, fmt);
        LocalDateTime finalDate = null;

        do {
            System.out.print("Type the final date to filter(dd/mm/yyyy): ");
            stringDate = sc.nextLine();
            stringDate = stringDate.concat(" 23:59:59");

            finalDate = LocalDateTime.parse(stringDate, fmt);
            if (finalDate.isBefore(initialDate)) {
                System.out.println("Error! The date isn't valid!");
            }
        } while (finalDate.isBefore(initialDate));

        LocalDateTime[] dateList = {initialDate, finalDate};
        return dateList;
    }
}
