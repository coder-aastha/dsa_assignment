package Q4;

//4(b)

import java.util.*;

// Definition for binary tree node.
class Node {
    int value;
    Node leftChild;
    Node rightChild;

    Node(int val) {
        value = val;
    }
}

public class ClosestNodesFinder {
    // Method to find the x closest values to the target in a binary search tree
    public static List<Integer> findClosestNodes(Node root, double target, int x) {
        // Use a max heap to keep track of closest values to the target
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(x,
                (a, b) -> Double.compare(Math.abs(b - target), Math.abs(a - target)));
        performInOrderTraversal(root, target, x, maxHeap);
        return extractClosestValues(maxHeap);
    }

    // Method to perform in-order traversal of the binary search tree
    private static void performInOrderTraversal(Node currentNode, double target, int x,
            PriorityQueue<Integer> maxHeap) {
        Stack<Node> stack = new Stack<>();
        while (currentNode != null || !stack.isEmpty()) {
            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.leftChild;
            }

            currentNode = stack.pop();
            maxHeap.offer(currentNode.value);

            // If the size of the max heap exceeds x, remove the element with the farthest
            // distance
            if (maxHeap.size() > x) {
                maxHeap.poll();
            }

            currentNode = currentNode.rightChild;
        }
    }

    // Method to extract the x closest values from the max heap
    private static List<Integer> extractClosestValues(PriorityQueue<Integer> maxHeap) {
        List<Integer> closestNodes = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            closestNodes.add(maxHeap.poll());
        }
        return closestNodes;
    }

    public static void main(String[] args) {
        // Construct the binary search tree
        Node root = new Node(4);
        root.leftChild = new Node(2);
        root.rightChild = new Node(5);
        root.leftChild.leftChild = new Node(1);
        root.leftChild.rightChild = new Node(3);

        double target = 3.8;
        int x = 2;

        // Find x closest values to the target
        List<Integer> closestNodes = findClosestNodes(root, target, x);
        System.out.println("Closest values to " + target + ": " + closestNodes);
    }
}
