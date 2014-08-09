package models;

import java.util.ArrayList;
import java.util.Collections;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

@Entity(name="Usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany(mappedBy = "evento")
	private List<Evento> eventosAdim = new ArrayList<Evento>();
	
	/**
	 * Retorna a lista de eventos que esse usuario é o adiministrador.
	 * @return
	 */
	public List<Evento> getEventosAdim() {
		return Collections.unmodifiableList(eventosAdim);
	}

	public void setEventosAdim(List<Evento> eventosAdim) {
		this.eventosAdim = eventosAdim;
	}

	private String email;
	private String pass;
	private String nome;
	
	public Usuario() {
	}
	
	public Usuario(String email, String pass, String nome) {
		this.email = email;
		this.nome = nome;
		this.pass = pass;
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
