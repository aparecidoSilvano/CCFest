import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.GerenteExperienciaNormal;
import models.GerentePrioridadeExperiencia;
import models.Local;
import models.Tema;
import models.Usuario;
import models.gerenteNormal;
import models.exceptions.EventoInvalidoException;
import models.exceptions.ImpossivelAddParticipante;
import models.exceptions.LocalException;
import models.exceptions.PessoaInvalidaException;

import org.junit.Before;
import org.junit.Test;

import base.AbstractTest;

public class EventoTest {
	private List<Tema> temas;
	
	@Before
	public void setUp(){
		temas = new ArrayList<>();
		temas.add(Tema.ARDUINO);
		temas.add(Tema.ELETRONICA);
	}
	
	@Test
	public void deveCriarEvento(){
		try {		
			Local l = new Local("teatro qualquer", 1000, "perto do centro");
			
			new Evento("teste 1", "primeiro evento ficticio criado", 
					new Date(), l, temas, new gerenteNormal());
			
			new Evento("teste 1", "primeiro evento ficticio criado", 
					new Date(), l, temas, new gerenteNormal());	
			
		} catch (EventoInvalidoException e) {
			fail();
		} catch (LocalException e) {
			fail();
		}
	}
	
	@Test
	public void deveDarException() {
		// Nome nulo.
		try {
			new Local(null, 150, "na avenida principal");
		} catch (LocalException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		
		// Capacidade inválida
		try {
			new Local("teatro", -300, "na avenida principal");
		} catch (LocalException e) {
			assertEquals("Parametro inválido", e.getMessage());
		}
		
		// Descrição inválida
		try {
			new Local("teatro", 300, null);
		} catch (LocalException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		
		// Local nulo.	
		try {
			new Evento("Python na cabeça", "Vamos programar em Python!", new Date(), null, temas, new gerenteNormal());
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}	
		
		// cria local para ser utilizado por todos os outros eventos.
		Local l = null;
		
		try {
			l = new Local("teatro", 300, "na avenida principal");
		} catch (LocalException e1) {
			fail();
		}
		
		try {
			new Evento(null,
					"Vamos programar em Python!", new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					null, new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", null, l, temas, new GerentePrioridadeExperiencia());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", new Date(), l, temas, null);
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		try {
			new Evento("Python na cabeça",
					"Vamos programar em Python!", new Date(), l, new ArrayList<Tema>(), new GerentePrioridadeExperiencia());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Nenhum tema", e.getMessage());
		}
		try {
			String descricaoLonga = "Vamos programar em Python!";
			
			for (int i = 0; i < 5; i++) {
				descricaoLonga += descricaoLonga;
			}
			
			new Evento("Python na cabeça",
					descricaoLonga, new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Descrição longa", e.getMessage());
		}
		try {
			new Evento("Python na cabeça na mente e no coração uhuuu",
					"Vamos programar em Python!", new Date(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Título longo", e.getMessage());
		}
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_WEEK, -1);

			new Evento("Python na cabeça",
					"Vamos programar em Python!", calendar.getTime(), l, temas, new gerenteNormal());
			fail();
		} catch (EventoInvalidoException e) {
			assertEquals("Data inválida", e.getMessage());
		}
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
		} catch (ImpossivelAddParticipante e) {
			System.out.println(e);
			fail();
		} catch (PessoaInvalidaException e) {
			fail();
		} catch (LocalException e) {
			fail();
		}
	}
	
	@Test
	public void deveAddUsuarioMaisExperiente(){
		try {
			Usuario u1 = new Usuario("jose@gmail.com", "12344", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.maria@gmail.com", "97878", "maria", new GerenteExperienciaNormal());
			Usuario u3 = new Usuario("lucas@gmail.com", "12345", "lucas", new GerenteExperienciaNormal());
			Usuario u4 = new Usuario("lucia@gmail.com", "jkui232H", "lucia", new GerenteExperienciaNormal());
			Usuario u5 = new Usuario("joice.silva@gmail.com", "12345", "joice", new GerenteExperienciaNormal());
			
			Local l = new Local("aúditorio central", 4, "na ufcg");
			
			Evento e1 = new Evento("evento teste1", "evento para testes",
					new Date(), l, temas, new GerentePrioridadeExperiencia());
			
			// supondo que ele tenha adiministrado um evento.
			u5.incrementaEventosAdim();
			// ele ganhou experiência.
			assertEquals(2, u5.getExperiencia());
			
			e1.addParticipante(u1);		e1.addParticipante(u2);		e1.addParticipante(u3);
			e1.addParticipante(u4);		e1.addParticipante(u5);		//e1.addParticipante(u6);
			
			assertEquals((int) e1.getTotalDeParticipantes(), 4);
			
			// ele retirou o usuario 4 para inserir o 5.
			assertFalse(e1.getParticipantes().contains(u4));
			assertTrue(e1.getParticipantes().contains(u5));
		
		} catch (EventoInvalidoException e) {
			fail();
		} catch (ImpossivelAddParticipante e) {
			System.out.println(e.getMessage());
			fail();
		} catch (PessoaInvalidaException e) {
			fail();
		} catch (LocalException e) {
			fail();
		}
		
	}

	@Test
	public void naoDeveAddParticipante(){
		Evento e1 = new Evento();
		try {
			Usuario u1 = new Usuario("jose@gmail.com", "12344", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.maria@gmail.com", "97878", "maria", new GerenteExperienciaNormal());
			Usuario u3 = new Usuario("lucas@gmail.com", "12345", "lucas", new GerenteExperienciaNormal());
			Usuario u4 = new Usuario("lucia@gmail.com", "jkui232H", "lucia", new GerenteExperienciaNormal());
			Usuario u5 = new Usuario("joice.silva@gmail.com", "12345", "joice", new GerenteExperienciaNormal());			
			
			Local l = new Local("aúditorio central", 4, "na ufcg");
			
			e1 = new Evento("evento teste1", "evento para testes", new Date(), l, temas, new gerenteNormal());

			e1.addParticipante(u1);		e1.addParticipante(u2);			e1.addParticipante(u3);
			e1.addParticipante(u4);		e1.addParticipante(u5);
			
			assertEquals((int)e1.getTotalDeParticipantes(), 1);				
		} catch (EventoInvalidoException e) {
			fail();
		} catch (ImpossivelAddParticipante e) {
			// confere se realemnte não foi inserido o usuario
			assertEquals(e1.getParticipantes().size(), 4);
		} catch (PessoaInvalidaException e) {
			fail();
		} catch (LocalException e) {
			fail();
		}
	}
	
	@Test
	public void naoDeveAddParticipantePoucaExperiencia(){
		try {
			Usuario u1 = new Usuario("jose@gmail.com", "12344", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.maria@gmail.com", "97878", "maria", new GerenteExperienciaNormal());
			Usuario u3 = new Usuario("lucas@gmail.com", "12345", "lucas", new GerenteExperienciaNormal());
			Usuario u4 = new Usuario("lucia@gmail.com", "jkui232H", "lucia", new GerenteExperienciaNormal());
			Usuario u5 = new Usuario("joice.silva@gmail.com", "12345", "joice", new GerenteExperienciaNormal());
			
			Local l = new Local("aúditorio central", 4, "na ufcg");
			
			Evento e1 = new Evento("evento teste1", "evento para testes",
					new Date(), l, temas, new GerentePrioridadeExperiencia());
			
			u1.incrementaEventosAdim(); 	u1.incrementaParticipacoes();
			assertEquals(3, u1.getExperiencia());
			
			u2.incrementaEventosAdim();
			assertEquals(2, u2.getExperiencia());
			
			u3.incrementaParticipacoes();
			assertEquals(1, u3.getExperiencia());
			
			/*
			 * Repare que u4 e u5 possuem a mesma experiência,
			 * logo u5 não deve participar do evento.
			 */
			assertEquals(0, u4.getExperiencia());
			assertEquals(0, u5.getExperiencia());
			
			e1.addParticipante(u1);		e1.addParticipante(u2);		e1.addParticipante(u3);
			e1.addParticipante(u4);		e1.addParticipante(u5);
			
			assertEquals((int) e1.getTotalDeParticipantes(), 4);
			
			// ele não inseriu o u5
			assertTrue(e1.getParticipantes().contains(u4));
			assertFalse(e1.getParticipantes().contains(u5));
		
		} catch (EventoInvalidoException e) {
			fail();
		} catch (ImpossivelAddParticipante e) {
			assertTrue(true);
		} catch (PessoaInvalidaException e) {
			fail();
		} catch (LocalException e) {
			fail();
		}
	}
}
