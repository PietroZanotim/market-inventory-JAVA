package model.dao.impl;

import db.DB;
import model.dao.ProductDao;
import model.dao.SaleDao;
import model.entities.Product;
import model.entities.Sale;
import model.entities.SaleItem;
import model.exceptions.DbException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

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
    public List<Sale> findAll(Integer type, LocalDateTime initialDate, LocalDateTime finalDate) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            if(type==0) {

                ps = conn.prepareStatement(
                        "SELECT s.Id as SaleId, s.Date, s.Total, " +
                                "si.Quantity, si.UnitPrice, p.Name as ProductName " +
                                "FROM sale s " +
                                "INNER JOIN sale_item si ON s.Id = si.SaleId " +
                                "INNER JOIN product p ON si.ProductId = p.Id " +
                                "ORDER BY s.Id"
                );

                rs = ps.executeQuery();

                Map<Integer, Sale> map = new HashMap<>();

                while (rs.next()) {

                    int saleId = rs.getInt("SaleId");

                    Sale sale = map.get(saleId);

                    if (sale == null) {
                        java.sql.Timestamp timestamp = rs.getTimestamp("Date");
                        LocalDateTime saleDate = timestamp.toLocalDateTime();

                        sale = new Sale(saleId, saleDate);
                        sale.setTotal(rs.getDouble("Total"));

                        map.put(saleId, sale);
                    }


                    Product product = new Product();
                    product.setName(rs.getString("ProductName"));

                    SaleItem saleItem = new SaleItem(product, rs.getInt("Quantity"), rs.getDouble("UnitPrice"));

                    sale.getItems().add(saleItem);
                }

                return new ArrayList<>(map.values());
            }
            else {

                StringBuilder sql = new StringBuilder(
                        "SELECT s.Id as SaleId, s.Date, s.Total, " +
                                "si.Quantity, si.UnitPrice, p.Name as ProductName " +
                                "FROM sale s " +
                                "INNER JOIN sale_item si ON s.Id = si.SaleId " +
                                "INNER JOIN product p ON si.ProductId = p.Id "
                );

                sql.append("WHERE s.Date >= ? AND s.Date <= ? ");
                sql.append("ORDER BY s.Id");

                ps = conn.prepareStatement(sql.toString());

                ps.setTimestamp(1, java.sql.Timestamp.valueOf(initialDate));
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(finalDate));

                rs = ps.executeQuery();

                Map<Integer, Sale> map = new HashMap<>();

                while (rs.next()) {

                    int saleId = rs.getInt("SaleId");

                    Sale sale = map.get(saleId);

                    if (sale == null) {
                        java.sql.Timestamp timestamp = rs.getTimestamp("Date");
                        LocalDateTime saleDate = timestamp.toLocalDateTime();

                        sale = new Sale(saleId, saleDate);
                        sale.setTotal(rs.getDouble("Total"));

                        map.put(saleId, sale);
                    }


                    Product product = new Product();
                    product.setName(rs.getString("ProductName"));

                    SaleItem saleItem = new SaleItem(product, rs.getInt("Quantity"), rs.getDouble("UnitPrice"));

                    sale.getItems().add(saleItem);
                }

                return new ArrayList<>(map.values());
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
