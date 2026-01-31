package Program;

import db.DB;
import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Product;
import model.exceptions.InputException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static Program.UI.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Testing Connection...");
        Connection conn = DB.getConnection();
        CategoryDao catDao = DaoFactory.categoryConnection();
        ProductDao prodDao = DaoFactory.productConnection();
        System.out.println("Sucess!");
        System.out.println();

        boolean running = true;

        while(running) {
            clearScreen();
            System.out.println("=== Market System ===");
            System.out.println("1. List Products");
            System.out.println("2. Register a new product");
            System.out.println("3. Make sale");
            System.out.println("4. Exit");
            System.out.println();
            System.out.print("Choose an option: ");

            try {

                int option = validateOption(sc.nextInt());
                sc.nextLine();

                switch (option) {
                    case 1:
                        List<Product> list = new ArrayList<>();
                        list = prodDao.findALL();
                        for(Product p : list) {
                            System.out.println(p.toString());
                        }
                        waitEnter(sc);
                    break;

                    case 2:
                        registerProduct(sc);
                    break;

                    case 3:
                    break;

                    case 4:
                        running = false;
                        System.out.println("Closing...");
                    break;
                }
            }
            catch (InputException e) {
                System.out.println("Input invalid! Choose an valid option.");
            }
            catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        DB.closeConnection();
    }

    public static void registerProduct(Scanner sc) {

        clearScreen();
        System.out.println("Enter the product name: ");
        String name = sc.nextLine();
        System.out.println("Enter the product price: ");
        Double price = sc.nextDouble();
        System.out.println("Enter the product quantity: ");
        Integer quantity = sc.nextInt();
        System.out.println("Enter the product category name: ");
        String category = sc.nextLine();

    }
}