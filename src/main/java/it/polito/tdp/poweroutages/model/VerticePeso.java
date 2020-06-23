package it.polito.tdp.poweroutages.model;

public class VerticePeso implements Comparable<VerticePeso>{
	
	Nerc n;

	int peso;
	

	


	public VerticePeso(Nerc n, int peso) {
		super();
		this.n = n;
		this.peso = peso;
	}


	public Nerc getN() {
		return n;
	}


	public void setN(Nerc n) {
		this.n = n;
	}


	public int getPeso() {
		return peso;
	}


	public void setPeso(int peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(VerticePeso o) {
		// TODO Auto-generated method stub
		return -(this.getPeso()-o.getPeso());
	}
	
	

}
