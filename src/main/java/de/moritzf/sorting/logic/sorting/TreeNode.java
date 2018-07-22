package de.moritzf.sorting.logic.sorting;

import java.util.*;

/**
 * The type Tree node. This class can be used for describing trees with nodes of various datatypes.
 * This class can be used with {@link de.moritzf.sorting.gui.components.TreeNodePane} to render the
 * tree in the gui. For rendering, the toString()-Method is used to display the value of every node.
 *
 * @author Moritz Floeter
 * @param <T> the type parameter
 */
public class TreeNode<T> {
  private T value;
  private TreeNode<T> parent;
  private List<TreeNode<T>> children = new ArrayList<>();

  /**
   * Instantiates a new Tree node.
   *
   * @param value the value
   */
  public TreeNode(T value) {
    this.value = value;
  }

  /**
   * Gets value.
   *
   * @return the value
   */
  public T getValue() {
    return this.value;
  }

  /**
   * Add child.
   *
   * @param child the child
   */
  public void addChild(TreeNode<T> child) {
    this.children.add(child);
    child.parent = this;
  }

  /**
   * Remove child.
   *
   * @param child the child
   */
  public void removeChild(TreeNode<T> child) {
    this.children.remove(child);
    child.setParent(null);
  }

  /**
   * Sets parent.
   *
   * @param parent the parent
   */
  public void setParent(TreeNode<T> parent) {
    if (this.parent != null) {
      this.parent.children.remove(this);
    }
    if (parent != null) {
      parent.addChild(this);
    }
    this.parent = parent;
  }

  public String toString() {
    return this.value.toString();
  }

  /**
   * Gets node.
   *
   * @param index the index
   * @return the node
   */
  public TreeNode<T> getNode(int index) {
    Queue<TreeNode<T>> queue = new LinkedList<>();
    queue.add(this.getRootNode());
    int breathSearchNiv = 0;
    TreeNode<T> currentNode = null;
    while (breathSearchNiv < index && !queue.isEmpty()) {
      currentNode = queue.poll();
      currentNode.getChildren().forEach(child -> queue.add(child));
      breathSearchNiv++;
    }

    if (breathSearchNiv == index) {
      return currentNode;
    } else {
      return null;
    }
  }

  /**
   * Gets children.
   *
   * @return the children
   */
  public List<TreeNode<T>> getChildren() {
    return new ArrayList<>(this.children);
  }

  /**
   * Gets root node.
   *
   * @return the root node
   */
  public TreeNode<T> getRootNode() {
    TreeNode<T> parent = this;

    while (parent.parent != null) {
      parent = parent.parent;
    }

    return parent;
  }

  /**
   * Gets last node.
   *
   * @return the last node
   */
  public TreeNode<T> getLastNode() {
    Queue<TreeNode<T>> queue = new LinkedList<>();
    queue.add(this.getRootNode());
    TreeNode<T> currentNode = null;
    while (!queue.isEmpty()) {
      currentNode = queue.poll();
      currentNode.getChildren().forEach(child -> queue.add(child));
    }
    return currentNode;
  }
}
