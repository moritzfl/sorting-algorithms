package de.moritzf.sorting.logic.sorting.steps;

import java.util.ArrayList;

/**
 * The class represents a step of the radixsort algorithm.
 *
 * @author Moritz Floeter
 */
public class RadixStep {

    /**
     * The post boxes.
     */
    private ArrayList<ArrayList<String>> postBoxes = new ArrayList<>(10);

    /**
     * The memory. Stores which position is currently considered for distribution into the boxes.
     */
    private int memory = 0;

    /**
     * Instantiates a new radix step.
     */
    public RadixStep() {
        for (int i = 0; i <= 9; i++) {
            postBoxes.add(new ArrayList<>());
        }
    }

    /**
     * Gets one of the boxes that stands for one of the numbers 0-9. The value passed to this function
     * must be 0-9
     *
     * @param i the i
     * @return the post box
     */
    public ArrayList<String> getPostBox(int i) {
        return postBoxes.get(i);
    }

    /**
     * Collect boxes.
     *
     * @return the array list
     */
    public ArrayList<String> collectBoxes() {
        ArrayList<String> collectedBoxes = new ArrayList<String>();
        for (int i = 0; i < postBoxes.size(); i++) {
            for (int j = 0; j < postBoxes.get(i).size(); j++) {
                collectedBoxes.add(postBoxes.get(i).get(j));
            }
        }
        return collectedBoxes;
    }

    /**
     * Do step.
     *
     * @return true, if successful
     */
    public boolean doStep() {
        boolean stepDone = false;

        return stepDone;
    }

    /**
     * Gets the memory.
     *
     * @return the memory
     */
    public int getMemory() {
        return memory;
    }

    /**
     * Sets the memory.
     *
     * @param memory the new memory
     */
    public void setMemory(int memory) {
        this.memory = memory;
    }
}
