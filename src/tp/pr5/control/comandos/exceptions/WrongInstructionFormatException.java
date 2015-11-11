package tp.pr5.control.comandos.exceptions;

import java.io.Serializable;

public class WrongInstructionFormatException extends Exception implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// si el formato de instruccion no es como se esperaba
	public WrongInstructionFormatException() {
	}

	public WrongInstructionFormatException(String arg0) {
		super(arg0);
	}

	public WrongInstructionFormatException(java.lang.Throwable arg0) {
		super(arg0);
	}

	public WrongInstructionFormatException(java.lang.String arg0,
			java.lang.Throwable arg1) {
		super(arg0, arg1);
	}

}
