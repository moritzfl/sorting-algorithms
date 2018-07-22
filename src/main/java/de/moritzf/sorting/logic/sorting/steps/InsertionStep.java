package de.moritzf.sorting.logic.sorting.steps;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * This class represents a step within the insertion sort algorithm.
 *
 * @author Moritz Floeter
 */
public class InsertionStep {

  /** The Sorted marker. */
  private int sortedMarker;
  /** The Search marker. */
  private int searchMarker;

  /** The Memory. */
  private int memory;

  /** The Array. */
  private int[] array;

  /**
   * Instantiates a new Insertion step.
   *
   * @param sortedMarker the sorted marker
   * @param searchMarker the search marker
   * @param memory the memory
   * @param array the array
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

  @Override
  public String toString() {
    return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
        .add("array = " + Arrays.toString(array))
        .add("memory = " + memory)
        .add("searchMarker = " + searchMarker)
        .add("sortedMarker = " + sortedMarker)
        .toString();
  }
}
