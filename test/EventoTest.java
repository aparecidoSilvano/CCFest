import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.GerentePrioridadeExperiencia;
import models.Local;
import models.Tema;
import models.Usuario;
import models.gerenteNormal;
import models.exceptions.EventoInvalidoException;
import models.exceptions.InpossivelAddParticipante;

import org.junit.Before;
import org.junit.Test;

public class EventoTest {
	// testar a criação de eventos;
	// testar a adição de participantes segundo a logica da capacidade do loacal;
	// testar essa coisa de evento para usuarios experientes.
	
	private List<Tema> temas;
	
	@Before
	public void setUp(){
		temas = new ArrayList<>();
		temas.add(Tema.ARDUINO);
		temas.add(Tema.ELETRONICA);
	}
	
	// testa a adição de participantes no evento normal (sem prioridade)
	@Test
	public void deveAddParticipante(){
		try {
						
			Usuario usuario = new Usuario("jose.silva@gmail.com", "12345", "jose");
			Local l = new Local("aúditorio central", 100, "na ufcg");
			
			// e1 é um evento que gerência a adição de usuarios normalmente, de maneira sequêncial.
			Evento e1 = new Evento("evento teste1", "evento para testes", new Date(), l, temas, new gerenteNormal());
			e1.addParticipante(usuario);
			assertEquals((int)e1.getTotalDeParticipantes(), 1);				

			// e2 é um evento que gerência a adição de usuarios de acordo com a experiência do usuario.
			Evento e2 = new Evento("evento teste2", "evento para testes", new Date(), l, temas, new GerentePrioridadeExperiencia());
			e2.addParticipante(usuario);
			assertEquals((int)e2.getTotalDeParticipantes(), 1);
			
		} catch (EventoInvalidoException e) {
			System.out.println(e);
			fail();
		} catch (InpossivelAddParticipante e) {
			System.out.println(e);
			fail();
		}
	}

	@Test
	public void naoDeveAddParticipante(){
		try {
						
			Usuario usuario = new Usuario("jose.silva@gmail.com", "12345", "jose");
			Local l = new Local("aúditorio central", 100, "na ufcg");
			Evento e1 = new Evento("evento teste1", "evento para testes", new Date(), l, temas, new gerenteNormal());

			e1.addParticipante(usuario);
			
			assertEquals((int)e1.getTotalDeParticipantes(), 1);				
		} catch (EventoInvalidoException e) {
			fail();
		} catch (InpossivelAddParticipante e) {
			fail();
		}
	}
		
	
	
	
	@Test
	public void deveCriarEvento(){
		try {
			Local l = new Local("teatro qualquer", 1000, "perto do centro");
			
			Evento e1 = new Evento("teste 1", "primeiro evento ficticio criado", 
					new Date(), l, temas, new gerenteNormal());
			
			
		} catch (EventoInvalidoException e) {
			fail();
		}
	}
	
	@Test
	public void deveDarException() {
		Local l = new Local("teatro qualquer", 1000, "perto do centro");
		
		try {
			new Evento(null,
					"Vamos programar em Python!", new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					null, new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça", "Vamos programar em Python!", new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", new Date(), l, null, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", new Date(), l, new ArrayList<Tema>(), new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Nenhum tema", e.getMessage());
		}
		try {
			String descricaoLonga = "Vamos programar em Python!";
			
			for (int i = 0; i < 5; i++) {
				descricaoLonga += descricaoLonga;
			}
			
			new Evento("Python na cabeça",
					descricaoLonga, new Date(), l,temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Descrição longa", e.getMessage());
		}
		try {
			new Evento("Python na cabeça na mente e no coração uhuuu",
					"Vamos programar em Python!", new Date(), l, null, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Título longo", e.getMessage());
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, -1);

			new Evento("Python na cabeça",
					"Vamos programar em Python!", calendar.getTime(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals(2, 4);//("Data inválida", e.getMessage());
		}
	}
	
	
		
}
