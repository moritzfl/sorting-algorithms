package de.moritzf.sorting.logic.sorting;

import java.util.ArrayList;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class QuickStep. Represents one step in the algorithm quicksort
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
public class QuickStep {

    /**
     * The qarrays. Representation of all the parts that the original array was
     * divided into
     */
    private ArrayList<QuickArray> qarrays = new ArrayList<QuickArray>();


    /**
     * Instantiates a new quick step.
     */
    public QuickStep() {
        super();
    }

    /**
     * Gets the qarrays.
     *
     * @return the qarrays
     */
    public ArrayList<QuickArray> getQarrays() {
        return qarrays;
    }

    /**
     * Sets the qarrays.
     *
     * @param qarrays the new qarrays
     */
    public void setQarrays(ArrayList<QuickArray> qarrays) {
        this.qarrays = qarrays;
    }


    /**
     * Gets all the start markers (leftStart and rightStart) and puts them into
     * an array together so that they both stand at the same position in the
     * array that gets returned by this function as they would represent in
     * regards to the complete array (the array that consists of all parts/all
     * qarrays).
     *
     * @return the start marker array
     */
    public String[] getStartMarkerArray() {
        int size = 1;
        for (int i = 0; i < this.qarrays.size(); i++) {
            size += qarrays.get(i).getArray().length;
        }

        String[] retArray = new String[size];
        int currentPos = 1;

        for (int i = 0; i < this.qarrays.size(); i++) {
            if (this.qarrays.get(i).getArray().length > 1) {
                int leftStart = this.qarrays.get(i).getLeftStart();
                int rightStart = this.qarrays.get(i).getRightStart();

                if (retArray[currentPos + leftStart] == null) {
                    retArray[currentPos + leftStart] = "i";
                } else {
                    retArray[currentPos + leftStart] += "|i";
                }
                retArray[currentPos + rightStart] = "j";
            }
            currentPos += this.qarrays.get(i).getArray().length;
        }
        return retArray;

    }

    /**
     * Gets the size of all parts together/ the sum of all qarrays' array
     * lengths.
     *
     * @return the size
     */
    private int getSize() {
        int size = 0;
        for (int i = 0; i < this.qarrays.size(); i++) {
            size += qarrays.get(i).getArray().length;
        }
        return size;
    }

    /**
     * Gets the value at position.
     *
     * @param position the position
     * @return the value at position
     */
    public int getValueAtPosition(int position) {

        int i = 0;
        boolean foundArray = false;

        while (!foundArray) {
            if (this.qarrays.get(i).getArray().length > position) {
                foundArray = true;
            } else {
                position -= this.qarrays.get(i).getArray().length;
                i++;
            }
        }
        return qarrays.get(i).getArray()[position];

    }

    /**
     * Checks if is terminated.
     *
     * @return true, if is terminated
     */
    public boolean isTerminated() {
        boolean terminated = true;
        for (int i = 0; terminated & i < this.qarrays.size(); i++) {
            if (this.qarrays.get(i).getArray().length != 1) {
                terminated = false;
            }
        }
        return terminated;
    }

    /**
     * Gets the end marker array.
     *
     * @return the end marker array
     */
    public String[] getEndMarkerArray() {
        int size = this.getSize() + 1;

        String[] retArray = new String[size];
        int currentPos = 1;
        for (int i = 0; i < this.qarrays.size(); i++) {
            if (this.qarrays.get(i).getArray().length > 1) {
                int leftEnd = this.qarrays.get(i).getLeftEnd();
                int rightEnd = this.qarrays.get(i).getRightEnd();

                retArray[currentPos + leftEnd] = "i";

                if (retArray[currentPos + rightEnd] == null) {
                    retArray[currentPos + rightEnd] = "j";
                } else {
                    retArray[currentPos + rightEnd] += ",j";
                }
            }

            currentPos += this.qarrays.get(i).getArray().length;
        }
        return retArray;
    }
}
