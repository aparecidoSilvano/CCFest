package controllers;

import java.util.List;

import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	private static GenericDAO dao = new GenericDAOImpl();
	private static Usuario usuarioLogado;

	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	@Transactional
	public static Result index() {
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



}
