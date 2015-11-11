package tp.pr5.movimientos;

import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoComplica extends Movimiento {
	private Ficha bajada;

	public MovimientoComplica(int i, Ficha turno) {
		columna = i;
		this.color = turno;
	}

	// Si la columna introducida esta fuera del rango 1-anchuraDelTablero, da
	// error

	@Override
	public void ejecutaMovimiento(Tablero tab)
			throws InstructionExecutionException {
		if (columna <= 0 || columna > tab.getAncho()) {
			throw new InstructionExecutionException(
					"Columna incorrecta. Debe estar entre 1 y "
							+ tab.getAncho() + ".");
		}
		int pos = tab.getAlto();
		while ((pos > 0) && (tab.getFichaPos(pos, columna) != Ficha.VACIA)) {
			pos--;
		}
		if (pos > 0) {
			bajada = Ficha.VACIA;
			tab.setFichaPos(pos - 1, columna - 1, color);
		} else {
			bajada = tab.getFichaPos(tab.getAlto(), columna);
			for (int i = tab.getAlto() - 2; i > -1; i--) {
				tab.setFichaPos(i + 1, columna - 1,
						tab.getFichaPos(i + 1, columna));
			}
			tab.setFichaPos(0, columna - 1, color);
		}
	}

	@Override
	public void undo(Tablero tab) {
		int pos = tab.getAlto();
		if (columna > 0 && columna <= tab.getAncho()) {
			if (!bajada.equals(Ficha.VACIA)) {
				for (int i = 1; i < tab.getAlto(); i++) {
					tab.setFichaPos(i - 1, columna - 1,
							tab.getFichaPos(i + 1, columna));
				}
				tab.setFichaPos(tab.getAlto() - 1, columna - 1, bajada);
			} else {
				while ((pos > 0)
						&& (tab.getFichaPos(pos, columna) != Ficha.VACIA)) {
					pos--;
				}
				tab.setFichaPos(pos, columna - 1, Ficha.VACIA);
			}
		}
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
