package tp.pr5.control.comandos;

import tp.pr5.Main;

public class Interpreter {
	// clase que convierte la linea introducida por jugador en uno de Comandos
	private static Comando[] listaCom = new Comando[] { new ExitComando(),
			new PlayComando(null), new PlayerComando(null, null),
			new PutComando(), new RestartComando(), new UndoComando() };

	public static Comando generateComando(String line) {
		Comando com = listaCom[0];
		int i = 0;
		while (i < listaCom.length && com.parse(line) == null) {
			i++;
			if (i != listaCom.length)
				com = listaCom[i];
			else
				com = null;
		}
		if (com != null)
			return com.parse(line);
		else
			return null;
	}

	public static String interpreterHelp() {
		String chathelp = "Instrucciones validas son: " + Main.LINE_SEPARATOR;
		for (Comando com : listaCom) {
			chathelp = chathelp + com.getHelp() + Main.LINE_SEPARATOR;
		}
		return chathelp;
	}
}
