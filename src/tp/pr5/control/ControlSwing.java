package tp.pr5.control;

import java.util.Hashtable;
import java.util.Scanner;

import tp.pr5.Main;
import tp.pr5.control.comandos.Interpreter;
import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.control.factorias.FactoriaJuegoComplica;
import tp.pr5.control.factorias.FactoriaJuegoConecta4;
import tp.pr5.control.factorias.FactoriaJuegoGravity;
import tp.pr5.control.factorias.FactoriaJuegoReversi;
import tp.pr5.jugadores.Jugador;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.Partida;
import tp.pr5.logica.TipoJuego;
import tp.pr5.logica.TipoTurno;
import tp.pr5.movimientos.Movimiento;

//Controlador para modo Swing 
public class ControlSwing {
	private FactoriaJuego factoria;
	private Hashtable<Ficha, Jugador> jug;
	private Hashtable<Ficha, TipoTurno> jugAut;
	private Partida partida;
	private Scanner sc;

	public ControlSwing(FactoriaJuego f, Partida partida) {
		this.factoria = f;
		this.partida = partida;
		setFactoria(factoria);
	}

	public void setFactoria(FactoriaJuego factoria) {
		this.factoria = factoria;
		jug = new Hashtable<Ficha, Jugador>();
		jug.put(Ficha.ROJA, factoria.creaJugadorHumano(sc));
		jug.put(Ficha.NEGRA, factoria.creaJugadorHumano(sc));

		jugAut = new Hashtable<Ficha, TipoTurno>();
		jugAut.put(Ficha.ROJA, TipoTurno.HUMANO);
		jugAut.put(Ficha.NEGRA, TipoTurno.HUMANO);
	}

	// el reset depende de tipo de juego elegido de la lista en FunctionPanel
	public void reset(TipoJuego t, int filas, int cols) {
		FactoriaJuego f = null;
		if (t == TipoJuego.COMPLICA)
			f = new FactoriaJuegoComplica();
		else if (t == TipoJuego.CONECTA4)
			f = new FactoriaJuegoConecta4();
		else if (t == TipoJuego.REVERSI)
			f = new FactoriaJuegoReversi();
		else
			f = new FactoriaJuegoGravity(filas, cols);
		setFactoria(f);
		partida.reset(f.creaReglas());
	}

	public void undo() {
		partida.deshacer();
	}

	// necesito este metodo para que en Function Panel el modo automatico se
	// arranca automaticamente-
	// en otro caso, si estas jugando con, por ejemplo, ROJAS y quieres cambiar
	// ROJAS en modo automatico-
	// habra que esperar el siguiente turno de rojas
	public Partida getPartida() {
		return this.partida;
	}

	public void poner(int fila, int col) {
		Movimiento m = factoria.creaMovimiento(fila, col, partida.getTurno());
		partida.ejecutaMovimiento(m);
	}

	public void poner() {
		setPlayer(partida.getTurno(), factoria.creaJugadorAleatorio());
		Movimiento m;
		do {
			m = partida.getMovimiento(factoria, getPlayer());
		} while (!m.isValid(m, partida.getTablero()));
		partida.ejecutaMovimiento(m);
		setPlayer(partida.getTurno(), factoria.creaJugadorHumano(sc));
	}

	public void ponerAutomatico() {
		if (!finalizada()) {
			setPlayer(partida.getTurno(), factoria.creaJugadorAleatorio());
			Movimiento m = null;
			do {
				m = partida.getMovAutomatico(factoria, getPlayer());
			} while (!m.isValid(m, partida.getTablero()));
			partida.ejecutaMovimiento(m);
		} else {
			Thread.currentThread().interrupt();
		}
	}

	public void reiniciar() {
		partida.reset();
	}

	public void finalizar() {
		System.exit(1);
	}

	public boolean finalizada() {
		return partida.isTerminada();
	}

	public void addObserver(Observador o) {
		partida.addObserver(o);
	}

	public void removeObservador(Observador o) {
		partida.removeObserver(o);
	}

	public void setPlayer(Ficha aux, Jugador creaJugadorHumano) {
		jug.put(aux, creaJugadorHumano);
	}

	public FactoriaJuego getFactoria() {
		return this.factoria;
	}

	public Jugador getPlayer() {
		return jug.get(partida.getTurno());
	}

	public TipoTurno getJugTipo(Ficha t) {
		return jugAut.get(t);
	}

	public void setJugTipo(Ficha f, TipoTurno t) {
		jugAut.put(f, t);
	}

	public void requestHelp() {
		String line = Interpreter.interpreterHelp();
		System.out.println(line + Main.LINE_SEPARATOR);
	}
}
