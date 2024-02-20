package src.Q2;

public class MinMovesToEqualizeDresses {
  public static int minMoves(int[] dresses) {
      int n = dresses.length;

      // Calculate the total number of dresses
      int totalDresses = 0;
      for (int dress : dresses) {
          totalDresses += dress;
      }

      // If the total number of dresses is not divisible by the number of machines,
      // it's not possible to equalize the dresses
      if (totalDresses % n != 0) {
          return -1;
      }

      // Calculate the target number of dresses for each machine
      int targetDresses = totalDresses / n;

      int moves = 0;
      int currentSum = 0;

      // Iterate through the machines and calculate the moves needed
      for (int i = 0; i < n; i++) {
          int diff = dresses[i] - targetDresses;
          currentSum += diff;

          // Update the moves if needed
          moves = Math.max(moves, Math.abs(currentSum));
      }

      return moves;
  }

  public static void main(String[] args) {
      int[] dresses = {2, 1, 3, 0, 2};
      int result = minMoves(dresses);

      System.out.println("Minimum moves to equalize dresses: " + result);
  }
}

