package tp.pr5.vistas.grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import tp.pr5.control.ControlSwing;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;
import tp.pr5.logica.TipoTurno;

//Se crea la ventana izquierda de UI que contiene el campo de juego, la ventana para mensajes y el boton para movimiento aleatorio 

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Observador {
	private celdaListener listener;
	private JButton[] buttons; // botones por debajo de grid
	private JButton botonPonerAl;
	private JPanel table; // tablero + ventana de log
	private static JPanel gZone; // tablero
	private JLabel log; // ventana de mensajes
	private ControlSwing cS;
	private TableroInmutable tab;
	private Ficha inicial;

	// Constructora
	public GamePanel(ControlSwing cS, TableroInmutable tab, Ficha in) {
		this.cS = cS;
		this.tab = tab;
		this.inicial = in;
		iniciateGamePanel();
	}

	// Se inicializa GamePanel
	public void iniciateGamePanel() {
		table = new JPanel();
		crearNewGameZone();

		this.setPreferredSize(new java.awt.Dimension(350, 500));

		table.setBorder(new TitledBorder(null, null, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		table.setPreferredSize(new java.awt.Dimension(300, 350));

		// Boton para poner ficha aleatoria
		botonPonerAl = new JButton("Put randomly");
		botonPonerAl.setIcon(new ImageIcon(FunctionPanel.class
				.getResource("/img/random.png")));
		botonPonerAl.addActionListener(new PutAlClass());

		this.add(table, BorderLayout.NORTH);
		this.add(botonPonerAl, BorderLayout.PAGE_END);

		cS.addObserver(this);
	}

	// Creando el tablero; es un Grid con array de botones por debajo
	private void crearNewGameZone() {
		gZone = new JPanel(new GridLayout(this.tab.getAlto(),
				this.tab.getAncho(), 5, 5));
		buttons = new JButton[tab.getAlto() * tab.getAncho()];
		gZone.setBorder(new TitledBorder(null, null, TitledBorder.LEADING,
				TitledBorder.TOP, null, Color.GREEN));
		gZone.setPreferredSize(new java.awt.Dimension(300, 300));

		for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
			buttons[i] = new JButton();
			buttons[i].setName(i / tab.getAncho() + " " + i % tab.getAncho());
			listener = new celdaListener();
			listener.ActionListener(buttons[i]);
			buttons[i].addActionListener(listener);
			buttons[i].setSize(40, 40);
			if (tab.getFichaPos(i / tab.getAncho() + 1, i % tab.getAncho() + 1) == Ficha.ROJA)
				buttons[i].setIcon(new ImageIcon(VistaSwing.class
						.getResource("/img/red.png")));
			else if (tab.getFichaPos(i / tab.getAncho() + 1, i % tab.getAncho()
					+ 1) == Ficha.NEGRA)
				buttons[i].setIcon(new ImageIcon(VistaSwing.class
						.getResource("/img/white.png")));
			gZone.add(buttons[i]);
		}

		// Creando la ventana para informes
		log = new JLabel();
		log.setPreferredSize(new java.awt.Dimension(250, 50));
		log.setBackground(Color.WHITE);
		log.setHorizontalAlignment(SwingConstants.CENTER);
		log.setVerticalAlignment(SwingConstants.CENTER);
		if (inicial == Ficha.ROJA)
			log.setText("Juegan rojas");
		else
			log.setText("Juegan negras");

		table.add(gZone, BorderLayout.NORTH);
		table.add(log, BorderLayout.SOUTH);
	}

	// Listener de boton del tablero presionado
	private class celdaListener implements ActionListener {
		@SuppressWarnings("unused")
		JButton celda;

		public void ActionListener(JButton celda) {
			this.celda = celda;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton aux = (JButton) e.getSource();
			// Cada boton de Grid tiene por debajo un nombre asociado de forma
			// (i j)
			// que me indica fila y columna del tablero real
			String[] miCelda = aux.getName().split(" ");
			int f = Integer.parseInt(miCelda[0]);
			int c = Integer.parseInt(miCelda[1]);
			try {
				cS.poner(f + 1, c + 1);
			} catch (Exception e1) {

			}
		}
	}

	// Listener de PonerAleatorio
	private class PutAlClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cS.poner();
		}
	}

	@Override
	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		this.tab = tab;
		redibuja(this.tab);
		if (cS.getJugTipo(turno) == TipoTurno.AUTOMATICO) {
			botonPonerAl.setEnabled(false);
			for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
				buttons[i].setEnabled(false);
			}
		} else {
			botonPonerAl.setEnabled(true);
			for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
				buttons[i].setEnabled(true);
			}
		}
	}

	// Una funcion auxiliar para rellenar otra vez el array de botones
	public void redibuja(TableroInmutable tab) {
		for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
			if (tab.getFichaPos(i / tab.getAncho() + 1, i % tab.getAncho() + 1) == Ficha.ROJA)
				buttons[i].setIcon(new ImageIcon(VistaSwing.class
						.getResource("/img/red.png")));
			else if (tab.getFichaPos(i / tab.getAncho() + 1, i % tab.getAncho()
					+ 1) == Ficha.NEGRA)
				buttons[i].setIcon(new ImageIcon(VistaSwing.class
						.getResource("/img/white.png")));
			else
				buttons[i].setIcon(null);
		}
	}

	@Override
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		redibuja(tabIni);
		if (turno == Ficha.ROJA)
			log.setText("Juegan rojas");
		else
			log.setText("Juegan negras");
		for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
			buttons[i].setEnabled(true);
		}
		botonPonerAl.setEnabled(true);
	}

	@Override
	public void onUndoNotPossible() {
		log.setText("No se puede deshacer");
	}

	@Override
	public void onCambioTurno(Ficha turno) {
		/*
		 * if (turno == Ficha.ROJA) log.setText("Juegan rojas"); else
		 * log.setText("Juegan negras");
		 */
	}

	@Override
	public void onMovimientoIncorrecto(String explicacion) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				log.setText(explicacion);
			}
		});
	}

	// Si la partida se termina, el tablero pasa a ser deshabilitado: se puede
	// pinchar sobre los botones,
	// pero eso no invoca a celdaListener. Tambien se puede deshabilitar los
	// botones, pero en este caso no
	// se ven los colores de casillas ocupadas (son todas grises)
	@Override
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				redibuja(tabFin);
				if (ganador == Ficha.ROJA)
					log.setText("Ganan rojas");
				else if (ganador == Ficha.NEGRA)
					log.setText("Ganan negras");
				else
					log.setText("Empate");
				for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
					buttons[i].setEnabled(true);
				}
				table.setEnabled(false);
				botonPonerAl.setEnabled(false);
			}
		});
	}

	@Override
	public void onMovimientoStart(Ficha turno) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (turno == Ficha.ROJA)
					log.setText("Juegan rojas");
				else
					log.setText("Juegan negras");
			}
		});
	}

	@Override
	public void onMovimientoEnd(TableroInmutable tabl, Ficha turno) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tab = tabl;
				redibuja(tab);
				if (cS.getJugTipo(turno) == TipoTurno.AUTOMATICO) {
					botonPonerAl.setEnabled(false);
					for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
						buttons[i].setEnabled(false);
						buttons[i].setBackground(null);
					}
				} else {
					botonPonerAl.setEnabled(true);
					for (int i = 0; i < tab.getAlto() * tab.getAncho(); i++) {
						buttons[i].setEnabled(true);
					}
				}
			}

		});
	}

	// El unico caso cuando es necesario generar una GameZone nueva
	@Override
	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		this.tab = tab;
		table.removeAll();
		crearNewGameZone();
		if (turno == Ficha.ROJA)
			log.setText("Juegan rojas");
		else
			log.setText("Juegan negras");
		table.revalidate();
		botonPonerAl.setEnabled(true);
	}
}
