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

            ps = conn.prepareStatement("SELECT product.*, category.Name as CatName "
                                        + "FROM product "
                                        + "INNER JOIN category "
                                        + "ON product.CategoryId = category.Id");

            rs = ps.executeQuery();

            while(rs.next()) {

                Category cat = new Category(rs.getInt("CategoryId"), rs.getString("CatName"));
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

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("INSERT INTO product "
                                        + "(Name, Price, Quantity, CategoryId "
                                        + "VALUES (?, ?, ?, ?)", ps.RETURN_GENERATED_KEYS
            );

            ps.setString(1, obj.getName());
            ps.setDouble(2, obj.getPrice());
            ps.setInt(3, obj.getQuantity());
            ps.setInt(4, obj.getCategory().getId());

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()) {
                obj.setId(rs.getInt(1));
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("Error!");
            }

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void updateProduct(Product obj) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("UPDATE product "
                                        + "SET Name = ?, Price = ?, Quantity = ?, CategoryId = ?"
                                        + "WHERE Id = ?"
            );

            ps.setString(1, obj.getName());
            ps.setDouble(2, obj.getPrice());
            ps.setInt(3, obj.getQuantity());
            ps.setInt(4, obj.getCategory().getId());
            ps.setInt(5, obj.getId());

            int rows = ps.executeUpdate();

            if(rows>0) {
                System.out.print("Sucess!");
            }
            else {
                throw new DbException("Error!");
            }

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("DELETE FROM product "
                                        + "WHERE Id = ?"
            );

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if(rows>0) {
                System.out.println("Sucess!");
            }
            else {
                throw new DbException("Error! Id not found.");
            }

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Product findById(Integer id) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("SELECT * FROM product, category.Name as CatName "
                                        + "INNER JOIN category "
                                        + "ON category.Id = CategoryId "
                                        + "WHERE Id = ?"
            );

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Category category = new Category(rs.getInt("CategoryId"), rs.getString("CatName"));
                Product findProduct = instantiateProduct(rs, category);
                DB.closeResultSet(rs);

                return findProduct;
            }
            else {
                throw new DbException("Error!");
            }

        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }

    }

    private Product instantiateProduct(ResultSet rs, Category category) throws SQLException {

        return new Product(rs.getInt(1)
                , rs.getString("Name")
                , rs.getDouble("Price")
                ,rs.getInt("Quantity")
                ,category);

    }
}
