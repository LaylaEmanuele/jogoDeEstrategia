package classes;

import java.awt.Color;
import java.util.ArrayList;

public class Templo extends Thread {
	private Vila vila;
	private boolean construido = true;
	private String acao = "Parado";
	private ArrayList<String> evolucoes = new ArrayList<String>();

	public void run() {
		while (this.construido) {
			try {
				switch (this.acao) {
					case "Nuvem de gafanhotos":

						try {
							if (this.alreadyEvolved(this.acao)) {
								throw new IllegalArgumentException("Templo já construido");
							}
							this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
							this.vila.setValues(0, -1000, 0);
							Thread.sleep(Constants.HOURS * 50);
							this.evoluir(this.acao);
						} catch (Exception e) {
							this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
							this.setAcao("Parado");
						}
						break;

					case "Morte dos primogênitos":

						try {
							if (this.alreadyEvolved(this.acao)) {
								throw new IllegalArgumentException("Templo já construido");
							}
							this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
							this.vila.setValues(0, -1500, 0);
							Thread.sleep(Constants.HOURS * 100);
							this.evoluir(this.acao);
						} catch (Exception e) {
							this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
							this.setAcao("Parado");
						}
						break;

					case "Chuva de pedras":

						try {
							if (this.alreadyEvolved(this.acao)) {
								throw new IllegalArgumentException("Templo já construido");
							}
							this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
							this.vila.setValues(0, -2000, 0);
							Thread.sleep(Constants.HOURS * 200);
							this.evoluir(this.acao);
						} catch (Exception e) {
							this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
							this.setAcao("Parado");
						}
						break;

					case "Proteção contra nuvem de gafanhotos":

						try {
							if (this.alreadyEvolved(this.acao)) {
								throw new IllegalArgumentException("Templo já construido");
							}
							this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
							this.vila.setValues(0, -5000, 0);
							Thread.sleep(Constants.HOURS * 500);
							this.evoluir(this.acao);
						} catch (Exception e) {
							this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
							this.setAcao("Parado");
						}
						break;

					case "Proteção contra morte dos primogenitos":

						try {
							if (this.alreadyEvolved(this.acao)) {
								throw new IllegalArgumentException("Templo já construido");
							}
							this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
							this.vila.setValues(0, -6000, 0);
							Thread.sleep(Constants.HOURS * 600);
							this.evoluir(this.acao);
						} catch (Exception e) {
							this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
							this.setAcao("Parado");
						}
						break;

					case "Proteção contra chuva de pedras":
						try {
							if (this.alreadyEvolved(this.acao)) {
								throw new IllegalArgumentException("Templo já construido");
							}
							this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
							this.vila.setValues(0, -7000, 0);
							Thread.sleep(Constants.HOURS * 700);
							this.evoluir(this.acao);
						} catch (Exception e) {
							this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
							this.setAcao("Parado");
						}
						break;

					default:
						this.vila.getTela().mostrarTemplo(this.acao, Color.MAGENTA);
						break;
				}
			} catch (Exception e) {
				this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
				this.setAcao("Parado");
			}
		}
	}

	private boolean alreadyEvolved(String evolucao) {
		return this.evolucoes.contains(evolucao);
	}

	public Templo(Vila vila) {
		this.vila = vila;
	}

	public void setConstruido(boolean v) {
		this.construido = v;
	}

	public boolean isContruido() {
		return this.construido;
	}

	public synchronized void setAcao(String acao) {
		this.acao = acao;
		this.notifyAll();
	}

	private void evoluir(String evolucao) {
		this.evolucoes.add(evolucao);
		this.vila.getTela().mostrarAtaques(evolucoes);
		this.setAcao("Parado");
	}

	public ArrayList<String> getEvolucoes() {
		return evolucoes;
	}

	public void setEvolucoes(ArrayList<String> evolucoes) {
		this.evolucoes = evolucoes;
	}
}
