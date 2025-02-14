import java.util.ArrayList;

public class Vendedor {
    private int idVendedor;
    private String cpf;
    private String nome;

    public Vendedor(String caminho, int chave, String valorChave) throws Exception {
        Leitor leitor = new Leitor(caminho, chave, valorChave);
        ArrayList<String> vendedores = leitor.conteudo();
        String vendedor = vendedores.get(0); // Pegando o primeiro valor encontrado no arquivo
        String[] campos = vendedor.split(";"); // Dividindo os campos
        this.idVendedor = Integer.parseInt(campos[0]);
        this.cpf = campos[1];
        this.nome = (campos[2]);
    }

    public int getIdVendedor() {
        return this.idVendedor;
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getNome() { return this.nome; }

    @Override
    public String toString() {
        return "Vendedor [idVendedor=" + idVendedor + ", nome=" + nome + ", cpf=" + cpf + "]";
    }
}