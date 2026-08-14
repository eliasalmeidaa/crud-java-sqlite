package org.example;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public  class Main {

    static String connectionString = "jdbc:sqlite:exemplo.db";

    static void main(){
        Scanner scanner = new Scanner(System.in);
        var opcao = 0;

        do{
            exibirMenu();
            System.out.println("Escolha a opção: ");
            opcao = scanner.nextInt();

            switch (opcao){
                case 1 ->  inserirDados();
                case 2 -> consultarTodos();
                case 3 -> buscarAluno();
                default -> System.out.println("Opção Invalida!");
            }

        }while (opcao != 0);

    }

    private static void exibirMenu(){
        System.out.println();
        System.out.println("====================");
        System.out.println("SISTEMA DE ALUNOS");
        System.out.println("====================");
        System.out.println("1- Cadastrar Alunos");
        System.out.println("2-Listar Alunos");
        System.out.println("3-Buscar aluno");
        System.out.println("4-Atualizar Aluno");
        System.out.println("5-Excluir aluno");
        System.out.println("0-Sair");
        System.out.println("====================");
    }

    private static void buscarAluno(){
        var scanner = new Scanner(System.in);

        System.out.println("Digite seu dados abaixo \n");

        System.out.println("Digite o seu nome: ");
        var nome = scanner.nextLine();

        System.out.println("Digite o seu email: ");
        var email = scanner.nextLine();

        String sql = String.format("SELECT nome,email FROM Alunos WHERE nome = '%s' AND email = '%s'; ", nome, email);

        try (var connection = DriverManager.getConnection(connectionString)){

            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                var nome1 = resultSet.getString("nome");
                var email1 = resultSet.getString("email");

                System.out.printf("")
            }
        } catch (Exception e){
            System.out.println("Não Foi possivel buscar aluno!");
        }
    }

    private static void consultarTodos(){
        String sql = """
                   SELECT id, nome, email, idade
                   FROM Alunos;
                """;

        try (var connection = DriverManager.getConnection(connectionString)){

            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                var id = resultSet.getInt("id");
                var nome = resultSet.getString("nome");
                var email = resultSet.getString("email");
                var idade = resultSet.getInt("idade");

                System.out.printf("Dados da tabela: %s, %s, %s, %s \n", id, nome, email, idade);
            }

        } catch (Exception e){
            System.out.println("Não consegui inserir na base!");
        }
    }

    private static void inserirDados(){

        var scanner = new Scanner(System.in);
        System.out.println("Digite o seu nome: ");
        var nome = scanner.nextLine();

        System.out.println("Digite o seu email: ");
        var email = scanner.nextLine();

        System.out.println("Digite a sua idade: ");
        var idade = scanner.nextInt();

        String sql = " INSERT INTO ALUNOS (nome, email, idade)";
        sql+= String.format("VALUES('%s', '%s', '%s');", nome, email, idade);

        try (var connection = DriverManager.getConnection(connectionString)){

            var statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Aluno Cadastrado com sucesso!");

        } catch (Exception e){
            System.out.println("Não consegui inserir na base!");
        }
    }

    private static void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Alunos(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    email TEXT NOT NULL,
                    idade INTEGER NOT NULL 
                );           
                """;
        try(var connection = DriverManager.getConnection(connectionString)){

            var statement = connection.createStatement();
            statement.execute(sql);

        } catch (Exception e) {
            System.out.println("Não consegui criar tabela");
        }
    }
}