package servidor;

import tela.Principal;

public class Cliente extends Thread{
	public ClienteTCP clienteTCP;
	public boolean conectado;
	public Object edtNome;
	public Object edtHost;
	private Principal tela;

	public Cliente(Principal tela) {
		super();
		this.tela = tela;
		this.clienteTCP = new ClienteTCP(this);

	}

	//J� adicionado com sucesso
	public void adicionarMensagem(String mensagem) {
		this.tela.adicionarMensagem(mensagem);
	}

//	private void conectarDesconectar() {
//		if (this.conectado) { // desconectar
//			this.clienteTCP.desconectar();
//			this.conectado = false;
//		}
//		else { // conectar
//			if (this.edtNome.getText().length() == 0) {
//				this.adicionarMensagem("Informe o nome");
//				this.edtNome.requestFocus();
//			}
//			else {
//				this.conectado = this.clienteTCP.conectar(this.edtHost.getText(), this.edtNome.getText());
//				if (!this.conectado)
//					this.adicionarMensagem("Erro de conex�o");
//			}
//		}
//		
//	}

//	private void enviarMensagem() {
//		this.clienteTCP.enviarMensagem(this.edtMensagem.getText());
//		this.edtMensagem.setText("");
//		this.edtMensagem.requestFocus();
//	}

}