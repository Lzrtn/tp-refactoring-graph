package org.acme.graph.routing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.acme.graph.TestGraphFactory;
import org.acme.graph.model.Edge;
import org.acme.graph.model.Path;
import org.acme.graph.model.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.acme.graph.errors.NotFoundException;

/**
 * Tests fonctionnels sur DijkstraPathFinder
 * 
 * @author MBorne
 *
 */
public class DijkstraRegressTest {

	private Graph graph;

	private DijkstraPathFinder finder;

	@Before
	public void setUp() throws Exception {
		this.graph = TestGraphFactory.createGraph01();
		this.finder = new DijkstraPathFinder(graph);
	}

	@Test
	public void testABFound() {
//		List<Edge> path = finder.findPath(graph.findVertex("a"), graph.findVertex("b"));
		Path path = finder.findPath(graph.findVertex("a"), graph.findVertex("b"));
		assertNotNull(path);
//		assertEquals(1, path.size());
		assertEquals(1, path.getEdges().size());
	}

	@Test
	public void testBANotFound() {
//		List<Edge> path = finder.findPath(graph.findVertex("b"), graph.findVertex("a"));
//		assertNull(path);
		assertThrows(NotFoundException.class,()-> finder.findPath(graph.findVertex("b"), graph.findVertex("a")));
	}

	@Test
	public void testACFoundWithCorrectOrder() {
//		List<Edge> path = finder.findPath(graph.findVertex("a"), graph.findVertex("c"));
		Path path = finder.findPath(graph.findVertex("a"), graph.findVertex("c"));
		assertNotNull(path);
//		assertEquals(2, path.size());
		assertEquals(2, path.getEdges().size());

		int index = 0;
		{
//			Edge edge = path.get(index++);
			Edge edge = path.getEdges().get(index++);
			assertEquals("a", edge.getSource().getId());
			assertEquals("b", edge.getTarget().getId());
		}
		{
//			Edge edge = path.get(index++);
			Edge edge = path.getEdges().get(index++);
			assertEquals("b", edge.getSource().getId());
			assertEquals("c", edge.getTarget().getId());
		}
	}
}
