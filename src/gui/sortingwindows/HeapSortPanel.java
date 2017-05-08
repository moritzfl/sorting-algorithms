package gui.sortingwindows;

import java.util.Arrays;

import gui.general.BinaryTreePanel;
import logic.sorting.HeapStep;

// TODO: Auto-generated Javadoc

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class HeapSortPanel. This panel is used to display the binary tree for a
 *         heapsort step.
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

public class HeapSortPanel extends BinaryTreePanel {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 2139431116975562997L;

    /**
     * enableNodeCentering. If this is set to true, the original value of a node
     * will get centered. If it is set to false, the entire text for that node
     * will get centered.
     */
    private static final boolean enableNodeCentering = true;

    /**
     * The Constant verticalNodeSpace. The Value added (Recommendation: ca 50)
     * to the fontHeight defines the space for the lines between the rows of
     * nodes.
     */
    private static final double verticalNodeSpace = BinaryTreePanel.getFontHeight() + 50;

    /**
     * The Constant letterSpace defines how much space should be taken for each
     * letter in a node.
     */
    private static final double letterSpace = BinaryTreePanel.getFontWidth();

    /**
     * The Constant horizontalNodeSpace defines the horizontal spacing between
     * the nodes (Recommendation: ca 10).
     */
    private static final int horizontalNodeSpace = 10;

    /**
     * Instantiates a new heap sort panel.
     *
     * @param step the step
     */
    public HeapSortPanel(HeapStep step) {
        super(pullAsStringArray(step), getMaxWidth(step), getMaxHeight(step.getStep()));
    }

    /**
     * Int to string array.
     */
    public static String[] pullAsStringArray(HeapStep step) {

        int[][] array = step.getStep();
        int memory = step.getMemory();

        String[][] sArray = new String[array.length][];

        // generate a String[][] Array
        for (int i = 0; i < array.length; i++) {
            sArray[i] = Arrays.toString(array[i]).split("[\\[\\]]")[1].split(", ");
            if ((i == memory + 1) && (memory < sArray.length / 2 - 1)) {
                sArray[memory + 1][0] = ">" + sArray[memory + 1][0] + "<";
            }
            for (int j = 0; j < sArray[i].length - 1; j++) {
                sArray[i][j] = crossOut(sArray[i][j]) + ", ";
            }
        }

        // combines all Strings of the String[][] array to a String[] array
        String[] retArray = new String[sArray.length];
        for (int i = 0; i < sArray.length; i++) {
            retArray[i] = "";
            for (int j = 0; j < sArray[i].length; j++) {
                retArray[i] += sArray[i][j];
            }
        }

        if (enableNodeCentering) {
            for (int i = 0; i < retArray.length; i++) {
                int spaceLength = retArray[i].length() - sArray[i][0].length();
                String spacing = "";
                for (int j = 0; j < spaceLength; j++) {
                    spacing += " ";
                }
                retArray[i] = spacing + retArray[i];
            }
        }

        return retArray;
    }

    /**
     * Gets the max width.
     */
    private static int getMaxWidth(HeapStep step) {
        // the current maximum number of characters that are displayed in one
        // line.
        int max = 0;
        String[] array = pullAsStringArray(step);

        // describes the number of values in the line.
        int lineValue = 0;

        for (int i = 0; i < array.length; i++) {
            // calculates the line of the element i in the binary tree
            for (int j = 1; lineValue <= i + 1; j = j * 2) {
                lineValue = j;
            }
            lineValue = lineValue / 2;

            // depending on the line, the maximum number of characters gets
            // calculated
            if (array[i].length() * lineValue > max) {
                max = array[i].length() * lineValue;
            }
        }
        // return the maximum width with consideration of the space taken by
        // each letter.
        return (int) Math.round(max * HeapSortPanel.letterSpace + lineValue * HeapSortPanel.horizontalNodeSpace);
    }

    /**
     * Gets the max height.
     *
     * @param array the array representing the binary tree @return the max
     *              height that the binary tree will have in the gui
     */
    private static int getMaxHeight(int[][] array) {
        /*
         * maximum height is calculated by taking the number of lines times the
         * Space that every line should take
         */
        int maxHeight =
                (int) (Math.floor(Math.log(array.length) / Math.log(2)) * verticalNodeSpace + verticalNodeSpace);
        return maxHeight;
    }

    /**
     * Cross out all characters in a string.
     *
     * @param input the input @return the string with all characters crossed
     *              out.
     */
    private static String crossOut(String input) {

        char[] inputAsArray = input.toCharArray();

        /*
         * why is +1 as size? -> because Windows handles the chars differently
         * than OSX and Linux. While one crosses out the element before the
         * crossing out char, the other crosses it out after the crossing out
         * char. This way we cope with both of those mechanics.
         */
        char[] outputAsArray = new char[inputAsArray.length * 2 + 1];
        for (int i = 0; i < inputAsArray.length; i++) {
            outputAsArray[2 * i + 1] = inputAsArray[i];
            // char for crossing out a char.
            outputAsArray[2 * i] = 822;
        }
        // set the last symbol to be a crossing out char aswell
        outputAsArray[2 * inputAsArray.length] = 822;

        return new String(outputAsArray);

    }

}
