package classes;

import java.util.ArrayList;

public class Mina {
	private int id;
	static private int limite = 5;
	static private boolean evoluido = false;
	private Vila vila;
	private ArrayList<Aldeao> aldeoes = new ArrayList<Aldeao>();

	public Mina(int id, Vila vila) {
		this.id = id;
		this.vila = vila;
	}

	public int getQtdAldeoes() {
		return this.aldeoes.size();
	}

	public ArrayList<Aldeao> getAldeoes() {
		return this.aldeoes;
	}

	public boolean removeAldeao(Aldeao a) {
		if (this.aldeoes.size() <= 0) {
			return false;
		}

		if (Utils.getAldeaoById(this.aldeoes, a.pegaId()) == null) {
			return false;
		}

		boolean r = this.aldeoes.remove(a);
		this.atualizarInfo();
		return r;
	}

	public int getId() {
		return this.id;
	}

	public boolean adicionaAldeao(Aldeao a) {
		if (this.isFull()) {
			return false;
		}

		if (Utils.getAldeaoById(this.aldeoes, a.pegaId()) == null) {
			this.aldeoes.add(a);
			this.atualizarInfo();
		}

		return true;
	}

	public static void evoluir() {
		limite = 10;
		evoluido = true;
	}

	public static boolean isEvoluido() {
		return evoluido;
	}

	private void atualizarInfo() {
		this.vila.getTela().mostrarMinaOuro(this.id, String.valueOf(this.aldeoes.size()));
	}

	public boolean isFull() {
		return this.aldeoes.size() >= limite;
	}
}