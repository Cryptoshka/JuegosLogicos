package tp.pr5.control;

import java.util.Hashtable;
import java.util.Scanner;

import tp.pr5.control.comandos.Comando;
import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.jugadores.Jugador;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.Partida;
import tp.pr5.movimientos.Movimiento;

//Controlador para modo Consola
public class ControlConsola {
	private FactoriaJuego factoria;
	private Hashtable<Ficha, Jugador> jug;
	private Partida partida;
	private Scanner sc;

	// constructora
	public ControlConsola(FactoriaJuego factoria, Partida partida, Scanner in) {
		this.partida = partida;
		this.sc = in;
		setFactoria(factoria);
	}

	// constructora de factoria
	public void setFactoria(FactoriaJuego factoria) {
		this.factoria = factoria;
		jug = new Hashtable<Ficha, Jugador>();
		jug.put(Ficha.ROJA, factoria.creaJugadorHumano(sc));
		jug.put(Ficha.NEGRA, factoria.creaJugadorHumano(sc));
	}

	// resetear
	public void reset(FactoriaJuego f) {
		setFactoria(f);
		partida.reset(f.creaReglas());
	}

	// deshacer
	public void undo() {
		partida.deshacer();
	}

	// cambio del tipo de jugador
	void cambiarJugador(Ficha color, String tipoJugador) {
		if (tipoJugador.equalsIgnoreCase("humano")) {
			jug.put(color, factoria.creaJugadorHumano(sc));
		} else if (tipoJugador.equalsIgnoreCase("aleatorio")) {
			jug.put(color, factoria.creaJugadorAleatorio());
		}
	}

	// poner la ficha
	public void poner() {
		Movimiento m;
		m = partida.getMovimiento(factoria, getPlayer());
		partida.ejecutaMovimiento(m);
	}

	public void reiniciar() {
		partida.reset();
	}

	public void finalizar() {
		System.exit(1);
	}

	boolean finalizada() {
		return partida.isTerminada();
	}

	public void addObserver(Observador o) {
		partida.addObserver(o);
	}

	public void communicateTablero(Comando c) {
		c.execute();
	}

	public void removeObservador(Observador o) {
		partida.removeObserver(o);
	}

	// asigna el tipo de jugador al color
	public void setPlayer(Ficha aux, Jugador creaJugador) {
		jug.put(aux, creaJugador);
	}

	public FactoriaJuego getFactoria() {
		return this.factoria;
	}

	public Jugador getPlayer() {
		return jug.get(partida.getTurno());
	}

	public Partida getPartida() {
		return this.partida;
	}
}
