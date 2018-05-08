package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;
import java.util.List;

public class HeapStep {

    TreeNode<HeapSortNodeValue> rootNode;
    List<Integer> sortedNumbers;
    int currentNode;

    public HeapStep(TreeNode<HeapSortNodeValue> rootNode, List<Integer> sortedNumbers, int currentNode) {

        this.rootNode = rootNode;
        this.currentNode = currentNode;
        this.sortedNumbers = sortedNumbers;
    }

    public TreeNode<HeapSortNodeValue> getRootNode() {
        return rootNode;
    }

    public List<Integer> getSortedNumbers() {
        if (sortedNumbers != null) {
            return new ArrayList<>(sortedNumbers);
        } else {
            return null;
        }
    }

    public void setSortedNumbers(List<Integer> sortedNumbers) {
        this.sortedNumbers = sortedNumbers;
    }
}
