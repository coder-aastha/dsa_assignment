package Q2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecretSharing {

    // Method to find individuals who know the secret based on intervals and the first person
    public static List<Integer> identifySecretKeepers(int numberOfPeople, int[][] knowledgeIntervals, int firstPerson) {
        // Array to track whether each person knows the secret
        boolean[] knowsSecret = new boolean[numberOfPeople];

        // The first person knows the secret initially
        knowsSecret[firstPerson] = true;

        // Iterate through each knowledge interval
        for (int[] interval : knowledgeIntervals) {
            int intervalStart = interval[0];
            int intervalEnd = interval[1];

            // Check if anyone in the current interval knows the secret
            for (int i = intervalStart; i <= intervalEnd; i++) {
                if (knowsSecret[i]) {
                    // Share the secret with everyone in the interval
                    for (int j = intervalStart; j <= intervalEnd; j++) {
                        knowsSecret[j] = true;
                    }
                    break;
                }
            }
        }

        // List to store individuals who know the secret
        List<Integer> secretKeepers = new ArrayList<>();

        // Populate the secretKeepers list with individuals who know the secret
        for (int i = 0; i < numberOfPeople; i++) {
            if (knowsSecret[i]) {
                secretKeepers.add(i);
            }
        }

        return secretKeepers;
    }

    // Main method for testing the identifySecretKeepers method
    public static void main(String[] args) {
        // Example parameters
        int numberOfPeople = 5;
        int[][] knowledgeIntervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        // Find and print individuals who know the secret
        List<Integer> secretKeepers = identifySecretKeepers(numberOfPeople, knowledgeIntervals, firstPerson);

        System.out.println("Individuals who know the secret: " + Arrays.toString(secretKeepers.toArray()));
    }
}
