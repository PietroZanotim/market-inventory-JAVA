package model.dao.impl;

import model.dao.ProductDao;
import model.entities.Product;

import java.util.List;

public class ProductDaoJDBC implements ProductDao {
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
