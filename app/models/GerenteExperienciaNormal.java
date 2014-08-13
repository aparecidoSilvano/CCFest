package models;

public class GerenteExperienciaNormal extends GerenciadorExpereincia {

	@Override
	public int calculaExperiencia(Usuario usuario) {
		// (a) maior número de eventos criados no sistema como admin e 
		// (b) maior número de eventos nos quais confirmou participação (e foi aceito). 
		
		// a experiência é dada por 2x + y, onde x é número de eventos administrados +
		// número de eventos participados.
		int numEventosAdiministrados = usuario.getNumEventosAdim();
		int numParticipacoes = usuario.getNumParticipacoes();
		return (2*numEventosAdiministrados) + numParticipacoes;
	}

}
