//3(a)

package Q3;

import java.util.PriorityQueue;

public class ScoreTracker {

    private PriorityQueue<Double> lowerHalfHeap; // To store the lower half of scores
    private PriorityQueue<Double> upperHalfHeap; // To store the higher half of scores

    // Constructor to initialize the MedianScoreCalculator object
    public ScoreTracker() {
        lowerHalfHeap = new PriorityQueue<>((a, b) -> Double.compare(b, a));
        upperHalfHeap = new PriorityQueue<>();
    }

    // Method to add a new score to the data stream
    public void addScore(double score) {
        // Add the score to the appropriate heap
        if (lowerHalfHeap.isEmpty() || score <= lowerHalfHeap.peek()) {
            lowerHalfHeap.offer(score);
        } else {
            upperHalfHeap.offer(score);
        }

        // Balance the heaps to maintain the property: lowerHalfHeap.size() == upperHalfHeap.size() or lowerHalfHeap.size() == upperHalfHeap.size() + 1
        if (lowerHalfHeap.size() > upperHalfHeap.size() + 1) {
            upperHalfHeap.offer(lowerHalfHeap.poll());
        } else if (upperHalfHeap.size() > lowerHalfHeap.size()) {
            lowerHalfHeap.offer(upperHalfHeap.poll());
        }
    }

    // Method to calculate and return the median of all the scores in the data stream
    public double calculateMedianScore() {
        if (lowerHalfHeap.isEmpty()) {
            // If no scores are available, return 0.0
            return 0.0;
        }

        // If the number of scores is even, the median is the average of the two middle scores
        if (lowerHalfHeap.size() == upperHalfHeap.size()) {
            return (lowerHalfHeap.peek() + upperHalfHeap.peek()) / 2.0;
        } else {
            // If the number of scores is odd, the median is the middle score from the lowerHalfHeap
            return lowerHalfHeap.peek();
        }
    }

    // Main method to demonstrate the usage of the MedianScoreCalculator class
    public static void main(String[] args) {
        ScoreTracker scoreCalculator = new ScoreTracker();

        // Adding scores to the stream
        scoreCalculator.addScore(85.5);
        scoreCalculator.addScore(92.3);
        scoreCalculator.addScore(77.8);
        scoreCalculator.addScore(90.1);

        // Calculating and displaying the median
        double median1 = scoreCalculator.calculateMedianScore();
        System.out.println("Median 1: " + median1);

        // Adding more scores to the stream
        scoreCalculator.addScore(81.2);
        scoreCalculator.addScore(88.7);

        // Calculating and displaying the updated median
        double median2 = scoreCalculator.calculateMedianScore();
        System.out.println("Median 2: " + median2);
    }
}
