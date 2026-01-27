package model.dao.impl;

import db.DB;
import model.dao.CategoryDao;
import model.entities.Category;
import model.entities.Product;
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

    }

    @Override
    public void updateCategory(Category obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Product findById(Integer id) {
        return null;
    }

    private Category instantiateCategory(ResultSet rs) throws SQLException {

        return new Category(rs.getInt(01), rs.getString("Name"));

    }
}
