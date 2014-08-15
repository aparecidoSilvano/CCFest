package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import models.exceptions.LocalException;

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
	
	public Local(String nome, int capacidade, String comoChegar) throws LocalException {
		setNomeLocal(nome);
		setCapacidade(capacidade);
		setComoChegar(comoChegar);
	}

	/**
	 * @return the nome
	 */
	public String getNomeLocal() {
		return nomeLocal;
	}

	/**
	 * @param nome the nome to set
	 * @throws LocalException 
	 */
	public void setNomeLocal(String nome) throws LocalException {		
		if(nome == null){
			throw new LocalException("Parametro nulo");
		}if(nome.length() == 0){
			throw new LocalException("Parametro inválido");
		}
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
	 * @throws LocalException 
	 */
	public void setCapacidade(int capacidade) throws LocalException {
		if(capacidade < 0){
			throw new LocalException("Parametro inválido");
		}
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
	 * @throws LocalException 
	 */
	public void setComoChegar(String comoChegar) throws LocalException {
		if(comoChegar == null){
			throw new LocalException("Parametro nulo");
		}if(comoChegar.length() == 0){
			throw new LocalException("Parametro inválido");
		}
		this.comoChegar = comoChegar;
	}

}
