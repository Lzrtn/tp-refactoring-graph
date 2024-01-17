package org.acme.graph.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.acme.graph.errors.NotFoundException;
import org.acme.graph.model.Edge;
import org.acme.graph.model.Path;
import org.acme.graph.model.PathNode;
import org.acme.graph.model.PathTree;
import org.acme.graph.model.Graph;
import org.acme.graph.model.Vertex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * Utilitaire pour le calcul du plus court chemin dans un graphe
 * 
 * @author MBorne
 *
 */
public class DijkstraPathFinder {

	private static final Logger log = LogManager.getLogger(DijkstraPathFinder.class);

	private Graph graph;
//	private Map<Vertex,PathNode> nodes;
	private PathTree pathTree;

	public DijkstraPathFinder(Graph graph) {
		this.graph = graph;
	}
	
//	public DijkstraPathFinder(Graph graph, Map<Vertex,PathNode> nodes) {
//		this.graph = graph;
//		this.nodes = new HashMap<Vertex,PathNode>();
//	}
	
	public DijkstraPathFinder(Graph graph, Map<Vertex,PathNode> nodes) {
		this.graph = graph;
		this.pathTree = new PathTree();
	}
	
//	public PathNode getNode(Vertex vertex) {
//		return this.nodes.get(vertex);
//	}

	/**
	 * Calcul du plus court chemin entre une origine et une destination
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 */
//	public List<Edge> findPath(Vertex origin, Vertex destination) {
	public Path findPath(Vertex origin, Vertex destination) {
		log.info("findPath({},{})...", origin, destination);
//		initGraph(origin);
		this.pathTree = new PathTree(this.graph, origin);
		Vertex current;
		while ((current = findNextVertex()) != null) {
			visit(current);
//			if (destination.getReachingEdge() != null) {
//			if (getNode(destination).getReachingEdge() != null) {
			if (this.pathTree.getNode(destination).getReachingEdge() != null) {
				log.info("findPath({},{}) : path found", origin, destination);
				//return buildPath(destination);
				return new Path(this.pathTree.getPath(destination));
			}
		}
//		log.info("findPath({},{}) : path not found", origin, destination);
//		return null;
		throw new NotFoundException(String.format("Path not found from '%s' to '%s'", origin.getId(), destination.getId()));
	}

	/**
	 * Parcourt les arcs sortants pour atteindre les sommets avec le meilleur coût
	 * 
	 * @param vertex
	 */
	private void visit(Vertex vertex) {
		log.trace("visit({})", vertex);
		List<Edge> outEdges = graph.getOutEdges(vertex);
		/*
		 * On étudie chacun des arcs sortant pour atteindre de nouveaux sommets ou
		 * mettre à jour des sommets déjà atteint si on trouve un meilleur coût
		 */
		for (Edge outEdge : outEdges) {
			Vertex reachedVertex = outEdge.getTarget();
			/*
			 * Convervation de arc permettant d'atteindre le sommet avec un meilleur coût
			 * sachant que les sommets non atteint ont pour coût "POSITIVE_INFINITY"
			 */
//			double newCost = vertex.getCost() + outEdge.getCost();
//			if (newCost < reachedVertex.getCost()) {
//				reachedVertex.setCost(newCost);
//				reachedVertex.setReachingEdge(outEdge);
//			double newCost = getNode(vertex).getCost() + outEdge.getCost();
//			if (newCost < getNode(reachedVertex).getCost()) {
//				getNode(reachedVertex).setCost(newCost);
//				getNode(reachedVertex).setReachingEdge(outEdge);
			
			double newCost = this.pathTree.getNode(vertex).getCost() + outEdge.getCost();
			if (newCost < this.pathTree.getNode(reachedVertex).getCost()) {
				this.pathTree.getNode(reachedVertex).setCost(newCost);
				this.pathTree.getNode(reachedVertex).setReachingEdge(outEdge);
			}
		}
		
		/*
		 * On marque le sommet comme visité
		 */
//		vertex.setVisited(true);
//		getNode(vertex).setVisited(true);
		
		this.pathTree.getNode(vertex).setVisited(true);
	}

//	/**
//	 * Construit le chemin en remontant les relations incoming edge
//	 * 
//	 * @param target
//	 * @return
//	 */
//	private List<Edge> buildPath(Vertex target) {
//		List<Edge> result = new ArrayList<>();
//
////		Edge current = target.getReachingEdge();
//		Edge current = getNode(target).getReachingEdge();
//		do {
//			result.add(current);
////			current = current.getSource().getReachingEdge();
//			current = getNode(current.getSource()).getReachingEdge();
//		} while (current != null);
//
//		Collections.reverse(result);
//		return result;
//	}

//	/**
//	 * Prépare le graphe pour le calcul du plus court chemin
//	 * 
//	 * @param source
//	 */
//	private void initGraph(Vertex source) {
//		log.trace("initGraph({})", source);
//		for (Vertex vertex : graph.getVertices()) {
////			vertex.setCost(source == vertex ? 0.0 : Double.POSITIVE_INFINITY);
////			vertex.setReachingEdge(null);
////			vertex.setVisited(false);
//			getNode(vertex).setCost(source == vertex ? 0.0 : Double.POSITIVE_INFINITY);
//			getNode(vertex).setReachingEdge(null);
//			getNode(vertex).setVisited(false);
//
//		}
//	}

	/**
	 * Recherche le prochain sommet à visiter. Dans l'algorithme de Dijkstra, ce
	 * sommet est le sommet non visité le plus proche de l'origine du calcul de plus
	 * court chemin.
	 * 
	 * @return
	 */
	private Vertex findNextVertex() {
		double minCost = Double.POSITIVE_INFINITY;
		Vertex result = null;
		for (Vertex vertex : graph.getVertices()) {
//			// sommet déjà visité?
//			if (vertex.isVisited()) {
//				continue;
//			}
//			// sommet non atteint?
//			if (vertex.getCost() == Double.POSITIVE_INFINITY) {
//				continue;
//			}
//			// sommet le plus proche de la source?
//			if (vertex.getCost() < minCost) {
//				result = vertex;
//			}
			
			// sommet déjà visité?
			if (this.pathTree.getNode(vertex).isVisited()) {
				continue;
			}
			// sommet non atteint?
			if (this.pathTree.getNode(vertex).getCost() == Double.POSITIVE_INFINITY) {
				continue;
			}
			// sommet le plus proche de la source?
			if (this.pathTree.getNode(vertex).getCost() < minCost) {
				result = vertex;
			}
		}
		return result;
	}

}
