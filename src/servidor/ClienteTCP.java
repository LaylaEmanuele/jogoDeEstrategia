package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import classes.Jogador;
import classes.JogadorAux;
import tela.Principal;

public class ClienteTCP extends Thread {
	private Socket conexao;
	private ObjectOutputStream saida;
	private ObjectInputStream entrada;
	private ClienteTCP clienteRecebe;
	private boolean escutando;
	private Cliente cliente;
	private ArrayList <JogadorAux> jogadores;
	private Principal tela;
	private String jogadorHost;
	
	public ClienteTCP(Cliente cliente) {
		this.cliente = cliente;
	}

	private ClienteTCP(Socket conexao, Cliente cliente, ObjectInputStream entrada, ObjectOutputStream saida, Principal tela, JogadorAux jogador) {
		this.conexao = conexao;
		this.cliente = cliente;
		this.escutando = true;
		this.entrada = entrada;
		this.saida = saida;
		this.tela = tela;
		this.jogadores = new ArrayList<JogadorAux>();
		this.jogadorHost = jogador.getNome();
	}

	@SuppressWarnings("unchecked")
	public void run() {
		String mensagem;
		try {

			while (this.escutando) {
				mensagem = (String) entrada.readObject();
				System.out.println("instrucao recebida->" + mensagem);
				if(mensagem.equals("Adicionar mensagem")) {
					mensagem = (String) entrada.readObject();
					this.cliente.adicionarMensagem(mensagem);
				}else if(mensagem.equals("Adicionar jogador")) {
					JogadorAux jogadoraux;
					this.jogadores.clear();
					while((jogadoraux = (JogadorAux)entrada.readObject()) != null) {
						this.jogadores.add(jogadoraux);
					}
					if(this.jogadores != null) {
						int i= 1; 
						this.tela.limpaJogadores();
						for(JogadorAux jogador: jogadores) {
							this.tela.adicionarJogador(jogador.getNome(), jogador.getNomeC(), jogador.getIp(), "aguardando...");
							this.tela.mostrarSituacaoJogador(i, "aguardandinho");
							i++;
						}
					}
				}else if(mensagem.equals("Desconectar")) {
					JogadorAux jogadorJ = (JogadorAux)entrada.readObject();
					this.jogadores.remove(jogadorJ);
					int i= 1; 
					this.tela.limpaJogadores();
					for(JogadorAux jogador: jogadores) {
						this.tela.adicionarJogador(jogador.getNome(), jogador.getNomeC(), jogador.getIp(), "Jogando..."); 
						this.tela.mostrarSituacaoJogador(i, "Jogando");
						i++;
					}
				}else if(mensagem.equals("Liberar tela")) { // liberar tela para os outros jogadores 
					this.tela.getTpJogo().setEnabledAt(1, true);
					this.tela.getTpJogo().setSelectedIndex(1); // começa na tela inicial
					int i= 1; 
					this.tela.limpaJogadores();
					for(JogadorAux jogador: jogadores) {
						this.tela.adicionarJogador(jogador.getNome(), jogador.getNomeC(), jogador.getIp(), "Jogando..."); // atualizando a tela apos iniciar o jogo 
						this.tela.mostrarSituacaoJogador(i, "Jogando");
						if(!jogadorHost.equals(jogador.getNome())) {
							this.tela.adicionarInimigo(jogador.getNome());
						}
						i++;
					}
//					this.tela.iniciar();
				}else if(mensagem.equals("Lançar ataque")) {
					JogadorAux atacante = (JogadorAux) entrada.readObject();
					String nomeInimigo = (String) entrada.readObject();
					String ataque = (String) entrada.readObject();
					System.out.println("------> " + atacante.getProtecoes());
					JogadorAux inimigo = null;
					for (JogadorAux jogadorAux : jogadores) {
						if(jogadorAux.getNome().equals(nomeInimigo)) {
							inimigo = jogadorAux;
							break;
						}
					}
					if(inimigo != null && inimigo.getNome().equals(jogadorHost)) {
						System.out.println(inimigo.getProtecoes());
						boolean atacar = false;
						switch (ataque) {
							case "Nuvem de gafanhotos":
								if(!inimigo.getProtecoes().contains("Protecao contra nuvem de gafanhotos")) {
									// TODO: Implementar penalidade
									atacar = true;
								}
								break;
							case "Morte dos primogenitos":
								if(inimigo.getProtecoes().contains("Protecao contra morte dos primognitos")) {
									// TODO: Implementar penalidade
									atacar = true;
								}
								break;
							case "Chuva de pedras":
								if(inimigo.getProtecoes().contains("Protecao contra chuva de pedras")) {
									// TODO: Implementar penalidade
									atacar = true;
								}
								break;
							default:
								System.out.println("Ataque invalido");
								atacar = false;
								break;
						}
						if(!atacar) {
							tela.mostrarMensagemErro("Ataque", atacante.getNome() + " tentou te atacar com " + ataque + ", mas você possui proteção!");							
						} else {
							tela.mostrarMensagemErro("Ataque", atacante.getNome() + " atacou você com " + ataque);	
						}
					}
				} else if(mensagem.equals("Atualizar jogador")) {
					JogadorAux jogadorAtualizado = (JogadorAux) entrada.readObject();
					List<String> protecoes = new ArrayList<>();
					String protecao;
					do {
						protecao = (String) entrada.readObject();
						if(protecao != null) {
							protecoes.add(protecao);
						}
					} while (protecao != null);
					System.out.println("Jogador atualizado: " + jogadorAtualizado.getNome());
					System.out.println("Protecoes: " + protecoes);
					for (JogadorAux elemento : jogadores) {
						if(elemento.getNome().equals(jogadorAtualizado.getNome())) {
							elemento.setProtecoes(protecoes);
							break;
						}
					}
				}
			}
			entrada.close();
		} catch (IOException | ClassNotFoundException e) {
			
		}
	}

