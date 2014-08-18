import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.*;
import models.dao.*;
import models.exceptions.*;

import org.junit.Test;

import base.AbstractTest;


public class EventTest extends AbstractTest {
	private static GenericDAO dao = new GenericDAOImpl();
	private List<Tema> temas;
	
	@Test
	public void deveFuncionarNoBD() {
		temas = new ArrayList<>();
		temas.add(Tema.ARDUINO);
		temas.add(Tema.ELETRONICA);
		
		List<Usuario> usuarios = dao.findAllByClassName("Usuario");
		List<Evento> eventos = dao.findAllByClassName("Evento");
		
		assertEquals(0, usuarios.size());
		assertEquals(0, eventos.size());
		
		try {
			Usuario u1 = new Usuario("jose@gmail.com", "12344", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.maria@gmail.com", "97878", "maria", new GerenteExperienciaNormal());
			Usuario u3 = new Usuario("lucas@gmail.com", "12345", "lucas", new GerenteExperienciaNormal());
			Usuario u4 = new Usuario("lucia@gmail.com", "jkui232H", "lucia", new GerenteExperienciaNormal());
			Usuario u5 = new Usuario("joice.silva@gmail.com", "12345", "joice", new GerenteExperienciaNormal());
			Usuario u6 = new Usuario("pedro89@gmail.com", "pedro123", "pedro", new GerenteExperienciaNormal());
			dao.persist(u1);	dao.persist(u2);	dao.persist(u3);	dao.persist(u4);	dao.persist(u5);	dao.persist(u6);
			
			usuarios = dao.findAllByClassName("Usuario");
			
			assertEquals(6, usuarios.size());
			
			// quero saber o que acontece com os dados dos usuarios depois que
			// eles estão no evento.
			Local l = new Local("aúditorio central", 4, "na ufcg");

			Evento e1 = new Evento("evento teste1", "evento para testes",
					new Date(), l, temas, new GerenteNormal());
			
			dao.persist(e1);
			eventos = dao.findAllByClassName("Evento");
			assertEquals(1, eventos.size());
			assertEquals(0, eventos.get(0).getParticipantes().size());
			
			e1.addParticipante(u1); 	e1.addParticipante(u2);		e1.addParticipante(u3);		e1.addParticipante(u4);
			dao.merge(e1);
			eventos = dao.findAllByClassName("Evento");
			assertEquals(1, eventos.size());
			
			// verifica se foram adicionados os quatro usuários.
			assertEquals(4, eventos.get(0).getParticipantes().size());
			
			// testa se os usuarios estão mesmo no evento salvo.
			List<Usuario> participantes = eventos.get(0).getParticipantes();
			assertTrue(participantes.contains(u1));
			assertTrue(participantes.contains(u2));
			assertTrue(participantes.contains(u3));
			
			// se os dados são atualizados.
			u1.incrementaEventosAdim();
			// lembre-se que a experência dele é três pois ele já faz parte desse evento.
			assertEquals(3, u1.getExperiencia());
			dao.merge(u1);			
			
			participantes = eventos.get(0).getParticipantes();
			
			// testa se a experiência foi atualizada dentro da lista de participantes. 
			assertEquals(3, participantes.get(0).getExperiencia());
		} catch (PessoaInvalidaException e) {
			fail();
		} catch (LocalException e) {
			fail();
		} catch (EventoInvalidoException e) {
			fail();
		} catch (ImpossivelAddParticipante e) {
			fail();
		}
 	}
	
	@Test
	public void deveAddParticipanteEmVariosEventos(){
		temas = new ArrayList<>();
		temas.add(Tema.ARDUINO);
		temas.add(Tema.ELETRONICA);
		try {
			Local l = new Local("aúditorio central", 4, "na ufcg");
			
			Evento e1 = new Evento("evento teste1", "evento para testes",
					new Date(), l, temas, new GerentePrioridadeExperiencia());
			Evento e2 = new Evento("evento teste2", "evento para testes",
					new Date(), l, temas, new GerentePrioridadeExperiencia());
			
			Usuario u1 = new Usuario("jose@gmail.com", "12344", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.maria@gmail.com", "97878", "maria", new GerenteExperienciaNormal());
			
			dao.persist(u1);	dao.persist(u2);	dao.persist(e1);	dao.persist(e2);
			
			e1.addParticipante(u1);		e1.addParticipante(u2);
			e2.addParticipante(u1);		e2.addParticipante(u2);
			
			dao.merge(e1);				dao.merge(e2);
			assertEquals(2, (int)e1.getTotalDeParticipantes());
			assertEquals(2, (int)e2.getTotalDeParticipantes());
			
			List<Evento> eventos = dao.findAllByClassName("Evento");

			assertEquals(2, (int)eventos.get(0).getTotalDeParticipantes());
			assertEquals(2, (int)eventos.get(1).getTotalDeParticipantes());
		} catch (LocalException e) {
			fail();
		} catch (EventoInvalidoException e) {
			fail();
		} catch (PessoaInvalidaException e) {
			fail();
		} catch (ImpossivelAddParticipante e) {
			fail();
		}
		
		
	}
}