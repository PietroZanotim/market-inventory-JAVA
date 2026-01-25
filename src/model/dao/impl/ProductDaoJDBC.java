package model.dao.impl;

import model.dao.ProductDao;
import model.entities.Product;

import java.sql.Connection;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

    private Connection conn;

    public ProductDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Product> findALL() {
        return List.of();
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
}
