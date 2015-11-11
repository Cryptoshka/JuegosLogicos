package tp.pr5.control.comandos;

import java.util.Scanner;

import tp.pr5.control.ControlConsola;

public class PutComando implements Comando {
	private ControlConsola controlC;
	private boolean isConsola = false;
	@SuppressWarnings("unused")
	private Scanner sc;

	@Override
	public Comando parse(String cad) {
		String[] words = cad.split(" ");
		if (words[0].equalsIgnoreCase("PONER")) {
			return new PutComando();
		} else {
			return null;
		}
	}

	@Override
	public String getHelp() {
		return "PONER";
	}

	@Override
	public void execute() {
		if (isConsola) {
			this.controlC.poner();
		}
	}

	public void configureContext(Object controlador, boolean isC, Scanner in) {
		if (isC)
			this.controlC = (ControlConsola) controlador;
		this.isConsola = isC;
		this.sc = in;
	}
}
