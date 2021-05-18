package classes;

import java.util.ArrayList;
import java.awt.Color;

import tela.Principal;

public class Vila {
	private ArrayList<Aldeao> aldeoes = new ArrayList<Aldeao>();
	private ArrayList<Fazenda> fazendas = new ArrayList<Fazenda>();
	private ArrayList<Mina> minas = new ArrayList<Mina>();
	private Prefeitura prefeitura = new Prefeitura(this);
	private Templo templo = new Templo(this);
	private Maravilha maravilha = new Maravilha(this);
	private Principal tela;

	private int comida = 150;
	private int ouro = 100;
	private int oferendaFe = 0;

	public Vila(Principal tela) {
		this.tela = tela;
		this.prefeitura.start();
		this.templo.start();

		// Criação das estruturas e aldeões iniciais
		for (int i = 0; i < 5; i++) {
			Aldeao a = new Aldeao(this.aldeoes.size() + 1, this);
			this.adicionarAldeao(a);
		}

		Fazenda f = new Fazenda(this.fazendas.size() + 1, this);
		this.adicionarFazenda(f);

		Mina m = new Mina(this.minas.size() + 1, this);
		this.adicionarMina(m);

		// Informações iniciais
		this.tela.mostrarComida(this.comida);
		this.tela.mostrarOuro(this.ouro);
		this.tela.mostrarOferendaFe(this.oferendaFe);
		this.tela.mostrarPrefeitura("Parado", Color.cyan);
		this.tela.mostrarTemplo("Parado", Color.MAGENTA);
		this.tela.mostrarMaravilha(0);
	}

	public Aldeao adicionarAldeao(Aldeao a) {
		a.start();
		this.aldeoes.add(a);
		this.tela.adicionarAldeao(String.valueOf(a.pegaId()), a.getAcao().toString());

		return a;
	}

	public void adicionarFazenda(Fazenda f) {
		this.fazendas.add(f);
		this.tela.adicionarFazenda(String.valueOf(f.getId()), String.valueOf(f.getQtdAldeoes()));
		this.tela.mostrarFazenda(f.getId(), String.valueOf(f.getQtdAldeoes()));
	}

	public void adicionarMina(Mina m) {
		this.minas.add(m);
		this.tela.adicionarMinaOuro(String.valueOf(m.getId()), String.valueOf(m.getQtdAldeoes()));
		this.tela.mostrarMinaOuro(m.getId(), String.valueOf(m.getQtdAldeoes()));
	}

	public int getComida() {
		return comida;
	}

	private void setComida(int comida) {
		this.comida = this.comida + comida;
		this.tela.mostrarComida(this.comida);
	}

	public int getOuro() {
		return ouro;
	}

	public Principal getTela() {
		return this.tela;
	}

	private void setOuro(int ouro) {
		this.ouro = this.ouro + ouro;
		this.tela.mostrarOuro(this.ouro);
	}

	public int getOferendaFe() {
		return oferendaFe;
	}

	private void setOferendaFe(int oferendaFe) {
		this.oferendaFe = this.oferendaFe + oferendaFe;
		this.tela.mostrarOferendaFe(this.oferendaFe);
	}

	public ArrayList<Aldeao> getAldeoes() {
		return this.aldeoes;
	}

	public ArrayList<Fazenda> getFazendas() {
		return this.fazendas;
	}

	public ArrayList<Mina> getMinas() {
		return this.minas;
	}

	public Prefeitura getPrefeitura() {
		return this.prefeitura;
	}

	public Templo getTemplo() {
		return this.templo;
	}

	public Maravilha getMaravilha() {
		return this.maravilha;
	}

	public Mina getMinaById(int id) {
		for (Mina mina : this.minas) {
			if (mina.getId() == id) {
				return mina;
			}
		}

		throw new IllegalArgumentException("Not found");
	}

	public Fazenda getFazendaById(int id) {
		for (Fazenda fazenda : this.fazendas) {
			if (fazenda.getId() == id) {
				return fazenda;
			}
		}

		throw new IllegalArgumentException("Not found");
	}

	public void setValues(int ouro, int fe, int comida) {
		if (this.ouro + ouro < 0) {
			throw new IllegalArgumentException("Ouro insuficiente");
		}

		if (this.oferendaFe + oferendaFe < 0) {
			throw new IllegalArgumentException("Fé insuficiente");
		}

		if (this.comida + comida < 0) {
			throw new IllegalArgumentException("Comida insuficiente");
		}

		setOferendaFe(fe);
		setComida(comida);
		setOuro(ouro);
	}

}
