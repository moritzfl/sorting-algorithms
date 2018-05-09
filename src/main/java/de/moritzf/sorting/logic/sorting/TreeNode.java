package de.moritzf.sorting.logic.sorting;

import apple.laf.JRSUIUtils;

import java.util.*;

public class TreeNode<T> {
  private T value;
  private TreeNode<T> parent;
  private List<TreeNode<T>> children = new ArrayList<>();

  public TreeNode(T value) {
    this.value = value;
  }

  public T getValue() {
    return this.value;
  }

  public void addChild(TreeNode<T> child) {
    this.children.add(child);
    child.parent = this;
  }

  public void removeChild(TreeNode<T> child) {
    this.children.remove(child);
    child.setParent(null);
  }

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


  public List<TreeNode<T>> getChildren() {
    return new ArrayList<>(this.children);
  }

  public TreeNode<T> getRootNode() {
    TreeNode<T> parent = this;

    while (parent.parent != null) {
      parent = parent.parent;
    }

    return parent;
  }

    public TreeNode<T>  getLastNode() {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(this.getRootNode());
        TreeNode<T> currentNode = null;
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            this.getChildren().forEach(child -> queue.add(child));
        }
        return currentNode;
    }
}
