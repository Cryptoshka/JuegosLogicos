package tp.pr5.vistas.consola;

import java.util.Scanner;

import tp.pr5.Main;
import tp.pr5.control.ControlConsola;
import tp.pr5.control.comandos.Comando;
import tp.pr5.control.comandos.Interpreter;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;

//Tiene el metodo "run" para empezar el juego y escribe por pantalla diferentes mensajes en funcion de situacion actual

public class VistaConsola implements Observador {
	private ControlConsola controller;
	private Scanner sc;
	private boolean terminada;
	private boolean ended;
	private Ficha turno;

	// Constructora
	public VistaConsola(ControlConsola controller, Scanner sc) {
		this.controller = controller;
		this.sc = sc;
	}

	@Override
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		if (ganador == Ficha.ROJA)
			System.out.println("Ganan rojas");
		else if (ganador == Ficha.NEGRA)
			System.out.println("Ganan negras");
		else
			System.out.println("Partida terminada en tablas");
		System.exit(0);
	}

	@Override
	public void onMovimientoStart(Ficha turno) {
		if (turno == Ficha.ROJA)
			System.out.println("Juegan rojas");
		else
			System.out.println("Juegan negras");
	}

	@Override
	public void onMovimientoEnd(TableroInmutable tab, Ficha turno) {
		System.out.println(tab.toString());
	}

	@Override
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		System.out.println(tab.toString());
		if (!hayMas) {
			onMovimientoIncorrecto("OJO: no se queda nada en la pila");
		}
	}

	@Override
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		System.out.println(tabIni.toString());
		if (turno == Ficha.ROJA)
			System.out.println("Juegan rojas");
		else
			System.out.println("Juegan negras");
	}

	@Override
	public void onUndoNotPossible() {
		onMovimientoIncorrecto("No se puede deshacer");
	}

	@Override
	public void onCambioTurno(Ficha turno) {
		if (turno == Ficha.ROJA)
			System.out.println("Juegan rojas");
		else
			System.out.println("Juegan negras");
	}

	public void onMovimientoIncorrecto(String explicacion) {
		System.out.println(explicacion);
	}

	// helper
	public void requestHelp() {
		String line = Interpreter.interpreterHelp();
		System.out.println(line + Main.LINE_SEPARATOR);
	}

	@Override
	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		System.out.println(tab.toString());
		if (turno == Ficha.ROJA)
			System.out.println("Juegan rojas");
		else
			System.out.println("Juegan negras");
	}

	// Empieza el juego
	public void run() {
		ended = false;
		// onMovimientoStart(Ficha.ROJA);
		System.out.println("Que quieres hacer?");
		while (!terminada) {
			Comando com = null;
			String line = sc.nextLine();
			if (line.equalsIgnoreCase("AYUDA"))
				requestHelp();
			else {
				com = Interpreter.generateComando(line);
				if (com == null) {
					onMovimientoIncorrecto("Comando no reconocido");
				} else {
					com.configureContext(controller, true, sc);
					controller.communicateTablero(com);
				}
				if (line.equalsIgnoreCase("REINICIAR")) {
					controller.setPlayer(Ficha.ROJA, controller.getFactoria()
							.creaJugadorHumano(sc));
					controller.setPlayer(Ficha.NEGRA, controller.getFactoria()
							.creaJugadorHumano(sc));
				}
				System.out.println("Que quieres hacer?");
			}
		}
		if (!ended) {
			onPartidaTerminada(null, turno);
			terminada = true;
		}
		sc.close();
	}
}
