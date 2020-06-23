package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	
	private int id;
	private Nerc n;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	
	public PowerOutage(int id, Nerc n, LocalDateTime inizio, LocalDateTime fine) {
		super();
		this.id = id;
		this.n = n;
		this.inizio = inizio;
		this.fine = fine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getN() {
		return n;
	}

	public void setN(Nerc n) {
		this.n = n;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	
	
	
	

}
