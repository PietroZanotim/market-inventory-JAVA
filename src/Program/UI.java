package Program;

import model.dao.CategoryDao;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;
import model.exceptions.DbException;
import model.exceptions.InputException;

import java.util.List;
import java.util.Scanner;

public class UI {

    public static void clearScreen(){
        for(int i = 0; i < 50; i++){
            System.out.println();
        }
    }

    public static void waitEnter(Scanner sc) {
        System.out.println("\n_________________________________");
        System.out.println("Press ENTER to continue...");
        sc.nextLine(); // Aqui ele trava e fica esperando
    }

    public static Integer validateOption(int option) {
        if(option<1 || option >4) {
            throw new InputException("Invalid option!\n");
        }
        else {
            return option;
        }
    }

    public static Product makeSale (Scanner sc, ProductDao prodDao, List<Product> list) throws InputException {

        clearScreen();
        for(Product p : list) {
            System.out.println(p.toString());
        }

        boolean flag = true;
        Product findProduct;
        do {
            System.out.println();
            System.out.printf("Text the Id from the item you do want to sell (text 0 to exit): ");
            int id = sc.nextInt();
            if(id==0) {
                return null;
            }
                findProduct = list.stream().filter(p -> p.getQuantity()==id).findFirst().orElse(null);
                if(findProduct==null) {
                    System.out.println("Id does not exist, try again.");
                }
                else {
                    flag = false;
                }
        } while (flag);

        System.out.println(findProduct.toString());

        int quantity;
        do {
            System.out.println();
            System.out.printf("Text the quantity of the item: ");
            quantity = sc.nextInt();

            if(quantity > findProduct.getQuantity()) {
                System.out.println("This quantity is not valid, try again.");
            }
        } while (quantity > findProduct.getQuantity());

        findProduct.setQuantity(findProduct.getQuantity()-quantity);

        System.out.println("Sale completed and saved in the database.");
        return findProduct;
    }
}
