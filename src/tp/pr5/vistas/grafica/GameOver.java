package tp.pr5.vistas.grafica;

import javax.swing.JOptionPane;

//La ventana sencilla para terminar la aplicacion

public class GameOver extends JOptionPane {
	private static final long serialVersionUID = 1L;

	public GameOver() {
		int ax = JOptionPane.showConfirmDialog(null, "Quieres salir?");
		setVisible(true);
		if (ax == JOptionPane.YES_OPTION)
			System.exit(0);
	}

}
