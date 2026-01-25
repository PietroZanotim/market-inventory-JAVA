package model.dao.impl;

import model.dao.CategoryDao;
import model.entities.Category;
import model.entities.Product;

import java.sql.Connection;
import java.util.List;

public class CategoryDaoJDBC implements CategoryDao {

    private Connection conn;

    public CategoryDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Category> findALL() {
        return List.of();
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
}
