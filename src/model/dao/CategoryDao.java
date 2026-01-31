package model.dao;

import model.entities.Category;

import java.util.List;

public interface CategoryDao {

    public List<Category> findALL();
    public void insertCategory(Category obj);
    public void updateCategory(Category obj);
    public void deleteById(Integer id);
    public Category findById(Integer id);

}
