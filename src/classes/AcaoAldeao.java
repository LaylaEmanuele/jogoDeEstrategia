package classes;

public enum AcaoAldeao {
	CONSTRUIR("Construindo"), CULTIVAR("Cultivando"), MINERAR("Minerando"), ORAR("Orando"), SACRIFICAR("Sacrificado"),
	NADA("Parado");

	private final String name;

	private AcaoAldeao(String s) {
		name = s;
	}

	public String toString() {
		return this.name;
	}
}
