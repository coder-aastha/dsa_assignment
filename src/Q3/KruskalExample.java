package src.Q3;

import java.util.*;

// Class to represent an edge in the graph
class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}

// Class to represent a subset for union-find
class Subset {
    int parent, rank;

    public Subset(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

// Class to represent an undirected, connected graph with weighted edges
class Graph {
    List<Edge> edges;
    int vertices;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
    }

    // Add an edge to the graph
    public void addEdge(int src, int dest, int weight) {
        edges.add(new Edge(src, dest, weight));
    }

    // Kruskal's algorithm to find the minimum spanning tree
    public List<Edge> kruskalMST() {
        List<Edge> result = new ArrayList<>();

        // Sort the edges based on their weights
        Collections.sort(edges);

        // Create subsets for union-find
        Subset[] subsets = new Subset[vertices];
        for (int i = 0; i < vertices; i++) {
            subsets[i] = new Subset(i, 0);
        }

        int edgeIndex = 0;
        int resultIndex = 0;

        // Keep adding edges to the result until the minimum spanning tree is formed
        while (resultIndex < vertices - 1) {
            Edge nextEdge = edges.get(edgeIndex++);

            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            // If including this edge does not cause a cycle, add it to the result
            if (x != y) {
                result.add(nextEdge);
                union(subsets, x, y);
                resultIndex++;
            }
        }

        return result;
    }

    // Helper function to find the subset of an element
    private int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    // Helper function to perform union of two subsets
    private void union(Subset[] subsets, int x, int y) {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        // Attach the smaller rank tree under the root of the higher rank tree
        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            subsets[yRoot].parent = xRoot;
        } else {
            // If ranks are the same, make one as the root and increment its rank
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }
}

// Priority Queue using minimum heap
class MinHeap {
    private List<Edge> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void insert(Edge edge) {
        heap.add(edge);
        int currentIndex = heap.size() - 1;
        heapifyUp(currentIndex);
    }

    public Edge extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        Edge minEdge = heap.get(0);
        int lastIndex = heap.size() - 1;

        // Swap the root with the last element
        swap(0, lastIndex);

        // Remove the last element
        heap.remove(lastIndex);

        // Heapify down from the root
        heapifyDown(0);

        return minEdge;
    }

    private void heapifyUp(int currentIndex) {
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;

            if (heap.get(currentIndex).compareTo(heap.get(parentIndex)) < 0) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int currentIndex) {
        int leftChild = 2 * currentIndex + 1;
        int rightChild = 2 * currentIndex + 2;
        int smallest = currentIndex;

        if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) {
            smallest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) {
            smallest = rightChild;
        }

        if (smallest != currentIndex) {
            swap(currentIndex, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        Edge temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

public class KruskalExample {
    public static void main(String[] args) {
        // Example usage of Kruskal's algorithm
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);

        List<Edge> minimumSpanningTree = graph.kruskalMST();

        System.out.println("Minimum Spanning Tree Edges:");
        for (Edge edge : minimumSpanningTree) {
            System.out.println(edge.src + " - " + edge.dest + " Weight: " + edge.weight);
        }

        // Example usage of Priority Queue using Minimum Heap
        MinHeap minHeap = new MinHeap();
        minHeap.insert(new Edge(0, 1, 10));
        minHeap.insert(new Edge(0, 2, 6));
        minHeap.insert(new Edge(0, 3, 5));

        System.out.println("\nExtracted Minimum Edge: " + minHeap.extractMin().weight);
    }
}
