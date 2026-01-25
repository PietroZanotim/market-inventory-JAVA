package Program;

import db.DB;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        System.out.println("Tentando conectar...");
        Connection conn = DB.getConnection();
        System.out.println("Sucesso!");


        DB.closeConnection();

//        while(true) {
//
//            System.out.println("=== Market System ===");
//            System.out.println("1. Listar Produtos");
//            System.out.println("2. Cadastrar Novo Produto");
//            System.out.println("3. Realizar Venda (Baixar Estoque)");
//            System.out.println("4. Sair");
//            System.out.println();
//
//            System.out.print("Escolha uma opção: ");
//
//
//        }

    }
}