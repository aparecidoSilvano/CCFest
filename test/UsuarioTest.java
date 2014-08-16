import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.ExperienciaComparator;
import models.GerenteExperienciaNormal;
import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.PessoaInvalidaException;

import org.junit.Test;

import base.AbstractTest;


public class UsuarioTest extends AbstractTest{
	private static GenericDAO dao = new GenericDAOImpl();

	
	@Test
	public void DeveCriarUsuarios() {
		try {
			new Usuario("jose.silva@gmail.com", "12345", "jose", new GerenteExperienciaNormal());
			new Usuario("maria.nobrega@gmail.com", "airam123", "maria", new GerenteExperienciaNormal());
		} catch (PessoaInvalidaException e) {
			fail();
		}		
	}
	
	@Test
	public void DeveDarExecao() {
		// email nulo
		try {
			new Usuario(null, "12345", "teste1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		
		// email inválido
		try {
			new Usuario("teste1gmail.com", "12345", "test1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email inválido", e.getMessage());
		}

		// email inválido
		try {
			new Usuario("teste1@gmail..com", "12345", "test1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email inválido", e.getMessage());
		}

		// email inválido
		try {
			new Usuario("teste1@gmailcom", "12345", "test1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Email inválido", e.getMessage());
		}

		// senha nula
		try {
			new Usuario("teste1@gmail.com", null, "teste1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		
		// senha inválida
		try {
			new Usuario("teste1@gmail.com", "", "teste1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Senha muito curta", e.getMessage());
		}

		// senha inválida
		try {
			new Usuario("teste1@gmail.com", "123", "teste1", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Senha muito curta", e.getMessage());
		}
		
		// nome nulo
		try {
			new Usuario("teste1@gmail.com", "12345", null, new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Parametro nulo", e.getMessage());
		}
		
		// nome nulo
		try {
			new Usuario("teste1@gmail.com", "12345", "David Paulo da Silva Alcântara Barbosa Melo Nobrega Bragança Albuquerque", new GerenteExperienciaNormal());
			fail();
		} catch (PessoaInvalidaException e) {
			assertEquals("Nome longo", e.getMessage());
		}

	}
	
	@Test
	public void testsComBanco() {
		// testa a inserção e a remoção.
		try {
			List<Usuario> usuarios = dao.findAllByClassName("Usuario");
			assertThat(usuarios.size()).isEqualTo(0);
			
			Usuario u1 = new Usuario("jose.silva@gmail.com", "12345", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.nobrega@gmail.com", "airam123", "maria", new GerenteExperienciaNormal());
			dao.persist(u1);			
			dao.persist(u2);			
			
			usuarios = dao.findAllByClassName("Usuario");
			assertThat(usuarios.size()).isEqualTo(2);
			
			dao.remove(u2);
			usuarios = dao.findAllByClassName("Usuario");
			assertThat(usuarios.size()).isEqualTo(1);
			
			dao.remove(u1);
			usuarios = dao.findAllByClassName("Usuario");
			assertThat(usuarios.size()).isEqualTo(0);
		
		} catch (PessoaInvalidaException e) {
			fail();
		}
	}
	
	@Test
	public void DeveCalcularExperiencia() {
		try {
			Usuario usuario = new Usuario("jose.silva@gmail.com", "12345", "jose", new GerenteExperienciaNormal());
			usuario.setGerenExperiencia(new GerenteExperienciaNormal());
			assertThat(usuario.getExperiencia()).isEqualTo(0);
			
			// o usuario adiministrou dois eventos.
			usuario.incrementaEventosAdim();
			usuario.incrementaEventosAdim();
			
			assertThat(usuario.getExperiencia()).isEqualTo(4);

			// o usuario adiministrou dois eventos e participou de evento.
			usuario.incrementaParticipacoes();
			assertThat(usuario.getExperiencia()).isEqualTo(5);
			
			// testa com o bd.
			dao.persist(usuario);
			List<Usuario> l = dao.findAllByClassName("Usuario");
			Usuario u = l.get(0);
			
			assertThat(u.getExperiencia()).isEqualTo(5);
		} catch (PessoaInvalidaException e) {
			fail();
		}
		
	}
	
	@Test
	public void DeveOrdenarPelaExperiencia() {
		List<Usuario> participantes = new ArrayList<Usuario>();
		
		try {
			Usuario u1 = new Usuario("jose@gmail.com", "12344", "jose", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.maria@gmail.com", "97878", "maria", new GerenteExperienciaNormal());
			Usuario u3 = new Usuario("lucas@gmail.com", "12345", "lucas", new GerenteExperienciaNormal());
			Usuario u4 = new Usuario("lucia@gmail.com", "jkui232H", "lucia", new GerenteExperienciaNormal());
			Usuario u5 = new Usuario("joice.silva@gmail.com", "12345", "joice", new GerenteExperienciaNormal());
			Usuario u6 = new Usuario("pedro89@gmail.com", "pedro123", "pedro", new GerenteExperienciaNormal());
						
			u3.incrementaEventosAdim(); 	u3.incrementaEventosAdim();
			u2.incrementaParticipacoes(); 	u2.incrementaParticipacoes();
			u6.incrementaEventosAdim(); 	u6.incrementaParticipacoes();
			
			participantes.add(u1);		participantes.add(u2);		participantes.add(u3);
			participantes.add(u4);		participantes.add(u5);		participantes.add(u6);
			
			Collections.sort(participantes, new ExperienciaComparator());
			
			assertEquals(u3, participantes.get(0));
			assertEquals(u6, participantes.get(1));
			assertEquals(u2, participantes.get(2));
			assertEquals(u1, participantes.get(3));
			assertEquals(u4, participantes.get(4));
			assertEquals(u5, participantes.get(5));
			
		} catch (PessoaInvalidaException e) {
			fail();
		}
	}

}
