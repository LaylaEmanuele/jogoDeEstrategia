package servidor;




import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import tela.Principal;

public class ClienteTCP extends Thread {
	private Socket conexao;
	private ObjectOutputStream saida;
	private ClienteTCP clienteRecebe;
	private boolean escutando;
	private Cliente cliente;
	
	public ClienteTCP(Cliente cliente) {
		this.cliente = cliente;
	}

	private ClienteTCP(Socket conexao, Cliente cliente) {
		this.conexao = conexao;
		this.cliente = cliente;
		this.escutando = true;
	}

	public void run() {
		String mensagem;
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(this.conexao.getInputStream());
			while (this.escutando) {
				mensagem = (String) entrada.readObject();
				
				System.out.println("ADICIONAR MSG: "+mensagem);
				this.cliente.adicionarMensagem(mensagem);
			}
			entrada.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean conectar(String host, String nome) {
		try {
			this.conexao = new Socket(host, 12345);
			this.saida = new ObjectOutputStream(this.conexao.getOutputStream());
			this.saida.writeObject(nome);
			this.clienteRecebe = new ClienteTCP(conexao, this.cliente);
			this.clienteRecebe.start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public void desconectar() {
		try {
			this.saida.writeObject("CMD|DESCONECTAR");
			this.clienteRecebe.escutando = false;
			this.saida.close();
			this.conexao.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enviarMensagem(String mensagem) {
		try {
			this.saida.writeObject(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}