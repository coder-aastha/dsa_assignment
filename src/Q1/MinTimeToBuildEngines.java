// 1(b)

package Q1;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MinTimeToBuildEngines {

    public static int calculateMinTimeToBuildEngines(int[] buildingTimes, int splitCost) {
        // Priority queue to store the time required for each engineer to build an engine
        PriorityQueue<Integer> timeQueue = new PriorityQueue<>();
        
        // Sort the building times in ascending order
        Arrays.sort(buildingTimes);
        
        // Iterate through each building time
        for (int time : buildingTimes) {
            if (!timeQueue.isEmpty() && time > timeQueue.peek()) {
                // If splitting is more efficient, split an engineer
                timeQueue.add(timeQueue.poll() + splitCost);
            } else {
                // Assign an engineer to build the current engine
                timeQueue.add(time);
            }
        }

        // The final answer is the maximum time from the priority queue
        return timeQueue.poll();
    }

    // Main method for testing the calculateMinTimeToBuildEngines method
    public static void main(String[] args) {
        // Example array of engine building times
        int[] buildingTimes = {3, 4, 5, 2};
        
        // Splitting cost for more efficient building
        int splitCost = 2;
        
        // Calculate and print the result
        int result = calculateMinTimeToBuildEngines(buildingTimes, splitCost);
        System.out.println("Minimum time to build engines: " + result);
    }
}
