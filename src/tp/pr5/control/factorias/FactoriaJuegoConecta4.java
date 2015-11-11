package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasConecta4;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.jugadores.JugadorAleatorioConecta4;
import tp.pr5.jugadores.JugadorHumanoConecta4;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoConecta4;

//define factoria Conecta4
public class FactoriaJuegoConecta4 implements FactoriaJuego {

	@Override
	public ReglasJuego creaReglas() {
		return new ReglasConecta4();
	}

	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color) {
		return new MovimientoConecta4(columna, fila, color);
	}

	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioConecta4();
	}

	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoConecta4(sc);
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.CONECTA4;
	}
}
