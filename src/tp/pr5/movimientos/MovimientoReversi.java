package tp.pr5.movimientos;

import java.util.ArrayList;

import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoReversi extends Movimiento {
	private ArrayList<MovimientoReversi> flanqueadas;

	public MovimientoReversi(int columna, int fila, Ficha color) {
		// fila y columna son "normales"
		this.color = color;
		this.fila = fila;
		this.columna = columna;
		flanqueadas = new ArrayList<MovimientoReversi>();
	}

	@Override
	public void ejecutaMovimiento(Tablero t)
			throws InstructionExecutionException {
		if (hayCambios(this, t))
			t.setFichaPos(fila - 1, columna - 1, color);
		else if (!hayCambios(this, t))
			throw new InstructionExecutionException("Eliga otra posicion");
	}

	private boolean hayCambios(MovimientoReversi mov, Tablero t) {
		int j = comprueba(mov, 1, 0, true, t);
		j += comprueba(mov, -1, 0, true, t);
		j += comprueba(mov, 0, 1, true, t);
		j += comprueba(mov, 0, -1, true, t);
		j += comprueba(mov, 1, 1, true, t);
		j += comprueba(mov, 1, -1, true, t);
		j += comprueba(mov, -1, 1, true, t);
		j += comprueba(mov, -1, -1, true, t);
		if (j > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean sePuedeMover(Ficha jug, Tablero t) {
		for (int i = 1; i < t.getAlto() + 1; i++) {
			for (int j = 1; j < t.getAncho() + 1; j++) {
				if ((t.getFichaPos(i, j) == Ficha.VACIA)
						&& isValid(new MovimientoReversi(j, i, jug), t)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isValid(Movimiento movimientoReversi, Tablero t) {
		if (comprueba(movimientoReversi, 1, 0, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, -1, 0, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, 0, 1, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, 0, -1, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, 1, 1, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, -1, 1, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, -1, -1, false, t) != 0)
			return true;
		if (comprueba(movimientoReversi, 1, -1, false, t) != 0)
			return true;
		return false;
	}

	// la funcion para comprobar que en esa direccion hay linea valida (1-n
	// fichas opuestas y cerradas por mi ficha)
	private int comprueba(Movimiento movimientoReversi, int incX, int incY,
			boolean b, Tablero t) {
		Ficha opuesta = movimientoReversi.getColor().opposite();
		int x = movimientoReversi.getFila();
		int y = movimientoReversi.getColumna();
		int num_flanq = 0;
		x += incX;
		y += incY;
		while ((x <= t.getAlto()) && (x > 0) && (y <= t.getAncho()) && (y > 0)
				&& (t.getFichaPos(x, y) == opuesta)) {
			x += incX;
			y += incY;
			num_flanq++;
		}
		if ((num_flanq != 0) && (x <= t.getAlto()) && (x > 0)
				&& (y <= t.getAncho()) && (y > 0)
				&& (t.getFichaPos(x, y) == movimientoReversi.getColor())) {
			// b es un flag: la funcion esValid() solo comprueba si se puede
			// mover (en teoria) y no hace cambios reales
			// la funcion hayCambios() se usa para movimientos reales y modifica
			// el tablero
			if (b) {
				for (int j = 1; j <= num_flanq; j++) {
					x -= incX;
					y -= incY;
					t.setFichaPos(x - 1, y - 1, movimientoReversi.getColor());
					flanqueadas.add(new MovimientoReversi(x, y,
							movimientoReversi.getColor()));
				}
			}
			return num_flanq;
		} else {
			return 0;
		}
	}

	@Override
	public void undo(Tablero tab) {
		tab.setFichaPos(fila - 1, columna - 1, Ficha.VACIA);
		for (MovimientoReversi mov : flanqueadas) {
			tab.setFichaPos(mov.columna - 1, mov.fila - 1, mov.color.opposite());
		}
	}

}
