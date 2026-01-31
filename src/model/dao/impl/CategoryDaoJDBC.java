package model.dao.impl;

import db.DB;
import model.dao.CategoryDao;
import model.entities.Category;
import model.exceptions.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoJDBC implements CategoryDao {

    private Connection conn;

    public CategoryDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Category> findALL() {

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Category> list = new ArrayList<>();

        try {

            ps = conn.prepareStatement("SELECT * FROM category");

            rs = ps.executeQuery();

            while(rs.next()) {

            list.add(instantiateCategory(rs));

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
    public void insertCategory(Category obj) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("INSERT INTO category "
                    + "(Name) "
                    + "VALUES (?)", java.sql.Statement.RETURN_GENERATED_KEYS
            );

            ps.setString(1, obj.getName());

            int rows = ps.executeUpdate();

            if(rows>0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    obj.setId(rs.getInt(1));
                    System.out.println("Sucess!");
                }
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
    public void updateCategory(Category obj) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("UPDATE category "
                                        + "SET Name = ? "
                                        + "WHERE Id = ?"
            );

            ps.setString(1, obj.getName());
            ps.setInt(2, obj.getId());

            int rows = ps.executeUpdate();

            if(rows>0) {
                System.out.println("Sucess!");
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

            ps = conn.prepareStatement("DELETE FROM category "
                                        + "WHERE Id = ?"
            );

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if(rows>0){
                System.out.println("Sucess!");
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
    public Category findById(Integer id) {

        PreparedStatement ps = null;

        try {

            ps = conn.prepareStatement("SELECT * FROM category "
                                        + "WHERE Id = ?"
            );

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Category findCategory = new Category(rs.getInt(1), rs.getString("Name"));
                DB.closeResultSet(rs);

                return findCategory;
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

    private Category instantiateCategory(ResultSet rs) throws SQLException {

        return new Category(rs.getInt(01), rs.getString("Name"));

    }
}
