package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum Tipo{
		INIZIO_CORRENTE,
		FINE_CORRENTE
	}

	private LocalDateTime time;
	
	private Tipo type;
	private PowerOutage p;
	
	public Evento(LocalDateTime time, Tipo type, PowerOutage n) {
		super();
		this.time = time;
		this.type = type;
		this.p = n;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Tipo getType() {
		return type;
	}

	public void setType(Tipo type) {
		this.type = type;
	}

	public PowerOutage getN() {
		return p;
	}

	public void setN(PowerOutage n) {
		this.p = n;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return this.getTime().compareTo(o.getTime());
	}
	
	
	
}
