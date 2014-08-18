package models;

import java.util.Comparator;

public class UsuarioComparator implements Comparator<Usuario> {

	@Override
	public int compare(Usuario user1, Usuario user2) {
		if (user1.getExperiencia() < user2.getExperiencia()) {
			return 1;
		} else if (user1.getExperiencia() > user2.getExperiencia()) {
			return -1;
		}
		return 0;
	}

}