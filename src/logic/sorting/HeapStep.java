package logic.sorting;

import java.util.ArrayList;


/**
 * @author Moritz Floeter
 *         <p>
 *         The Class HeapStep. Represents one step in the algorithm heapsort
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
public class HeapStep {

    /**
     * The step. Represents the current step in form of a binary tree stored as
     * an array. The outer array defines the node while the inner array contains
     * all the numbers that stood and stand in the location of the node when
     * perfoming the step. If the inner array for example contained a {1} in the
     * beginning but the 1 got swapped with a 2, it then looks like this: {1,2}
     * The outer array describes note position as follows (numbers stand for the
     * position of the node inside the array): 0 -> root node, 1->left
     * childnode, 2->right childnode, 4 -> left child of left childnode, 5 right
     * child of left childnode etc...
     */
    private int[][] step;

    /**
     * The memory. Stores the position of the current element that is being
     * heapified it is -1 when the heap condition has already been fulfilled and
     * elements are taken out of the array and put into a sorted list.
     */
    private int memory;

    /**
     * The sorted list.
     */
    private ArrayList<Integer> sortedList;


    /**
     * Instantiates a new heap step.
     */
    public HeapStep() {
        super();
    }

    /**
     * Instantiates a new heap step.
     *
     * @param step       the step
     * @param memory     the memory
     * @param sortedList the sorted list
     */
    public HeapStep(int[][] step, int memory, ArrayList<Integer> sortedList) {
        super();
        this.step = step;
        this.memory = memory;
        this.sortedList = sortedList;
    }

    /**
     * Sets the array representing the binary tree (see description of the step
     * variable of this class).
     *
     * @param step the new step
     */
    public void setStep(int[][] step) {
        this.step = step;
    }

    /**
     * Sets the memory.
     *
     * @param memory the new memory
     */
    public void setMemory(int memory) {
        this.memory = memory;
    }

    /**
     * Sets the sorted list.
     *
     * @param sortedList the new sorted list
     */
    public void setSortedList(ArrayList<Integer> sortedList) {
        this.sortedList = sortedList;
    }


    /**
     * Gets the step.
     *
     * @return the step
     */
    public int[][] getStep() {
        return step;
    }

    /**
     * Gets the memory.
     *
     * @return the memory
     */
    public int getMemory() {
        return memory;
    }

    /**
     * Gets the sorted list.
     *
     * @return the sorted list
     */
    public ArrayList<Integer> getSortedList() {
        return sortedList;
    }

}
