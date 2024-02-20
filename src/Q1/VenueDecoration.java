package src.Q1;

public class VenueDecoration {

  public static int minCostToDecorate(int[][] costs) {
      if (costs == null || costs.length == 0 || costs[0].length == 0) {
          return 0;
      }

      int n = costs.length;
      int k = costs[0].length;

      // dp[i][j] represents the minimum cost to decorate venues up to index i with theme j
      int[][] dp = new int[n][k];

      // Initialize the first row of dp with the costs of decorating the first venue
      for (int j = 0; j < k; j++) {
          dp[0][j] = costs[0][j];
      }

      // Build the dp array iteratively
      for (int i = 1; i < n; i++) {
          for (int j = 0; j < k; j++) {
              // Find the minimum cost of decorating venue i with theme j
              dp[i][j] = Integer.MAX_VALUE;
              for (int l = 0; l < k; l++) {
                  if (l != j) {
                      dp[i][j] = Math.min(dp[i][j], dp[i - 1][l] + costs[i][j]);
                  }
              }
          }
      }

      // Find the minimum cost from the last row of dp
      int minCost = Integer.MAX_VALUE;
      for (int j = 0; j < k; j++) {
          minCost = Math.min(minCost, dp[n - 1][j]);
      }

      return minCost;
  }

  public static void main(String[] args) {
      int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
      int result = minCostToDecorate(costs);
      System.out.println("Minimum cost to decorate all venues: " + result);
  }
}

