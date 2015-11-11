package tp.pr5.jugadores;

import java.util.Random;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public class JugadorAleatorioReversi implements Jugador {

	@Override
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab,
			Ficha color) {
		boolean correcto = false;
		int i = 1;
		int j = 1;
		while (!correcto) {
			Random rn = new Random();
			i = rn.nextInt(tab.getAlto());
			j = rn.nextInt(tab.getAncho());
			correcto = (tab.getFichaPos(i + 1, j + 1) == Ficha.VACIA);
		}
		return factoria.creaMovimiento(i + 1, j + 1, color);
	}

}
