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
    public List<Sale> findAll(Integer type, Scanner sc) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        if(type==0) {

            try {
                ps = conn.prepareStatement(
                        "SELECT s.Id as SaleId, s.Date, s.Total, " +
                                "si.Quantity, si.UnitPrice, p.Name as ProductName " +
                                "FROM sale s " +
                                "INNER JOIN sale_item si ON s.Id = si.SaleId " +
                                "INNER JOIN product p ON si.ProductId = p.Id " +
                                "ORDER BY s.Id" // Ordenar ajuda a manter organizado
                );

                rs = ps.executeQuery();

                // O nosso "caderninho" de anotações para não duplicar vendas
                Map<Integer, Sale> map = new HashMap<>();

                while (rs.next()) {

                    // Qual é a venda dessa linha que passou na esteira?
                    int saleId = rs.getInt("SaleId");

                    // Tenta achar essa venda no nosso Map
                    Sale sale = map.get(saleId);

                    // Se for nulo, é a primeira vez que vemos essa Venda. Vamos instanciar!
                    if (sale == null) {
                        java.sql.Timestamp timestamp = rs.getTimestamp("Date");
                        LocalDateTime saleDate = timestamp.toLocalDateTime();

                        sale = new Sale(saleId, saleDate);
                        sale.setTotal(rs.getDouble("Total")); // Importante puxar o total também

                        // Salva no Map pra quando a próxima linha vier com o mesmo ID
                        map.put(saleId, sale);
                    }

                    // Agora, independente se a Venda acabou de ser criada ou se já existia,
                    // nós criamos o Produto e o Item dessa linha específica:
                    Product product = new Product();
                    product.setName(rs.getString("ProductName"));

                    SaleItem saleItem = new SaleItem(product, rs.getInt("Quantity"), rs.getDouble("UnitPrice"));

                    // Pendura o item na Venda!
                    sale.getItems().add(saleItem); // Note que usei getItems().add() em vez de addItem() para não recalcular o total que já veio do banco
                }

                // No final, o map.values() devolve todas as Vendas montadinhas.
                // A gente só converte para List e retorna.
                return new ArrayList<>(map.values());

            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            } finally {
                DB.closeResultSet(rs); // Lembre-se de fechar o RS
                DB.closeStatement(ps);
            }

        }
        else {

            System.out.println();
            LocalDateTime dateTime;
            String stringDate;
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.print("Text the inital date to filter: ");
            stringDate = sc.nextLine();


            System.out.print("Text the final date to filter: ");


            return new ArrayList<>(map.values());
        }


    }
}
