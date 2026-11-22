public class Biblioteca {
    private ArvoreBinariaBusca acervo;
    private PilhaDevolucoes pilhaDevolucoes;

    public Biblioteca() {
        this.acervo = new ArvoreBinariaBusca();
        this.pilhaDevolucoes = new PilhaDevolucoes();
    }

    public void cadastrarLivro(String isbn, String titulo, String autor) {
        if (isbn == null || isbn.trim().isEmpty() || titulo == null || titulo.trim().isEmpty()) {
            System.out.println("[ERRO] ISBN e Titulo sao obrigatorios.");
            return;
        }
        Livro novo = new Livro(isbn.trim(), titulo.trim(), autor.trim());
        boolean inserido = acervo.inserir(novo);
        if (inserido) {
            System.out.println("[SUCESSO] Livro cadastrado com sucesso!");
        } else {
            System.out.println("[ERRO] Nao foi possivel cadastrar: ISBN " + isbn + " ja existente no acervo.");
        }
    }

    public Livro consultarLivro(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("[ERRO] Informe um ISBN valido.");
            return null;
        }
        Livro livro = acervo.buscar(isbn.trim());
        if (livro != null) {
            System.out.println("\n--- LIVRO ENCONTRADO ---");
            System.out.println(livro);
            if (!livro.isDisponivel()) {
                System.out.println("Fila de Reservas deste livro:");
                livro.getFilaReservas().listar();
            }
        } else {
            System.out.println("[AVISO] Livro com ISBN " + isbn + " nao encontrado no acervo.");
        }
        return livro;
    }

    public void removerLivroAcervo(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            System.out.println("[ERRO] Informe um ISBN valido.");
            return;
        }
        Livro livro = acervo.buscar(isbn.trim());
        if (livro == null) {
            System.out.println("[ERRO] Livro com ISBN " + isbn + " nao existe no acervo.");
            return;
        }
        if (!livro.isDisponivel()) {
            System.out.println("[BLOQUEADO] Nao e permitido remover um livro que esta atualmente emprestado.");
            return;
        }
        if (!livro.getFilaReservas().estaVazia()) {
            System.out.println("[BLOQUEADO] Nao e permitido remover livro com leitores na fila de reserva.");
            return;
        }

        boolean removido = acervo.remover(isbn.trim());
        if (removido) {
            System.out.println("[SUCESSO] Livro removido do acervo com sucesso!");
        } else {
            System.out.println("[ERRO] Falha ao remover livro do acervo.");
        }
    }

    public void emprestarLivro(String isbn, String nomeLeitor, String matriculaLeitor) {
        Livro livro = acervo.buscar(isbn.trim());
        if (livro == null) {
            System.out.println("[ERRO] Livro com ISBN " + isbn + " nao encontrado no acervo.");
            return;
        }

        if (livro.isDisponivel()) {
            Leitor leitor = new Leitor(nomeLeitor.trim(), matriculaLeitor.trim());
            livro.setDisponivel(false);
            livro.setLeitorAtual(leitor);
            System.out.println("[SUCESSO] Livro \"" + livro.getTitulo() + "\" emprestado com sucesso para " + leitor.getNome() + "!");
        } else {
            System.out.println("[AVISO] O livro \"" + livro.getTitulo() + "\" ja esta emprestado.");
            Leitor existente = livro.getFilaReservas().buscar(matriculaLeitor.trim());
            if (existente != null) {
                System.out.println("[ERRO] O leitor com matricula " + matriculaLeitor + " ja esta na fila de reserva deste livro.");
                return;
            }
            Leitor leitorReserva = new Leitor(nomeLeitor.trim(), matriculaLeitor.trim());
            livro.getFilaReservas().adicionar(leitorReserva);
            System.out.println("[RESERVA REGISTRADA] O leitor foi incluido na fila de reserva na posicao " + livro.getFilaReservas().getTamanho() + ".");
        }
    }

    public void devolverLivro(String isbn) {
        Livro livro = acervo.buscar(isbn.trim());
        if (livro == null) {
            System.out.println("[ERRO] Livro com ISBN " + isbn + " nao encontrado no acervo.");
            return;
        }
        if (livro.isDisponivel()) {
            System.out.println("[AVISO] Este livro ja esta disponivel no acervo (nao consta como emprestado).");
            return;
        }

        // Empilha no historico do dia para reposicao nas prateleiras
        pilhaDevolucoes.adicionar(livro);
        System.out.println("[DEVOLUCAO REGISTRADA] Livro \"" + livro.getTitulo() + "\" devolvido e inserido na pilha de reposicao.");

        // Se houver reservas na fila, o primeiro da fila retira/fica com o livro
        if (!livro.getFilaReservas().estaVazia()) {
            Leitor proximo = livro.getFilaReservas().remover();
            livro.setLeitorAtual(proximo);
            livro.setDisponivel(false);
            System.out.println("[RESERVA ATENDIDA] O livro foi repassado automaticamente para o leitor reservista:");
            System.out.println("  -> " + proximo);
        } else {
            livro.setDisponivel(true);
            livro.setLeitorAtual(null);
            System.out.println("[STATUS] Exemplar liberado e disponivel para novos emprestimos.");
        }
    }

    public void reporLivro() {
        if (pilhaDevolucoes.estaVazia()) {
            System.out.println("[AVISO] Pilha de devolucoes vazia. Nenhum livro aguardando reposicao.");
            return;
        }
        Livro reposto = pilhaDevolucoes.remover();
        System.out.println("[REPOSICAO CONCLUIDA] O seguinte livro foi retirado da pilha e reposto na prateleira:");
        System.out.println("  -> " + reposto.getResumo());
        System.out.println("  Restam " + pilhaDevolucoes.getTamanho() + " livro(s) aguardando reposicao.");
    }

    public void reporTodosLivros() {
        if (pilhaDevolucoes.estaVazia()) {
            System.out.println("[AVISO] Pilha de devolucoes vazia. Nenhum livro aguardando reposicao.");
            return;
        }
        System.out.println("\n--- PROCESSANDO REPOSICAO TOTAL (LIFO) ---");
        int count = 0;
        while (!pilhaDevolucoes.estaVazia()) {
            Livro reposto = pilhaDevolucoes.remover();
            count++;
            System.out.println("  " + count + "º desempilhado e reposto: " + reposto.getResumo());
        }
        System.out.println("[CONCLUIDO] Todos os " + count + " livro(s) foram repostos nas prateleiras!");
    }

    public void listarAcervo(int opcao) {
        if (acervo.estaVazia()) {
            System.out.println("[AVISO] O acervo esta vazio.");
            return;
        }
        switch (opcao) {
            case 1:
                System.out.println("\n=== ACERVO EM ORDEM (ISBN Crescente) ===");
                acervo.emOrdem();
                break;
            case 2:
                System.out.println("\n=== ACERVO EM PRE-ORDEM (Raiz, Esquerda, Direita) ===");
                acervo.preOrdem();
                break;
            case 3:
                System.out.println("\n=== ACERVO EM POS-ORDEM (Esquerda, Direita, Raiz) ===");
                acervo.posOrdem();
                break;
            default:
                System.out.println("[ERRO] Opcao de percurso invalida.");
        }
    }

    public void listarFilaReservaLivro(String isbn) {
        Livro livro = acervo.buscar(isbn.trim());
        if (livro == null) {
            System.out.println("[ERRO] Livro com ISBN " + isbn + " nao encontrado no acervo.");
            return;
        }
        System.out.println("\n=== FILA DE RESERVA DO LIVRO: " + livro.getTitulo() + " (ISBN: " + livro.getIsbn() + ") ===");
        livro.getFilaReservas().listar();
    }

    public void listarPilhaDevolucoes() {
        System.out.println("\n=== HISTORICO DE DEVOLUCOES DO DIA (Pilha de Reposicao - LIFO) ===");
        pilhaDevolucoes.listar();
    }

    public void carregarDadosIniciais() {
        cadastrarLivro("105", "Estruturas de Dados e Algoritmos", "Robert Lafore");
        cadastrarLivro("102", "Codigo Limpo", "Robert C. Martin");
        cadastrarLivro("108", "Algoritmos: Teoria e Pratica", "Thomas Cormen");
        cadastrarLivro("101", "Java: Como Programar", "Paul Deitel");
        cadastrarLivro("104", "O Programador Pragmatico", "Andrew Hunt");
        cadastrarLivro("107", "Padroes de Projeto", "Gang of Four");
        cadastrarLivro("110", "Introducao aos Sistemas Operacionais", "Tanenbaum");
        System.out.println("[DEMO] 7 livros de demonstracao carregados com sucesso no acervo!");
    }
}
