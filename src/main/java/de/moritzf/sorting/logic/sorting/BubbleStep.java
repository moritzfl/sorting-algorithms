package de.moritzf.sorting.logic.sorting;

/**
 * The Class {@link BubbleStep}. Serves the representation of one step for the bubblsort * algorithm
 *
 * @author Moritz Floeter
 */
public class BubbleStep {

    /**
     * The array.
     */
    private int[] array;

    /**
     * The Sortingmarker. This marks the position which is looked at in the
     * according step.
     */
    int sortingmarker;

    /**
     * The memory. This stores information about whether numbers have been
     * swapped or not
     */
    private boolean memory;


    /**
     * Instantiates a new sorting step.
     *
     * @param array the array @param sortingmarker the sortingmarker @param
     *              memory the memory @param explanation the explanation
     */
    public BubbleStep(int[] array, int sortingmarker, boolean memory) {
        super();
        this.array = array;
        this.sortingmarker = sortingmarker;
        this.memory = memory;
    }


    /**
     * Gets the array.
     *
     * @return the array
     */
    public int[] getArray() {
        return array;
    }

    /**
     * Gets the markers.
     *
     * @return the markers
     */
    public int getSortingmarker() {
        return sortingmarker;
    }

    /**
     * Gets the memory.
     *
     * @return the memory
     */
    public boolean getMemory() {
        return memory;
    }

}
