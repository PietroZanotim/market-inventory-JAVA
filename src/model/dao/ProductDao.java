package model.dao;

import model.entities.Product;

import java.util.List;

public interface ProductDao {

    public List<Product> findALL();
    public void insertProduct(Product obj);
    public void updateProduct(Product obj);
    public void deleteById(Integer id);
    public Product findById(Integer id);

}
