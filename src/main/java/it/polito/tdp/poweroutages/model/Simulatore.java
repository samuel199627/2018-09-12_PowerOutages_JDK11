package it.polito.tdp.poweroutages.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.model.Evento.Tipo;

public class Simulatore {
	
	Model model;
	PriorityQueue<Evento> coda;
	SimpleWeightedGraph<Nerc, DefaultWeightedEdge> grafo=null;
	List<PowerOutage> interruzioni;
	int mesi;
	int numCatastofi;
	
	
	
	
	public int getNumCatastofi() {
		return numCatastofi;
	}

	public void setMesi(int mesi) {
		this.mesi = mesi;
	}

	public Simulatore(Model m) {
		model=m;
	}
	
	public void init() {
		coda=new PriorityQueue<>();
		numCatastofi=0;
		this.grafo=new SimpleWeightedGraph<Nerc, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.grafo=model.grafo;
		interruzioni=new ArrayList<>();
		interruzioni=model.interruzioni();
		
		for(PowerOutage p: interruzioni) {
			coda.add(new Evento(p.getInizio(),Tipo.INIZIO_CORRENTE,p));
			coda.add(new Evento(p.getFine(),Tipo.FINE_CORRENTE,p));
			
			System.out.println("AGGIUNTO");
		}
		
	}
	
	public void run() {
		while(!coda.isEmpty()) {
			Evento e=coda.poll();
			ProcessEvent(e);
		}
	}
	
	public void ProcessEvent(Evento e){
		Nerc nerc=e.getN().getN();
		switch(e.getType()) {
		case INIZIO_CORRENTE:
			//devo cercare chi mi puo' aiutare
			//prima cosa cerco se ho donanto a qualcuno negli ultimi 'mesi'
			Nerc richiedere=null;
			if(nerc.getAiutati().size()>0) {
				int pesoArcoMinore=Integer.MAX_VALUE;
				for(NercMonth n: nerc.getAiutati()) {
					int pesoArco=0;
					//controllo che l'aiuto sia stato negli ultimi 'mesi' altrimenti non devo considerarlo
					if(Math.abs(ChronoUnit.MONTHS.between(e.getTime().toLocalDate(), n.getDataAiuto().toLocalDate()))<=mesi) {
						//ho trovato un vicino che avevo aiutato negli ultimi 'mesi' quindi e' un potenziale a cui chiedere, ma devo cercare 
						//se ce ne sono altri per avere il peso di arco minore
						if(!n.getN().isAiutando()) { //controllo che posso fare la richiesta al Nerc, cioe' se non sta gia' aiutando qualcun altro
							pesoArco=(int) grafo.getEdgeWeight(grafo.getEdge(nerc, n.getN()));
							if(pesoArco<pesoArcoMinore) {
								pesoArcoMinore=pesoArco;
								richiedere=n.getN();
							}
						}
					}
				}
			}
			if(richiedere!=null) {
				nerc.setAiutante(richiedere);
				richiedere.setAiutando(true);
				richiedere.addAiutato(nerc, e.getTime());
				
			}
			else {//se non ho trovato nulla tra quelli che avevo gia' aiutato devo cercare tra i vicini sul grafo e prendere quello con arco minore
				int pesoArcoMinore=Integer.MAX_VALUE;
				for(Nerc n: Graphs.neighborListOf(grafo, nerc)) {
					int pesoArco=0;
					//controllo che l'aiuto sia stato negli ultimi 'mesi' altrimenti non devo considerarlo
					
					//ho trovato un vicino che avevo aiutato negli ultimi 'mesi' quindi e' un potenziale a cui chiedere, ma devo cercare 
					//se ce ne sono altri per avere il peso di arco minore
					if(!n.isAiutando()) { //controllo che posso fare la richiesta al Nerc, cioe' se non sta gia' aiutando qualcun altro
						pesoArco=(int) grafo.getEdgeWeight(grafo.getEdge(nerc, n));
						if(pesoArco<pesoArcoMinore) {
							pesoArcoMinore=pesoArco;
							richiedere=n;
						}
					}
					
				}
				if(richiedere!=null) {
					nerc.setAiutante(richiedere);
					richiedere.setAiutando(true);
					richiedere.addAiutato(nerc, e.getTime());
					
				}
				else { //non ho trovato ne' uno che avevo aiutato in passato e ne' uno tra i miei vicini
					numCatastofi++;
				}
				
				
			}
			
			break;
		case FINE_CORRENTE:
			if(nerc.getAiutante()!=null) {
				nerc.getAiutante().setBonus(nerc.getAiutante().getBonus()+(int) Math.abs(ChronoUnit.DAYS.between(e.getN().getFine().toLocalDate(),e.getN().getInizio().toLocalDate())));
				nerc.getAiutante().setAiutando(false);
			}
			nerc.setAiutante(null);
			break;
		}
	}

}
