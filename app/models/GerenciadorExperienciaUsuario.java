package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="GerenciadorExpereincia")
public abstract class GerenciadorExperienciaUsuario {
	@Id
	@GeneratedValue
	Long id;
	
	public abstract int calculaExperiencia(Usuario usuario);
}
