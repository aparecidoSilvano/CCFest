package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;

@Entity
public class Local {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	
	private String nomeLocal;
	
	
	private int capacidade;
	
	
	private String comoChegar;
	
	public Local(){
		
	}
	
	public Local(String nome, int capacidade, String descricao) {
		setNomeLocal(nome);
		setCapacidade(capacidade);
		setComoChegar(descricao);
	}

	/**
	 * @return the nome
	 */
	public String getNomeLocal() {
		return nomeLocal;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNomeLocal(String nome) {
		this.nomeLocal = nome;
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

	@Override
	public String toString() {
		return "Local [nome=" + nomeLocal + ", capacidade=" + capacidade
				+ ", descricao=" + comoChegar + "]";
	}

	/**
	 * @return the descricao
	 */
	public String getComoChegar() {
		return comoChegar;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setComoChegar(String descricao) {
		this.comoChegar = descricao;
	}

}
