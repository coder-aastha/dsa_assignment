package src.Q1;

import java.util.PriorityQueue;

public class SpaceshipMission{
    
  // Calculates the minimum time needed to build all engines using available engineers.
  public static int minTimeToBuildEngines(int[] engines, int splitCost) {
      PriorityQueue<Integer> q = new PriorityQueue<>();
      for (int x : engines) {
          q.offer(x);
      }
      while (q.size() > 1) {
          // Combine two engines with the most time, add splitCost, and insert back into the priority queue.
          q.offer(splitCost + Integer.max(q.poll(), q.poll()));
      }
      // Finally the remaining element in the priority queue is the minimum cost.
      return q.poll();
  }
  public static void main(String[] args) {
      int[] engines = {1,2,3};
      int splitCost = 1;

      int result = minTimeToBuildEngines(engines, splitCost);
      System.out.println("Minimum time needed to build all engines: " + result);
  }
}