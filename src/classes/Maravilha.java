package classes;

public class Maravilha {
	private int tijolos = 0;
	private boolean construido = false;
	private Vila vila;

	public Maravilha(Vila vila) {
		this.vila = vila;
	}

	public int getTijolos() {
		return tijolos;
	}

	public void addTijolos(int tijolos) {
		this.tijolos = this.tijolos + 1;
		this.vila.getTela().mostrarMaravilha(this.tijolos);
	}

	public void setConstruido(boolean v) {
		this.construido = v;
	}

	public boolean isContruido() {
		return this.construido;
	}

}
