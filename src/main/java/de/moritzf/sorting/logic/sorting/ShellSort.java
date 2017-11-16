package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;
import java.util.List;

public class ShellSort extends SortingAlgorithm {

    private static final String NAME = "Shellsort";

    private static final int STEP_LIMIT = 20;

    private ArrayList<ShellStep> protocol = new ArrayList<>();

    private int[] stepLengths;

    public ShellSort(int[] input) {
        this(input, generateDefaultSteplengths(input.length));
    }

    public ShellSort(int[] input, int[] stepLengths) {
        this.stepLengths = stepLengths;
    }

    private static int[] generateDefaultSteplengths(int arrayLength){
        List<Integer> stepLengthsList = new ArrayList<>();

        //fill with the sequence of 2^n (1,2,4,8,...)
        int stepLength = 1;
        while (stepLength < arrayLength) {
            stepLengthsList.add(stepLength);
            stepLength = stepLength * 2;
        }

        int[] stepLengthsArray = new int[stepLengthsList.size()];
        for (int i = 0; i < stepLengthsList.size(); i++){
            stepLengthsArray[i] = stepLengthsList.get(i);
        }

        return stepLengthsArray;
    }

    @Override
    public void doAllSteps() {
        while (doStep()) {
            //as doSteps returns true if successful this will execute all steps until no further step is possible
        }
    }

    @Override
    public boolean doStep() {
        return false;
    }

    @Override
    public int getInputSize() {
        return 0;
    }

    @Override
    public int getProtocolSize() {
        return this.protocol.size();
    }

    @Override
    public void reset() {
        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }
    }

    @Override
    public String protocol2LaTeX() {
        return null;
    }

    @Override
    public String step2LaTeX(int i) {
        return null;
    }

    /**
     * Undo step.
     *
     * @return true, if successful
     */
    @Override
    public boolean undoStep() {
        boolean removeStep = protocol.size() > 1;
        if (removeStep) {
            protocol.remove(protocol.size() - 1);
        }
        return removeStep;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getStepLimit() {
        return STEP_LIMIT;
    }
}
