package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import models.exceptions.ImpossivelAddParticipante;

@Entity(name="GerenciadorDeParticipacao")
public abstract class GerenciadorDeParticipacao {
	
	@Id
	@GeneratedValue
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public abstract void addParticipante(Evento evento, Usuario participante) throws ImpossivelAddParticipante;

}
