import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileOutputStream;

//import do itextpdf
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;

public class Leitor {
	private String caminhoArquivo;
	private int indiceCampoChave;
	private String valorCampoChave;

	public Leitor(String caminhoArquivo, int indiceCampoChave, String valorCampoChave) {
		this.caminhoArquivo = caminhoArquivo;
		this.indiceCampoChave = indiceCampoChave;
		this.valorCampoChave = valorCampoChave;
	}


	public ArrayList<String> conteudo() throws Exception {

		File file = new File(this.caminhoArquivo);

		BufferedReader br = new BufferedReader(new FileReader(file));

		String st;
		int contadorLinha = 0;
		ArrayList<String> linhas = new ArrayList<String>();
		while ((st = br.readLine()) != null) {
			contadorLinha = contadorLinha + 1;
			if (contadorLinha == 1) {
				continue;
			}
			String[] campos = st.split(";");
			if (campos[indiceCampoChave].equals(valorCampoChave)){
				linhas.add(st);
			}
		}
		br.close();
		return linhas;
	}



	public static void main(String[] args) throws Exception {

		// Caminhos para os arquivos de simulação.
		String caminhoPedidos = "./src/Arquivos/Pedido.txt";
		String caminhoItens = "./src/Arquivos/Item.txt";
		String caminhoProdutos = "./src/Arquivos/Produto.txt";
		String caminhoCategorias = "./src/Arquivos/Categoria.txt";
		String caminhoClientes = "./src/Arquivos/WDCliente.txt";
		String caminhoVendedores = "./src/Arquivos/Vendedor.txt";

		// Processamento dos pedidos

		String pdfDestino = "Nota_Fiscal_Simulada.pdf";
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(pdfDestino));

		document.open();

		// Fonte para o título e para o conteúdo
		Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
		Font fontConteudo = new Font(Font.FontFamily.HELVETICA, 12);

		document.add(new Paragraph("NOTAS FISCAIS SIMULADAS:\n", fontTitulo));

		// Processamento dos pedidos
		for (int idPedido = 1; idPedido <= 5; idPedido++) {
			// Carregar o Pedido
			Pedido pedido = new Pedido(caminhoPedidos, 0, String.valueOf(idPedido));

			// Obter cliente
			Cliente cliente = pedido.getCliente();

			Vendedor vendedor = pedido.getVendedor();

			// Obter itens do pedido
			Item[] itens = pedido.getItens();

			// Calcular valores totais do pedido e valores de comissão
			double valorTotalPedido = 0;
			double comissaoTotal = 0;

			document.add(new Paragraph("Pedido ID: " + idPedido, fontConteudo));
			document.add(new Paragraph("Cliente: " + cliente.getNome() + " (CPF: " + cliente.getCpf() + ")", fontConteudo));
			document.add(new Paragraph("Vendedor: " + vendedor.getNome(), fontConteudo));
			document.add(new Paragraph("Data: "+pedido.getData()));
			document.add(new Paragraph("Itens do Pedido:", fontConteudo));


			PdfPTable table = new PdfPTable(5);
			table.addCell("Produto");
			table.addCell("Categoria");
			table.addCell("Qtd");
			table.addCell("Valor");
			table.addCell("Comissão");


			for (Item item : itens) {
				Produto produto = item.getProduto();

				// Buscar a categoria do produto
				Categoria categoria = new Categoria(caminhoCategorias, 0, String.valueOf(produto.getIdProduto()));

				// Calcular o preço total e a comissão
				double valorItem = produto.getPreco() * item.getQtVenda();
				double comissaoItem = valorItem * (categoria.getPercentual() / 100);

				// Acumular valores no pedido.
				valorTotalPedido += valorItem;
				comissaoTotal += comissaoItem;

				// Exibir detalhes do item
				table.addCell(produto.getCodigo());
				table.addCell(categoria.getNome());
				table.addCell(String.valueOf(item.getQtVenda()));
				table.addCell(String.format("R$ %.2f", valorItem));
				table.addCell(String.format("R$ %.2f", comissaoItem));
			}
			document.add(table);

			// Exibir resumo do pedido
			document.add(new Paragraph("\nResumo do Pedido:", fontConteudo));
			document.add(new Paragraph(" - Valor Total: R$ " + String.format("%.2f", valorTotalPedido), fontConteudo));
			document.add(new Paragraph(" - Comissão Total do vendedor "+vendedor.getNome()+": R$ " + String.format("%.2f", comissaoTotal), fontConteudo));
			document.add(new Paragraph("---------------------------------------------------", fontConteudo));
		}
		document.close();
		System.out.println("PDF gerado com sucesso em: " + pdfDestino);
	}

}
