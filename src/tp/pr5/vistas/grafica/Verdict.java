package tp.pr5.vistas.grafica;

import javax.swing.JOptionPane;

import tp.pr5.logica.Ficha;

public class Verdict extends JOptionPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Verdict(Ficha f) {
		if (f == Ficha.ROJA) {
			JOptionPane.showMessageDialog(null, "Han ganado rojas!");
		} else if (f == Ficha.NEGRA) {
			JOptionPane.showMessageDialog(null, "Han ganado negras!");
		} else {
			JOptionPane.showMessageDialog(null, "Empate!");
		}
	}
}
