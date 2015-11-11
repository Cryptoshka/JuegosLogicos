package tp.pr5.logica;

import java.util.ArrayList;

import tp.pr5.control.comandos.exceptions.InstructionExecutionException;
import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.movimientos.Movimiento;

public class Partida {
	private ReglasJuego reglas;
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private boolean askDes;
	private boolean noMirar;
	private Ficha ganador;
	private Pila pila;
	private int numUndo;
	private ArrayList<Observador> listaOb;

	// constructora
	public Partida(ReglasJuego rules) {
		// generamos un array de observadores
		// en caso Consola- para VistaConsola
		// en caso Swing- para GamePanel y FunctionPanel
		// cualquier cambio => les notificamos con parametros necesarios
		listaOb = new ArrayList<Observador>();
		reset(rules);
	}

	public int getNumUndo() {
		return numUndo;
	}

	// reiniciar la partida
	public void reset(ReglasJuego reglas) {
		this.reglas = reglas;
		this.tablero = reglas.inicializarTablero();
		this.ganador = Ficha.VACIA;
		this.terminada = false;
		this.pila = new Pila();
		this.turno = reglas.turnoInicio();
		for (Observador ob : listaOb) {
			ob.onCambioJuego(tablero, reglas.getTipoJuego(), turno);
		}
	}

	public void reset() {
		this.tablero = reglas.inicializarTablero();
		this.ganador = Ficha.VACIA;
		this.terminada = false;
		this.pila = new Pila();
		this.turno = reglas.turnoInicio();
		for (Observador ob : listaOb) {
			ob.onResetPartida(tablero, turno);
		}
	}

	// deshacer el movimiento
	public boolean deshacer() {
		Movimiento ultimo = pila.pop();
		if (ultimo == null) {
			for (Observador ob : listaOb) {
				ob.onUndoNotPossible();
			}
			return false;
		} else {
			// como mi modo automatico es inteligente- puedo mantener el
			// DESHACER simple
			Ficha c = ultimo.getColor();
			ultimo.undo(tablero);
			turno = c; // se queda con color de ficha deshecha
			for (Observador ob : listaOb) {
				ob.onUndo(tablero, !pila.isVacia(), turno);
				ob.onMovimientoStart(turno);
			}
			return true;
		}
	}

	// ejecutar el movimiento
	public void ejecutaMovimiento(Movimiento mov) {
		if (!terminada) {
			noMirar = false;
			// en juegos de FamiliaConecta4 sePuedeMover() es siempre true
			// asi que este metodo es para filtrar caso de Reversi cuando el
			// jugador no se mueve
			if (mov.sePuedeMover(turno, tablero)) {
				// si, hay al menos 1 posicion correcta
				try {
					mov.ejecutaMovimiento(tablero);
				} catch (ArrayIndexOutOfBoundsException
						| InstructionExecutionException e) {
					noMirar = true;
					for (Observador ob : listaOb) {
						ob.onMovimientoIncorrecto(e.getMessage());
					}
				}
			} else {
				// jugador de color actual no puede mover
				noMirar = true;
				pasaTurno(mov, turno.opposite(), false);
			}
			// se pone a true (y no se ejecuta miraWinner()) si
			// 1) se puede mover pero el mov ha sido incorrecto
			// 2) no se puede mover
			if (!noMirar) {
				miraWinner(mov);
			}
		}
	}

	// metodo para mirar, si hay ganador
	public void miraWinner(Movimiento mov) {
		for (Observador ob : listaOb) {
			ob.onMovimientoStart(turno.opposite());
		}
		pila.push(mov);
		turno = reglas.siguienteTurno(turno);
		pasaTurno(mov, turno, true);
		if (reglas.getTipoJuego() != TipoJuego.REVERSI) {
			ganador = reglas.comprobarGanador(tablero, mov);
			if (ganador != Ficha.VACIA) {
				for (Observador ob : listaOb) {
					ob.onPartidaTerminada(tablero, ganador);
				}
				terminada = true;
				// tablero esta lleno pero no tiene ganador
			} else if (reglas.hayTablasEmpate(tablero, mov)) {
				for (Observador ob : listaOb) {
					ob.onPartidaTerminada(tablero, Ficha.VACIA);
				}
				terminada = true;
			}
		} else {
			// si el tablero esta lleno- se puede buscar ganador
			if (reglas.hayTablasEmpate(tablero, mov)) {
				ganador = reglas.comprobarGanador(tablero, mov);
				if (ganador != Ficha.VACIA) {
					for (Observador ob : listaOb) {
						ob.onPartidaTerminada(tablero, ganador);
					}
				} else {
					for (Observador ob : listaOb) {
						ob.onPartidaTerminada(tablero, Ficha.VACIA);
					}
				}
				terminada = true;
			}
		}
		for (Observador ob : listaOb) {
			ob.onMovimientoEnd(tablero, turno);
		}
	}

	// si hemos llegado aqui- significa que con el turno anterior no se podia
	// mover
	// ahora ya entramos con color opuesto
	private void pasaTurno(Movimiento mov, Ficha turno, boolean anteriorBien) {
		if (!mov.sePuedeMover(turno, tablero)) {
			// el color opuesto tampoco puede mover- hemos acabado, buscamos
			// ganador
			if (anteriorBien == false) {
				ganador = reglas.comprobarGanador(tablero, mov);
				if (ganador != Ficha.VACIA) {
					for (Observador ob : listaOb) {
						ob.onPartidaTerminada(tablero, ganador);
					}
					terminada = true;
				}
			} else {
				// el jugador de color opuesto no puede hacer movimiento-
				// volvemos al anterior
				for (Observador ob : listaOb) {
					ob.onMovimientoIncorrecto("Tengo que pasar el turno");
				}
				this.turno = turno.opposite();
			}
		} else {
			// el color opuesto si que puede mover- pasa a ser el color para
			// construir movimiento
			// this.turno = turno;
		}
	}

	// imprimir tablero
	public String toString() {
		String msg = new String();
		msg = System.getProperty("line.separator");
		return msg += this.tablero.toString();
	}

	// lo necesito para reaccion instantanea de JComboBox<TipoTurno> en
	// FunctionPanel.java
	public Ficha getTurno() {
		return this.turno;
	}

	public Ficha getGanador() {
		return ganador;
	}

	// obtener movimiento. Si es imposible- dar un error
	public Movimiento getMovimiento(FactoriaJuego fct, Jugador jug) {
		try {
			return jug.getMovimiento(fct, tablero, turno);
		} catch (InstructionExecutionException e) {
			for (Observador ob : listaOb) {
				ob.onMovimientoIncorrecto("No se puede realizar dicho movimiento");
			}
			return null;
		}
	}

	public boolean isTerminada() {
		return terminada;
	}

	public boolean isAskDes() {
		return askDes;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void removeObserver(Observador o) {
		// TODO Auto-generated method stub

	}

	public void addObserver(Observador o) {
		listaOb.add(o);
	}

	public Movimiento getMovAutomatico(FactoriaJuego factoria, Jugador player) {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			for (Observador ob : listaOb) {
				ob.onMovimientoIncorrecto(e1.getMessage());
			}
		}
		try {
			return player.getMovimiento(factoria, tablero, turno);
		} catch (InstructionExecutionException e) {
			for (Observador ob : listaOb) {
				ob.onMovimientoIncorrecto("No se puede realizar dicho movimiento");
			}
			return null;
		}
	}
}
