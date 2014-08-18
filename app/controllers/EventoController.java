package controllers;

import static play.data.Form.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.EventoComparator;
import models.GerenciadorDeParticipacao;
import models.GerenteParticipacaoPrioritario;
import models.Local;
import models.Tema;
import models.Usuario;
import models.GerenteParticipacaoNormal;
import models.exceptions.EventoInvalidoException;
import models.exceptions.ImpossivelAddParticipante;
import models.exceptions.LocalException;
import models.exceptions.PessoaInvalidaException;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Enumeration.Val;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventoController extends Controller {

	private final static Form<Evento> EVENTO_FORM = form(Evento.class);
//	private final static Form<Participante> participanteForm = form(Participante.class);

	@Transactional
	public static Result eventosPorTema(int id) throws PessoaInvalidaException, EventoInvalidoException{
	
		List<Evento> todosEventos = Application.getDao().findAllByClassName("Evento");
		
		List<Evento> eventosRequeridos = new ArrayList<Evento>();
		
		for (Evento ev : todosEventos) {
			if (ev.getTemas().contains(Tema.values()[(int) id])){
				eventosRequeridos.add(ev);
			}
		}

		Collections.sort(eventosRequeridos, new EventoComparator());
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		try {
			json = mapper.writeValueAsString(eventosRequeridos);
		} catch (Exception e) {
			return badRequest();
		}
		
		return ok(json);
	}
	
	@Transactional
	public static Result novo() throws PessoaInvalidaException, EventoInvalidoException, ParseException, LocalException{
		Form<Evento> eventoFormRequest = EVENTO_FORM.bindFromRequest();
				
		// Para recuperar os valores do local.
		String titulo = eventoFormRequest.field("titulo").value();
		String descricao = eventoFormRequest.field("descricao").value();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date data =  df.parse(eventoFormRequest.field("data").value());
		String nomeLocal = eventoFormRequest.field("nomeLocal").value();
		String comoChegar = eventoFormRequest.field("comoChegar").value();
		int capacidade = Integer.valueOf(eventoFormRequest.field("capacidade").value());
		
		boolean prioritario = false;
		if(eventoFormRequest.field("prioritario").value() != null && eventoFormRequest.field("prioritario").value().equals("on")){
			prioritario = true;
		}		
	
		Local local = new Local(nomeLocal, capacidade, comoChegar);
		
		List<Tema> temas = new ArrayList<Tema>();
		for(Tema tema : Tema.values()) {
			if (eventoFormRequest.field(tema.name()).value() != null && !eventoFormRequest.field(tema.name()).equals("null")) {
				temas.add(tema);
			}
		}
		
		GerenciadorDeParticipacao gerente;
		if(prioritario){
			gerente = new GerenteParticipacaoPrioritario();
		}else{
			gerente = new GerenteParticipacaoNormal();
		}
		
		Evento novoEvento = new Evento(titulo, descricao, data, local, temas, gerente);		
		
		if (EVENTO_FORM.hasErrors()) {
			return badRequest();
		} else {
			// é necessario atualizar o contador do usuario que criou esse evento.
			Usuario usuario = Application.getUsuarioLogado();
			usuario.incrementaEventosAdim();
			Application.getDao().merge(usuario);
			Application.getDao().persist(novoEvento);
			Application.getDao().merge(novoEvento);
			Application.getDao().flush();
			return redirect(controllers.routes.Application.index());
		}
	}
	
	@Transactional
	public static Result participar(long id) throws PessoaInvalidaException, EventoInvalidoException{
		// A ideia aqui é só pegar o usuario logado e adicionar esse user na lista do evento.
		Usuario usuarioLogado = Application.getUsuarioLogado();
		
		Evento evento = Application.getDao().findByEntityId(Evento.class, id);
		try {
			evento.addParticipante(usuarioLogado);
			Application.getDao().merge(usuarioLogado);
			Application.getDao().merge(evento);
			Application.getDao().flush();
			return redirect(controllers.routes.Application.index());
		} catch (ImpossivelAddParticipante e) {
			return badRequest();
		}
	}
}
