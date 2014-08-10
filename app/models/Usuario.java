package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

@Entity()
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
//	@OneToMany(mappedBy = "usuario")
//	private List<Evento> eventosAdim = new ArrayList<Evento>();
	
	@Email
	@MaxLength(value = 70)
	private String email;
	
	private String pass;
	
	@MaxLength(value = 70)
	private String nome = "";
	
	public Usuario() {
	}
	
	public Usuario(String email, String pass, String nome) {
		setEmail(email);
		setNome(nome);
		setPass(pass);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	@Override
	public String toString() {
		return "Usuario [email=" + email + ", nome=" + nome + "]";
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
