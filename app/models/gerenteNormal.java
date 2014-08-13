package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import models.exceptions.InpossivelAddParticipante;

@Entity(name="gerenteNormal")
public class gerenteNormal extends GerenciadorDeParticipacao {
	
	@Override
	public void addParticipante(Evento evento, Usuario participante) throws InpossivelAddParticipante {
		/*
		 * para o caso de um gerente normal eu só posso
		 * adicionar um user se a capacidade do local permitir. 
		 */
		Local local = evento.getLocal();
		if(evento.getTotalDeParticipantes() < local.getCapacidade()){
			evento.getParticipantes().add(participante);
		}else{
			throw new InpossivelAddParticipante("capacidade do local já esgotada");
		}
	
	}

}
