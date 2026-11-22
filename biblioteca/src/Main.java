import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();
        boolean rodando = true;

        while (rodando) {
            exibirMenu();
            int opcao = lerInteiro(scanner, "Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    System.out.println("\n--- CADASTRO DE LIVRO ---");
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Titulo: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    biblioteca.cadastrarLivro(isbn, titulo, autor);
                    break;

                case 2:
                    System.out.println("\n--- CONSULTAR LIVRO POR ISBN ---");
                    System.out.print("Informe o ISBN: ");
                    biblioteca.consultarLivro(scanner.nextLine());
                    break;

                case 3:
                    System.out.println("\n--- REMOVER LIVRO DO ACERVO ---");
                    System.out.print("Informe o ISBN a remover: ");
                    biblioteca.removerLivroAcervo(scanner.nextLine());
                    break;

                case 4:
                    System.out.println("\n--- EMPRESTIMO OU RESERVA ---");
                    System.out.print("Informe o ISBN do livro: ");
                    String isbnEmp = scanner.nextLine();
                    System.out.print("Nome do leitor: ");
                    String nome = scanner.nextLine();
                    System.out.print("Matricula do leitor: ");
                    String mat = scanner.nextLine();
                    biblioteca.emprestarLivro(isbnEmp, nome, mat);
                    break;

                case 5:
                    System.out.println("\n--- DEVOLUCAO DE LIVRO ---");
                    System.out.print("Informe o ISBN do livro a devolver: ");
                    biblioteca.devolverLivro(scanner.nextLine());
                    break;

                case 6:
                    System.out.println("\n--- REPOR PROXIMO LIVRO NA PRATELEIRA ---");
                    biblioteca.reporLivro();
                    break;

                case 7:
                    System.out.println("\n--- REPOR TODOS OS LIVROS NA PRATELEIRA ---");
                    biblioteca.reporTodosLivros();
                    break;

                case 8:
                    System.out.println("\n--- PERCURSOS DO ACERVO (ABB) ---");
                    System.out.println("1 - Em-Ordem (ISBN crescente)");
                    System.out.println("2 - Pre-Ordem (Raiz, Esquerda, Direita)");
                    System.out.println("3 - Pos-Ordem (Esquerda, Direita, Raiz)");
                    int tipoPerc = lerInteiro(scanner, "Opcao de percurso: ");
                    biblioteca.listarAcervo(tipoPerc);
                    break;

                case 9:
                    System.out.println("\n--- CONSULTAR FILA DE RESERVA ---");
                    System.out.print("Informe o ISBN do livro: ");
                    biblioteca.listarFilaReservaLivro(scanner.nextLine());
                    break;

                case 10:
                    biblioteca.listarPilhaDevolucoes();
                    break;

                case 11:
                    biblioteca.carregarDadosIniciais();
                    break;

                case 0:
                    System.out.println("\nEncerrando o Sistema de Biblioteca. Ate logo!");
                    rodando = false;
                    break;

                default:
                    System.out.println("[ERRO] Opcao invalida. Digite um numero de 0 a 11.");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n========================================================");
        System.out.println("           SISTEMA DE GESTAO DE BIBLIOTECA");
        System.out.println("========================================================");
        System.out.println(" [1]  Cadastrar Livro (Inserir na ABB)");
        System.out.println(" [2]  Consultar Livro por ISBN (Buscar na ABB)");
        System.out.println(" [3]  Remover Livro do Acervo (Remover da ABB)");
        System.out.println(" [4]  Emprestar Livro (ou entrar na Fila de Reserva)");
        System.out.println(" [5]  Devolver Livro (Empilha devolucao + atende fila)");
        System.out.println(" [6]  Repor Proximo Livro na Prateleira (Desempilhar)");
        System.out.println(" [7]  Repor Todos os Livros na Prateleira");
        System.out.println(" [8]  Listar Acervo (Em-ordem, Pre-ordem, Pos-ordem)");
        System.out.println(" [9]  Listar Fila de Reserva de um Livro (FIFO)");
        System.out.println(" [10] Listar Historico de Devolucoes do Dia (LIFO)");
        System.out.println(" [11] Carregar Livros de Demonstracao (Carga Rapida)");
        System.out.println(" [0]  Sair do Sistema");
        System.out.println("========================================================");
    }

    private static int lerInteiro(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Por favor, digite um numero inteiro valido.");
            }
        }
    }
}
