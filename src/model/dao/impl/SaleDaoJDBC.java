package model.dao.impl;

import db.DB;
import model.dao.ProductDao;
import model.dao.SaleDao;
import model.entities.Product;
import model.entities.Sale;
import model.entities.SaleItem;
import model.exceptions.DbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoJDBC implements SaleDao {

    private Connection conn;

    public SaleDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Sale obj, ProductDao prodDao) {

        PreparedStatement ps = null;

        try {

            for(SaleItem saleItem : obj.getItems()) {
                prodDao.updateProduct(saleItem.getProduct());
            }

            ps = conn.prepareStatement("INSERT INTO sale "
                                        + "(Date, Total) "
                                        + "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS
            );

            ps.setTimestamp(1, java.sql.Timestamp.valueOf(obj.getDate()));
            ps.setDouble(2, obj.getTotal());

            int rows = ps.executeUpdate();

            if(rows>0) {
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    obj.setId(rs.getInt(1));
                    System.out.println("Sale registered!");
                }
                DB.closeResultSet(rs);
            }

            ps = conn.prepareStatement("INSERT INTO sale_item "
                    + "(SaleId, ProductId, Quantity, UnitPrice, Subtotal) "
                    + "VALUES (?, ?, ?, ?, ?)"
            );

            for(SaleItem saleItem : obj.getItems()) {

                ps.setInt(1, obj.getId());
                ps.setInt(2, saleItem.getProduct().getId());
                ps.setInt(3, saleItem.getQuantity());
                ps.setDouble(4, saleItem.getUnitPrice());
                ps.setDouble(5, saleItem.getSubTotal());

                rows = ps.executeUpdate();

                if(rows>0){
                    System.out.println("Sucess, SaleItem added.");
                }
                else {
                    throw new SQLException("Can't add SaleItem!");
                }
            }
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Sale> findAll() {

        PreparedStatement ps = null;
        List<Sale> saleList = new ArrayList<>();

        try {

            ps = conn.prepareStatement("SELECT \n" +
                    "    s.Id as SaleId, \n" +
                    "    s.Date, \n" +
                    "    s.Total, \n" +
                    "    si.Quantity, \n" +
                    "    si.UnitPrice, \n" +
                    "    p.Name as ProductName\n" +
                    "FROM sale s\n" +
                    "INNER JOIN sale_item si ON s.Id = si.SaleId\n" +
                    "INNER JOIN product p ON si.ProductId = p.Id;"
            );

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                Product product = new Product(rs.getString("ProductName"));
                SaleItem saleItem = new SaleItem(product, rs.getInt("Quantity"), rs.getDouble("UnitPrice"));
                Sale sale = new Sale();

            }


        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(ps);
        }

        return saleList;
    }

}
