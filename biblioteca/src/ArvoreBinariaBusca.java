public class ArvoreBinariaBusca {
    private NoArvore raiz;

    public ArvoreBinariaBusca() {
        this.raiz = null;
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    private int comparar(String isbn1, String isbn2) {
        try {
            long n1 = Long.parseLong(isbn1.trim());
            long n2 = Long.parseLong(isbn2.trim());
            return Long.compare(n1, n2);
        } catch (NumberFormatException e) {
            return isbn1.trim().compareToIgnoreCase(isbn2.trim());
        }
    }

    public boolean inserir(Livro livro) {
        if (livro == null || livro.getIsbn() == null) {
            return false;
        }
        if (raiz == null) {
            raiz = new NoArvore(livro);
            return true;
        }
        return inserirRec(raiz, livro);
    }

    private boolean inserirRec(NoArvore atual, Livro livro) {
        int comp = comparar(livro.getIsbn(), atual.getLivro().getIsbn());
        if (comp == 0) {
            return false; // Rejeita ISBN duplicado
        }
        if (comp < 0) {
            if (atual.getEsquerda() == null) {
                atual.setEsquerda(new NoArvore(livro));
                return true;
            }
            return inserirRec(atual.getEsquerda(), livro);
        } else {
            if (atual.getDireita() == null) {
                atual.setDireita(new NoArvore(livro));
                return true;
            }
            return inserirRec(atual.getDireita(), livro);
        }
    }

    public Livro buscar(String isbn) {
        if (isbn == null || raiz == null) {
            return null;
        }
        return buscarRec(raiz, isbn);
    }

    private Livro buscarRec(NoArvore atual, String isbn) {
        if (atual == null) {
            return null;
        }
        int comp = comparar(isbn, atual.getLivro().getIsbn());
        if (comp == 0) {
            return atual.getLivro();
        }
        if (comp < 0) {
            return buscarRec(atual.getEsquerda(), isbn);
        }
        return buscarRec(atual.getDireita(), isbn);
    }

    public boolean remover(String isbn) {
        if (buscar(isbn) == null) {
            return false;
        }
        raiz = removerRec(raiz, isbn);
        return true;
    }

    private NoArvore removerRec(NoArvore atual, String isbn) {
        if (atual == null) {
            return null;
        }

        int comp = comparar(isbn, atual.getLivro().getIsbn());

        if (comp < 0) {
            atual.setEsquerda(removerRec(atual.getEsquerda(), isbn));
        } else if (comp > 0) {
            atual.setDireita(removerRec(atual.getDireita(), isbn));
        } else {
            // Caso 1: No folha (sem filhos)
            if (atual.getEsquerda() == null && atual.getDireita() == null) {
                return null;
            }

            // Caso 2: No com apenas 1 filho
            if (atual.getEsquerda() == null) {
                return atual.getDireita();
            }
            if (atual.getDireita() == null) {
                return atual.getEsquerda();
            }

            // Caso 3: No com 2 filhos (sucessor in-ordem: menor da subarvore direita)
            NoArvore sucessor = obterMenorNo(atual.getDireita());
            atual.setLivro(sucessor.getLivro());
            atual.setDireita(removerRec(atual.getDireita(), sucessor.getLivro().getIsbn()));
        }
        return atual;
    }

    private NoArvore obterMenorNo(NoArvore no) {
        NoArvore atual = no;
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }
        return atual;
    }

    public void emOrdem() {
        if (estaVazia()) {
            System.out.println("  (Acervo vazio)");
            return;
        }
        emOrdemRec(raiz);
    }

    private void emOrdemRec(NoArvore no) {
        if (no != null) {
            emOrdemRec(no.getEsquerda());
            System.out.println("  " + no.getLivro());
            emOrdemRec(no.getDireita());
        }
    }

    public void preOrdem() {
        if (estaVazia()) {
            System.out.println("  (Acervo vazio)");
            return;
        }
        preOrdemRec(raiz);
    }

    private void preOrdemRec(NoArvore no) {
        if (no != null) {
            System.out.println("  " + no.getLivro());
            preOrdemRec(no.getEsquerda());
            preOrdemRec(no.getDireita());
        }
    }

    public void posOrdem() {
        if (estaVazia()) {
            System.out.println("  (Acervo vazio)");
            return;
        }
        posOrdemRec(raiz);
    }

    private void posOrdemRec(NoArvore no) {
        if (no != null) {
            posOrdemRec(no.getEsquerda());
            posOrdemRec(no.getDireita());
            System.out.println("  " + no.getLivro());
        }
    }
}
