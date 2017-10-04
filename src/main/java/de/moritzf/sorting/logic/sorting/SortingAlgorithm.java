package de.moritzf.sorting.logic.sorting;

/**
 * The type Sorting algorithm.
 */
public abstract class SortingAlgorithm {

    /**
     * Executes all steps of the algorithm.
     */
    public abstract void doAllSteps();

    /**
     * Executes one step of the algorithm.
     *
     * @return true if successful.
     */
    public abstract boolean doStep();

    /**
     * Gets the size of the array that was used as input.
     *
     * @return the input size
     */
    public abstract int getInputSize();

    /**
     * Gets the stepcount for the protocol.
     *
     * @return the protocol size
     */
    public abstract int getProtocolSize();

    /**
     * Resets the algorithm to the start and removes
     * any information previously generated during execution.
     */
    public abstract void reset();

    /**
     * Generates a LaTeX-expression representing the steps that were performed.
     *
     * @return the latex expression.
     */
    public abstract String protocol2LaTeX();

    /**
     * Generates a LaTeX-expression representing a single step.
     *
     * @param i the number of the step.
     * @return the latex expression.
     */
    public abstract String step2LaTeX(int i);

    /**
     * Reverts the last step performed by the algorithm.
     *
     * @return the boolean
     */
    public abstract boolean undoStep();

    /**
     * Gets name of the algorithm
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Gets step limit. Used to limit the maximum number of steps that can be performed at once.
     * This is used when a long protocol is expected due to a large input and replaces the execute all steps option in the gui.
     *
     * @return the step limit
     */
    public abstract int getStepLimit();


}