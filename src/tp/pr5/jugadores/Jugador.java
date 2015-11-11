package tp.pr5.jugadores;

import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.movimientos.Movimiento;

public interface Jugador {
	// crea distintos tipos de jugadores
	public Movimiento getMovimiento(FactoriaJuego factoria, Tablero tab,
			Ficha color) throws InstructionExecutionException;
}
