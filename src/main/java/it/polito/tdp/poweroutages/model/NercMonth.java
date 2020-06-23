package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class NercMonth {
	
	Nerc n;
	LocalDateTime dataAiuto;
	
	public NercMonth(Nerc n, LocalDateTime dataAiuto) {
		super();
		this.n = n;
		this.dataAiuto = dataAiuto;
	}

	public Nerc getN() {
		return n;
	}

	public void setN(Nerc n) {
		this.n = n;
	}

	public LocalDateTime getDataAiuto() {
		return dataAiuto;
	}

	public void setDataAiuto(LocalDateTime dataAiuto) {
		this.dataAiuto = dataAiuto;
	}
	
	

}
