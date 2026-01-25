package Program;

import db.DB;
import model.dao.CategoryDao;
import model.dao.DaoFactory;
import model.dao.ProductDao;
import model.dao.impl.CategoryDaoJDBC;
import model.dao.impl.ProductDaoJDBC;
import model.exceptions.InputException;

import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Tentando conectar...");

        Connection conn = DB.getConnection();
        CategoryDao catDao = DaoFactory.categoryConnection();
        ProductDao prodDao = DaoFactory.productConnection();

        System.out.println("Sucesso!");
        System.out.println();

        while(true) {

            System.out.println("=== Market System ===");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Cadastrar Novo Produto");
            System.out.println("3. Realizar Venda (Baixar Estoque)");
            System.out.println("4. Sair");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            try {

                int option = validateOption(sc.nextInt());




            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }


        }

    }

    static Integer validateOption(int option) {

        if(option<1 || option >4) {
            throw new InputException("Invalid option!\n");
        }
        else {
            return option;
        }
    }
}