package de.moritzf.sorting.logic.sorting;

import java.util.Arrays;

public class ShellStep {

    int[][] sourceMatrix;
    int[][] targetMatrix;

    public int[][] getSourceMatrix() {
        return Arrays.copyOf(sourceMatrix, sourceMatrix.length);
    }

    public int[][] getTargetMatrix() {
        return Arrays.copyOf(targetMatrix, targetMatrix.length);
    }

    public ShellStep(int[][] sourceMatrix, int[][] targetMatrix) {
        this.sourceMatrix = sourceMatrix;
        this.targetMatrix = targetMatrix;
    }
}
