package classes;

public class Jogador {
	private int id;
	private String nome;
	private Civilizacao civilizacao;

	public Jogador(String nome, int id, Civilizacao civilizacao) {
		this.nome = nome;
		this.id = id;
		this.civilizacao = civilizacao;
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

	// capaz de acompanhar a situçao de todas as entiidades

}
