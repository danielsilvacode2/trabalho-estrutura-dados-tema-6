public class FilaReserva {
    private NoLista<Leitor> inicio;
    private NoLista<Leitor> fim;
    private int tamanho;

    public FilaReserva() {
        this.inicio = null;
        this.fim = null;
        this.tamanho = 0;
    }

    public boolean estaVazia() {
        return inicio == null;
    }

    public int getTamanho() {
        return tamanho;
    }

    // Enfileirar no fim da fila (FIFO)
    public void adicionar(Leitor leitor) {
        NoLista<Leitor> novo = new NoLista<>(leitor);
        if (estaVazia()) {
            inicio = novo;
            fim = novo;
        } else {
            fim.setProximo(novo);
            fim = novo;
        }
        tamanho++;
    }

    // Desenfileirar do inicio da fila (FIFO)
    public Leitor remover() {
        if (estaVazia()) {
            return null;
        }
        Leitor leitor = inicio.getDado();
        inicio = inicio.getProximo();
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return leitor;
    }

    // Localizar leitor por matricula
    public Leitor buscar(String matricula) {
        NoLista<Leitor> atual = inicio;
        while (atual != null) {
            if (atual.getDado().getMatricula().equalsIgnoreCase(matricula)) {
                return atual.getDado();
            }
            atual = atual.getProximo();
        }
        return null;
    }

    public void listar() {
        if (estaVazia()) {
            System.out.println("  (Fila de reservas vazia)");
            return;
        }
        NoLista<Leitor> atual = inicio;
        int posicao = 1;
        while (atual != null) {
            System.out.println("  " + posicao + "º da fila -> " + atual.getDado());
            atual = atual.getProximo();
            posicao++;
        }
    }
}
