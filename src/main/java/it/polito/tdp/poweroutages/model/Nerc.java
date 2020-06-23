package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Nerc {
	private int id;
	private String value;
	private boolean aiutando;
	private List<NercMonth> aiutati;
	private int bonus;
	private Nerc aiutante;

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
		aiutati=new ArrayList<>();
		aiutando=false;
		bonus=0;
		aiutante=null;
	}
	
	
	

	public Nerc getAiutante() {
		return aiutante;
	}




	public void setAiutante(Nerc aiutante) {
		this.aiutante = aiutante;
	}




	public int getBonus() {
		return bonus;
	}




	public void setBonus(int bonus) {
		this.bonus = bonus;
	}




	public boolean isAiutando() {
		return aiutando;
	}



	public void setAiutando(boolean aiutando) {
		this.aiutando = aiutando;
	}



	public List<NercMonth> getAiutati() {
		return aiutati;
	}

	public void addAiutato(Nerc n, LocalDateTime data) {
		aiutati.add(new NercMonth(n,data));
		
	}






	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}
}
