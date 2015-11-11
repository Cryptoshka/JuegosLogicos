package tp.pr5.control.comandos;

import java.util.Scanner;

import tp.pr5.control.ControlConsola;
import tp.pr5.logica.Ficha;

public class PlayerComando implements Comando {
	private ControlConsola controlC;
	private boolean isConsola = false;
	private Scanner sc;
	private String tip, pl;

	public PlayerComando(String tipo, String jug) {
		this.tip = tipo;
		this.pl = jug;
	}

	@Override
	public Comando parse(String cad) {
		String[] words = cad.split(" ");
		if (words.length == 3) { // metiendo la condicion de que son 3 palabras
			if (words[0].equalsIgnoreCase("JUGADOR")
					&& (words[1].equalsIgnoreCase("ROJAS") || words[1]
							.equalsIgnoreCase("NEGRAS"))
					&& (words[2].equalsIgnoreCase("HUMANO") || words[2]
							.equalsIgnoreCase("ALEATORIO"))) {
				return new PlayerComando(words[1], words[2]);
			} else
				return null;
		} else {
			return null;
		}
	}

	@Override
	public String getHelp() {
		return "JUGADOR [rojas|negras] [humano|aleatorio]: cambia el tipo de jugador";
	}

	public void configureContext(Object controlador, boolean isC, Scanner in) {
		if (isC)
			this.controlC = (ControlConsola) controlador;
		this.isConsola = isC;
		this.sc = in;
	}

	public void execute() {
		Ficha aux;
		if (tip.equalsIgnoreCase("ROJAS"))
			aux = Ficha.ROJA;
		else
			aux = Ficha.NEGRA;
		if (pl.equalsIgnoreCase("HUMANO")) {
			if (isConsola)
				this.controlC.setPlayer(aux, this.controlC.getFactoria()
						.creaJugadorHumano(this.sc));
		} else {
			if (isConsola)
				this.controlC.setPlayer(aux, this.controlC.getFactoria()
						.creaJugadorAleatorio());
		}
	}

}
