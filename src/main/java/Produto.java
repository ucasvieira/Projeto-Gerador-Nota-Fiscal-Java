import java.util.ArrayList;

public class Produto {
	private int idProduto;
	private String codigo;
	private double preco;
	private int idCategoria; // Adicionado para relacionar com Categoria
	private String nomeProduto; // Apenas para armazenar o nome do produto

	public Produto(String caminho, int chave, String valorChave) throws Exception {
		Leitor leitor = new Leitor(caminho, chave, valorChave);
		ArrayList<String> produtos = leitor.conteudo();
		String produto = produtos.get(0); // Pegando o primeiro valor encontrado no arquivo
		String[] campos = produto.split(";"); // Dividindo os campos
		this.idProduto = Integer.parseInt(campos[0]);
		this.codigo = campos[1];
		this.preco = Double.parseDouble(campos[2]);
		this.idCategoria = Integer.parseInt(campos[3]);
		this.nomeProduto = campos[4];
	}

	public int getIdProduto() {
		return this.idProduto;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public double getPreco() {
		return this.preco;
	}

	public int getIdCategoria() {
		return this.idCategoria;
	}

	public String getNomeProduto() {
		return this.nomeProduto;
	}

	@Override
	public String toString() {
		return "Produto [idProduto=" + idProduto + ", codigo=" + codigo + ", preco=" + preco +
				", idCategoria=" + idCategoria + ", nomeProduto=" + nomeProduto + "]";
	}
}