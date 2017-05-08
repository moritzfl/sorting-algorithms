package logic.util;

import java.util.Random;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class InputGeneration.
 *         <p>
 *         The Class InputGeneration. Serves the generation of Input for the different
 *         sorting algorithms.
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
public class InputGeneration {

    /**
     * Generates an array out of a String containing numbers separated by
     * commas.
     *
     * @param input the input as String @return the int[] numbers from the input
     *              as an array
     */
    public static int[] arrayFromString(String input) {
        String[] sArray = input.split(",");

        int[] iArray = new int[sArray.length];

        for (int i = 0; i < sArray.length; i++) {
            try {
                iArray[i] = Integer.parseInt(sArray[i]);
            } catch (NumberFormatException e) {
                iArray[i] = 2147483647;
            }
        }
        return iArray;
    }

    /**
     * Generates an Input as String under conditions defined by the values
     * passed to the function.
     *
     * @param max maximum value of a single element @param min minimum value of
     *            a single element @param count total count of numbers generated @return
     *            the generated String
     */
    public static String generate(int max, int min, int count) {
        String retString = "";
        for (int i = 0; i < count; i++) {
            Random rand = new Random();
            int randomInt = rand.nextInt((max - min) + 1) + min;
            retString += randomInt;
            if (i < count - 1) {
                retString += ",";
            }
        }

        return retString;
    }

}
