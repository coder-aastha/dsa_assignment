//1(a)

package Q1;

public class DecorationCostCalculator {

    // Method to calculate the minimum cost to decorate venues with different themes
    public static int calculateMinimumCost(int[][] venueCosts) {
        // Check if the input venue cost matrix is valid
        if (venueCosts == null || venueCosts.length == 0 || venueCosts[0].length == 0) {
            return 0; // Return 0 if the input is invalid
        }

        // Get the number of venues (rows) and the number of themes (columns)
        int numberOfVenues = venueCosts.length;
        int numberOfThemes = venueCosts[0].length;

        // Initialize a DP table to store the minimum cost at each venue and theme combination
        int[][] dp = new int[numberOfVenues][numberOfThemes];

        // Initialize the first row of the DP table with the costs of decorating the first venue
        System.arraycopy(venueCosts[0], 0, dp[0], 0, numberOfThemes);

        // Iterate through the venues starting from the second one
        for (int venueIndex = 1; venueIndex < numberOfVenues; venueIndex++) {
            for (int themeIndex = 0; themeIndex < numberOfThemes; themeIndex++) {
                // Initialize each dp[i][j] with Integer.MAX_VALUE to ensure it gets updated correctly
                dp[venueIndex][themeIndex] = Integer.MAX_VALUE;

                // For each theme, calculate the minimum cost of decorating the current venue
                // considering the minimum cost of the previous venue with a different theme
                for (int prevTheme = 0; prevTheme < numberOfThemes; prevTheme++) {
                    if (prevTheme != themeIndex) {
                        dp[venueIndex][themeIndex] = Math.min(dp[venueIndex][themeIndex], dp[venueIndex - 1][prevTheme] + venueCosts[venueIndex][themeIndex]);
                    }
                }
            }
        }

        // Find the minimum cost of decorating the last venue
        int minCost = Integer.MAX_VALUE;
        for (int themeCost : dp[numberOfVenues - 1]) {
            minCost = Math.min(minCost, themeCost);
        }

        // Return the overall minimum cost
        return minCost;
    }

    // Main method for testing the calculateMinimumCost method
    public static void main(String[] args) {
        // Example cost matrix
        int[][] costMatrix = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        
        // Calculate and print the result
        int result = calculateMinimumCost(costMatrix);
        System.out.println("Minimum cost to decorate venues: " + result); // Output: 7
    }
}

