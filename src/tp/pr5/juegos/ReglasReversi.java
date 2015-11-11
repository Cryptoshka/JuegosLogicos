package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;

public class ReglasReversi extends ReglasJuego {

	private final static int HEIGHT = 8;
	private final static int WIDTH = 8;

	@Override
	public Tablero inicializarTablero() {

		Tablero t = new Tablero(WIDTH, HEIGHT);
		t.setFichaPos(3, 3, Ficha.ROJA);
		t.setFichaPos(4, 4, Ficha.ROJA);
		t.setFichaPos(3, 4, Ficha.NEGRA);
		t.setFichaPos(4, 3, Ficha.NEGRA);
		/*
		 * //tablero de prueba para 2+ jugadas del mismo color Tablero t = new
		 * Tablero(4, 4); t.setFichaPos(1, 1, Ficha.ROJA); t.setFichaPos(2, 2,
		 * Ficha.ROJA); t.setFichaPos(1, 2, Ficha.NEGRA); t.setFichaPos(2, 1,
		 * Ficha.NEGRA);
		 */
		return t;
	}

	@Override
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha f = Ficha.VACIA;
		int[] roNe = new int[2];
		for (int i = 1; i < tab.getAlto() + 1; i++) {
			for (int j = 1; j < tab.getAncho() + 1; j++) {
				if (tab.getFichaPos(i, j) == Ficha.ROJA)
					roNe[0]++;
				else if (tab.getFichaPos(i, j) == Ficha.NEGRA)
					roNe[1]++;
			}
		}
		if (roNe[0] > roNe[1])
			f = Ficha.ROJA;
		else if (roNe[0] < roNe[1])
			f = Ficha.NEGRA;
		return f;
	}

	@Override
	public boolean hayTablasEmpate(Tablero tab, Movimiento mov) {
		boolean no = true;
		for (int i = 0; i < tab.getAncho(); i++) {
			for (int j = 0; j < tab.getAlto(); j++) {
				if (tab.getFichaPos(j + 1, i + 1) == Ficha.VACIA) {
					no = false;
				}
			}
		}
		return no;
	}

	@Override
	public Ficha siguienteTurno(Ficha turno) {
		return turno.opposite();
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.REVERSI;
	}

	@Override
	public Ficha turnoInicio() {
		return Ficha.NEGRA;
	}

}
