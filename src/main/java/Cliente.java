import java.util.ArrayList;

public class Cliente {
	private int idCliente;
	private String cpf;
	private String nome;
 	public Cliente(String caminho, int chave, String valorChave) throws Exception {
		Leitor leitor = new Leitor(caminho, chave, valorChave);
		ArrayList<String> clientes = leitor.conteudo();
		String cliente = clientes.get(0);
		String[] campos = cliente.split(";");
        this.idCliente = Integer.parseInt(campos[0]);
        this.cpf = campos[1];
        this.nome = campos[2];
	}

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", cpf=" + cpf + ", nome=" + nome + "]";
	}

	public int getIdCliente() {
		return idCliente;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}
	

}
