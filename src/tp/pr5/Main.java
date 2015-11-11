package tp.pr5;

import java.util.Scanner;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tp.pr5.control.ControlConsola;
import tp.pr5.control.ControlSwing;
import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.control.factorias.FactoriaJuegoComplica;
import tp.pr5.control.factorias.FactoriaJuegoConecta4;
import tp.pr5.control.factorias.FactoriaJuegoGravity;
import tp.pr5.control.factorias.FactoriaJuegoReversi;
import tp.pr5.logica.Partida;
import tp.pr5.vistas.consola.VistaConsola;
import tp.pr5.vistas.grafica.VistaSwing;

public class Main {
	static Options opciones = new Options();

	public static String LINE_SEPARATOR = System.getProperty("line.separator");

	public static void main(String[] args) {
		loadOptions();
		// inicializaciones por defecto (si la linea de ordenes es vacia)
		int ti = 10, tj = 10;
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;

		FactoriaJuego f = new FactoriaJuegoConecta4();

		try {
			cmd = parser.parse(opciones, args);
			// parser de opciones
			if (cmd.hasOption("h")) {
				new HelpFormatter().printHelp(Main.class.getCanonicalName(),
						opciones);
			} else {
				// genera factoria dependiendo de parametros
				if (cmd.hasOption("g")) {
					if (cmd.getOptionValue("g").equalsIgnoreCase("c4")
							&& !cmd.hasOption("x") && !cmd.hasOption("y")) {
						f = new FactoriaJuegoConecta4();
					} else if (cmd.getOptionValue("g").equalsIgnoreCase("co")
							&& !cmd.hasOption("x") && !cmd.hasOption("y")) {
						f = new FactoriaJuegoComplica();
					} else if (cmd.getOptionValue("g").equalsIgnoreCase("rv")
							&& !cmd.hasOption("x") && !cmd.hasOption("y")) {
						f = new FactoriaJuegoReversi();
					} else if (cmd.getOptionValue("g").equalsIgnoreCase("gr")) {
						// si hay parametro x- crea tablero con x=valor de
						// argumento, y = 10 (por def)
						if (cmd.hasOption("x")) {
							ti = Integer.parseInt(cmd.getOptionValue("x"));
						}
						// si hay parametro y- crea tablero con y=valor de
						// argumento, x = 10 (por def)
						if (cmd.hasOption("y")) {
							tj = Integer.parseInt(cmd.getOptionValue("y"));
						}
						// si no hay ninguno de los 2- crea por defecto 10x10
						f = new FactoriaJuegoGravity(ti, tj);
						// nombre de juego incorrecto
					} else {
						System.err.println(error("Juego '"
								+ cmd.getOptionValue("g") + "' incorrecto"));
						System.exit(1);
					}
				}
			}
			// argumentos incorrectos
			if (cmd.getArgs().length != 0) {
				String aux = "";
				for (int i = 0; i < cmd.getArgs().length - 1; i++) {
					aux += cmd.getArgs()[i] + " ";
				}
				aux += cmd.getArgs()[cmd.getArgs().length - 1];
				System.err.println(error("Argumentos no entendidos: " + aux));
				System.exit(1);
			}
			// preparacion para iniciar el juego
			Partida p = new Partida(f.creaReglas());
			Scanner sc = new Scanner(System.in);
			// Dependiendo de parametros, se crean Controlador y Vista
			// correspondientes
			if (cmd.hasOption("u")
					&& cmd.getOptionValue("u").equalsIgnoreCase("window")) {
				ControlSwing c = new ControlSwing(f, p);
				VistaSwing v = new VistaSwing(c, c.getPartida().getTablero());
				v.run();
			} else if (cmd.hasOption("u")
					&& !(cmd.getOptionValue("u").equalsIgnoreCase("console"))) {
				System.err.println(error("No existe este tipo de ejecucion"));
			} else {
				ControlConsola c = new ControlConsola(f, p, sc);
				System.out.println(c.getPartida().getTablero().toString());
				VistaConsola v = new VistaConsola(c, sc);
				c.addObserver(v);
				v.run();
			}
			sc.close();
		} catch (ParseException e) {
			System.err.println(error(e.getMessage()));
			System.exit(1);
		} catch (NumberFormatException n) {
			System.err.println(error("Alguno de argumentos no es valido"));
			System.exit(1);
		}
	}

	private static String error(String string) {
		return "Uso incorrecto: " + string + Main.LINE_SEPARATOR
				+ "Use -h|--help para mas detalles";
	}

	// Definimos opciones
	private static void loadOptions() {
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("g", "game", true,
				"Tipo de juego (c4, co, gr, rv). Por defecto, c4"));
		group.addOption(new Option("h", "help", false, "Muestra esta ayuda"));
		OptionGroup group2 = new OptionGroup();
		group2.addOption(new Option("x", "tamX", true,
				"Numero de columnas del tablero"));
		OptionGroup group3 = new OptionGroup();
		group3.addOption(new Option("y", "tamY", true,
				"Numero de filas en tablero"));
		OptionGroup group4 = new OptionGroup();
		group4.addOption(new Option("u", "ui", true,
				"Permite elegir el formato: consola o ventana. Por def., consola"));
		opciones.addOptionGroup(group);
		opciones.addOptionGroup(group2);
		opciones.addOptionGroup(group3);
		opciones.addOptionGroup(group4);
		opciones.getOption("g").setArgName("game");
		opciones.getOption("x").setArgName("numColumnas");
		opciones.getOption("y").setArgName("numFilas");
		opciones.getOption("u").setArgName("tipo");
	}

}
