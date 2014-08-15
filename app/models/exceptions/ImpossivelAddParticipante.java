package models.exceptions;

public class ImpossivelAddParticipante extends Exception {

	private static final long serialVersionUID = -4165837695281044656L;
	
	public ImpossivelAddParticipante(String message){
		super(message);
	}
}
