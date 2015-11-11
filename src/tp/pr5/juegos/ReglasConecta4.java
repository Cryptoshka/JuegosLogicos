package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;

//define reglas para Conecta4
public class ReglasConecta4 extends ReglasFamilia {

	@Override
	public Ficha comprobarGanador(Tablero tab, Movimiento mov) {
		return super.comprobarGanador(tab, mov);
	}

	public Ficha gan(Ficha val) {
		return val;
	}

	@Override
	public boolean hayTablasEmpate(Tablero tab, Movimiento mov) {
		return super.hayTablasEmpate(tab, mov);
	}

	@Override
	public Ficha siguienteTurno(Ficha turno) {
		return turno.opposite();
	}

	@Override
	public Tablero inicializarTablero() {
		return new Tablero(7, 6);
	}

	@Override
	public TipoJuego getTipoJuego() {
		return TipoJuego.CONECTA4;
	}

	@Override
	public Ficha turnoInicio() {
		return Ficha.ROJA;
	}
}
