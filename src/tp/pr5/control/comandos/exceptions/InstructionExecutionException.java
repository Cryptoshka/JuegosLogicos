package tp.pr5.control.comandos.exceptions;

public class InstructionExecutionException extends Exception {
	private static final long serialVersionUID = 1L;

	// si pasa un error de ejecucion de instruccion
	public InstructionExecutionException() {
	}

	public InstructionExecutionException(String arg0) {
		super(arg0);
	}

	public InstructionExecutionException(java.lang.Throwable arg0) {
		super(arg0);
	}

	public InstructionExecutionException(java.lang.String arg0,
			java.lang.Throwable arg1) {
		super(arg0, arg1);
	}

}