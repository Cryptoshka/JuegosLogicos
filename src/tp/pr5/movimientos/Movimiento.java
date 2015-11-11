package tp.pr5.movimientos;

import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;

public abstract class Movimiento {
	protected int columna;
	protected int fila;
	protected Ficha color;

	public abstract void ejecutaMovimiento(Tablero tab)
			throws InstructionExecutionException;

	public abstract void undo(Tablero tab);

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public Ficha getColor() {
		return color;
	}

	public abstract boolean sePuedeMover(Ficha jug, Tablero t);

	public abstract boolean isValid(Movimiento m, Tablero t);
}