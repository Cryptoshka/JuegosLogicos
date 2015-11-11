package tp.pr5.movimientos;

import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoConecta4 extends Movimiento {
	public MovimientoConecta4(int i, int j, Ficha turno) {
		columna = i;
		fila = j;
		this.color = turno;
	}

	// Si la columna introducida es fuera del rango 1-anchuraDelTablero- da
	// error

	@Override
	public void ejecutaMovimiento(Tablero tab)
			throws InstructionExecutionException {
		if (columna <= 0 || columna > tab.getAncho()) {
			throw new InstructionExecutionException(
					"Columna incorrecta. Debe estar entre 1 y "
							+ tab.getAncho() + ".");
		}
		if (!(tab.getFichaPos(1, columna) == Ficha.VACIA)) {
			throw new InstructionExecutionException("Columna ocupada");
		}
		int pos = tab.getAlto();
		while ((pos > 0) && (tab.getFichaPos(pos, columna) != Ficha.VACIA)) {
			pos--;
		}
		if (pos > 0) {
			fila = pos;
			tab.setFichaPos(pos - 1, columna - 1, this.color);
		}
	}

	@Override
	public void undo(Tablero tab) {
		int aux = columna;
		int pos = tab.getAlto();
		while ((pos > 0) && (tab.getFichaPos(pos, aux) != Ficha.VACIA)) {
			pos--;
		}
		tab.setFichaPos(pos, aux - 1, Ficha.VACIA);
	}

	@Override
	public boolean sePuedeMover(Ficha jug, Tablero t) {
		return true;
	}

	@Override
	public boolean isValid(Movimiento mr, Tablero t) {
		return true;
	}
}
