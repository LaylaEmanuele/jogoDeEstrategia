package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServidorTCP extends Thread {
	private Socket conexao;
	private List<ObjectOutputStream> saidas;
	
	public ServidorTCP(Socket conexao, List<ObjectOutputStream> saidas) {
		System.out.println("Cliente conectado: "+ conexao.getInetAddress().getHostAddress());
		this.conexao = conexao;
		this.saidas = saidas;
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
			String mensagem = (String) entrada.readObject();
			super.setName(mensagem);
			
			try {
				System.out.println("Chegou--> ["+ mensagem +"]");
				System.out.println("valor do booleano: "+!(mensagem = (String) entrada.readObject()).equals("CMD|DESCONECTAR"));
				
				
				while (!(mensagem = (String) entrada.readObject()).equals("CMD|DESCONECTAR")) {
				
					System.out.println("dentro --> ["+ mensagem +"]");
					synchronized (this.saidas) {
						String msg = super.getName() +"("+ formato.format(new Date()) +"): "+ mensagem;
						System.out.println("msg --> ["+ msg +"]");
						for (ObjectOutputStream saida2 : this.saidas)
							saida2.writeObject(msg);
					}
				}
				System.out.println("fora --> ["+ mensagem +"]");
			} catch (SocketException e) {} // Fechado no clinte sem desconectar
			synchronized (this.saidas) {
				this.saidas.remove(saida);
			}
			saida.close();
			entrada.close();
			System.out.println("Cliente desconectado: "+ conexao.getInetAddress().getHostAddress());
			this.conexao.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
