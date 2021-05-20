package servidor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LevantarServidor extends Thread {
	@Override
	public void run() {
		System.out.println("Servidor levantado...");
		List<ObjectOutputStream> saidas = new ArrayList<ObjectOutputStream>();
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(12345);
			System.out.println("A porta 12345 foi aberta.");
			System.out.println("Servidor esperando receber mensagens dos clientes...");
			while (true) {
				//Esperar os clientes
				Socket conexao = serverSocket.accept();
				//Start a Thread do SERVIDOR vai no run
				(new ServidorTCP(conexao, saidas)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

