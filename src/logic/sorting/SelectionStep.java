package logic.sorting;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectionStep.
 */
public class SelectionStep {
	
	/** The array. */
	int[] array;
	

	
	/** The sorted until position marker. */
	int sortedUntilPositionMarker;
	
	/** The searching position marker. */
	int searchingPositionMarker;
	
	/** The min position. */
	int minPosition;

	/**
	 * Instantiates a new selection step.
	 *
	 * @param array the array
	 * @param sortedUntilPositionMarker the sorted until position marker
	 * @param searchingPositionMarker the searching position marker
	 * @param minPosition the min position
	 */
	public SelectionStep(int[] array, int sortedUntilPositionMarker, int searchingPositionMarker, int minPosition) {
		super();
		this.array = array;
		this.sortedUntilPositionMarker = sortedUntilPositionMarker;
		this.searchingPositionMarker = searchingPositionMarker;
		this.minPosition = minPosition;
	}

	/**
	 * Instantiates a new selection step.
	 */
	public SelectionStep() {
		// TODO Auto-generated constructor stub
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
	 * Sets the array.
	 *
	 * @param array the new array
	 */
	public void setArray(int[] array) {
		this.array = array;
	}

	/**
	 * Gets the sorted until position marker.
	 *
	 * @return the sorted until position marker
	 */
	public int getSortedUntilPositionMarker() {
		return sortedUntilPositionMarker;
	}

	/**
	 * Sets the sorted until position marker.
	 *
	 * @param sortedUntilPositionMarker the new sorted until position marker
	 */
	public void setSortedUntilPositionMarker(int sortedUntilPositionMarker) {
		this.sortedUntilPositionMarker = sortedUntilPositionMarker;
	}

	/**
	 * Gets the searching position marker.
	 *
	 * @return the searching position marker
	 */
	public int getSearchingPositionMarker() {
		return searchingPositionMarker;
	}

	/**
	 * Sets the searching position marker.
	 *
	 * @param searchingPositionMarker the new searching position marker
	 */
	public void setSearchingPositionMarker(int searchingPositionMarker) {
		this.searchingPositionMarker = searchingPositionMarker;
	}

	/**
	 * Gets the min position.
	 *
	 * @return the min position
	 */
	public int getMinPosition() {
		return minPosition;
	}

	/**
	 * Sets the min position.
	 *
	 * @param minPosition the new min position
	 */
	public void setMinPosition(int minPosition) {
		this.minPosition = minPosition;
	}




}
