package de.moritzf.sorting.logic.sorting;


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
        int breathSearchNiv = 1;
        while (queue.poll() != null && breathSearchNiv != index) {
            this.getChildren().forEach(child -> queue.add(child));
            queue.remove();
            breathSearchNiv++;
        }
        return queue.poll();
    }


    private int getNodeNumber() {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(this.getRootNode());
        int breathSearchNiv = 1;
        while (queue.poll() != null && !this.equals(queue.poll())) {
            this.getChildren().forEach(child -> queue.add(child));
            queue.remove();
            breathSearchNiv++;
        }
        return breathSearchNiv;

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


}
