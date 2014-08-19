package controllers;

import static play.data.Form.form;
import models.Usuario;
import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Registro extends Controller {
	
	private static GenericDAO dao = new GenericDAOImpl();
	private static Form<Usuario> registroForm = form(Usuario.class).bindFromRequest();

	@Transactional
    public static Result show() {
        return ok(registro.render(registroForm));
    }
    
	@Transactional
	public static Result registrar() {
		Usuario u;
		try {
			u = registroForm.bindFromRequest().get();
		} catch (IllegalStateException e) {
			return badRequest(registro.render(registroForm));
		}
		
		if (registroForm.hasErrors() || validate(u.getEmail())) {
			flash("fail", "Email já está em uso");
            return badRequest(registro.render(registroForm));
        } else {
        	dao.persist(u);
        	
        	// limpa a session pra garantir que eu possa fazer o login novamente.
        	session().clear();
            return redirect(routes.Login.showLogin());
        }
    }
	
	private static boolean validate(String enderecoEmail) {
		boolean status = false;

		final String[] emails = enderecoEmail.split(",");
		for (String email : emails) {
			email = email.replace(" ", "");
			if (email.matches(".+@.+\\.[a-z]+")) {
				status = true;
			}
		}
		return status;
	}
}
