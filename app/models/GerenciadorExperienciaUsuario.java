package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="GerenciadorExpereincia")
public abstract class GerenciadorExperienciaUsuario {
	@Id
	@GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public abstract int calculaExperiencia(Usuario usuario);
}
