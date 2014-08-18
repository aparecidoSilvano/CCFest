package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import models.exceptions.PessoaInvalidaException;

import org.hibernate.validator.constraints.Email;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.Required;

@Entity(name = "Usuario")
public class Usuario {
	private final int MAX_LENGHT_NOME = 70;
	private final int MAX_LENGHT_EMAIL = 70;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;
	
	public Long getId() {
		return id;
	}
	
	private final String EMAILPATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Email
	@MaxLength(value = MAX_LENGHT_NOME)
	private String email;
	
	private String senha;
	
	@MaxLength(value = MAX_LENGHT_EMAIL)
	private String nome = "";
	
	@Required
	private int numParticipacoes = 0;
	
	@Required
	private int numEvetosAdim = 0;
	
	@OneToOne(targetEntity = GerenciadorExpereincia.class, cascade = CascadeType.ALL)
	private GerenciadorExpereincia gerenExperiencia;
	
	public Usuario() {
	}
	
	public Usuario(String email, String senha, String nome,
			GerenciadorExpereincia gerenteExperiencia) throws PessoaInvalidaException {
		setEmail(email);
		setNome(nome);
		setSenha(senha);
		setGerenExperiencia(gerenteExperiencia);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws PessoaInvalidaException {
		if (email == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (!email.matches(EMAILPATTERN)){
			throw new PessoaInvalidaException("Email inválido");
		}
		if (email.length() > MAX_LENGHT_EMAIL){
			throw new PessoaInvalidaException("Email longo");
		}
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nome=" + nome + ", experiência " + getExperiencia()+ "]";
	}

	public void setSenha(String senha) throws PessoaInvalidaException {
		if(senha == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}if(senha.length() < 5){
			throw new PessoaInvalidaException("Senha muito curta");
		}		
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws PessoaInvalidaException {
		if (nome == null){
			throw new PessoaInvalidaException("Parametro nulo");
		}
		if (nome.length() > MAX_LENGHT_NOME){
			throw new PessoaInvalidaException("Nome longo");
		}
		this.nome = nome;
	}

	
	/**
	 * @return the numParticipacoes
	 */
	public int getNumParticipacoes() {
		return numParticipacoes;
	}

	/**
	 * @param numParticipacoes the numParticipacoes to set
	 */
	public void incrementaParticipacoes() {
		this.numParticipacoes++;
	}
	
	public int getExperiencia(){
		if(gerenExperiencia != null){
			return gerenExperiencia.calculaExperiencia(this);
		}
		return 0;
	}

	/**
	 * @return the gerenExperiencia
	 */
	public GerenciadorExpereincia getGerenExperiencia() {
		return gerenExperiencia;
	}
	
	/**
	 * @param gerenExperiencia the gerenExperiencia to set
	 */
	public void setGerenExperiencia(GerenciadorExpereincia gerenExperiencia) {
		this.gerenExperiencia = gerenExperiencia;
	}

	
	
	public void incrementaEventosAdim() {
		this.numEvetosAdim++;		
	}
	
	public int getNumEvetosAdim() {
		return numEvetosAdim;
	}
	
}
