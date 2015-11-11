package tp.pr5.logica;

public class Tablero implements TableroInmutable {
	private Ficha[][] tablero;
	private int ancho;
	private int alto;

	// constructora
	public Tablero(int width, int height) {
		this.alto = height;
		this.ancho = width;
		tablero = new Ficha[ancho][alto];
		for (int i = 0; i < ancho; i++) {
			for (int j = 0; j < alto; j++) {
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}

	// resetear
	public void reset() {
		for (int i = 0; i < this.ancho; i++) {
			for (int j = 0; j < this.alto; j++) {
				tablero[i][j] = Ficha.VACIA;
			}
		}
	}

	public boolean isLlena() {
		boolean r = true;
		for (int i = 1; i < this.getAlto() + 1; i++) {
			for (int j = 1; j < this.getAncho() + 1; j++) {
				if (this.getFichaPos(i, j) == Ficha.VACIA) {
					r = false;
				}
			}
		}
		return r;
	}

	// poner ficha
	public void setFichaPos(int fila, int col, Ficha color) {
		this.tablero[col][fila] = color;
	}

	// obtener color de ficha
	public Ficha getFichaPos(int fila, int columna) {
		return tablero[columna - 1][fila - 1];
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	// imprimir tablero
	public String toString() {
		String msg = System.getProperty("line.separator");
		// Mostramos linea por linea
		for (int i = 0; i < this.alto; i++) {
			msg += "|";
			for (int j = 0; j < this.ancho; j++) {
				msg += this.tablero[j][i].toString();
			}
			msg += "|" + System.getProperty("line.separator");
		}
		msg += "+";
		for (int j = 1; j < this.ancho + 1; j++) {
			msg += "-";
		}
		msg += "+" + System.getProperty("line.separator");
		msg += " ";
		for (int j = 1; j < this.ancho + 1; j++) {
			msg += j;
		}
		msg += System.getProperty("line.separator");
		return msg;
	}
}