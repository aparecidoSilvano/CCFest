package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import models.exceptions.ImpossivelAddParticipante;

@Entity(name="gerenteNormal")
public class gerenteNormal extends GerenciadorDeParticipacao {
	
	@Override
	public void addParticipante(Evento evento, Usuario participante) throws ImpossivelAddParticipante {
		/*
		 * para o caso de um gerente normal eu só posso
		 * adicionar um user se a capacidade do local permitir. 
		 */
		if(evento.haVagas()){
			evento.getParticipantes().add(participante);
		}else{
			throw new ImpossivelAddParticipante("capacidade do local já esgotada");
		}
	
	}

}
