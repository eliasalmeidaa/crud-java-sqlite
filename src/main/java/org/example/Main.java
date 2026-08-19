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
                    case 4 -> atualizarAluno();
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

        private static void atualizarAluno(){

            var scanner = new Scanner(System.in);
            System.out.println("Digite o ID do aluno para alterar os dados:");
            var idUsuario = scanner.nextLine();
            
            System.out.println("Digite o novo nome:");
            var novoNome = scanner.nextLine();

            System.out.println("Digite seu email: ");
            var novoEmail = scanner.nextLine();

            System.out.println("Digite sua idade: ");
            var novaIdade = scanner.nextInt();

            scanner.nextLine();


            String sql = String.format("UPDATE Alunos 
                                        SET nome = '%s',
                                        email = '%s',
                                        idade = %d
                                        WHERE id = %s; ", novoNome, novoEmail, novaIdade, idadeUsuario);

            try ( var connection = DriverManager.getConnection(connectionString)){

                var statement = connection.createStatement();
                var resultSet = statement.executeUpdate(sql);

                if (resultSet > 0){

                    System.out.printlf("Aluno atualizado com sucesso!!! \n Id: %s, Nome: %s, Email: %s, Idade: %d", id, novoNome, novoEmail, novaIdade);

                }catch(Exception e){

                    System.out.println("Não foi possivel atualizar o Aluno!!");

                }

            }
        }
        

        private static String buscarAluno(){
            var scanner = new Scanner(System.in);

            System.out.println("Digite o seu ID:\n");

            var id = scanner.nextLine();

            String sql = String.format("SELECT id, nome, email, idade 
                                       FROM Alunos
                                       WHERE id = %s ;", id);

            try (var connection = DriverManager.getConnection(connectionString)){

                var statement = connection.createStatement();
                var resultSet = statement.executeQuery(sql);
            

                while(resultSet.next()){

                    var idUsuario = resultSet.getInt("id");
                    var nameUsuario = resultSet.getString("nome");
                    var emailUsuario = resultSet.getString("email");
                    var idadeUsuario = resultSet.getString("idade");

                    System.out.printf("Segue usuario encontrado: Nome: %s, Email: %s, Idade: %s", nameUsuario, emailUsuario, idadeUsuario);

                    return id;
                }
            } catch (Exception e){
                System.out.println("Não Foi possivel buscar aluno!");
            }

            return id;
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
