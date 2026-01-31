package Program;

import db.DB;
import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;
import model.exceptions.DbException;
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
                        clearScreen();
                        List<Product> list = new ArrayList<>();
                        list = prodDao.findALL();
                        for(Product p : list) {
                            System.out.println(p.toString());
                        }
                        waitEnter(sc);
                    break;

                    case 2:
                        Product newProduct = registerProduct(sc, catDao, prodDao);
                        prodDao.insertProduct(newProduct);
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
                clearScreen();
                System.out.println("Input invalid! Enter an valid data.");
                waitEnter(sc);
            }
            catch (DbException e) {
                clearScreen();
                System.out.println(e.getMessage());
                waitEnter(sc);
            }
            catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        sc.close();
        DB.closeConnection();
    }

    public static Product registerProduct(Scanner sc, CategoryDao catDao, ProductDao prodDao) {

        clearScreen();

        try {
            System.out.println("Enter the product name: ");
            String name = sc.nextLine();
            System.out.println("Enter the product price: ");
            Double price = sc.nextDouble();
            System.out.println("Enter the product quantity: ");
            Integer quantity = sc.nextInt();
            sc.nextLine();

            String categoryName;
            Category newCategory = null;

            while(true) {
                System.out.println("Enter the product category name: ");
                categoryName = sc.nextLine();

                newCategory = catDao.findByName(categoryName);

                if(newCategory!=null) {
                    break;
                }
                else {
                    System.out.println("Category non-existent. Enter again.");
                    System.out.println();
                }
            }

            Product newProduct = new Product(1, name, price, quantity, newCategory);

            return newProduct;
        }
        catch (InputException e) {
            throw new InputException(e.getMessage());
        }
        catch (DbException e) {
            throw new DbException(e.getMessage());
        }
    }
}