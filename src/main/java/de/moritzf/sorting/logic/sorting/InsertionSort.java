package de.moritzf.sorting.logic.sorting;

import de.moritzf.sorting.logic.util.IntArrayUtils;

import java.util.ArrayList;

/**
 * Created by moritz on 13.05.17.
 */
public class InsertionSort extends SortingAlgorithm {

    private static final String NAME = "Insertionsort";


    private ArrayList<InsertionStep> protocol = new ArrayList<>();

    /**
     * Instantiates a new instance of bubblesort.
     *
     * @param input the inputarray
     */
    public InsertionSort(int[] input) {

        protocol.add(new InsertionStep(1, 0, input[1], input));
    }


    /**
     * Do step boolean.
     *
     * @return the boolean
     */
    public boolean doStep() {
        InsertionStep lastStep = protocol.get(protocol.size() - 1);
        int[] lastArray = lastStep.getArray();
        int lastSearchMarker = lastStep.getSearchMarker();
        int lastSortedMarker = lastStep.getSortedMarker();
        int lastMemory = lastStep.getMemory();
        boolean stepDone = false;


        //Check if the algorithm has terminated
        if (lastMemory != -1) {
            int[] currentArray = IntArrayUtils.copyArray(lastArray);
            int currentSearchMarker = lastSearchMarker - 1;
            int currentSortedMarker = lastSortedMarker;
            int currentMemory = lastMemory;

            //handle insertion if a suitable position was found
            if (lastSearchMarker == -1 || currentArray[lastSearchMarker] < lastMemory) {
                currentArray[lastSearchMarker + 1] = lastMemory;
                currentSortedMarker = lastSortedMarker + 1;
                currentSearchMarker = currentSortedMarker - 1;
                if (currentArray.length > currentSortedMarker) {
                    currentMemory = currentArray[currentSortedMarker];
                } else {
                    currentMemory = -1;
                }
            } else {
                currentArray[currentSearchMarker + 2] = currentArray[currentSearchMarker + 1];
            }

            protocol.add(new InsertionStep(currentSortedMarker, currentSearchMarker, currentMemory, currentArray));


            stepDone = true;
        }


        return stepDone;
    }

    /**
     * Gets input size.
     *
     * @return the input size
     */
    public int getInputSize() {
        return this.protocol.get(0).getArray().length;
    }

    /**
     * Gets protocol size.
     *
     * @return the protocol size
     */
    public int getProtocolSize() {
        return protocol.size();
    }

    /**
     * Reset.
     */
    public void reset() {

        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }
    }


    /**
     * Protocol 2 la te x string.
     *
     * @return the string
     */
    /**
     * This produces a LaTeX-Expression representing the entire protocol.
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString +=  this.step2LaTeX(i) + "\n \n";
        }
        return retString;
    }

    /**
     * Converts the chosen step to LaTeX code.
     *
     * @param chosenStepNumber the chosen step number
     * @return the string
     */
    public String step2LaTeX(int chosenStepNumber) {
        String retString = "$\n\\begin{smallmatrix}\n ";
        InsertionStep chosenStep = this.protocol.get(chosenStepNumber);
        int[] lastArray = chosenStep.getArray();


        // adds the actual array of the step to the String
        for (int i = 0; i < lastArray.length; i++) {
            //Check if the current field in the array was different from the one in the last step
            if (chosenStepNumber >= 1 && lastArray[i] != this.protocol.get(chosenStepNumber - 1).getArray()[i]) {
                retString += " \\textcolor{red}{" + lastArray[i] + "} & ";
            } else {
                retString += lastArray[i] + " & ";
            }
        }

        if (chosenStep.getMemory() != -1) {
            retString += "k=" + chosenStep.getMemory();
        }

        if (chosenStep.getSearchMarker() != -1
                && chosenStep.getMemory() != -1) {
            retString += " \\\\ \n";

            // writes the marker (i)
            for (int i = 0; i < lastArray.length; i++) {

                if (chosenStep.getSortedMarker() == i) {
                    retString += "i &";
                } else if (chosenStep.getSearchMarker() == i) {
                    retString += "j-1 &";
                } else {
                    retString += " &";
                }
            }
        }
        retString += " \n\\end{smallmatrix} \n$ ";

        return retString;
    }


    public boolean undoStep() {
        boolean removed = false;
        if (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
            removed = true;
        }
        return removed;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return NAME;
    }



}
