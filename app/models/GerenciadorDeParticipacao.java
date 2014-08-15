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
	
	public abstract void addParticipante(Evento evento, Usuario participante) throws ImpossivelAddParticipante;

}
