package classes;

import tela.Principal;

public class Aldeao extends Thread {
	private int id;
	private boolean vivo;
	private int acaoAlvo;
	private String construcao;
	private Vila vila;
	private AcaoAldeao acao;
	private Principal tela;
	private static boolean evoluido = false;

	public Aldeao(int id, Vila vila) {
		this.id = id;
		this.acao = AcaoAldeao.NADA;
		this.vivo = true;
		this.vila = vila;
		this.tela = vila.getTela();
	}

	public void run() {
		while (this.vivo) {
			try {
				switch (this.acao) {
					case CONSTRUIR:
						this.construir(this.construcao);
						break;

					case CULTIVAR:
						if (this.vila.getFazendas().size() > 0) {
							this.cultivar(this.acaoAlvo);
						} else {
							this.setAcao(AcaoAldeao.NADA);
						}
						break;

					case MINERAR:
						if (this.vila.getMinas().size() > 0) {
							this.minerar(this.acaoAlvo);
						} else {
							this.setAcao(AcaoAldeao.NADA);
						}
						break;

					case ORAR:
						this.orar();
						break;

					case SACRIFICAR:
						this.sacrificar();
						break;

					default:
						this.parar();
						break;
				}
			} catch (Exception e) {
				this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
				this.setAcao(AcaoAldeao.NADA);
			}
		}
	}

	public int pegaId() {
		return this.id;
	}

	public boolean isVivo() {
		return this.vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public AcaoAldeao getAcao() {
		return this.acao;
	}

	public synchronized void setAcao(AcaoAldeao acao) {
		if (this.isAlive()) {

			if (this.acao != AcaoAldeao.NADA && acao == AcaoAldeao.NADA) {
				this.parar();
			}

			this.acao = acao;

			this.notifyAll();
		}
	}

	public synchronized void setAcao(AcaoAldeao acao, String[] info) {
		if (this.isAlive()) {
			if (this.acao != AcaoAldeao.NADA && acao == AcaoAldeao.NADA) {
				this.parar();
			}
			this.acaoAlvo = Integer.parseInt(info[0].equals("") ? "0" : info[0]);
			this.construcao = info[1];
			this.acao = acao;

			this.notifyAll();
		}
	}

	public void minerar(int mina) {
		Mina m = this.vila.getMinaById(mina);

		if (!m.isFull() || Utils.getAldeaoById(m.getAldeoes(), this.pegaId()) != null) {
			try {
				m.adicionaAldeao(this);
				this.tela.mostrarAldeao(this.id, this.acao.toString());
				Thread.sleep(Constants.HOURS * 2 / (evoluido ? 1 : 0 + 1));
				Thread.sleep(Constants.HOURS * 3 / (evoluido ? 1 : 0 + 1));
				this.vila.setValues(5, 0, 0);
			} catch (Exception e) {
				this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
				this.setAcao(AcaoAldeao.NADA);
			}
		} else {
			this.setAcao(AcaoAldeao.NADA);
		}
	}

	public void orar() {
		try {
			if (!this.vila.getTemplo().isContruido()) {
				throw new IllegalArgumentException("Templo não construido");
			}
			this.tela.mostrarAldeao(this.id, this.acao.toString());
			Thread.sleep(Constants.HOURS * 5);
			this.vila.setValues(0, 2, 0);
		} catch (Exception e) {
			this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
			this.setAcao(AcaoAldeao.NADA);
		}
	}

	public synchronized void sacrificar() {
		if (!this.vila.getTemplo().isContruido()) {
			throw new IllegalArgumentException("Templo não construido");
		}
		this.tela.mostrarAldeao(this.id, this.acao.toString());
		this.vila.setValues(0, 100, 0);
		this.vivo = false;
		this.notifyAll();
	}

	public void cultivar(int fazenda) {
		Fazenda f = this.vila.getFazendaById(fazenda);
		if (!f.isFull() || Utils.getAldeaoById(f.getAldeoes(), this.pegaId()) != null) {
			try {
				f.adicionaAldeao(this);
				this.tela.mostrarAldeao(this.id, this.acao.toString());
				Thread.sleep(Constants.HOURS * 1 / (isEvoluido() ? 1 : 0 + 1));
				Thread.sleep(Constants.HOURS * 2 / (isEvoluido() ? 1 : 0 + 1));
				this.vila.setValues(0, 0, 10);
			} catch (Exception e) {
				this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
				this.setAcao(AcaoAldeao.NADA);
			}
		} else {
			this.setAcao(AcaoAldeao.NADA);
		}

	}

	public void construir(String construcao) {
		try {
			switch (construcao) {
				case "Fazenda":
					try {
						this.tela.mostrarAldeao(this.id, this.acao.toString());
						this.vila.setValues(-500, 0, -100);
						Thread.sleep(Constants.HOURS * 30);
						Fazenda f = new Fazenda(this.vila.getFazendas().size() + 1, this.vila);
						this.vila.adicionarFazenda(f);
					} catch (Exception e) {

						this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
						this.setAcao(AcaoAldeao.NADA);
					}
					break;

				case "Mina de ouro":
					try {
						this.tela.mostrarAldeao(this.id, this.acao.toString());
						this.vila.setValues(0, 0, -1000);
						Thread.sleep(Constants.HOURS * 40);
						Mina m = new Mina(this.vila.getMinas().size() + 1, this.vila);
						this.vila.adicionarMina(m);
					} catch (Exception e) {

						this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
						this.setAcao(AcaoAldeao.NADA);
					}
					break;

				case "Templo":
					try {
						if (this.vila.getTemplo().isContruido()) {
							throw new IllegalArgumentException("Templo já construido");
						}
						this.tela.mostrarAldeao(this.id, this.acao.toString());
						this.vila.setValues(-2000, 0, -2000);
						Thread.sleep(Constants.HOURS * 100);
						this.vila.getTemplo().setConstruido(true);
						this.tela.habilitarTemplo();
					} catch (Exception e) {

						this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
						this.setAcao(AcaoAldeao.NADA);
					}
					break;

				case "Maravilha":
					try {
						this.tela.mostrarAldeao(this.id, this.acao.toString());
						this.vila.setValues(-1, 0, -1);
						Thread.sleep(Constants.HOURS * 10);
						this.vila.getMaravilha().addTijolos(1);
					} catch (Exception e) {
						this.setAcao(AcaoAldeao.NADA);
						this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
						this.setAcao(AcaoAldeao.NADA);
					}
					break;

				default:
					throw new IllegalArgumentException("Unexpected value: " + construcao);

			}
		} catch (Exception e) {
			this.tela.mostrarMensagemErro(e.getMessage(), e.getMessage());
		}
		if (construcao != "Maravilha") {
			this.setAcao(AcaoAldeao.NADA);
		}
	}

	public synchronized void parar() {
		switch (this.acao) {
			case MINERAR:
				this.vila.getMinaById(this.acaoAlvo).removeAldeao(this);
				break;
			case CULTIVAR:
				this.vila.getFazendaById(this.acaoAlvo).removeAldeao(this);
				break;
			default:
				break;
		}

		this.tela.mostrarAldeao(this.id, this.acao.toString());

		// Não entendemos o motivo do wait parar totalmenta a aplicação, se o senhor
		// puder nos explicar depois de corrigir ficariamos felizes!

		// try {
		// this.wait();
		// } catch (Exception e) {
		// this.tela.mostrarMensagemErro("Error", e.getMessage());
		// }
	}

	public static void evoluir() {
		evoluido = true;
	}

	public static boolean isEvoluido() {
		return evoluido;
	}

}
