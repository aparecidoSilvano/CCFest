package models;

import java.util.Collections;

import javax.persistence.Entity;

import models.exceptions.ImpossivelAddParticipante;

@Entity(name = "GerentePrioridadeExperiencia")
public class GerenteParticipacaoPrioritario extends GerenciadorDeParticipacao {

	@Override
	public void addParticipante(Evento evento, Usuario participante)
			throws ImpossivelAddParticipante {
		if (evento.haVagas()) {
			evento.getParticipantes().add(participante);
		} else {
			Collections.sort(evento.getParticipantes(),
					new UsuarioComparator());
			int numParticipantes = evento.getTotalDeParticipantes();
			if (evento.getParticipantes().get(numParticipantes -1)
					.getExperiencia() < participante.getExperiencia()) {
				evento.getParticipantes().remove(numParticipantes -1);
				evento.addParticipante(participante);

				// reeordena a lista.
				Collections.sort(evento.getParticipantes(),
						new UsuarioComparator());
			} else {
				throw new ImpossivelAddParticipante("O usuário não possui experiência suficiente para participar desse evento");
			}
		}

	}

}
