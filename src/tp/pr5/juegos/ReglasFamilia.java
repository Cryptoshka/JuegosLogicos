package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public abstract class ReglasFamilia extends ReglasJuego {

	@Override
	public Tablero inicializarTablero() {
		// TODO Auto-generated method stub
		return null;
	}

	private int inline(int f, int aux, int df, int i, Tablero tab) {
		int count = 1;
		Ficha color = tab.getFichaPos(f, aux);
		while ((f + df > 0) && (f + df <= tab.getAlto()) && (aux + i > 0)
				&& (aux + i <= tab.getAncho())
				&& (tab.getFichaPos(f + df, aux + i) == color)) {
			count++;
			f += df;
			aux += i;
		}
		return count;
	}

	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha ficha = Ficha.VACIA;
		int aux = mov.getColumna();
		int pos = mov.getFila();
		while ((pos > 0) && (tab.getFichaPos(pos, aux) != Ficha.VACIA)) {
			pos--;
		}
		// compruebo horizontal, vertical y 2 diagonales
		if (inline(pos + 1, aux, 0, -1, tab) + inline(pos + 1, aux, 0, 1, tab)
				- 1 >= 4) {
			ficha = mov.getColor();
		} else if (inline(pos + 1, aux, 1, 0, tab)
				+ inline(pos + 1, aux, -1, 0, tab) - 1 >= 4) {
			ficha = mov.getColor();
		} else if (inline(pos + 1, aux, -1, -1, tab)
				+ inline(pos + 1, aux, 1, 1, tab) - 1 >= 4) {
			ficha = mov.getColor();
		} else if (inline(pos + 1, aux, 1, -1, tab)
				+ inline(pos + 1, aux, -1, 1, tab) - 1 >= 4) {
			ficha = mov.getColor();
		}
		return ficha;
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
	public abstract Ficha siguienteTurno(Ficha turno);

}
