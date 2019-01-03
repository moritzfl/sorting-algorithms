package de.moritzf.sorting.logic.sorting.steps;

import de.moritzf.sorting.logic.sorting.algorithms.ShellSort;

import java.util.Arrays;

/**
 * This class represents a step of the shellsort algorithm as implemented in {@link ShellSort}.
 *
 * @author Moritz Floeter
 */
public class ShellStep {

    /**
     * The Source matrix.
     */
    private int[][] sourceMatrix;
    /**
     * The Target matrix.
     */
    private int[][] targetMatrix;

    /**
     * Get the matrix before the columns are sorted .
     *
     * @return the matrix
     */
    public int[][] getSourceMatrix() {
        return Arrays.copyOf(sourceMatrix, sourceMatrix.length);
    }

    /**
     * Get the matrix after the columns are sorted.
     *
     * @return the matrix
     */
    public int[][] getTargetMatrix() {
        return Arrays.copyOf(targetMatrix, targetMatrix.length);
    }

    /**
     * Instantiates a new Shell step.
     *
     * @param sourceMatrix the source matrix
     * @param targetMatrix the target matrix
     */
    public ShellStep(int[][] sourceMatrix, int[][] targetMatrix) {
        this.sourceMatrix = sourceMatrix;
        this.targetMatrix = targetMatrix;
    }
}
