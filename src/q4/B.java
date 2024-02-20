package src.q4;

import java.util.LinkedList;
import java.util.List;

public class B {
    static class Node {
        int item, height;
        Node left, right;
      
        Node(int d) {
          item = d;
          height = 1;
        }
      }
      
      static class AVLTree {
        Node root;
      
        int height(Node N) {
          if (N == null)
            return 0;
          return N.height;
        }
      
        int max(int a, int b) {
          return (a > b) ? a : b;
        }
      
        Node rightRotate(Node y) {
          Node x = y.left;
          Node temp = x.right;
          x.right = y;
          y.left = temp;
          y.height = max(height(y.left), height(y.right)) + 1;
          x.height = max(height(x.left), height(x.right)) + 1;
          return x;
        }
      
        Node leftRotate(Node x) {
          Node y = x.right;
          Node temp = y.left;
          y.left = x;
          x.right = temp;
          x.height = max(height(x.left), height(x.right)) + 1;
          y.height = max(height(y.left), height(y.right)) + 1;
          return y;
        }
      
        int getBalanceFactor(Node N) {
          if (N == null)
            return 0;
          return height(N.left) - height(N.right);
        }
      
        Node insertNode(Node node, int item) {
          if (node == null)
            return (new Node(item));
          if (item < node.item)
            node.left = insertNode(node.left, item);
          else if (item > node.item)
            node.right = insertNode(node.right, item);
          else
            return node;
      
          node.height = 1 + max(height(node.left), height(node.right));
          int balanceFactor = getBalanceFactor(node);
          if (balanceFactor > 1) {
            if (item < node.left.item) {
              return rightRotate(node);
            } else if (item > node.left.item) {
              node.left = leftRotate(node.left);
              return rightRotate(node);
            }
          }
          if (balanceFactor < -1) {
            if (item > node.right.item) {
              return leftRotate(node);
            } else if (item < node.right.item) {
              node.right = rightRotate(node.right);
              return leftRotate(node);
            }
          }
          return node;
        }
      
        void findClosest(Node node , int first, int second, double target) {
          if (node != null) {
            double diff = Math.abs(node.item - target);
            double firstDifference = Math.abs(first - target);
            if(diff < firstDifference){
                first = node.item;
                System.out.println(first);
            }
            findClosest(node.left, first, second, target);
            findClosest(node.right, first, second, target);
            System.out.println(diff + " " + firstDifference + " firstOtem : "+ first);
          }
        }
        private List<Integer> ans;
        private double target;
        private int k;
        public List<Integer> closestKValues(Node root, double target, int k) {
            ans = new LinkedList<>();
            this.target = target;
            this.k = k;
            dfs(root);
            return ans;
        }
        private void dfs(Node root) {
            if (root == null) {
                return;
            }
            dfs(root.left);
            if (ans.size() < k) {
                ans.add(root.item);
            } else {
                if (Math.abs(root.item - target) >= Math.abs(ans.get(0) - target)) {
                    return;
                }
                ans.remove(0);
                ans.add(root.item);
            }
            dfs(root.right);
        }
        public static void main(String[] args) {
          AVLTree tree = new AVLTree();
          tree.root = tree.insertNode(tree.root, 4);
          tree.root = tree.insertNode(tree.root, 2);
          tree.root = tree.insertNode(tree.root, 5);
          tree.root = tree.insertNode(tree.root, 1);
          tree.root = tree.insertNode(tree.root, 3);
          System.out.println(tree.closestKValues(tree.root, 3.8, 2));
        }
      }
}
