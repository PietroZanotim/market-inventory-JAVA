package model.dao.impl;

import db.DB;
import model.dao.ProductDao;
import model.dao.SaleDao;
import model.entities.Sale;
import model.entities.SaleItem;
import model.exceptions.DbException;

import java.sql.*;

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

            for(SaleItem saleItem : obj.getItems()) {

                ps = conn.prepareStatement("INSERT INTO sale_item "
                                            + "(SaleId, ProductId, Quantity, UnitPrice, Subtotal) "
                                            + "VALUES (?, ?, ?, ?, ?)"
                );

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
}
