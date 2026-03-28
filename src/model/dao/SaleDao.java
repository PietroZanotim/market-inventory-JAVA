package model.dao;

import model.dao.impl.ProductDaoJDBC;
import model.entities.Sale;

import java.util.List;

public interface SaleDao {

    public void insert(Sale obj, ProductDao prodDao);
    public List<Sale> findAll();

}
