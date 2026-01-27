package model.dao.impl;

import db.DB;
import model.dao.ProductDao;
import model.entities.Category;
import model.entities.Product;
import model.exceptions.DbException;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

    private Connection conn;

    public ProductDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Product> findALL() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> list = new ArrayList<>();

        try {

            ps = conn.prepareStatement("SELECT product.*, category.Name as CatName"
                                        + "FROM product"
                                        + "INNER JOIN category"
                                        + "ON product.CategoryId = category.Id");

            rs = ps.executeQuery();

            while(rs.next()) {

                Category cat = new Category(rs.getInt("DepartmentId"), rs.getString("CatName"));
                list.add(instantiateProduct(rs, cat));

            }

            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public void insertProduct(Product obj) {

    }

    @Override
    public void updateProdutc(Product obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Product findById(Integer id) {
        return null;
    }

    private Product instantiateProduct(ResultSet rs, Category category) throws SQLException {

        return new Product(rs.getInt(1)
                , rs.getString("Name")
                , rs.getDouble("Price")
                ,rs.getInt("Quantity")
                ,category);

    }
}
