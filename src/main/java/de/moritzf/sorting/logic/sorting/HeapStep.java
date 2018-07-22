package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Heap step.
 *
 * @author Moritz Floeter
 */
public class HeapStep {

  /** The Root node. */
  TreeNode<HeapSortNodeValue> rootNode;

  /**
   * List of numbers that have already been removed from the heap and inserted into this (sorted)
   * list.
   */
  List<Integer> sortedNumbers;

  /** The node considered for the current step. */
  int currentNode;

  /**
   * Gets the node that is considered for the current step.
   *
   * @return the current node
   */
  public int getCurrentNode() {
    return currentNode;
  }

  /**
   * Instantiates a new Heap step.
   *
   * @param rootNode the root node
   * @param sortedNumbers the sorted numbers
   * @param currentNode the current node
   */
  public HeapStep(
      TreeNode<HeapSortNodeValue> rootNode, List<Integer> sortedNumbers, int currentNode) {

    this.rootNode = rootNode;
    this.currentNode = currentNode;

    this.sortedNumbers = sortedNumbers;
  }

  /**
   * Gets root node.
   *
   * @return the root node
   */
  public TreeNode<HeapSortNodeValue> getRootNode() {
    return rootNode;
  }

  /**
   * Gets sorted numbers. This list a list of numbers that have already been removed from the heap.
   *
   * @return the sorted numbers
   */
  public List<Integer> getSortedNumbers() {
    if (sortedNumbers != null) {
      return new ArrayList<>(sortedNumbers);
    } else {
      return null;
    }
  }
}
