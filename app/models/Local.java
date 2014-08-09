package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="Usuario")
public class Local {
	@Id
	@GeneratedValue
	private long id;

	private String nome;
	private int capacidade;
	private String descricao;
	
	public Local(){
		
	}
	
	public Local(String nome, int capacidade, String descricao) {
		setNome(nome);
		setCapacidade(capacidade);
		setDescricao(descricao);
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the capacidade
	 */
	public int getCapacidade() {
		return capacidade;
	}

	/**
	 * @param capacidade the capacidade to set
	 */
	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
