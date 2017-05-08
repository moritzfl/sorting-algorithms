package logic.util;

/**
 * The Class IntArrayUtils.
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

    /**
     * Returns a copy of the array with a new int-value added at its end.
     *
     * @param src        the source
     * @param number2add the number2add
     * @return the int[]
     */
    public static int[] copyAdd2IntArray(int[] src, int number2add) {
        int[] dest = new int[src.length + 1];
        System.arraycopy(src, 0, dest, 0, src.length);
        dest[dest.length - 1] = number2add;
        return dest;
    }

}
