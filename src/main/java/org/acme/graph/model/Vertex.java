package org.acme.graph.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;

/**
 * 
 * Un sommet dans un graphe
 * 
 * @author MBorne
 *
 */
public class Vertex {

	/**
	 * Identifiant du sommet
	 */
	private String id;

	/**
	 * Position du sommet
	 */
	private Coordinate coordinate;

//	/**
//	 * dijkstra - coût pour atteindre le sommet
//	 */
//	private double cost;
//	/**
//	 * dijkstra - arc entrant avec le meilleur coût
//	 */
//	private Edge reachingEdge;
//	/**
//	 * dijkstra - indique si le sommet est visité
//	 */
//	private boolean visited;
	
	private List<Edge> inEdges;
	private List<Edge> outEdges;

	//public Vertex() {}
	Vertex(){
		this.inEdges = new ArrayList<Edge>();
		this.outEdges = new ArrayList<Edge>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

//	@JsonIgnore
//	public double getCost() {
//		return cost;
//	}
//
//	public void setCost(double cost) {
//		this.cost = cost;
//	}
//
//	@JsonIgnore
//	public Edge getReachingEdge() {
//		return reachingEdge;
//	}
//
//	public void setReachingEdge(Edge reachingEdge) {
//		this.reachingEdge = reachingEdge;
//	}
//
//	public boolean isVisited() {
//		return visited;
//	}
//
//	public void setVisited(boolean visited) {
//		this.visited = visited;
//	}
	
	@JsonIgnore
	public List<Edge> getInEdges(){
		return inEdges;
	}
	
	@JsonIgnore
	public List<Edge> getOutEgdes(){
		return outEdges;
	}

	@Override
	public String toString() {
		return id;
	}

}
