package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * The type Shell sort.
 *
 * @author Moritz Floeter
 * <p>
 * <p>
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class ShellSort extends SortingAlgorithm {

    private static final String NAME = "Shellsort";

    private static final int STEP_LIMIT = -1;

    private ArrayList<ShellStep> protocol = new ArrayList<>();

    private int[] input;

    private int[] stepLengths;


    /**
     * Instantiates a new Shell sort. Uses the default steplengths as proposed by Shell in his original paper
     * ( ... 8,4,2,1)
     *
     * @param input the input
     */
    public ShellSort(int[] input) {
        this(input, generateDefaultStepLengths(input.length));
    }

    /**
     * Instantiates a new Shell sort.
     *
     * @param input       the input
     * @param stepLengths the step lengths
     */
    public ShellSort(int[] input, int[] stepLengths) {
        this.input = input;
        this.stepLengths = stepLengths;
        int[][] targetMatrix = generateMatrix(input, stepLengths[0]);
        sortColumns(targetMatrix);
        protocol.add(new ShellStep(generateMatrix(input, stepLengths[0]), targetMatrix));
    }


    /**
     * Generates a matrix with a given number of colums and fills it with the numbers provided through an array.
     *
     * @param numbers         the numbers
     * @param numberOfColumns the number of columns
     * @return matrix
     */
    private static int[][] generateMatrix(int[] numbers, int numberOfColumns) {
        int numberOfRows = (int) Math.ceil(((double) numbers.length) / ((double) numberOfColumns));
        int[][] matrix = new int[numberOfColumns][numberOfRows];

        for (int[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                row[i] = -1;
            }
        }

        int currentColumn = 0;
        int currentRow = 0;
        for (int i = 0; i < numbers.length; i++) {
            matrix[currentColumn][currentRow] = numbers[i];
            currentColumn++;
            if (currentColumn > matrix.length - 1) {
                currentColumn = 0;
                currentRow++;
            }
        }

        return matrix;
    }

    /**
     * Generate an array of steplengths according to the originally proposed steplengths ( ... 8,4,2,1).
     *
     * @param arrayLength the array length
     * @return the int [ ]
     */
    private static int[] generateDefaultStepLengths(int arrayLength) {
        List<Integer> stepLengthsList = new ArrayList<>();

        //fill with the sequence of 2^n (1,2,4,8,...)
        int stepLength = 1;
        while (stepLength < arrayLength) {
            stepLengthsList.add(0, stepLength);
            stepLength = stepLength * 2;
        }

        int[] stepLengthsArray = new int[stepLengthsList.size()];
        for (int i = 0; i < stepLengthsList.size(); i++) {
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


        boolean stepDone = false;

        //each step one steplength is used and the algorithm terminates with the use of the last steplength
        //therfore only perform a step if this is not the case
        if (protocol.size() < this.stepLengths.length) {
            ShellStep previousStep = this.protocol.get(protocol.size() - 1);

            int[] collectedNumbers = collectNumbersFromMatrix(previousStep.getTargetMatrix());

            int[][] sourceMatrix = generateMatrix(collectedNumbers, stepLengths[protocol.size()]);
            int[][] targetMatrix = generateMatrix(collectedNumbers, stepLengths[protocol.size()]);

            sortColumns(targetMatrix);

            protocol.add(new ShellStep(sourceMatrix, targetMatrix));
            stepDone = true;
        }


        return stepDone;
    }

    /**
     * Sorts all columns in a given matrix. Only sorts each column until the first -1 if present.
     *
     * @param targetMatrix the target matrix
     */
    private static void sortColumns(int[][] targetMatrix) {
        for (int[] column : targetMatrix) {
            //Only sort arrays until the first empty position in it (marked by -1)
            int sortUntil = column.length - 1;
            for (int i = 0; i < column.length; i++) {
                //we do not need to check for i == 0 as every row has at least one entry
                //otherwise one could assume that sortUntil gets assigned -1 which is not possible in this case
                if (column[i] == -1) {
                    sortUntil = i - 1;
                    break;
                }
            }
            Arrays.sort(column, 0, sortUntil);
        }
    }

    /**
     * Collects all number going first row from left to right, then second row and so on. Does ignore -1.
     *
     * @param matrix the matrix
     * @return the collected numbers
     */
    private int[] collectNumbersFromMatrix(int[][] matrix) {
        List<Integer> collectedEntriesList = new ArrayList<>();
        for (int i = 0; i < matrix[0].length; i++) {

            for (int j = 0; j < matrix.length; j++) {
                // insert the value of the matrix except for cases where the value is -1
                // as -1 signals an empty field
                if (matrix[j][i] != -1) {
                    collectedEntriesList.add(matrix[j][i]);
                }
            }
        }

        int[] collectedEntriesArray = new int[collectedEntriesList.size()];

        for (int i = 0; i < collectedEntriesList.size(); i++) {
            collectedEntriesArray[i] = collectedEntriesList.get(i);
        }

        return collectedEntriesArray;

    }


    @Override
    public int getInputSize() {
        return this.input.length;
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
        StringJoiner protocolString = new StringJoiner("\n\n");
        for (int i = 0; i < protocol.size(); i++) {
            protocolString.add(step2LaTeX(i));
        }
        return protocolString.toString();
    }

    @Override
    public String step2LaTeX(int i) {
        StringBuilder latexString = new StringBuilder();

        ShellStep step = protocol.get(i);
        latexString.append("$");

        if (i == 0) {
            latexString.append("\nInput:  \\\\\n array=" + Arrays.toString(this.input) + " \\\\\n " +
                    "steplengths=" + Arrays.toString(this.stepLengths) + "\n\\\\\n..............................\\\\");
        }

        //append value of current steplength
        latexString.append("\nsteplength = " + this.stepLengths[i] + "\\\\\n");


        latexString.append(generateLatexTable(step.getSourceMatrix()));

        latexString.append("\n\\rightarrow\n");

        latexString.append(generateLatexTable(step.getTargetMatrix()));

        latexString.append("\n$");

        return latexString.toString();
    }

    /**
     * Generates a LaTeX expression to represent a given int[][] matrix as a table.
     *
     * @param matrix the matrix
     * @return the string
     */
    private String generateLatexTable(int[][] matrix) {
        StringBuilder table = new StringBuilder();
        StringJoiner separatorPattern = new StringJoiner(" | ", "{", "}");
        for (int i = 0; i < matrix.length; i++) {
            separatorPattern.add("c");
        }

        table.append("\\begin{tabular}");
        table.append(separatorPattern.toString());
        table.append("\n");

        for (int i = 0; i < matrix[0].length; i++) {
            StringJoiner row = new StringJoiner("&");
            for (int j = 0; j < matrix.length; j++) {
                // insert the value of the matrix except for cases where the value is -1
                // as -1 signals an empty field
                if (matrix[j][i] != -1) {
                    row.add(Integer.toString(matrix[j][i]));
                } else {
                    row.add(" ");
                }
            }
            table.append(row);

            //Append row separator for every row but the last
            if (i < matrix[0].length - 1) {
                table.append("\\\\");
            }

            //next row starts in new line
            table.append("\n");
        }

        table.append("\\end{tabular}");

        return table.toString();
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