	public boolean conectar(String host, String nome, JogadorAux jogador, Principal tela) {
		this.tela = tela;
		this.jogadorHost = jogador.getNome();
		try {
			this.conexao = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(this.conexao.getOutputStream());
			this.entrada = new ObjectInputStream(this.conexao.getInputStream());
			this.saida.writeObject("Adicionar jogador");
			this.saida.writeObject(jogador); 
			this.clienteRecebe = new ClienteTCP(conexao, this.cliente, this.entrada, this.saida, this.tela, jogador);
			this.clienteRecebe.start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void desconectar() {
		try {
			this.saida.writeObject("CMD|DESCONECTAR");
			this.saida.writeObject(tela.jogador2);
			this.clienteRecebe.escutando = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensagem(String mensagem) {
		try {
			this.saida.writeObject("Enviar mensagem");
			this.saida.writeObject(mensagem);
			this.saida.writeObject(tela.jogador2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void liberarTela() {
		try {
			this.saida.writeObject("Liberar tela");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void lancarAtaque(String praga, String inimigo, JogadorAux jogador) {
		System.out.println("PRAGA: " + praga);
		System.out.println("INIMIGO: " + inimigo);
		System.out.println("JOGADOR: " + jogador);
		try {
			this.saida.writeObject("Lançar ataque");
			this.saida.writeObject(jogador);
			this.saida.writeObject(inimigo);
			this.saida.writeObject(praga);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void atualizarJogador(JogadorAux jogador) {
		try {
			this.saida.writeObject("Atualizar jogador");
			this.saida.writeObject(jogador);
			for (String protecao : jogador.getProtecoes()) {
				this.saida.writeObject(protecao);
			}
			this.saida.writeObject(null);
			System.out.println("Lista de Jogadores P1: " + jogador.getProtecoes());
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}