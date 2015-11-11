package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;

public abstract class ReglasJuego {
	// crea reglas para distintos tipos de juegos
	public abstract Tablero inicializarTablero();

	public abstract Ficha comprobarGanador(Tablero tab, Movimiento mov);

	public abstract boolean hayTablasEmpate(Tablero tab, Movimiento mov);

	public abstract Ficha siguienteTurno(Ficha turno);

	public abstract Ficha turnoInicio();

	public abstract TipoJuego getTipoJuego();
}
