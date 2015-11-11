package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;

//define reglas para Complica
public class ReglasComplica extends ReglasJuego {

	@Override
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		Ficha r = Ficha.VACIA;
		boolean gB = false;
		boolean gN = false;
		for (int j = 0; j < tab.getAlto(); j++) {
			int cont = 0;
			boolean intr = true;
			while ((cont < 3) && (intr)) {
				if (tab.getFichaPos(j + 1, cont + 1) != tab.getFichaPos(j + 1,
						cont + 2)) {
					intr = false;
				}
				cont++;
			}
			if ((intr) && (tab.getFichaPos(j + 1, cont + 1) == Ficha.ROJA)) {
				gB = true;
			} else if ((intr)
					&& (tab.getFichaPos(j + 1, cont + 1) == Ficha.NEGRA)) {
				gN = true;
			}
		}
		// comprobar vertical
		for (int j = 0; j < tab.getAlto() - 3; j++) {
			for (int i = 0; i < tab.getAncho(); i++) {
				int cont = j;
				boolean intr = true;
				while ((cont < j + 3) && (intr)) {
					if (tab.getFichaPos(cont + 1, i + 1) != tab.getFichaPos(
							cont + 2, i + 1)) {
						intr = false;
					}
					cont++;
				}
				if ((intr) && (tab.getFichaPos(cont + 1, i + 1) == Ficha.ROJA)) {
					gB = true;
				} else if ((intr)
						&& (tab.getFichaPos(cont + 1, i + 1) == Ficha.NEGRA)) {
					gN = true;
				}
			}
		}
		// comprobar diagonal principal
		for (int j = 0; j < tab.getAlto() - 3; j++) {
			int ax = j;
			int cont = 0;
			boolean intr = true;
			while ((cont < 3) && (intr)) {
				if (tab.getFichaPos(ax + 1, cont + 1) != tab.getFichaPos(
						ax + 2, cont + 2)) {
					intr = false;
				}
				cont++;
				ax++;
			}
			if ((intr) && (tab.getFichaPos(ax + 1, cont + 1) == Ficha.ROJA)) {
				gB = true;
			} else if ((intr)
					&& (tab.getFichaPos(ax + 1, cont + 1) == Ficha.NEGRA)) {
				gN = true;
			}
		}
		// comprobar diagonal inversa
		for (int j = 0; j < tab.getAlto() - 3; j++) {
			int ax = j;
			int cont = tab.getAncho() - 1;
			boolean intr = true;
			while ((cont > 0) && (intr)) {
				if (tab.getFichaPos(ax + 1, cont + 1) != tab.getFichaPos(
						ax + 2, cont)) {
					intr = false;
				}
				cont--;
				ax++;
			}
			if ((intr) && (tab.getFichaPos(ax + 1, cont + 1) == Ficha.ROJA)) {
				gB = true;
			} else if ((intr)
					&& (tab.getFichaPos(ax + 1, cont + 1) == Ficha.NEGRA)) {
				gN = true;
			}
		}
		if (gB && !gN)
			r = Ficha.ROJA;
		else if (!gB && gN)
			r = Ficha.NEGRA;
		return r;
	}

	@Override
	public boolean hayTablasEmpate(Tablero tab, Movimiento mov) {
		if (comprobarGanador(tab, mov) == Ficha.VACIA)
			return false;
		else
			return true;
	}

	@Override
	public Ficha siguienteTurno(Ficha turno) {
		return turno.opposite();
	}

	@Override
	public Tablero inicializarTablero() {
		return new Tablero(4, 7);
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.COMPLICA;
	}

	@Override
	public Ficha turnoInicio() {
		return Ficha.ROJA;
	}
}
