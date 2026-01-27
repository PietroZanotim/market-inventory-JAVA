# 📦 Market Stock Manager (Sistema de Controle de Estoque)

> Um sistema robusto de gerenciamento de estoque e vendas via console, desenvolvido para demonstrar o domínio sobre JDBC, Transações ACID e o Padrão de Projeto DAO.

---

## 🚀 Sobre o Projeto

Este projeto simula o back-end de um sistema de mercado real. O objetivo principal não é a interface gráfica, mas sim a **integridade dos dados** e a **arquitetura de software**.

Diferente de frameworks que "escondem" a complexidade (como Hibernate/JPA), este projeto utiliza **JDBC Puro (Java Database Connectivity)**. 
Isso demonstra um entendimento profundo de como o Java conversa nativamente com o Banco de Dados Relacional, gerenciando conexões, drivers e queries SQL manualmente.

### ✨ Funcionalidades Principais

* **Gerenciamento de Produtos e Categorias (CRUD Completo):**
    * Cadastro, Leitura, Atualização e Remoção.
    * Integridade referencial (Um produto sempre pertence a uma categoria).
* **Sistema de Vendas com Baixa Automática:**
    * Verificação de estoque em tempo real antes da venda.
    * Atualização atômica da quantidade disponível (`UPDATE`).
* **Buscas Inteligentes:**
    * Listagem de produtos com junção de tabelas (`INNER JOIN` para trazer nomes de categorias).
* **Arquitetura Profissional:**
    * Separação total de responsabilidades (Camada de Aplicação, Modelo e Acesso a Dados).

---

## 🛠️ Tecnologias e Conceitos Aplicados

* **Java 17+**: Linguagem principal.
* **MySQL**: Banco de dados relacional.
* **JDBC**: Manipulação de dados em baixo nível.
* **Padrão DAO (Data Access Object)**: Abstração da camada de persistência.
* **Factory Pattern**: Para instanciar os DAOs sem acoplar o `Main` ao banco.
* **Singleton Pattern**: (Na conexão com o banco).
* **Transações (ACID)**: Uso de `commit` e `rollback` para garantir consistência em operações críticas.

---

## 🗄️ Estrutura do Banco de Dados

O projeto utiliza um banco MySQL normalizado.

