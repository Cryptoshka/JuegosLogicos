package tp.pr5.control.comandos;

import java.util.Scanner;

import tp.pr5.control.ControlConsola;

public class RestartComando implements Comando {
	private ControlConsola controlC;
	private boolean isConsola = false;
	@SuppressWarnings("unused")
	private Scanner sc;

	@Override
	public Comando parse(String cad) {
		String[] words = cad.split(" ");
		if (words.length == 1 && words[0].equalsIgnoreCase("REINICIAR")) {
			return new RestartComando();
		} else {
			return null;
		}
	}

	@Override
	public String getHelp() {
		return "REINICIAR";
	}

	@Override
	public void execute() {
		if (isConsola)
			this.controlC.reiniciar();
	}

	public void configureContext(Object controlador, boolean isC, Scanner in) {
		if (isC)
			this.controlC = (ControlConsola) controlador;
		this.isConsola = isC;
		this.sc = in;
	}
}
