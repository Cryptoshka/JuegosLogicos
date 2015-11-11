package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;

public interface FactoriaJuego {
	// crea factorias para distintos tipos de juegos
	public abstract ReglasJuego creaReglas();

	public abstract Movimiento creaMovimiento(int fila, int columna, Ficha color);

	public abstract Jugador creaJugadorAleatorio();

	public abstract Jugador creaJugadorHumano(Scanner sc);

	public abstract TipoJuego getTipoJuego();
}
