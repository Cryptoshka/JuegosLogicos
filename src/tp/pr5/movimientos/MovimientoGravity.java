package tp.pr5.movimientos;

import tp.pr5.Main;
import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public class MovimientoGravity extends Movimiento {
	private final static int TOP = 0;
	private final static int RIGHT = 1;
	private final static int DOWN = 2;
	private final static int LEFT = 3;

	public MovimientoGravity(int col, int fila, Ficha colour) {
		this.color = colour;
		this.columna = col;
		this.fila = fila;
	}

	@Override
	public void ejecutaMovimiento(Tablero tab)
			throws InstructionExecutionException {
		calculos(fila, columna, tab);
		if (columna < 1 || columna > tab.getAncho() || fila < 1
				|| fila > tab.getAlto()) {
			throw new InstructionExecutionException("Posicion incorrecta");
		} else if (tab.getFichaPos(fila, columna) != Ficha.VACIA) {
			throw new InstructionExecutionException(Main.LINE_SEPARATOR
					+ "Casilla ocupada");
		} else
			tab.setFichaPos(fila - 1, columna - 1, color);
	}

	// El metodo mas importante del Gravity

	private void calculos(int fila2, int columna2, Tablero tab) {
		int[] dir = new int[4]; // arriba-derecha-abajo-izquierda
		dir[TOP] = fila2 - 1;
		dir[RIGHT] = tab.getAncho() - columna2;
		dir[DOWN] = tab.getAlto() - fila2;
		dir[LEFT] = columna2 - 1;

		// 1 paso: calcula la distancia minima a un borde

		int marker = Math.min(Math.min(dir[TOP], dir[RIGHT]),
				Math.min(dir[DOWN], dir[LEFT]));

		// 2 paso: asigna 1 para la direccion en la cual movemos
		if ((dir[TOP] == dir[DOWN])
				&& (Math.min(dir[RIGHT], dir[LEFT]) > marker)
				&& (Math.min(dir[RIGHT], dir[LEFT]) != dir[TOP])) {
			dir[TOP] = 0;
			dir[DOWN] = 0;
			if (dir[RIGHT] == dir[LEFT]) {
				dir[RIGHT] = 0;
				dir[LEFT] = 0;
			} else if (dir[RIGHT] > dir[LEFT]) {
				dir[RIGHT] = 0;
				dir[LEFT] = 1;
			} else {
				dir[RIGHT] = 1;
				dir[LEFT] = 0;
			}
		} else if ((dir[RIGHT] == dir[LEFT])
				&& (Math.min(dir[TOP], dir[DOWN]) > marker)
				&& (Math.min(dir[TOP], dir[DOWN]) != dir[RIGHT])) {
			dir[RIGHT] = 0;
			dir[LEFT] = 0;
			if (dir[TOP] == dir[DOWN]) {
				dir[TOP] = 0;
				dir[DOWN] = 0;
			} else if (dir[TOP] > dir[DOWN]) {
				dir[TOP] = 0;
				dir[DOWN] = 1;
			} else {
				dir[TOP] = 1;
				dir[DOWN] = 0;
			}
		} else {
			for (int i = 0; i < 4; i++) {
				if (dir[i] == marker)
					dir[i] = 1;
				else
					dir[i] = 0;
			}
		}

		// 3 paso: calcula la posicion de la ficha una vez realizado 1
		// movimiento

		int mX = columna2 + dir[RIGHT] - dir[LEFT];
		int mY = fila2 + dir[DOWN] - dir[TOP];

		// 4 paso: si la posicion en paso 3 es distinta de la posicion actual-
		// mueve la ficha en direccion elegida hasta el borde o casilla ocupada

		if (mX != columna2 || mY != fila2) {
			boolean foundPlace = false;
			while (!foundPlace && (mY > 0) && (mY <= tab.getAlto()) && (mX > 0)
					&& (mX <= tab.getAncho())) {
				if (tab.getFichaPos(mY, mX) == Ficha.VACIA) {
					columna = mX;
					fila = mY;
					mX += dir[RIGHT] - dir[LEFT];
					mY += dir[DOWN] - dir[TOP];
				} else
					foundPlace = true;
			}
		}
	}

	@Override
	public void undo(Tablero tab) {
		tab.setFichaPos(fila - 1, columna - 1, Ficha.VACIA);
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
