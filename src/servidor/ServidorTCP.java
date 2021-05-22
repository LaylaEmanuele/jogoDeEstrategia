package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import classes.Jogador;
import classes.JogadorAux;

public class ServidorTCP extends Thread {
	private Socket conexao;
	private List<ObjectOutputStream> saidas;
	private ArrayList<JogadorAux> jogadores;
	
	public ServidorTCP(Socket conexao, List<ObjectOutputStream> saidas, ArrayList<JogadorAux> jogadores) {
		this.conexao = conexao;
		this.saidas = saidas;
		this.jogadores = jogadores;
	}

	public void run() {
		DateFormat formato = new SimpleDateFormat("HH:mm");
		try {
			//Define a entrada de dados no servidor
			ObjectInputStream entrada = new ObjectInputStream(this.conexao.getInputStream());
			ObjectOutputStream saida = new ObjectOutputStream(this.conexao.getOutputStream());
			synchronized (this.saidas) {
				this.saidas.add(saida);
			}
//			String mensagem = (String) entrada.readObject();
//			super.setName(mensagem);
			
			try {
//				System.out.println("Chegou--> ["+ mensagem +"]");
//				System.out.println("valor do booleano: "+!(mensagem = (String) entrada.readObject()).equals("CMD|DESCONECTAR"));
//				
				String acao;
				while (!(acao = (String) entrada.readObject()).equals("CMD|DESCONECTAR")) {
					switch(acao) {
						case "Enviar mensagem":
							String mensagem = (String) entrada.readObject();
							JogadorAux jogador2 = (JogadorAux)entrada.readObject();
							synchronized (this.saidas) {
								String msg = "("+ formato.format(new Date()) +") "+ jogador2.getNome()+ " "+ jogador2.getNomeC()+ ": "+  mensagem;
								for (ObjectOutputStream saida2 : this.saidas) {
									saida2.writeObject("Adicionar mensagem");
									saida2.writeObject(msg);
								}
							}
							break;
						case "Adicionar jogador":	
							JogadorAux jogador = (JogadorAux) entrada.readObject();
							this.jogadores.add(jogador);

							for (ObjectOutputStream saida2 : this.saidas) {
								saida2.writeObject("Adicionar jogador");
								for (JogadorAux jogadorAux : jogadores) {
									saida2.writeObject(jogadorAux);
								}
								saida2.writeObject(null);
							}
							
							break;
							
						case "manda o ultimo":	
							int pos = this.jogadores.size()-1;
							JogadorAux jogadorJ = this.jogadores.get(pos); 
							saida.writeObject(jogadorJ);
							break;	
							
						case "Liberar tela":	
							for (ObjectOutputStream saida2 : this.saidas) {
								saida2.writeObject("Liberar tela");
							}
							break;	
						case "Lançar ataque":	
							JogadorAux jogadorEu = (JogadorAux) entrada.readObject();
							String inimigo = (String) entrada.readObject();
							String praga = (String) entrada.readObject();
							
							for (ObjectOutputStream saida2 : this.saidas) {
								saida2.writeObject("Lançar ataque");
								saida2.writeObject(jogadorEu);
								saida2.writeObject(inimigo);
								saida2.writeObject(praga);
							}								
							
//							switch(ataque) {
//								case "Nuvem de gafanhotos":
//									if(jogadorEu.getCivilizacao().getVila().getTemplo().getEvolucoes().equals("Proteção contra nuvem de gafanhotos")) { //verificar se tem proteçao 
//										atacar = false;
//									}
//									
//									break;
//								case "Morte dos primogenitos":
//									break;
//								case "Chuva de pedras":
//									break;
//							}
							break;
						case "Atualizar jogador":
							JogadorAux jogadorAtualizado = (JogadorAux) entrada.readObject();
							List<String> protecoes = new ArrayList<>();
							String protecao;
							do {
								protecao = (String) entrada.readObject();
								if(protecao != null) {
									protecoes.add(protecao);
								}
							} while (protecao != null);
							System.out.println("Lista de Jogadores P2: " + protecoes);
							for (ObjectOutputStream saida2 : this.saidas) {
								saida2.writeObject("Atualizar jogador");
								saida2.writeObject(jogadorAtualizado);
								for (String elemento : protecoes) {
									saida2.writeObject(elemento);
								}
								saida2.writeObject(null);
							}
							break;
					}
				}

			} catch (SocketException e) {} // Fechado no clinte sem desconectar
			synchronized (this.saidas) {
				this.saidas.remove(saida);
			}
			JogadorAux jogador = (JogadorAux) entrada.readObject();
			this.jogadores.remove(jogador);
			for (ObjectOutputStream saida2 : this.saidas) {
				saida2.writeObject("Desconectar");
				saida2.writeObject(jogador);

			}

			saida.close();
			entrada.close();
			System.out.println("Cliente desconectado: "+ conexao.getInetAddress().getHostAddress());
			this.conexao.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarAtaque(String acao, String ataque, Jogador inimigo, boolean atacar) {
//		try {
//			saida.writeObject(acao);
//			saida.writeObject(ataque);
//			saida.writeObject(inimigo);
//			saida.writeObject(String.valueOf(atacar));
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
	}

}
