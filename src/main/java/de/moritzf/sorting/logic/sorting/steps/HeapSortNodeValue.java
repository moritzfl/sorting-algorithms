package de.moritzf.sorting.logic.sorting.steps;

import java.util.ArrayList;

/**
 * This class represents the node values for the Heap sort algorithm.
 */
public class HeapSortNodeValue {

    private ArrayList<Integer> numbers = new ArrayList<>();
    private boolean heapfyStart;

    /**
     * Instantiates a new Heap sort node value.
     *
     * @param initialNumber the initial number
     */
    public HeapSortNodeValue(int initialNumber) {
        numbers.add(initialNumber);
    }

    /**
     * Sets heapify start.
     *
     * @param heapifyStart the heapify start
     */
    public void setHeapifyStart(boolean heapifyStart) {
        this.heapfyStart = heapifyStart;
    }

    /**
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return numbers.get(numbers.size() - 1);
    }

    /**
     * Replace number.
     *
     * @param number the number
     */
    public void replaceNumber(int number) {
        this.numbers.add(number);
    }

    @Override
    public String toString() {
        StringBuilder joiner = new StringBuilder();
        for (int i = 0; i < numbers.size(); i++) {
            if (i == 0 && numbers.size() > 1) {
                if (heapfyStart) {
                    joiner.append(
                            "\\rhd\\st{" + Integer.toString(numbers.get(i)) + "}\\lhd");
                } else {
                    joiner.append("\\st{" + Integer.toString(numbers.get(i)) + "}");
                }
                joiner.append("%begin-above-node\n");
            } else if (i == 0 && numbers.size() == 1) {
                if (heapfyStart) {
                    joiner.append("\\rhd" + Integer.toString(numbers.get(i)) + "\\lhd");
                } else {
                    joiner.append(Integer.toString(numbers.get(i)));
                }
                // even if no old numbers are written above the node, insert an empty space
                // so that all node-boxes are aligned next to each other
                // if this is not done, a neighbour node-box WITH numbers above the node would differ in
                // height from the one without numbers above the node.
                joiner.append("%begin-above-node\n");
                joiner.append("\\,");
            } else if (i == numbers.size() - 1) {
                joiner.append(Integer.toString(numbers.get(numbers.size() - 1)));
            } else {
                joiner.append("\\st{" + Integer.toString(numbers.get(i)) + "}, ");
            }
        }

        return joiner.toString();
    }
}
