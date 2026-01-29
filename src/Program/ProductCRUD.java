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
        Category newCategory = new Category(2, "Comida");
        Product newProduct = new Product(1, "Beans", 10.0, 10, newCategory);
        prodDao.insertProduct(newProduct);
        System.out.println();

        System.out.println("=== Update product ===");
        Category updateCategory = new Category(1, "Drinks");
        Product updateProduct = new Product(1, "Pepsi", 4.0, 60, updateCategory);
        prodDao.updateProduct(updateProduct);
        System.out.println();

        System.out.println("=== Delete product ===");
        prodDao.deleteById(2);
        System.out.println();

        System.out.println("=== FindById product ===");
        Product findProduct = prodDao.findById(3);
        System.out.println(findProduct.toString());
        System.out.println();

        DB.getConnection();
    }
}
