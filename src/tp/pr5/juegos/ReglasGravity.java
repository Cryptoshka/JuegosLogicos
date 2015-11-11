package tp.pr5.juegos;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Tablero;
import tp.pr5.logica.TipoJuego;
import tp.pr5.movimientos.Movimiento;

//define reglas para Gravity
public class ReglasGravity extends ReglasFamilia {
	private int ancho = 0, alto = 0;
	private final static int HEIGHT = 10;
	private final static int WIDTH = 10;

	public ReglasGravity(int ti, int tj) {
		this.ancho = ti;
		this.alto = tj;
	}

	public ReglasGravity() {
		this.ancho = WIDTH;
		this.alto = HEIGHT;
	}

	@Override
	public Tablero inicializarTablero() {
		return new Tablero(ancho, alto);
	}

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
	public TipoJuego getTipoJuego() {
		return TipoJuego.GRAVITY;
	}

	@Override
	public Ficha turnoInicio() {
		return Ficha.ROJA;
	}
}
