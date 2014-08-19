package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import models.exceptions.EventoInvalidoException;
import models.exceptions.ImpossivelAddParticipante;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity(name = "Evento")
public class Evento {
	private static final int MAX_LENGHT_TITULO = 40;
	private static final int MAX_LENGHT_DESCRICAO = 450;
	

	@Id
	@GeneratedValue
	private long id;

	@Required
	@MaxLength(value = MAX_LENGHT_TITULO)
	private String titulo;
	
	@Required
	@MaxLength(value = MAX_LENGHT_DESCRICAO)
	@Column(name = "CONTENT", length = MAX_LENGHT_DESCRICAO)
	private String descricao;

	@Temporal(value = TemporalType.DATE)
	@Required
	private Date data;

	@ManyToMany(targetEntity = Usuario.class)
	private List<Usuario> participantes = new ArrayList<Usuario>();

	@ElementCollection
	@Enumerated(value = EnumType.ORDINAL)
	@NotNull
	private List<Tema> temas = new ArrayList<Tema>();
	
	@Required
	private boolean reservadoExperientes = false;
	
	@OneToOne(targetEntity = Local.class, cascade = CascadeType.ALL)
	private Local local;
	
	@OneToOne(targetEntity = GerenciadorDeParticipacao.class, cascade = CascadeType.ALL)
	private GerenciadorDeParticipacao gerenciadorDeParticipacao;

	public Evento() {
	}

	
	public Evento(String titulo, String descricao, Date data, Local local,
			List<Tema> temas, GerenciadorDeParticipacao gerente) throws EventoInvalidoException {
		
		setTitulo(titulo);
		setDescricao(descricao);
		setData(data);
		setLocal(local);
		setTemas(temas);
		setGerenteParticipacao(gerente);
		if(gerente instanceof GerenteParticipacaoPrioritario){
			reservadoExperientes = true;
		}
	}

	public boolean isReservadoExperientes() {
		return reservadoExperientes;
	}


	public void setReservadoExperientes(boolean reservadoExperientes) {
		this.reservadoExperientes = reservadoExperientes;
	}


	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getData() {
		return data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public void setTitulo(String titulo) throws EventoInvalidoException {
		if (titulo == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (titulo.length() > MAX_LENGHT_TITULO){
			throw new EventoInvalidoException("Título longo");
		}
		this.titulo = titulo;
	}

	public void setDescricao(String descricao) throws EventoInvalidoException {
		if (descricao == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (descricao.length() > MAX_LENGHT_DESCRICAO){
			throw new EventoInvalidoException("Descrição longa");
		}		
		this.descricao = descricao;
	}

	public void setData(Date data) throws EventoInvalidoException {
		if (data == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (data.compareTo(new Date()) < 0){
			throw new EventoInvalidoException("Data inválida");
		}
		this.data = data;
	}

	public void setTemas(List<Tema> temas) throws EventoInvalidoException {
		if (temas == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		if (temas.size() == 0){
			throw new EventoInvalidoException("Nenhum tema");
		}
		this.temas = temas;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) throws EventoInvalidoException {
		if(local == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		this.local = local;
	}
	
	public Integer getTotalDeParticipantes() {
		return participantes.size();
	}

	public List<Usuario> getParticipantes() {
		return participantes;
	}
	
	public void addParticipante(Usuario participante) throws ImpossivelAddParticipante{
		try {
			if(containsParticpante(participante)){
				throw new ImpossivelAddParticipante("O usuario " + participante.getNome() + " já está inscrito no evento");
			}
			gerenciadorDeParticipacao.addParticipante(this, participante);
			
			// atualiza o contador do participante
			participante.incrementaParticipacoes();
		} catch (ImpossivelAddParticipante e) {
			throw e;
		}		
	}

	private boolean containsParticpante(Usuario participante) {
		for (Usuario u : participantes) {
			if(u.getId() == participante.getId() && u.getNome() == participante.getNome()){
				return true;
			}
		}
		return false;
	}


	public GerenciadorDeParticipacao getGerenciadorDeParticipacao() {
		return gerenciadorDeParticipacao;
	}

	public void setGerenteParticipacao(
			GerenciadorDeParticipacao gerenciadorDeParticipacao) throws EventoInvalidoException {
		if(gerenciadorDeParticipacao == null){
			throw new EventoInvalidoException("Parametro nulo");
		}
		this.gerenciadorDeParticipacao = gerenciadorDeParticipacao;
	}
	
	public boolean haVagas(){
		if(getTotalDeParticipantes() < local.getCapacidade()){
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Evento [titulo=" + titulo + ", descricao=" + descricao
				+ ", data=" + data + ", participantes=" + participantes
				+ ", temas=" + temas + ", local=" + local + "]";
	}
}
