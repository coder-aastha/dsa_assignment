package Q2;

public class EqualizeDresses {

    // Function to calculate the minimum moves required to equalize the dresses in sewing machines
    public static int calculateMinimumMoves(int[] sewingMachines) {
        int machineCount = sewingMachines.length;
        int totalDresses = 0;

        // Calculate the total number of dresses
        for (int machine : sewingMachines) {
            totalDresses += machine;
        }

        // If the total number of dresses is not divisible by the number of machines,
        // it's not possible to equalize the dresses.
        if (totalDresses % machineCount != 0) {
            return -1;
        }

        // Calculate the target number of dresses that each machine should have after equalizing
        int targetDresses = totalDresses / machineCount;
        int minMoves = 0;      // Counter for the minimum moves required
        int currentDifference = 0; // Counter to keep track of the difference between current dress count and target

        // Iterate through each sewing machine
        for (int i = 0; i < machineCount; i++) {
            int difference = sewingMachines[i] - targetDresses;  // Calculate the difference between current dress count and target
            currentDifference += difference;               // Update the current sum of differences

            // The minimum moves required to equalize is the maximum of the currentDifference
            // and the difference between the current dress count and the target.
            minMoves = Math.max(minMoves, Math.abs(currentDifference));
        }

        // Return the minimum moves required
        return minMoves;
    }

    // Main method to demonstrate the usage of the calculateMinimumMoves function
    public static void main(String[] args) {
        int[] sewingMachines = {1, 0, 5};
        int result = calculateMinimumMoves(sewingMachines);
        System.out.println("Minimum moves required to equalize dresses: " + result);
    }
}
