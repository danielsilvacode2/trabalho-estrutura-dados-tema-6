public class Livro {
    private String isbn;
    private String titulo;
    private String autor;
    private Boolean disponivel;
    private FilaReserva filaReservas;
    private Leitor leitorAtual;

    public Livro(String isbn, String titulo, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
        this.filaReservas = new FilaReserva();
        this.leitorAtual = null;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public FilaReserva getFilaReservas() {
        return filaReservas;
    }

    public Leitor getLeitorAtual() {
        return leitorAtual;
    }

    public void setLeitorAtual(Leitor leitorAtual) {
        this.leitorAtual = leitorAtual;
    }

    public String getResumo() {
        return "ISBN: " + isbn + " | Titulo: " + titulo + " | Autor: " + autor;
    }

    @Override
    public String toString() {
        String status = disponivel ? "DISPONIVEL" : "EMPRESTADO";
        if (!disponivel && leitorAtual != null) {
            status += " (com " + leitorAtual.getNome() + ")";
        }
        int reservas = filaReservas.getTamanho();
        String infoReserva = (reservas > 0) ? " | Reservas na fila: " + reservas : "";
        return "ISBN: " + isbn + " | Titulo: " + titulo + " | Autor: " + autor + " | Situacao: " + status + infoReserva;
    }
}
