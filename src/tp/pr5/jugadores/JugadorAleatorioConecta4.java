package tp.pr5.jugadores;

import java.util.Random;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public class JugadorAleatorioConecta4 implements Jugador {

	@Override
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab,
			Ficha color) {
		Random rn = new Random();
		int i = rn.nextInt(tab.getAncho());
		while (!(tab.getFichaPos(1, i + 1) == Ficha.VACIA)) {
			i = rn.nextInt(tab.getAncho());
		}
		return factoria.creaMovimiento(0, i + 1, color);
	}

}
