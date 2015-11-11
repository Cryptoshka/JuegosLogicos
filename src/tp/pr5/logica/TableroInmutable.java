package tp.pr5.logica;

public interface TableroInmutable {
	int getAlto();

	int getAncho();

	Ficha getFichaPos(int fila, int col);

	String toString();
}
