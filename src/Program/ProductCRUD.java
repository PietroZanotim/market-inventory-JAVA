package Program;

import db.DB;
import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ProductCRUD {

    public static void main(String[] args) {

        List<Product> list = new ArrayList<>();

        System.out.println("Testing connection...");

        Connection conn = DB.getConnection();
        ProductDao prodDao = DaoFactory.productConnection();

        System.out.println("Sucess!");
        System.out.println();

        System.out.println("=== findALl product ===");
        list = prodDao.findALL();
        for(Product prod : list) {
            System.out.println(prod);
        }
        System.out.println();

        System.out.println("=== Insert product ===");
        System.out.println();

        System.out.println("=== Update product ===");
        System.out.println();

        System.out.println("=== Delete product ===");
        System.out.println();

        System.out.println("=== FindById product ===");
        System.out.println();

        DB.getConnection();
    }
}
