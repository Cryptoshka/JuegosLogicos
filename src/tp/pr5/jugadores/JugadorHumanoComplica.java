package tp.pr5.jugadores;

import java.util.Scanner;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public class JugadorHumanoComplica implements Jugador {
	private Scanner sc;

	public JugadorHumanoComplica(Scanner sc2) {
		this.sc = sc2;
	}

	@Override
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab,
			Ficha color) {
		int col = 0;
		try {
			System.out.print("Introduce la columna: ");
			String columna = sc.nextLine();
			columna = columna.trim();
			col = Integer.parseInt(columna);
		} catch (NumberFormatException ne) {
			System.err.println(ne.getMessage() + " is not a number");
		}
		return factoria.creaMovimiento(0, col, color);
	}
}
