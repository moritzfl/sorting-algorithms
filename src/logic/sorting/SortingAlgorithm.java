package logic.sorting;

/**
 * The type Sorting algorithm.
 */
public abstract class SortingAlgorithm {

    /**
     * Do all steps.
     */
    public abstract void doAllSteps();

    /**
     * Do step boolean.
     *
     * @return the boolean
     */
    public abstract boolean doStep();

    /**
     * Gets input size.
     *
     * @return the input size
     */
    public abstract int getInputSize();

    /**
     * Gets protocol size.
     *
     * @return the protocol size
     */
    public abstract int getProtocolSize();

    /**
     * Reset.
     */
    public abstract void reset();

    /**
     * Protocol 2 la te x string.
     *
     * @return the string
     */
    public abstract String protocol2LaTeX();

    /**
     * Step 2 la te x string.
     *
     * @param i the
     * @return the string
     */
    public abstract String step2LaTeX(int i);

    /**
     * Undo step boolean.
     *
     * @return the boolean
     */
    public abstract boolean undoStep();

    /**
     * Gets name.
     *
     * @return the name
     */
    public abstract String getName();

    /**
     * Gets step limit.
     *
     * @return the step limit
     */
    public abstract int getStepLimit();


}