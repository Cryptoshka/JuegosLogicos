package tp.pr5.control.comandos;

import java.util.Scanner;

import tp.pr5.control.ControlConsola;
import tp.pr5.control.factorias.FactoriaJuegoComplica;
import tp.pr5.control.factorias.FactoriaJuegoConecta4;
import tp.pr5.control.factorias.FactoriaJuegoGravity;
import tp.pr5.control.factorias.FactoriaJuegoReversi;

public class PlayComando implements Comando {
	private ControlConsola controlC;
	private boolean isConsola = false;
	@SuppressWarnings("unused")
	private Scanner sc;
	private String[] words;

	public PlayComando(String[] palabras) {
		this.words = palabras;
	}

	public Comando parse(String cad) {
		String[] aux = cad.split(" ");
		if (aux[0].equalsIgnoreCase("JUGAR")
				&& (aux.length == 2 || aux.length == 4)) {
			return new PlayComando(aux);
		} else {
			return null;
		}
	}

	@Override
	public String getHelp() {
		return "JUGAR [c4|co|gr|rv] [tamX tamY]";
	}

	public void configureContext(Object controlador, boolean isC, Scanner in) {
		if (isC)
			this.controlC = (ControlConsola) controlador;
		this.isConsola = isC;
		this.sc = in;
	}

	@Override
	public void execute() {
		if (isConsola) {
			if (words[1].equalsIgnoreCase("CO")) {
				this.controlC.reset(new FactoriaJuegoComplica());
			} else if (words[1].equalsIgnoreCase("C4")) {
				this.controlC.reset(new FactoriaJuegoConecta4());
			} else if (words[1].equalsIgnoreCase("RV")) {
				this.controlC.reset(new FactoriaJuegoReversi());
			} else if (words[1].equalsIgnoreCase("GR") && words.length == 2) {
				this.controlC.reset(new FactoriaJuegoGravity());
			} else if (words[1].equalsIgnoreCase("GR")) {
				if (words.length == 4) {
					int ti = 0, tj = 0;
					try {
						ti = Integer.parseInt(words[2]);
						tj = Integer.parseInt(words[3]);
					} catch (NumberFormatException e) {
						System.out
								.println("Comando anterior incorrecto- una o ambas dimensiones no son numeros");
					}
					this.controlC.reset(new FactoriaJuegoGravity(ti, tj));
				}
			} else {
				System.out.println("Yo no se jugar en " + words[1]);
			}
		}
	}

}
