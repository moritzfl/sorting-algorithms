package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class RadixSort.
 *         This represents the implementation of the algorithm radixsort.
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class RadixSort extends SortingAlgorithm {

    private static final String NAME = "Radixsort";

    private static final int STEP_LIMIT = -1;

    /**
     * The protocol.
     */
    private ArrayList<RadixStep> protocol = new ArrayList<RadixStep>();

    /**
     * The input array.
     */
    private int[] inputArray;

    /**
     * Instantiates a new instance of radixsort.
     *
     * @param input the inputarray
     */
    public RadixSort(int[] input) {
        RadixStep firstStep = new RadixStep();
        ArrayList<String> inputAsStrings = new ArrayList<String>();
        int max = 0;
        this.inputArray = input;

        // find the maximum length of one input element (letters needed for the
        // according value)
        for (int i = 0; i < input.length; i++) {
            if (max < ("" + input[i]).length()) {
                max = ("" + input[i]).length();
            }
        }

		/*
         * all elements shorter than the longest element get filled with zeroes
		 * so that they have the same length when represented as String
		 */
        for (int i = 0; i < input.length; i++) {
            String input2add = "" + input[i];
            while (input2add.length() < max) {
                input2add = "0" + input2add;
            }
            inputAsStrings.add(input2add);
        }
        sortIntoBoxes(inputAsStrings, firstStep);

        protocol.add(firstStep);
    }


    /**
     * Gets the length of the numbers that are being sorted. For example 1234 would have the length 4.
     * All numbers do have the same length as they got normalized by adding 0s before the number in the
     * during initialization in the constructor.
     *
     * @return the number length
     */
    private int getNumberLength() {
        return this.protocol.get(0).collectBoxes().get(0).length();
    }

    /**
     * Sort into boxes.
     *
     * @param collectedStrings the collected strings
     * @param step             the step
     */
    private static void sortIntoBoxes(ArrayList<String> collectedStrings, RadixStep step) {
        // as memory marks the position counting from the right end of the
        // string, the position gets set accordingly
        int position = collectedStrings.get(0).length() - 1 - step.getMemory();

        for (int i = 0; i < collectedStrings.size(); i++) {
			/*
			 * in order to know which postBox to sort into, the character at the
			 * position gets pulled and converted to an integer (the char '0'
			 * has the value 48, the char '1' has the value 49 etc. -> that is
			 * why the reduction by 48 is necessary)
			 */

            int number = collectedStrings.get(i).charAt(position) - 48;
            step.getPostBox(number).add(collectedStrings.get(i));
        }

    }


    /**
     * Do step.
     *
     * @return true, if successful
     */
    public boolean doStep() {
        boolean stepDone = false;
        RadixStep prevStep = this.getStep(this.getProtocolSize() - 1);
        int nextMemory = prevStep.getMemory() + 1;
        if (nextMemory < this.getNumberLength()) {
            RadixStep nextStep = new RadixStep();
            nextStep.setMemory(nextMemory);
            sortIntoBoxes(prevStep.collectBoxes(), nextStep);
            protocol.add(nextStep);
            stepDone = true;
        }
        return stepDone;
    }

    /**
     * Collects all elements of a certain step and returns them as a LaTeX
     * expression that can be rendered to display all the according items as
     * comma seperated values.
     *
     * @param stepNumber the step number
     * @return the string
     */
    private String collect2LaTeX(int stepNumber) {
        String retString = "$\n \\underline{Collecting} \\\\ \n ";
        ArrayList<String> valuesAsString = this.protocol.get(stepNumber).collectBoxes();
        for (int i = 0; i < valuesAsString.size(); i++) {
            retString += valuesAsString.get(i);
            if (i < valuesAsString.size() - 1) {
                retString += ", ";
            }
            //add linebreaks after 10 elements.
            if ((i + 1) % 10 == 0 && this.inputArray.length > i + 1) {
                retString += "\\\\ ";
            }
        }
        retString += " \\\\ \n$";
        return retString;
    }

    /**
     * Returns a LaTeX expression (String) that contains
     * the input as a list of comma separated values.
     *
     * @return the string
     */
    private String originalArray2LaTeX() {
        String originalArray = "";
        for (int i = 0; i < this.inputArray.length; i++) {
            originalArray = originalArray + this.inputArray[i];
            if (this.inputArray.length > i + 1) {
                originalArray += ", ";
            }
            //add linebreaks after 10 elements.
            if ((i + 1) % 10 == 0 && this.inputArray.length > i + 1) {
                originalArray += "\\\\ ";
            }
        }

        originalArray = "$\n \\underline{Input} \\\\ \n " + originalArray + " \\\\ \n$\n";
        return originalArray;
    }

    /**
     * Returns a representation of the complete step as LaTeX expression.
     *
     * @param stepNumber the step number
     * @return the string
     */
    public String step2LaTeX(int stepNumber) {
        String retString = "";
        if (stepNumber == 0) {
            retString += originalArray2LaTeX();
        } else {
            retString += "$\n............................\\\\\n$\n \n ";
        }
        retString += distributionPartToLatex(stepNumber);
        retString += collect2LaTeX(stepNumber);
        return retString;
    }


    /**
     * Returns the complete protocol as LaTeX expression
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString += " " + this.step2LaTeX(i) + " \n\\newline \n";
        }

        return retString;
    }

    /**
     * Renders the distribution-part of the algorithm to a latex expression.
     *
     * @param stepNumber the step number
     * @return the string
     */
    private String distributionPartToLatex(int stepNumber) {
        String retString = "$\n\\underline{Distribution}\\\\\n$\n\n" + "$\n\\begin{matrix}\n"
                + "F_0 & F_1 & F_2 & F_3 & F_4 & F_5 & F_6 & F_7 & F_8 & F_9 \\\\ \n";
        RadixStep step = protocol.get(stepNumber);

        boolean checker = true;
        // i represents lines
        for (int i = 0; checker; i++) {
            checker = false;
            String line = "";
            // j represents columns
            for (int j = 0; j <= 9; j++) {
                if (step.getPostBox(j).size() > i) {
                    checker = true;
					/*
					 * puts the value into the right place of the matrix and
					 * highlights the number at the position that was looked at
					 * for sorting into the postBoxes
					 */
                    line += highlight(step.getPostBox(j).get(i), this.getNumberLength() - 1 - step.getMemory());
                }
                if (j < 9) {
                    line += " & ";
                } else {
                    line += " \\\\ \n ";
                }
            }
            if (checker) {
                retString += line;
            }
        }
        retString += "\\end{matrix}\n \\\\$\n\n";
        return retString;
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

    /**
     * Reset all steps (removes all step from protocol and go back to the
     * beginning).
     */
    public void reset() {
        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }

    }

    /**
     * Do all steps.
     */
    public void doAllSteps() {
        while (doStep())
            ;

    }

    /**
     * Highlights a certain part of a LaTeX expression by underlining it and
     * setting its color to red.
     *
     * @param input    the input
     * @param position the position
     * @return the string
     */
    private static String highlight(String input, int position) {
        String retString = input.substring(0, position) + "\\textcolor{red}{\\underline{"
                + input.substring(position, position + 1) + "}}" + input.substring(position + 1);
        return retString;

    }

    /**
     * Gets the step at the according position in the protocol.
     *
     * @param i the position in the protocol
     * @return the step
     */
    public RadixStep getStep(int i) {
        return protocol.get(i);
    }

    /**
     * Gets the protocol size.
     *
     * @return the protocol size
     */
    public int getProtocolSize() {
        return this.protocol.size();
    }

    @Override
    public int getInputSize() {
        return protocol.get(0).collectBoxes().size();
    }

    @Override
    public String getName() {
        return NAME;
    }

    public int getStepLimit() {
        return STEP_LIMIT;
    }



}
