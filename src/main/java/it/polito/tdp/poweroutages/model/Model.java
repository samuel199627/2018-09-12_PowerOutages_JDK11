package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	SimpleWeightedGraph<Nerc,DefaultWeightedEdge> grafo=null;
	PowerOutagesDAO dao;
	
	Map<Integer, Nerc> vertici;
	List<Adiacenza> adiacenze;
	
	public String creaGrafo() {
		String ritornare="";
		dao= new PowerOutagesDAO();
		
		vertici=new HashMap<>();
		vertici=dao.loadAllNercs();
		
		grafo=new SimpleWeightedGraph<Nerc,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, vertici.values());
		adiacenze=new ArrayList<>();
		adiacenze=dao.loadAllAdiacenze(vertici);
		
		for(Adiacenza a: adiacenze) {
			Graphs.addEdgeWithVertices(grafo, a.getN1(), a.getN2(), a.getPeso());
		}
		
		
		ritornare="GRAFO CREATO con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi.";
		return ritornare;
	}
	
	public List<Nerc> vertici(){
		List<Nerc> vertici=new ArrayList<>();
		
		for(Nerc n: grafo.vertexSet()) {
			vertici.add(n);
		}
		
		return vertici;
	}
	
	public String vicini(Nerc nerc) {
		String ritornare="VICINI:\n\n";
		List<VerticePeso> viciniPesati=new ArrayList<>();
		
		for(Nerc n: Graphs.neighborListOf(grafo, nerc)) {
			viciniPesati.add(new VerticePeso(n, (int) grafo.getEdgeWeight(grafo.getEdge(nerc, n))));
		}
		
		viciniPesati.sort(null);
		
		for(VerticePeso v: viciniPesati) {
			ritornare=ritornare+v.getN().toString()+" con peso "+v.getPeso()+"\n";
		}
		return ritornare;
	}
	
	public String simula(int mesi) {
		String ritornare="SIMULAZIONE: \n";
		Simulatore sim=new Simulatore(this);
		sim.init();
		sim.setMesi(mesi);
		sim.run();
		
		ritornare=ritornare+"NUMERO CATASTROFI: "+sim.getNumCatastofi()+"\n\n";
		for(Nerc n: grafo.vertexSet()) {
			ritornare=ritornare+n.toString()+" con BONUS "+n.getBonus()+"\n";
		}
		
		return ritornare;
	}
	
	public List<PowerOutage> interruzioni(){
		dao= new PowerOutagesDAO();
		return dao.loadAllPOwerOutage(vertici);
	}
	
	
	

}
