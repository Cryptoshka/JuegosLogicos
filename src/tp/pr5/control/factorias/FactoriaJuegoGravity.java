package tp.pr5.control.factorias;

import java.util.Scanner;

import tp.pr5.juegos.ReglasGravity;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.jugadores.JugadorAleatorioGravity;
import tp.pr5.jugadores.JugadorHumanoGravity;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;
import tp.pr5.movimientos.MovimientoGravity;

//define factoria Gravity
public class FactoriaJuegoGravity implements FactoriaJuego {
	private int ti, tj;

	public FactoriaJuegoGravity() {
		ti = tj = 0;
	}

	public FactoriaJuegoGravity(int ti, int tj) {
		this.ti = ti;
		this.tj = tj;
	}

	public ReglasJuego creaReglas() {
		if (ti > 0 && tj > 0)
			return new ReglasGravity(ti, tj);
		return new ReglasGravity();
	}

	@Override
	public Movimiento creaMovimiento(int fila, int columna, Ficha color) {
		return new MovimientoGravity(columna, fila, color);
	}

	@Override
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioGravity();
	}

	@Override
	public Jugador creaJugadorHumano(Scanner sc) {
		return new JugadorHumanoGravity(sc);
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.GRAVITY;
	}

}
