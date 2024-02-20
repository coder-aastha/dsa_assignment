package src.Q3;

import java.util.PriorityQueue;

public class ScoreTracker {
    private PriorityQueue<Double> maxHeap; // Represents the left half of the scores (lower values)
    private PriorityQueue<Double> minHeap; // Represents the right half of the scores (higher values)

    public ScoreTracker() {
        // Initialize the max and min heaps
        maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b, a)); // Max heap for the left half
        minHeap = new PriorityQueue<>(); // Min heap for the right half
    }

    // Adds a new assignment score to the data stream
    public void addScore(double score) {
        // Add the score to the appropriate heap
        if (maxHeap.isEmpty() || score <= maxHeap.peek()) {
            maxHeap.offer(score);
        } else {
            minHeap.offer(score);
        }

        // Balance the heaps to maintain the property that maxHeap.size() >= minHeap.size()
        if (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
        } else if (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
        }
    }

    // Returns the median of all the assignment scores in the data stream
    public double getMedianScore() {
        if (maxHeap.isEmpty()) {
            return 0.0; // No scores available
        }

        if (maxHeap.size() == minHeap.size()) {
            // If the number of scores is even, return the average of the two middle scores
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            // If the number of scores is odd, return the middle score from maxHeap
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1);

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2);
    }
}

