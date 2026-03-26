package model.dao;

import model.dao.impl.ProductDaoJDBC;
import model.entities.Sale;

public interface SaleDao {

    public void insert(Sale obj, ProductDao prodDao);

}
