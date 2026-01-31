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

public class CategoryCRUD {

    public static void main(String[] args) {

        List<Category> list = new ArrayList<>();

        System.out.println("Testing connection...");

        Connection conn = DB.getConnection();
        CategoryDao catDao = DaoFactory.categoryConnection();

        System.out.println("Sucess!");
        System.out.println();

//        System.out.println("=== findALl category===");
//        list = catDao.findALL();
//        for(Category cat : list) {
//            System.out.println(cat);
//        }
//        System.out.println();

//        System.out.println("=== Insert category ===");
//        Category newCategory = new Category(4, "Groceries");
//        catDao.insertCategory(newCategory);
//        System.out.println();

//        System.out.println("=== Update category ===");
//        Category updateCategory = new Category(4, "Suplements");
//        catDao.updateCategory(updateCategory);
//        System.out.println();

//        System.out.println("=== Delete category ===");
//        catDao.deleteById(4);
//        System.out.println();

//        System.out.println("=== FindById category ===");
//        Category findCategory = catDao.findById(3);
//        System.out.println(findCategory.toString());
//        System.out.println();

        DB.getConnection();
    }

}
