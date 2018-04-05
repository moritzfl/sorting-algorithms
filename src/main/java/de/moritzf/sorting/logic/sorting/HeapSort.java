package de.moritzf.sorting.logic.sorting;


import java.util.*;

public class HeapSort extends SortingAlgorithm {


    private List<HeapStep> protocol = new ArrayList<>();

    public HeapSort(int[] input, boolean b) {
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
            if (i < input.length + 1) {
                TreeNode<HeapSortNodeValue> secondChild = new TreeNode<>(new HeapSortNodeValue(input[i + 1]));
                currentParent.addChild(secondChild);
                queue.add(secondChild);
            }
            queue.remove();
        }

        // define which element in the tree needs to be heapified first
        // example: heapifyStartPosition for 5 elements => (5 - 1)/2 = 2
        //       1
        //   2       3
        // 4   5
        int heapifyStartPosition = (input.length - (input.length % 2)) / 2;

        protocol.add(new HeapStep(root, null, heapifyStartPosition));
    }

    public void heapify(TreeNode<HeapSortNodeValue> elementToHeapify, boolean maxheap) {
        List<TreeNode<HeapSortNodeValue>> children = elementToHeapify.getChildren();
        Collections.sort(children, new HeapNodeComparator());
        if (maxheap) {
            Collections.reverse(children);
        }


    }

    @Override
    public void doAllSteps() {

    }

    @Override
    public boolean doStep() {
        return false;
    }

    @Override
    public int getInputSize() {
        return 0;
    }

    @Override
    public void reset() {

    }

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

    @Override
    public int getStepLimit() {
        return 0;
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
