package homework4Eclipse;

import java.util.ArrayList;
import java.util.TreeSet;

public class Graph {
	private int size;
	private Vertex[] vertices;

	/*
	 * 
	 * You may assume vertices given to you are numbered 0 to n-1,
	 * where n is the number of vertices
	 * 
	 */
	private class Vertex {
		ArrayList<Vertex> edges;
		int data;

		Vertex(int data) {
			this.data = data;
			edges = new ArrayList<>();
		}
	}

	/*
	 * Your constructor must take in a number of vertices
	 */
	Graph(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Graph must have at least 1 vertex");
		}
		size = n;
		vertices = new Vertex[n];
		for (int i = 0; i < n; i++) {
			vertices[i] = new Vertex(i);
		}
	}

	/**
	 * Adds an edge from start to end
	 * 
	 * It is reasonable to require your number of vertices to be set before
	 * this function is called (may throw an error if you haven't called
	 * setNumberVertices) if you choose.
	 * 
	 * @param start output vertex for edge
	 * @param end   input vertex for edge
	 */
	public void addEdge(int start, int end) {
		if (start >= size || end >= size || start < 0 || end < 0) {
			throw new IllegalArgumentException("That vertex does not exist");
		}
		if (!vertices[start].edges.contains(vertices[end])) {
			vertices[start].edges.add(vertices[end]);
		}
	}

	/**
	 * Returns true if there is an edge from start to end
	 * 
	 * @param start output vertex for edge
	 * @param end   input vertex for edge
	 * 
	 * @return If there is an edge from start to end
	 */
	public boolean hasEdge(int start, int end) {
		if (start >= size || end >= size) {
			throw new IllegalArgumentException("That vertex does not exist");
		}
		return vertices[start].edges.contains(vertices[end]);
	}

	/**
	 * 
	 * @return Returns true if our graph has a cycle anywhere. If our graph is
	 *         acyclic, return false.
	 */
	public boolean hasCycle() {
		if (size < 1) {
			return false;
		}

		if (size == 1) {
			return this.hasEdge(0, 0);
		}
		TreeSet<Integer> visited = new TreeSet<>(); // A TreeSet doesn't allow duplicates, and if a duplicate is found,
		visited.add(vertices[0].data);				// then we know to return true, and it makes more sense to use a TreeSet
		return this.hasCycle(vertices[1], visited); // over a HashSet because we don't need to store keys
	}

	/**
	 * Recursively determines if the current Graph has a cycle anywhere
	 * 
	 * @param current the current Vertex
	 * @param visited the TreeSet containing the values of all vertices we've visited already
	 * 
	 * @return true if the Graph is cyclic, false otherwise
	 */
	private boolean hasCycle(Vertex current, TreeSet<Integer> visited) {
		if (current.edges.size() == 0) {
			return false;
		}
		for (int i = 0; i < current.edges.size(); i++) {
			boolean added = visited.add(current.edges.get(i).data);
			if (!added || hasCycle(current.edges.get(i), visited)) {
				return true;
			}
		}
		return false;
	}
}