package tp.pr5.logica;

import tp.pr5.movimientos.Movimiento;

public class Pila {
	private Movimiento[] pila;
	private final static int TAMANIO = 10;
	private int prin;
	private static boolean vacia;

	public Pila() {
		pila = new Movimiento[TAMANIO];
		prin = 0;
		vacia = true;
	}

	public boolean isVacia() {
		return vacia;
	}

	// poner en pila
	public void push(Movimiento m) {
		if (prin < TAMANIO) {
			pila[prin] = m;
			vacia = false;
			prin++;
		} else {
			for (int i = 1; i < TAMANIO; i++) {
				pila[i - 1] = pila[i];
			}
			pila[TAMANIO - 1] = m;
		}
	}

	// quitar de la pila
	public Movimiento pop() {
		if (vacia)
			return null;
		Movimiento toReturn = pila[prin - 1];
		prin--;
		if (prin == 0)
			vacia = true;
		return toReturn;
	}

	// reiniciar la pila
	public void resetPila() {
		int size = pila.length;
		pila = new Movimiento[size];
		prin = 0;
		vacia = true;
	}

	public int getPrin() {
		return prin;
	}
}
