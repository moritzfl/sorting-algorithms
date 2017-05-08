package logic.sorting;

// TODO: Auto-generated Javadoc
/**
 * @author Moritz Floeter
 * 
 * The Class QuickArray. This is used to represent a subarray in the algorithm
 * quicksort as the array gets split into smaller arrays.
 * 
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class QuickArray {

	/** The array that the sorting algorithm is supposed to sort. */
	private int[] array;

	/**
	 * The left start. This indicates the starting position of i (see gui or
	 * pseudocode)
	 */
	private int leftStart;

	/**
	 * The left end. This indicates the stopping position of i (see gui or
	 * pseudocode)
	 */
	private int leftEnd;

	/**
	 * The right start. This indicates the starting position of j (see gui or
	 * pseudocode)
	 */
	private int rightStart;

	/**
	 * The right end. This indicates the stopping position of j (see gui or
	 * pseudocode)
	 */
	private int rightEnd;

	/**
	 * Instantiates a new quick array.
	 *
	 * @param array
	 *            the array
	 */
	public QuickArray(int[] array) {
		super();
		this.array = array;
		this.leftStart = -1;
		this.rightStart = array.length - 1;
	}

	/**
	 * Gets the right end.
	 *
	 * @return the right end
	 */
	public int getRightEnd() {
		return rightEnd;
	}

	/**
	 * Sets the right end.
	 *
	 * @param rightEnd
	 *            the new right end
	 */
	public void setRightEnd(int rightEnd) {
		this.rightEnd = rightEnd;
	}

	/**
	 * Sets the left end.
	 *
	 * @param leftEnd
	 *            the new left end
	 */
	public void setLeftEnd(int leftEnd) {
		this.leftEnd = leftEnd;
	}

	/**
	 * Instantiates a new quick array.
	 *
	 * @param array
	 *            the array
	 * @param leftStart
	 *            the left start
	 * @param rightStart
	 *            the right start
	 */
	public QuickArray(int[] array, int leftStart, int rightStart) {
		super();
		this.array = array;
		this.leftStart = leftStart;
		this.rightStart = rightStart;

	}

	/**
	 * Gets the array.
	 *
	 * @return the array
	 */
	public int[] getArray() {
		return array;
	}

	/**
	 * Gets the left start.
	 *
	 * @return the left start
	 */
	public int getLeftStart() {
		return leftStart;
	}

	/**
	 * Gets the left end.
	 *
	 * @return the left end
	 */
	public int getLeftEnd() {
		return leftEnd;
	}

	/**
	 * Gets the right start.
	 *
	 * @return the right start
	 */
	public int getRightStart() {
		return rightStart;
	}

}
