package model.dao;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import model.dao.impl.ProductDaoJDBC;
import model.entities.Sale;

import java.util.List;
import java.util.Scanner;

public interface SaleDao {

    public void insert(Sale obj, ProductDao prodDao);
    public List<Sale> findAll(Integer type, Scanner sc);

}
