package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;

/**
 * Created by moritz on 13.05.17.
 */
public class InsertionSort extends SortingAlgorithm {

    private static final String NAME = "Insertionsort";

    private static final int STEP_LIMIT = 20;

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
     * Do all steps.
     */
    public void doAllSteps() {
        while (doStep()) {

        }
    }

    /**
     * Do step boolean.
     *
     * @return the boolean
     */
    public boolean doStep() {
        return false;
    }

    /**
     * Gets input size.
     *
     * @return the input size
     */
    public int getInputSize() {
        return 0;
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
    }

    /**
     * Protocol 2 la te x string.
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        return step2LaTeX(0);
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
            if (chosenStepNumber >= 1 && lastArray[i] != this.protocol.get(chosenStepNumber - 1).getArray()[i]) {
                retString += " \\textcolor{red}{" + lastArray[i] + "} & ";
            } else {
                retString += lastArray[i] + " & ";
            }
        }

        retString += chosenStep.getMemory();

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
        retString += " \n\\end{smallmatrix} \n$ ";

        return retString;
    }


    /**
     * Undo step boolean.
     *
     * @return the boolean
     */
    public boolean undoStep() {
        return false;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return NAME;
    }

    /**
     * Gets step limit.
     *
     * @return the step limit
     */
    public int getStepLimit() {
        return STEP_LIMIT;
    }

}
