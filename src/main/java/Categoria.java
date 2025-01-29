import java.util.ArrayList;

public class Categoria {
    private int idCategoria;
    private String nome;
    private double percentual;



    public Categoria(String caminho, int chave, String valorChave) throws Exception {
        Leitor leitor = new Leitor(caminho, chave, valorChave);
        ArrayList<String> categorias = leitor.conteudo();
        String categoria = categorias.get(0);
        String[] campos = categoria.split(";");
        this.idCategoria = Integer.parseInt(campos[0]);
        this.nome = campos[1];
        this.percentual = Double.parseDouble(campos[2]);
    }


    @Override
    public String toString() {
        return "Categoria [idCategoria=" + idCategoria + ", nome=" + nome + ", percentual=" + percentual + "% ]";
    }

    public int getIdCadegoria() {
        return idCategoria;
    }

    public void setIdCadegoria(int idCadegoria) {
        this.idCategoria = idCadegoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }
}
