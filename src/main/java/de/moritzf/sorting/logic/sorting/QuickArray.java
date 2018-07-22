package de.moritzf.sorting.logic.sorting;

/**
 * The Class QuickArray. This is used to represent a subarray in the algorithm * quicksort as the
 * array gets split into smaller arrays.
 *
 * @author Moritz Floeter
 */
public class QuickArray {

  /** The array that the sorting algorithm is supposed to sort. */
  private int[] array;

  /**
   * The left start. This indicates the starting position of i (see de.moritzf.sorting.gui or
   * pseudocode)
   */
  private int leftStart;

  /**
   * The left end. This indicates the stopping position of i (see de.moritzf.sorting.gui or
   * pseudocode)
   */
  private int leftEnd;

  /**
   * The right start. This indicates the starting position of j (see de.moritzf.sorting.gui or
   * pseudocode)
   */
  private int rightStart;

  /**
   * The right end. This indicates the stopping position of j (see de.moritzf.sorting.gui or
   * pseudocode)
   */
  private int rightEnd;

  /**
   * Instantiates a new quick array.
   *
   * @param array the array
   */
  public QuickArray(int[] array) {
    super();
    this.array = array;
    this.leftStart = -1;
    this.rightStart = array.length - 1;
  }

  /**
   * Gets the right end.
   *
   * @return the right end
   */
  public int getRightEnd() {
    return rightEnd;
  }

  /**
   * Sets the right end.
   *
   * @param rightEnd the new right end
   */
  public void setRightEnd(int rightEnd) {
    this.rightEnd = rightEnd;
  }

  /**
   * Sets the left end.
   *
   * @param leftEnd the new left end
   */
  public void setLeftEnd(int leftEnd) {
    this.leftEnd = leftEnd;
  }

  /**
   * Instantiates a new quick array.
   *
   * @param array the array
   * @param leftStart the left start
   * @param rightStart the right start
   */
  public QuickArray(int[] array, int leftStart, int rightStart) {
    super();
    this.array = array;
    this.leftStart = leftStart;
    this.rightStart = rightStart;
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
   * Gets the left start.
   *
   * @return the left start
   */
  public int getLeftStart() {
    return leftStart;
  }

  /**
   * Gets the left end.
   *
   * @return the left end
   */
  public int getLeftEnd() {
    return leftEnd;
  }

  /**
   * Gets the right start.
   *
   * @return the right start
   */
  public int getRightStart() {
    return rightStart;
  }
}
