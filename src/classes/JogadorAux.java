package classes;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JogadorAux implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	transient ObjectOutputStream saida; 
	private String nomeC;
	private String ip;
	private List<String> protecoes;
	
	public JogadorAux(String nome, int id, String civilizacao, String ip) {
		this.nome = nome;
		this.id = id;
		this.nomeC = civilizacao;
		this.ip = ip;
		this.protecoes = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
	
	public List<String> getProtecoes() {
		return protecoes;
	}

	public void adicionarProtecao(String strEvolucao) {
		this.protecoes.add(strEvolucao);
	}

	public void setProtecoes(List<String> protecoes2) {
		for (String protecao : protecoes2) {
			if(!this.protecoes.contains(protecao)) {
				this.protecoes.add(protecao);
			}
		}
	}

	// capaz de acompanhar a situçao de todas as entiidades

}
