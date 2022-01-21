package de.moritzf.sorting.logic.sorting.algorithms;

import de.moritzf.sorting.logic.sorting.steps.HeapSortNodeValue;
import de.moritzf.sorting.logic.sorting.steps.HeapStep;
import de.moritzf.sorting.logic.sorting.SortingAlgorithm;
import de.moritzf.sorting.logic.sorting.TreeNode;

import java.util.*;

/**
 * Implementation of the Heapsort algorithm
 *
 * @author Moritz Floeter
 */
public class HeapSort extends SortingAlgorithm {

    private List<HeapStep> protocol = new ArrayList<>();

    private boolean maxHeap;

    /**
     * Instantiates a new Heap sort.
     *
     * @param input   the input
     * @param maxHeap the max heap
     */
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

    private void heapify(TreeNode<HeapSortNodeValue> elementToHeapify) {
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
                elementToHeapify.getValue().replaceNumber(valueChild);
                children.get(0).getValue().replaceNumber(valueElement);
                heapify(children.get(0));
            }
        }
    }

    @Override
    public boolean doStep() {

        HeapStep lastStep = protocol.get(protocol.size() - 1);
        TreeNode<HeapSortNodeValue> newRoot;
        boolean stepDone = false;

        // Only do a step, if the previous step had a tree with elements to sort
        if (lastStep.getRootNode() != null) {
            newRoot = createNewTree(lastStep.getRootNode());
            // Heap Creation
            if (lastStep.getCurrentNode() >= 1) {
                TreeNode<HeapSortNodeValue> nodeToHeapify = newRoot.getNode(lastStep.getCurrentNode());
                nodeToHeapify.getValue().setHeapifyStart(true);
                heapify(nodeToHeapify);
                protocol.add(
                        new HeapStep(newRoot, lastStep.getSortedNumbers(), lastStep.getCurrentNode() - 1));
                stepDone = true;
            } else {
                // Sorting of elements
                TreeNode<HeapSortNodeValue> rootNode = newRoot.getRootNode();
                TreeNode<HeapSortNodeValue> lastNode = newRoot.getLastNode();

                if (lastNode != rootNode) {
                    // remove the rootnode and put the last node in the heap to the top, then heapify
                    lastNode.setParent(null);
                    for (TreeNode<HeapSortNodeValue> child : rootNode.getChildren()) {
                        lastNode.addChild(child);
                    }

                    newRoot = lastNode;
                    newRoot.getValue().setHeapifyStart(true);
                    heapify(newRoot);

                } else {
                    // if lastNode == rootNode this is the last step of the heapsort algorithm
                    newRoot = null;
                }

                List<Integer> newSortedNumbers = new ArrayList(lastStep.getSortedNumbers());
                newSortedNumbers.add(rootNode.getValue().getNumber());

                protocol.add(new HeapStep(newRoot, newSortedNumbers, lastStep.getCurrentNode() - 1));

                stepDone = true;
            }
        }

        return stepDone;
    }

    private TreeNode<HeapSortNodeValue> createNewTree(TreeNode<HeapSortNodeValue> oldTree) {
        TreeNode<HeapSortNodeValue> newTree =
                new TreeNode<>(new HeapSortNodeValue(oldTree.getValue().getNumber()));
        copyChildren(newTree, oldTree);
        return newTree;
    }

    private void copyChildren(
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
    public void reset() {
    }

    /**
     * Renders the protocol to LaTeX-code.
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString += " " + this.step2LaTeX(i) + "\n \n";
        }
        return retString;
    }

    /**
     * Generates a LaTeX-Expression representing one step. This is used for
     * export only as JLaTeX-Math does not allow the use of tikz. The GUI uses
     * HeapSortPanelExtended as replacement (which uses BinaryTreePanel to
     * render the binary tree)
     *
     * @param chosenStepNumber the chosen step number
     * @return the string
     */
    public String step2LaTeX(int chosenStepNumber) {
        HeapStep chosenStep = this.protocol.get(chosenStepNumber);
        String retString = "";
        if (chosenStepNumber == 0) {
            retString += "\\underline{\\textbf{Heap Creation}}\n\n original binary tree \n\n";
        } else if (chosenStepNumber == 1) {
            retString += "$\\rhd number\\lhd$ := element that is to be heapified \n\n";
        }

        final TreeNode<HeapSortNodeValue> renderingTarget = chosenStep.getRootNode();

        /*
         * Generate elements for the part of the protocol where values get
         * removed from the Heap and inserted into the Array
         */
        if (chosenStep.getCurrentNode() < 1) {
            if (chosenStep.getCurrentNode() == 0) {
                retString += "\\underline{\\textbf{Sorting}}\n\n";
              }
            retString += "sorted array : [";
            for (int i = 0; i < chosenStep.getSortedNumbers().size(); i++) {
                retString += Integer.toString(chosenStep.getSortedNumbers().get(i));
                if (i < chosenStep.getSortedNumbers().size() - 1) {
                    retString += ", ";
                }
            }
            retString += "]\n\n";
        }


        if (renderingTarget != null) {
            retString += "\\begin{tikzpicture}[very thick,level/.style={sibling distance=60mm/#1}]\n \\"
                    + generateLaTeXHeapNode(renderingTarget, chosenStep) + "; \n \\end{tikzpicture}";
        } else {
            retString += "$\\rightarrow finished  \\, sorting$";
        }

        retString += "\n\n ............................ \n\n";
        return retString;
    }

    /**
     * Generate heap node. This generates a node of the binary tree as well as
     * all its children by recursively calling this function
     *
     * @param step       the step
     * @return the string
     */
    private String generateLaTeXHeapNode(TreeNode<HeapSortNodeValue> renderingTarget, HeapStep step) {
        String retString = "node [vertex]";
        String[] parts = renderingTarget.toString().split("%begin-above-node");
        if (parts.length > 1)  {
            retString += "[label={\\small $" + parts[1].replace("st", "cancel") + "$}] ";
        }
        retString += "{$" + parts[0].replace("st", "cancel") + "$}";

        if (!renderingTarget.getChildren().isEmpty()) {
            List<TreeNode<HeapSortNodeValue>> children = renderingTarget.getChildren();
            retString += "child{" + generateLaTeXHeapNode(children.get(0), step) + "}";
            if (children.size() > 1) {
                retString += "\nchild{" + generateLaTeXHeapNode(children.get(1), step) + "}";
            } else {
                retString += "child[missing]{}";
            }

        }
        return retString;
    }


    @Override
    public boolean undoStep() {
        return false;
    }

    @Override
    public String getName() {
        return "Heapsort";
    }

    /**
     * Gets protocol.
     *
     * @return the protocol
     */
    public List<HeapStep> getProtocol() {
        return this.protocol;
    }

    /**
     * Checks if maxheap or minheap is used.
     *
     * @return true if maxheap
     */
    public boolean isMaxHeap() {
        return this.maxHeap;
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
