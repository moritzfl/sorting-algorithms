package de.moritzf.sorting.logic.sorting.algorithms;

import java.util.ArrayList;

import de.moritzf.sorting.logic.sorting.steps.SelectionStep;
import de.moritzf.sorting.logic.sorting.SortingAlgorithm;
import de.moritzf.sorting.logic.util.IntArrayUtils;

/**
 * The class represents the implementation of the algorithm selection sort
 *
 * @author Moritz Floeter
 */
public class SelectionSort extends SortingAlgorithm {

  private static final String NAME = "Selectionsort";

  /** The protocol. */
  private ArrayList<SelectionStep> protocol = new ArrayList<>();

  /**
   * Instantiates a new selection sort.
   *
   * @param input the input
   */
  public SelectionSort(int[] input) {
    SelectionStep step = new SelectionStep(input, 0, -1, 0);
    this.protocol.add(step);
  }

  /**
   * Converts Step to LaTeX expression.
   *
   * @param chosenStepNumber the chosen step number
   * @return the string
   */
  public String step2LaTeX(int chosenStepNumber) {
    String retString = "$\n\\begin{smallmatrix}\n ";
    SelectionStep chosenStep = this.protocol.get(chosenStepNumber);
    int[] array = chosenStep.getArray();

    for (int i = 0; i < array.length; i++) {
      if (chosenStepNumber > 0
          && array[i] != this.protocol.get(chosenStepNumber - 1).getArray()[i]) {
        retString += " \\textcolor{red}{" + array[i] + "} &";
      } else {
        retString += array[i] + " & ";
      }
    }

    // insert minimum if the step was not the last step
    if (chosenStep.getSortedUntilPositionMarker() != -1) {
      retString += "min = " + (chosenStep.getMinPosition() + 1);
    }

    retString += " \\\\ \n";

    for (int i = 0; i < array.length; i++) {
      if ((chosenStep.getSortedUntilPositionMarker() == i)) {
        retString += "i &";
      } else if ((chosenStep.getSearchingPositionMarker() == i)) {
        retString += "j &";
      } else {
        retString += " &";
      }
    }
    retString += " \n\\end{smallmatrix} \n$ ";

    return retString;
  }

  /**
   * Do step.
   *
   * @return true, if successful
   */
  public boolean doStep() {
    boolean stepDone = false;
    if (!this.isTerminated()) {
      stepDone = true;
      SelectionStep lastStep = this.getLastStep();
      SelectionStep newStep = new SelectionStep();
      int[] oldArray = lastStep.getArray();
      int[] newArray = IntArrayUtils.copyArray(oldArray);
      int newMinPosition = lastStep.getMinPosition();
      int newSearchingPositionMarker = -1;
      int newSortedUntilPositionMarker = lastStep.getSortedUntilPositionMarker();
      int oldSortedUntilPositionMarker = lastStep.getSortedUntilPositionMarker();
      int oldSearchingPositionMarker = lastStep.getSearchingPositionMarker();

      // swapping of values
      if (oldSearchingPositionMarker == oldArray.length - 1) {

        newArray[oldSortedUntilPositionMarker] = oldArray[lastStep.getMinPosition()];
        newArray[lastStep.getMinPosition()] = oldArray[oldSortedUntilPositionMarker];
        newSortedUntilPositionMarker = oldSortedUntilPositionMarker + 1;
        newMinPosition = newSortedUntilPositionMarker;

        /*
         * newSearchingPositionMarker remains at -1.  this marks the
         * beginning of a new searching cycle for the next step
         */

        // setting searchingPosition marker to position next to the
        // sorted part of the array
      } else if (oldSearchingPositionMarker < 0) {
        newSearchingPositionMarker = newSortedUntilPositionMarker + 1;
        // if neither a swap has happened nor a new cycle was started,
        // the marker just gets moved to the nex position
      } else {
        newSearchingPositionMarker = oldSearchingPositionMarker + 1;
      }

      /*
       * if both markers stood at the last 2 positions of the array in the
       * previous step the sortedUntilPositionMarker gets set to -1 as
       * this marks the termination of the sorting algorithm. There is no
       * longer a sorted and an unsorted portion of the Array as the array
       * is completely sorted now.
       */
      if (oldSortedUntilPositionMarker == newArray.length - 2
          && oldSearchingPositionMarker == newArray.length - 1) {
        newSortedUntilPositionMarker = -1;
      }

      // if the sorting procedure is not completed and the step does not
      // mark the beginning
      // of a new searching cycle for the minimum, the Minimum gets
      // adjusted if
      // a new minimum has been found
      if (newSortedUntilPositionMarker != -1
          && newSearchingPositionMarker != -1
          && newArray[newSearchingPositionMarker] < newArray[newMinPosition]) {

        newMinPosition = newSearchingPositionMarker;
      }

      newStep.setArray(newArray);
      newStep.setMinPosition(newMinPosition);
      newStep.setSearchingPositionMarker(newSearchingPositionMarker);
      newStep.setSortedUntilPositionMarker(newSortedUntilPositionMarker);
      this.protocol.add(newStep);
    }

    return stepDone;
  }

  /**
   * Gets the input size.
   *
   * @return the input size
   */
  public int getInputSize() {

    return protocol.get(0).getArray().length;
  }

  /**
   * Gets the last step.
   *
   * @return the last step
   */
  private SelectionStep getLastStep() {
    return protocol.get(protocol.size() - 1);
  }

  /**
   * Undo step.
   *
   * @return true, if successful
   */
  public boolean undoStep() {
    boolean checker;
    if (protocol.size() > 1) {
      checker = true;
      protocol.remove(protocol.size() - 1);
    } else {
      checker = false;
    }
    return checker;
  }

  /** Reset. */
  public void reset() {
    while (protocol.size() > 1) {
      protocol.remove(protocol.size() - 1);
    }
  }

  /**
   * Protocol2 la tex.
   *
   * @return the string
   */
  public String protocol2LaTeX() {
    String retString = "";
    for (int i = 0; i < protocol.size(); i++) {
      retString += this.step2LaTeX(i) + "\n \n";
    }

    return retString;
  }

  private boolean isTerminated() {
    return this.getLastStep().getSortedUntilPositionMarker() == -1;
  }

  public String getName() {
    return NAME;
  }
}
