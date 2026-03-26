package model.dao;

import db.DB;
import model.dao.impl.CategoryDaoJDBC;
import model.dao.impl.ProductDaoJDBC;
import model.dao.impl.SaleDaoJDBC;

import java.sql.Connection;

public class DaoFactory {

    public static CategoryDao categoryConnection() {
        return new CategoryDaoJDBC(DB.getConnection());
    }

    public static ProductDao productConnection() {
        return new ProductDaoJDBC(DB.getConnection());
    }

    public static SaleDao saleConnection() {
        return new SaleDaoJDBC(DB.getConnection());
    }

}
