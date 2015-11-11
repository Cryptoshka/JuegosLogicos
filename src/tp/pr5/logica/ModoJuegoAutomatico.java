package tp.pr5.logica;

import tp.pr5.control.ControlSwing;

public class ModoJuegoAutomatico implements ModoJuego, Runnable {
	private ControlSwing coSw; // lo necesito para llamar a ponerAutomatico()
	private Thread hiloAuto = null;

	public ModoJuegoAutomatico(ControlSwing c) {
		this.coSw = c;
	}

	@Override
	// se crea el Thread
	public void comenzar() {
		if (hiloAuto == null) {
			hiloAuto = new Thread(ModoJuegoAutomatico.this);
			hiloAuto.start();
		}
	}

	@Override
	// se mata el Thread: se interrumpe y se iguala a NULL
	public void terminar() {
		if (hiloAuto != null) {
			hiloAuto.interrupt();
			hiloAuto = null;
		}
	}

	@Override
	public void deshacerPulsado() {
		// no necesito este metodo porque en modo automatico
		// "debe impedirse la opcion de deshacer el movimiento"

	}

	@Override
	public void run() { // ejecuta 1 vez ponerAutomatico() y mata la hebra
		coSw.ponerAutomatico();
		terminar();
	}

}
