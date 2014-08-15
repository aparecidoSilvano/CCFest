package models;

import java.util.Collections;

import javax.persistence.Entity;

import models.exceptions.ImpossivelAddParticipante;

@Entity(name = "GerentePrioridadeExperiencia")
public class GerentePrioridadeExperiencia extends GerenciadorDeParticipacao {

	@Override
	public void addParticipante(Evento evento, Usuario participante)
			throws ImpossivelAddParticipante {
		if (evento.haVagas()) {
			evento.getParticipantes().add(participante);
		} else {
			Collections.sort(evento.getParticipantes(),
					new ExperienciaComparator());
			int numParticipantes = evento.getTotalDeParticipantes();
			if (evento.getParticipantes().get(numParticipantes)
					.getExperiencia() < participante.getExperiencia()) {
				evento.getParticipantes().remove(numParticipantes);
				evento.addParticipante(participante);

				// reeordena a lista.
				Collections.sort(evento.getParticipantes(),
						new ExperienciaComparator());
			} else {
				throw new ImpossivelAddParticipante("O usuário não pode participar desse veneto.x	");
			}
		}

	}

}
