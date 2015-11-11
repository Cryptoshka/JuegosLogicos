package tp.pr5.logica;

import tp.pr5.control.factorias.FactoriaJuego;
import tp.pr5.control.factorias.FactoriaJuegoComplica;
import tp.pr5.control.factorias.FactoriaJuegoConecta4;
import tp.pr5.control.factorias.FactoriaJuegoGravity;
import tp.pr5.control.factorias.FactoriaJuegoReversi;
import tp.pr5.juegos.ReglasComplica;
import tp.pr5.juegos.ReglasConecta4;
import tp.pr5.juegos.ReglasGravity;
import tp.pr5.juegos.ReglasJuego;
import tp.pr5.juegos.ReglasReversi;

public enum TipoJuego {
	CONECTA4, COMPLICA, GRAVITY, REVERSI;

	public TipoJuego getTipoJuego() {
		return this;
	}

	// permite obtener reglas de juego a partir del tipo
	public ReglasJuego obtenDeType() {
		ReglasJuego resp = null;
		if (this == TipoJuego.COMPLICA)
			resp = new ReglasComplica();
		if (this == TipoJuego.CONECTA4)
			resp = new ReglasConecta4();
		if (this == TipoJuego.GRAVITY)
			resp = new ReglasGravity();
		if (this == TipoJuego.REVERSI)
			resp = new ReglasReversi();
		return resp;
	}

	// permite obtener factoria de juego a partir del tipo
	public FactoriaJuego obtenDeTypeF() {
		FactoriaJuego resp = null;
		if (this == TipoJuego.COMPLICA)
			resp = new FactoriaJuegoComplica();
		if (this == TipoJuego.CONECTA4)
			resp = new FactoriaJuegoConecta4();
		if (this == TipoJuego.GRAVITY)
			resp = new FactoriaJuegoGravity();
		if (this == TipoJuego.REVERSI)
			resp = new FactoriaJuegoReversi();
		return resp;
	}

	public int getAnchoDefecto() {
		if (this == TipoJuego.COMPLICA)
			return 4;
		else if (this == TipoJuego.CONECTA4)
			return 7;
		else if (this == TipoJuego.REVERSI)
			return 8;
		else
			return 10;
	}

	public int getAltoDefecto() {
		if (this == TipoJuego.COMPLICA)
			return 7;
		else if (this == TipoJuego.CONECTA4)
			return 6;
		else if (this == TipoJuego.REVERSI)
			return 8;
		else
			return 10;
	}

	public boolean esDimensionable() {
		if (this == TipoJuego.GRAVITY) {
			return true;
		} else {
			return false;
		}
	}

	public boolean empiezanRojas() {
		if (this == TipoJuego.REVERSI)
			return false;
		else
			return true;
	}
}
