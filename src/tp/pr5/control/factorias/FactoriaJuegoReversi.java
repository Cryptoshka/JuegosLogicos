package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasJuego;
import tp.pr5.juegos.ReglasReversi;
import tp.pr5.jugadores.Jugador;
import tp.pr5.jugadores.JugadorAleatorioReversi;
import tp.pr5.jugadores.JugadorHumanoReversi;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoReversi;

public class FactoriaJuegoReversi implements FactoriaJuego {

	@Override
	public ReglasJuego creaReglas() {
		return new ReglasReversi();
	}

	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color) {
		return new MovimientoReversi(columna, fila, color);
	}

	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioReversi();
	}

	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoReversi(sc);
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.REVERSI;
	}

}
