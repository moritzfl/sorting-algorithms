package de.moritzf.sorting.logic.sorting;

/**
 * Created by moritz on 13.05.17.
 */
public class InsertionStep {

    /**
     * The Sorted marker.
     */
    int sortedMarker;
    /**
     * The Search marker.
     */
    int searchMarker;

    /**
     * The Memory.
     */
    int memory;

    /**
     * The Array.
     */
    int[] array;

    /**
     * Instantiates a new Insertion step.
     *
     * @param sortedMarker the sorted marker
     * @param searchMarker the search marker
     * @param memory       the memory
     * @param array        the array
     */
    public InsertionStep(int sortedMarker, int searchMarker, int memory, int[] array) {
        this.sortedMarker = sortedMarker;
        this.searchMarker = searchMarker;
        this.memory = memory;
        this.array = array;
    }

    /**
     * Gets sorted marker.
     *
     * @return the sorted marker
     */
    public int getSortedMarker() {
        return sortedMarker;
    }

    /**
     * Gets search marker.
     *
     * @return the search marker
     */
    public int getSearchMarker() {
        return searchMarker;
    }

    /**
     * Gets memory.
     *
     * @return the memory
     */
    public int getMemory() {
        return memory;
    }

    /**
     * Get array int [ ].
     *
     * @return the int [ ]
     */
    public int[] getArray() {
        return array;
    }
}
