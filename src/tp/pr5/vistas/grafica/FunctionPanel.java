package tp.pr5.vistas.grafica;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import tp.pr5.control.ControlSwing;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.ModoJuego;
import tp.pr5.logica.ModoJuegoAutomatico;
import tp.pr5.logica.Observador;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;
import tp.pr5.logica.TipoTurno;

@SuppressWarnings("serial")
public class FunctionPanel extends JPanel implements Observador {
	private JPanel match; // ventana Partida
	private JPanel players;
	private JPanel centralP;
	private JPanel change; // ventana Cambia el juego
	private JPanel micro; // ventana que contiene campos para meter filas y
							// columnas
	private JButton botonDeshacer, botonSalir, botonReiniciar, botonCambiar; // botones
																				// con
																				// sus
																				// funciones
	private JComboBox<TipoJuego> tj; // comboBox Tipo de Juego
	private JComboBox<TipoTurno> jugRojo;
	private JComboBox<TipoTurno> jugNegro;
	private JTextField filas;
	private JTextField cols;
	private JTextArea jR, jN;
	private ControlSwing cS;
	@SuppressWarnings("unused")
	private GameOver gO;
	@SuppressWarnings("unused")
	private Verdict v;
	private ModoJuego m;
	protected int fil, col;

	// Constructora
	public FunctionPanel(ControlSwing control) {
		this.cS = control;
		iniciateFunctionPanel();
	}

	// Inicializa Function Panel
	public void iniciateFunctionPanel() {
		match = new JPanel();
		change = new JPanel();
		micro = new JPanel();
		players = new JPanel();
		centralP = new JPanel();

		this.setPreferredSize(new java.awt.Dimension(300, 500));
		LayoutManager elLayout = new BorderLayout();
		this.setLayout(elLayout);
		// Ventanas
		match.setBorder(new TitledBorder("Partida"));
		match.setPreferredSize(new java.awt.Dimension(300, 100));
		players.setBorder(new TitledBorder("Gestion de jugadores"));
		players.setPreferredSize(new java.awt.Dimension(300, 100));
		change.setBorder(new TitledBorder("Cambio de Juego"));
		change.setPreferredSize(new java.awt.Dimension(300, 200));
		// Botones
		botonDeshacer = new JButton("Undo");
		botonDeshacer.setIcon(new ImageIcon(VistaSwing.class
				.getResource("/img/undo.png")));
		botonDeshacer.addActionListener(new UndoClass());

		botonReiniciar = new JButton("Restart");
		botonReiniciar.setIcon(new ImageIcon(VistaSwing.class
				.getResource("/img/reiniciar.png")));
		botonReiniciar.addActionListener(new RestartClass());

		botonSalir = new JButton("Exit");
		botonSalir.setIcon(new ImageIcon(VistaSwing.class
				.getResource("/img/exit.png")));
		botonSalir.addActionListener(new ExitClass());

		botonCambiar = new JButton("Change game");
		botonCambiar.setIcon(new ImageIcon(VistaSwing.class
				.getResource("/img/aceptar.png")));
		botonCambiar.addActionListener(new PlayClass());

		match.add(botonDeshacer, BorderLayout.WEST);
		match.add(botonReiniciar, BorderLayout.EAST);
		// Jugador Rojas y Negras
		this.jugRojo = new JComboBox<TipoTurno>();
		this.jR = new JTextArea();
		this.jugNegro = new JComboBox<TipoTurno>();
		this.jN = new JTextArea();

		DefaultComboBoxModel<TipoTurno> tRojo = new DefaultComboBoxModel<TipoTurno>();
		tRojo.addElement(TipoTurno.HUMANO);
		tRojo.addElement(TipoTurno.AUTOMATICO);
		jugRojo.setModel(tRojo);
		jugRojo.setPreferredSize(new java.awt.Dimension(150, 20));
		jugRojo.addActionListener(new jRojoClass());
		jR.setText("Jugador rojas");
		jR.setPreferredSize(new java.awt.Dimension(100, 20));
		jR.setBackground(null);
		players.add(jR, BorderLayout.LINE_START);
		players.add(jugRojo, BorderLayout.LINE_END);

		DefaultComboBoxModel<TipoTurno> tNegro = new DefaultComboBoxModel<TipoTurno>();
		tNegro.addElement(TipoTurno.HUMANO);
		tNegro.addElement(TipoTurno.AUTOMATICO);
		jugNegro.setModel(tNegro);
		jugNegro.setPreferredSize(new java.awt.Dimension(150, 20));
		jugNegro.addActionListener(new jNegroClass());
		jN.setText("Jugador negras");
		jN.setPreferredSize(new java.awt.Dimension(100, 20));
		jN.setBackground(null);
		players.add(jN, BorderLayout.WEST);
		players.add(jugNegro, BorderLayout.EAST);
		// Tipos de Juego
		this.tj = new JComboBox<TipoJuego>();
		tj.setPreferredSize(new java.awt.Dimension(300, 20));

		DefaultComboBoxModel<TipoJuego> juegos = new DefaultComboBoxModel<TipoJuego>();
		juegos.addElement(TipoJuego.GRAVITY);
		juegos.addElement(TipoJuego.CONECTA4);
		juegos.addElement(TipoJuego.COMPLICA);
		juegos.addElement(TipoJuego.REVERSI);
		tj.setModel(juegos);
		tj.addActionListener(new VariantClass());
		change.add(tj, BorderLayout.NORTH);
		filas = new JTextField();
		cols = new JTextField();
		filas.setPreferredSize(new java.awt.Dimension(100, 20));
		cols.setPreferredSize(new java.awt.Dimension(100, 20));
		micro.add(filas, BorderLayout.WEST);
		micro.add(cols, BorderLayout.EAST);
		change.add(micro, BorderLayout.SOUTH);
		change.add(botonCambiar, BorderLayout.SOUTH);
		// Juntamos todo
		this.add(match, BorderLayout.NORTH);
		centralP.add(players, BorderLayout.NORTH);
		centralP.add(change, BorderLayout.SOUTH);
		this.add(centralP, BorderLayout.CENTER);
		this.add(botonSalir, BorderLayout.SOUTH);
		botonDeshacer.setEnabled(false);

		cS.addObserver(this);
	}

