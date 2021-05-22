package classes;

import java.awt.Color;

public class Prefeitura extends Thread {
	private Vila vila;
	private String acao = "Parado";
	private boolean construido;

	public Prefeitura(Vila vila) {
		this.vila = vila;
		this.construido = true;
	}

	public void run() {
		while (this.construido) {
			try {
				switch (this.acao) {
					case "Evolução de aldeão":
						if (Aldeao.isEvoluido()) {
						} else
							this.evoluirAldeao();
						break;

					case "Evolução de fazenda":
						if (Fazenda.isEvoluido()) {
						} else
							this.evoluirFazenda();
						break;

					case "Evolução de mina de ouro":
						if (Mina.isEvoluido()) {
						} else
							this.evoluirMina();
						break;

					case "Criando aldeão":
						this.criarAldeao();
						break;

					default:
						this.vila.getTela().mostrarPrefeitura(this.acao, Color.cyan);
						break;
				}
			} catch (Exception e) {
				this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
				this.setAcao("Parado");
			}
		}
	}

	public void criarAldeao() {
		try {
			this.vila.setValues(0, 0, -100);
			this.vila.getTela().mostrarPrefeitura(this.acao, Color.cyan);
			Thread.sleep(Constants.HOURS * 20);
			Aldeao a = new Aldeao(this.vila.getAldeoes().size() + 1, this.vila);
			this.vila.adicionarAldeao(a);
			setAcao("Parado");
		} catch (Exception e) {
			this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
			this.setAcao("Parado");
		}
	}

	public synchronized void setAcao(String acao) {
		this.acao = acao;
		this.notifyAll();
	}

	private void evoluirAldeao() {
		try {
			this.vila.setValues(-5000, 0, -5000);
			this.vila.getTela().mostrarPrefeitura(this.acao, Color.cyan);
			Thread.sleep(Constants.HOURS * 100);
			Aldeao.evoluir();
			setAcao("Parado");
		} catch (Exception e) {
			this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
			this.setAcao("Parado");
		}
	}

	private void evoluirFazenda() {
		try {
			this.vila.setValues(-5000, 0, -500);
			this.vila.getTela().mostrarPrefeitura(this.acao, Color.cyan);
			Thread.sleep(Constants.HOURS * 100);
			Fazenda.evoluir();
			setAcao("Parado");
		} catch (Exception e) {
			this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
			this.setAcao("Parado");
		}
	}

	private void evoluirMina() {
		try {
			this.vila.setValues(-1000, 0, -2000);
			this.vila.getTela().mostrarPrefeitura(this.acao, Color.cyan);
			Thread.sleep(Constants.HOURS * 100);
			Mina.evoluir();
			setAcao("Parado");
		} catch (Exception e) {
			this.vila.getTela().mostrarMensagemErro(e.getMessage(), e.getMessage());
			this.setAcao("Parado");
		}
	}
}
