import java.util.ArrayList;
import java.util.Arrays;

public class Pedido {
	private int idPedido;
	private Cliente cliente;
	private double valor;
	private Item[] itens;

	public Pedido(String caminho, int chave, String valorChave) throws Exception {
		Leitor leitor = new Leitor(caminho, chave, valorChave);

		// Buscar conteúdo do arquivo de pedidos
		ArrayList<String> pedidos;
		try {
			pedidos = leitor.conteudo();
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao ler o arquivo: " + caminho + ". Verifique se o arquivo existe e está acessível.", e);
		}

		// Validação: Verificar se há dados no arquivo
		if (pedidos.isEmpty()) {
			throw new IllegalArgumentException("Nenhum pedido encontrado no arquivo: " + caminho + ". Certifique-se de que o ID do pedido está correto e os dados estão presentes.");
		}

		String pedido = pedidos.get(0);
		String[] campos = pedido.split(";");

		// Validação: Garantir que os campos têm pelo menos os 4 campos esperados
		if (campos.length < 4) {
			throw new IllegalArgumentException("Formato inválido no pedido: " + pedido);
		}

		this.idPedido = Integer.parseInt(campos[0]); // Campo 0: ID do Pedido
		String idCliente = campos[1];               // Campo 1: ID do Cliente
		this.valor = Double.parseDouble(campos[3]); // Campo 3: Valor do Pedido

		// Buscar informações do cliente
		try {
			this.cliente = new Cliente("./src/Arquivos/Cliente.txt", 0, idCliente);
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao buscar cliente com ID: " + idCliente + ". Verifique se os dados do cliente estão corretos.", e);
		}

		// Capturar itens do pedido
		leitor = new Leitor("./src/Arquivos/Item.txt", 1, this.idPedido + "");
		ArrayList<String> itens;
		try {
			itens = leitor.conteudo();
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao buscar itens para o pedido ID: " + this.idPedido + ". Verifique o arquivo de itens.", e);
		}

		int qtItens = itens.size();
		this.itens = new Item[qtItens];

		int indice = 0;

		// Processar os itens
		for (String item : itens) {
			campos = item.split(";");

			// Validação para cada item
			if (campos.length < 4) {
				throw new IllegalArgumentException("Formato inválido no item: " + item);
			}

			int idItem = Integer.parseInt(campos[0]);          // Campo 0: ID do Item
			int idPedido = Integer.parseInt(campos[1]);        // Campo 1: ID do Pedido
			String idProduto = campos[2];                     // Campo 2: ID do Produto
			Produto produto;
			try {
				produto = new Produto("./src/Arquivos/Produto.txt", 0, idProduto);
			} catch (Exception e) {
				throw new IllegalArgumentException("Erro ao buscar dados do produto com ID: " + idProduto + ".", e);
			}
			int qtVenda = Integer.parseInt(campos[3]);         // Campo 3: Quantidade vendida
			this.itens[indice] = new Item(idItem, idPedido, produto, qtVenda);
			indice++;
		}

	}

	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", cliente=" + cliente + ", valor=" + valor + ", itens="
				+ Arrays.toString(itens) + "]";
	}

	public Item[] getItens() {
		return this.itens;
	}

	public Cliente getCliente() {
		return this.cliente;
	}
}