	// Listener Deshacer
	class UndoClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cS.undo();
		}
	}

	// Listener modo de juego para rojas
	class jRojoClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (jugRojo.getSelectedItem() == TipoTurno.HUMANO) {
				cS.setPlayer(Ficha.ROJA,
						cS.getFactoria().creaJugadorHumano(null));
				cS.setJugTipo(Ficha.ROJA, TipoTurno.HUMANO);
			} else {
				cS.setPlayer(Ficha.ROJA, cS.getFactoria()
						.creaJugadorAleatorio());
				cS.setJugTipo(Ficha.ROJA, TipoTurno.AUTOMATICO);
				// como se aclara en ControlSwing.java, necesito saber el turno
				// que juega ahora-
				// solo lo puedo conseguir a partir de
				// ControlSwing->Partida->getTurno()
				if (cS.getPartida().getTurno() == Ficha.ROJA) {
					onMovimientoStart(Ficha.ROJA);
				}
			}
		}

	}

	// Listener modo de juego para rojas
	class jNegroClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (jugNegro.getSelectedItem() == TipoTurno.HUMANO) {
				cS.setPlayer(Ficha.NEGRA,
						cS.getFactoria().creaJugadorHumano(null));
				cS.setJugTipo(Ficha.NEGRA, TipoTurno.HUMANO);
			} else {
				cS.setPlayer(Ficha.NEGRA, cS.getFactoria()
						.creaJugadorAleatorio());
				cS.setJugTipo(Ficha.NEGRA, TipoTurno.AUTOMATICO);
				// como se aclara en ControlSwing.java, necesito saber el turno
				// que juega ahora-
				// solo lo puedo conseguir a partir de
				// ControlSwing->Partida->getTurno()
				if (cS.getPartida().getTurno() == Ficha.NEGRA) {
					onMovimientoStart(Ficha.NEGRA);
				}
			}
		}

	}

	// Listener para saber si hay que mostrar campos de filas y columnas
	class VariantClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TipoJuego t = (TipoJuego) tj.getSelectedItem();
			if (!t.esDimensionable()) {
				filas.setVisible(false);
				cols.setVisible(false);
			} else {
				filas.setVisible(true);
				cols.setVisible(true);
			}
		}
	}

	// Listener Reiniciar
	class RestartClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (m != null)
				m.terminar();
			cS.reiniciar();
		}
	}

	// Listener Salir
	class ExitClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gO = new GameOver();
		}
	}

	// Listener Cambiar el Juego
	// permite generar un Tablero Gravity como maximo de tamanio 12*12
	// si alguno de los parametros es mas grande- lo sustituye por 10
	// si alguno de los parametros no es Integer- crea tablero 10*10
	class PlayClass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (m != null)
				m.terminar();
			TipoJuego gioco = (TipoJuego) tj.getSelectedItem();
			fil = 0;
			col = 0;
			if (filas.getText() != null && !filas.getText().isEmpty()
					&& cols.getText() != null && !cols.getText().isEmpty()) {
				try {
					fil = Integer.parseInt(filas.getText().trim());
					col = Integer.parseInt(cols.getText().trim());
					if (fil > 12)
						fil = 12;
					else if (fil < 1)
						fil = gioco.getAltoDefecto();// en vez de 10
					if (col > 12)
						col = 12;
					else if (col < 1)
						col = gioco.getAnchoDefecto();// en vez de 10
				} catch (NumberFormatException q) {
				}
			}
			cS.reset(gioco, fil, col);
			filas.setText(null);
			cols.setText(null);
		}
	}

	@Override
	public void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (m != null)
					m.terminar();
				v = new Verdict(ganador);
				botonDeshacer.setEnabled(false);
			}
		});
	}

	@Override
	// si toca al turno, que esta en modo automatico- lanzo ModoJuegoAutomatico
	// y nueva hebra
	public void onMovimientoStart(Ficha turno) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (((turno == Ficha.ROJA && jugRojo.getSelectedItem() == TipoTurno.AUTOMATICO) || (turno == Ficha.NEGRA && jugNegro
						.getSelectedItem() == TipoTurno.AUTOMATICO))
						&& !cS.finalizada()) {
					m = new ModoJuegoAutomatico(cS);
					m.comenzar();
				}
			}
		});
	}

	@Override
	// si toca al turno, que esta en modo automatico- deshabilito botones
	// correspondientes
	public void onMovimientoEnd(TableroInmutable tab, Ficha turno) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if ((turno == Ficha.ROJA && jugRojo.getSelectedItem() == TipoTurno.AUTOMATICO)
						|| (turno == Ficha.NEGRA && jugNegro.getSelectedItem() == TipoTurno.AUTOMATICO)) {
					botonDeshacer.setEnabled(false);
					jugRojo.setEnabled(false);
					jugNegro.setEnabled(false);
				} else {
					botonDeshacer.setEnabled(true);
					jugRojo.setEnabled(true);
					jugNegro.setEnabled(true);
				}
			}

		});
	}

	@Override
	public void onUndoNotPossible() {

	}

	@Override
	public void onCambioTurno(Ficha turno) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMovimientoIncorrecto(String explicacion) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			}
		});

	}

	public void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno) {
		if ((turno == Ficha.ROJA && jugRojo.getSelectedItem() == TipoTurno.AUTOMATICO)
				|| (turno == Ficha.NEGRA && jugNegro.getSelectedItem() == TipoTurno.AUTOMATICO)) {
			botonDeshacer.setEnabled(false);
			jugRojo.setEnabled(false);
			jugNegro.setEnabled(false);
		} else {
			botonDeshacer.setEnabled(true);
			jugRojo.setEnabled(true);
			jugNegro.setEnabled(true);
		}

		if (hayMas && (cS.getJugTipo(turno) != TipoTurno.AUTOMATICO))
			botonDeshacer.setEnabled(true);
		else
			botonDeshacer.setEnabled(false);
	}

	@Override
	public void onResetPartida(TableroInmutable tabIni, Ficha turno) {
		botonDeshacer.setEnabled(false);
		jugRojo.setSelectedItem(TipoTurno.HUMANO);
		jugRojo.setEnabled(true);
		jugNegro.setSelectedItem(TipoTurno.HUMANO);
		jugNegro.setEnabled(true);
	}

	@Override
	public void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno) {
		botonDeshacer.setEnabled(false);
		jugRojo.setSelectedItem(TipoTurno.HUMANO);
		jugRojo.setEnabled(true);
		jugNegro.setSelectedItem(TipoTurno.HUMANO);
		jugNegro.setEnabled(true);
	}
}
