package de.moritzf.sorting.logic.sorting;

import java.util.*;

public class HeapSort extends SortingAlgorithm {

  private List<HeapStep> protocol = new ArrayList<>();

  private boolean maxHeap;

  public HeapSort(int[] input, boolean maxHeap) {
    this.maxHeap = maxHeap;
    HeapSortNodeValue rootValue = new HeapSortNodeValue(input[0]);
    TreeNode<HeapSortNodeValue> root = new TreeNode<HeapSortNodeValue>(rootValue);

    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);

    // Build initial tree
    for (int i = 1; i < input.length; i = i + 2) {
      // Add a maximum of 2 children to the current node in the queue, then remove it from the queue
      TreeNode<HeapSortNodeValue> currentParent = queue.poll();
      TreeNode<HeapSortNodeValue> firstChild = new TreeNode<>(new HeapSortNodeValue(input[i]));
      currentParent.addChild(firstChild);
      queue.add(firstChild);
      if (i + 1 < input.length) {
        TreeNode<HeapSortNodeValue> secondChild =
            new TreeNode<>(new HeapSortNodeValue(input[i + 1]));
        currentParent.addChild(secondChild);
        queue.add(secondChild);
      }
    }

    // define which element in the tree needs to be heapified first
    // example: heapifyStartPosition for 5 elements => (5 - 1)/2 = 2
    //       1
    //   2       3
    // 4   5
    int heapifyStartPosition = (input.length - (input.length % 2)) / 2;

    protocol.add(new HeapStep(root, new ArrayList<>(), heapifyStartPosition));
  }

  public void heapify(TreeNode<HeapSortNodeValue> elementToHeapify) {
    List<TreeNode<HeapSortNodeValue>> children = elementToHeapify.getChildren();
    Collections.sort(children, new HeapNodeComparator());
    int valueElement = elementToHeapify.getValue().getNumber();
    int valueChild;
    boolean doSwap;
    if (children.size() > 0) {
      if (maxHeap) {
        Collections.reverse(children);
        valueChild = children.get(0).getValue().getNumber();
        doSwap = valueChild > valueElement;
      } else {
        valueChild = children.get(0).getValue().getNumber();
        doSwap = valueChild < valueElement;
      }
      if (doSwap) {
        elementToHeapify.getValue().setNumber(valueChild);
        children.get(0).getValue().setNumber(valueElement);
        heapify(children.get(0));
      }
    }
  }

  @Override
  public void doAllSteps() {}

  @Override
  public boolean doStep() {

    HeapStep lastStep = protocol.get(protocol.size() - 1);
    TreeNode<HeapSortNodeValue> newTree;
    boolean stepDone = false;

    // Only do a step, if the previous step had a tree with elements to sort
    if (lastStep.getRootNode() != null) {
      newTree = createNewTree(lastStep.getRootNode());
      // Heap Creation
      if (lastStep.currentNode >= 1) {
        TreeNode<HeapSortNodeValue> nodeToHeapify = newTree.getNode(lastStep.currentNode);
        heapify(nodeToHeapify);
        protocol.add(new HeapStep(newTree, lastStep.getSortedNumbers(), lastStep.currentNode - 1));
        stepDone = true;
      } else {
        // Sorting of elements
        TreeNode<HeapSortNodeValue> rootNode = newTree.getRootNode();
        TreeNode<HeapSortNodeValue> lastNode = newTree.getLastNode();

        if (lastNode != rootNode) {
          // remove the rootnode and put the last node in the heap to the top, then heapify
          lastNode.setParent(null);
          for (TreeNode<HeapSortNodeValue> child : rootNode.getChildren()) {
            lastNode.addChild(child);
          }

          heapify(lastNode);
        } else {
          // if lastNode == rootNode this is the last step of the heapsort algorithm
          newTree = null;
        }

        List<Integer> newSortedNumbers = new ArrayList(lastStep.getSortedNumbers());
        newSortedNumbers.add(rootNode.getValue().getNumber());

        protocol.add(new HeapStep(newTree, newSortedNumbers, lastStep.currentNode - 1));

        stepDone = true;
      }
    }

    return stepDone;
  }

  public TreeNode<HeapSortNodeValue> createNewTree(TreeNode<HeapSortNodeValue> oldTree) {
    TreeNode<HeapSortNodeValue> newTree =
        new TreeNode<>(new HeapSortNodeValue(oldTree.getValue().getNumber()));
    copyChildren(newTree, oldTree);
    return newTree;
  }

  public void copyChildren(
      TreeNode<HeapSortNodeValue> newTreeNode, TreeNode<HeapSortNodeValue> oldTreeNode) {
    for (TreeNode<HeapSortNodeValue> oldChild : oldTreeNode.getChildren()) {
      TreeNode<HeapSortNodeValue> newChild =
          new TreeNode<>(new HeapSortNodeValue(oldChild.getValue().getNumber()));
      newTreeNode.addChild(newChild);
      copyChildren(newChild, oldChild);
    }
  }

  @Override
  public int getInputSize() {
    return 0;
  }

  @Override
  public void reset() {}

  @Override
  public String protocol2LaTeX() {
    return null;
  }

  @Override
  public boolean undoStep() {
    return false;
  }

  @Override
  public String getName() {
    return "Heapsort";
  }

  public List<HeapStep> getProtocol() {
    return this.protocol;
  }

  private class HeapNodeComparator implements Comparator<TreeNode<HeapSortNodeValue>> {

    public int compare(TreeNode<HeapSortNodeValue> o1, TreeNode<HeapSortNodeValue> o2) {
      if (o1.getValue().getNumber() > o2.getValue().getNumber()) {
        return 1;
      } else if (o1.getValue().getNumber() < o2.getValue().getNumber()) {
        return -1;
      } else {
        return 0;
      }
    }
  }
}
