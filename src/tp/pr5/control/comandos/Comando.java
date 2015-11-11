package tp.pr5.control.comandos;

import java.util.Scanner;

//La interfaz para todos los comandos del juego

public interface Comando {
	// parsea la instruccion
	Comando parse(String cad);

	// devuelve informacion sobre el comando
	String getHelp();

	// actualiza el contenido
	void configureContext(Object controlador, boolean isC, Scanner in);

	// el comando se ejecuta
	void execute();
}
