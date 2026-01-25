package model.dao;

import model.entities.Category;
import model.entities.Product;

import java.util.List;

public interface CategoryDao {

    public List<Category> findALL();
    public void insertCategory(Category obj);
    public void updateCategory(Category obj);
    public void deleteById(Integer id);
    public Product findById(Integer id);

}
