package de.moritzf.sorting.logic.util;

/**
 * The Class IntArrayUtils.
 *
 * @author Moritz Floeter
 */
public class IntArrayUtils {

    /**
     * Copy array.
     *
     * @param src the src
     * @return the int[]
     */
    public static int[] copyArray(int[] src) {
        int[] dest = new int[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        return dest;
    }
}
