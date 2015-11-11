package tp.pr5.logica;

public enum Ficha {
	VACIA, ROJA, NEGRA;

	// metodo para cambiar color
	public Ficha opposite() {
		switch (this) {
		case ROJA:
			return Ficha.NEGRA;
		case NEGRA:
			return Ficha.ROJA;
		default:
			return Ficha.VACIA;
		}
	}

	// metodo para imprimir ficha
	public String toString() {
		String value;
		if (this == ROJA)
			value = "O";
		else if (this == NEGRA)
			value = "X";
		else
			value = " ";
		return value;
	}
}
