package controllers;

import java.util.List;

import models.GerenteExperienciaNormal;
import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import models.exceptions.PessoaInvalidaException;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	private static Usuario usuarioLogado;

	public static Usuario getUsuarioLogado() {
		// veja que isso pode talvez quem sabe um dia retornar null.
		return usuarioLogado;
	}

	@Transactional
	public static Result index() {
		povoaBD();
		// session().clear();
		List<Usuario> lista = dao.findByAttributeName("Usuario", "nome",
				session().get("user"));

		if (lista == null || lista.isEmpty()) {
			// para o caso de não haver um usuario logado eu mando ele entrar no
			// sistema.
			return Login.showLogin();
		}

		usuarioLogado = lista.get(0);
		return ok(index.render(usuarioLogado));
	}

	public static GenericDAO getDao() {
		return dao;
	}

	// só pra testar vou inserir uns users no bd.
	private static void povoaBD() {
		try {
			Usuario u1 = new Usuario("jose.silva@gmail.com", "12345", "josé", new GerenteExperienciaNormal());
			Usuario u2 = new Usuario("maria.silva@gmail.com", "12345", "maria", new GerenteExperienciaNormal());
			Usuario u3 = new Usuario("silvana123@gmail.com", "12345", "silvana", new GerenteExperienciaNormal());
			Usuario u4 = new Usuario("joao.jose@gmail.com", "12345", "joao", new GerenteExperienciaNormal());
			Usuario u5 = new Usuario("jonas.silva@gmail.com", "12345", "jonas", new GerenteExperienciaNormal());
			Usuario u6 = new Usuario("mariquinha@gmail.com", "12345",
					"mariquinha", new GerenteExperienciaNormal());

			dao.persist(u1);
			dao.persist(u2);
			dao.persist(u3);
			dao.persist(u4);
			dao.persist(u5);
			dao.persist(u6);

		} catch (PessoaInvalidaException e) {
			
		}
	}

}
