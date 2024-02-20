//3(b)
package Q3;

import java.util.*;

// Class representing an Edge with source, destination, and weight
class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    // Comparing edges based on their weights
    @Override
    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }
}

// Class representing a Disjoint Set for Union-Find operations
class DisjointSet {
    int[] parent, rank;

    public DisjointSet(int vertices) {
        parent = new int[vertices];
        rank = new int[vertices];
        for (int i = 0; i < vertices; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    // Find operation with path compression
    public int find(int vertex) {
        if (parent[vertex] != vertex) {
            parent[vertex] = find(parent[vertex]);
        }
        return parent[vertex];
    }

    // Union operation with rank optimization
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}

// Class representing Kruskal's Minimum Spanning Tree algorithm
public class MinimumSpanningTree {
    private List<Edge> edges; // List to store edges of the graph
    private int numberOfVertices; // Number of vertices in the graph

    // Constructor to initialize the number of vertices and edges list
    public MinimumSpanningTree(int numberOfVertices) {
        this.numberOfVertices = numberOfVertices;
        edges = new ArrayList<>();
    }

    // Method to add an edge to the graph
    public void addEdge(int source, int destination, int weight) {
        edges.add(new Edge(source, destination, weight));
    }

    // Method to find the Minimum Spanning Tree using Kruskal's algorithm
    public List<Edge> findMST() {
        List<Edge> result = new ArrayList<>();

        // Sort edges in non-decreasing order of their weights
        Collections.sort(edges);

        DisjointSet disjointSet = new DisjointSet(numberOfVertices);

        // Iterate through sorted edges
        for (Edge edge : edges) {
            int sourceRoot = disjointSet.find(edge.source);
            int destinationRoot = disjointSet.find(edge.destination);

            // If including this edge does not create a cycle, add it to the result
            if (sourceRoot != destinationRoot) {
                result.add(edge);
                disjointSet.union(sourceRoot, destinationRoot);
            }
        }

        return result;
    }

    // Main method for testing the MinimumSpanningTree class
    public static void main(String[] args) {
        // Example usage:
        MinimumSpanningTree graph = new MinimumSpanningTree(4);

        // Adding edges to the graph
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        // Finding and printing the edges in the Minimum Spanning Tree
        List<Edge> result = graph.findMST();
        System.out.println("Edges in Minimum Spanning Tree:");
        for (Edge edge : result) {
            System.out.println(edge.source + " - " + edge.destination + ": " + edge.weight);
        }
    }
}
