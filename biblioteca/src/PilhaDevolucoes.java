public class PilhaDevolucoes {
    private NoLista<Livro> topo;
    private int tamanho;

    public PilhaDevolucoes() {
        this.topo = null;
        this.tamanho = 0;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    // Empilhar no topo (LIFO)
    public void adicionar(Livro livro) {
        NoLista<Livro> novo = new NoLista<>(livro);
        novo.setProximo(topo);
        topo = novo;
        tamanho++;
    }

    // Desempilhar do topo (LIFO) - reposicao na prateleira
    public Livro remover() {
        if (estaVazia()) {
            return null;
        }
        Livro livro = topo.getDado();
        topo = topo.getProximo();
        tamanho--;
        return livro;
    }

    // Localizar livro na pilha por ISBN
    public Livro buscar(String isbn) {
        NoLista<Livro> atual = topo;
        while (atual != null) {
            if (atual.getDado().getIsbn().equalsIgnoreCase(isbn)) {
                return atual.getDado();
            }
            atual = atual.getProximo();
        }
        return null;
    }

    public void listar() {
        if (estaVazia()) {
            System.out.println("  (Nenhum livro aguardando reposicao na pilha)");
            return;
        }
        NoLista<Livro> atual = topo;
        int pos = 1;
        while (atual != null) {
            String prefixo = (pos == 1) ? "[TOPO - Proximo a repor]" : "[" + pos + "º na pilha]";
            System.out.println("  " + prefixo + " " + atual.getDado().getResumo());
            atual = atual.getProximo();
            pos++;
        }
    }
}
