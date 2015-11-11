package tp.pr5.vistas.grafica;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import tp.pr5.control.ControlSwing;
import tp.pr5.logica.TableroInmutable;

@SuppressWarnings("serial")
public class VistaSwing extends JFrame {
	private ControlSwing cS;
	private TableroInmutable tabl;
	private Container panelPrincipal;
	private JPanel panel;

	// Aqui se crea la vista principal de Swing, contiene GamePanel (izq) y
	// FunctionPanel (der)
	public VistaSwing(ControlSwing c, TableroInmutable tab) {
		super("Practica 5 Final- TP");
		cS = c;
		tabl = tab;
		panel = new JPanel();
		panelPrincipal = this.getContentPane();
		this.setSize(800, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel gamePanel = new GamePanel(cS, tabl, cS.getFactoria().creaReglas().turnoInicio());
		FunctionPanel functionPanel = new FunctionPanel(cS);
		panel.add(gamePanel, BorderLayout.WEST);
		panel.add(functionPanel, BorderLayout.EAST);
		panelPrincipal.add(panel);
		this.pack();
	}

	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				setVisible(true);
			}
		});
	}
}
