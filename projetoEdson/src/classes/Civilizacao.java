package classes;

import tela.Principal;

public class Civilizacao {
	private String nome;
	private Vila vila;
	private Principal tela;

	public Civilizacao(String nome, Principal tela) {
		this.nome = nome;
		this.tela = tela;
		this.vila = new Vila(this.tela);
	}

	public Vila getVila() {
		return vila;
	}

	public String getNome() {
		return nome;
	}

}