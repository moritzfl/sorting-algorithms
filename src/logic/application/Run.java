package logic.application;

import gui.general.AlgorithmSelection;
import gui.general.GeneralGuiFunctions;

// TODO: Auto-generated Javadoc
/**
 *
 * @author Moritz Floeter
 * 
 * The Class Run. The Class run has the sole purpose of starting the program. It
 * contains the main method that starts the main program as intended by the
 * developer.
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
public class Run {

    /**
     * The main method. This method launches the program.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        // Set System look and feel according to the current OS
    	GeneralGuiFunctions.setSystemLookAndFeel();

        // opens a Algorithm Selection window.
        new AlgorithmSelection();
    }
}
