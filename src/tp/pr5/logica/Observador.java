package tp.pr5.logica;

//La interfaz que contiene todas las notificaciones posibles
public interface Observador {
	void onPartidaTerminada(TableroInmutable tabFin, Ficha ganador);

	void onMovimientoStart(Ficha turno);

	void onMovimientoEnd(TableroInmutable tab, Ficha turno);

	void onUndo(TableroInmutable tab, boolean hayMas, Ficha turno);

	void onResetPartida(TableroInmutable tabIni, Ficha turno);

	void onUndoNotPossible();

	void onCambioTurno(Ficha turno);

	void onMovimientoIncorrecto(String explicacion);

	void onCambioJuego(TableroInmutable tab, TipoJuego tipo, Ficha turno);
}
