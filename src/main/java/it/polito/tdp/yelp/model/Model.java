package it.polito.tdp.yelp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Business,DefaultWeightedEdge> grafo;
	private Map<String,Business> idMap;
	
	
	public Model() {
		dao=new YelpDao();
		idMap=new HashMap<String,Business>();
		dao.getAllBusiness(idMap);
	}
	
	public List<String> getAllCities(){
		return dao.getAllCities();
	}
	
	public void creaGrafo(String city) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getAllBusinessByCity(city));
		
		
		for(Adiacenza a: dao.getArchi(city, idMap)) {
			DefaultWeightedEdge e = null;
			grafo.addEdge(a.getB1(), a.getB2(), e);
			grafo.setEdgeWeight(e, a.getPeso());
		}
		
		//System.out.println(grafo.vertexSet().size()+" "+grafo.edgeSet().size());
		
	}
	
	public String numVertici(){
		return "#vertici= "+this.grafo.vertexSet().size();
	}
	
	public String numArchi() {
		return "#archi= "+this.grafo.edgeSet().size();
	}
	
	
}
