package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasComplica;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.jugadores.JugadorAleatorioComplica;
import tp.pr5.jugadores.JugadorHumanoComplica;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoComplica;

//define factoria Complica
public class FactoriaJuegoComplica implements FactoriaJuego {

	@Override
	public ReglasJuego creaReglas() {
		return new ReglasComplica();
	}

	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color) {
		return new MovimientoComplica(columna, color);
	}

	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioComplica();
	}

	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoComplica(sc);
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.COMPLICA;
	}
}
