package controllers;

import static play.data.Form.form;

import java.util.List;

import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class Login extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	static Form<Usuario> loginForm = form(Usuario.class).bindFromRequest();
	
	// só pra testar vou inserir uns users no bd.
	private static void povoaBD(){
		Usuario u1 = new Usuario("jose.silva@gmail.com", "12345", "josé");
		Usuario u2 = new Usuario("maria.silva@gmail.com", "12345", "maria");
		Usuario u3 = new Usuario("silvana123@gmail.com", "12345", "silvana");
		Usuario u4 = new Usuario("joao.jose@gmail.com", "12345", "joao");
		Usuario u5 = new Usuario("jonas.silva@gmail.com", "12345", "jonas");
		Usuario u6 = new Usuario("mariquinha@gmail.com", "12345", "mariquinha");
		
		dao.persist(u1);	dao.persist(u2);	dao.persist(u3);	dao.persist(u4);
		dao.persist(u5);	dao.persist(u6);
	}
	
	@Transactional
    public static Result show() {
		povoaBD();		
		if (session().get("user") != null) {
			return redirect(routes.Application.index());
		}
        return ok(login.render(loginForm));
    }
	
	@Transactional
	public static Result authenticate() {
		System.out.println("entrou no authenticate");
		Usuario u = loginForm.bindFromRequest().get();
		
		String email = u.getEmail();
		String senha = u.getPass();

        if (loginForm.hasErrors() || !validate(email, senha)) {
        	flash("fail", "Email ou Senha Inválidos");
        	return badRequest(login.render(loginForm));
        } else {
        	Usuario user = (Usuario) dao.findByAttributeName(
        			"Usuario", "email", u.getEmail()).get(0);
            session().clear();
            session("user", user.getNome());
            return redirect(
                routes.Application.index()
            );
        }
    }
	
	private static boolean validate(String email, String senha) {
		List<Usuario> u = dao.findByAttributeName("Usuario", "email", email);
		if (u == null || u.isEmpty()) {
			return false;
		}
		if (!u.get(0).getPass().equals(senha)) {
			return false;
		}
		
		return true;
	}
}
