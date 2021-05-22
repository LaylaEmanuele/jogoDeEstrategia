package classes;

public class Jogador {

	private int id;
	private String nome;
	private Civilizacao civilizacao;
	private String nomeC;
	private String ip;
	

	public Jogador(String nome, int id, Civilizacao civilizacao) {
		this.nome = nome;
		this.id = id;
		this.civilizacao = civilizacao;
	}
	
	public Jogador(String nome, int id, String civilizacao, String ip) {
		this.nome = nome;
		this.id = id;
		this.nomeC = civilizacao;
		this.ip = ip;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Civilizacao getCivilizacao() {
		return civilizacao;
	}

	public int getId() {
		return this.id;
	}

	public String getNomeC() {
		return nomeC;
	}

	public void setNomeC(String nomeC) {
		this.nomeC = nomeC;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	

	// capaz de acompanhar a situçao de todas as entiidades

}
