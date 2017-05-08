package gui.general;

import java.awt.GraphicsDevice;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Window;
import java.lang.reflect.Method;

import javax.swing.UIManager;


/**
 * The Class WindowFunctions.
 *
 * @author Moritz Floeter
 *         <p>
 *         The Class WindowFunctions. This class provides certain functions for
 *         windows. (see description of the methods in this class for more
 *         details)
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or (at
 *         your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class GeneralGuiFunctions {

    /**
     * Centers the window passed to it.
     *
     * @param window the window
     */
    public static final void centerWindow(final Window window) {
        GraphicsDevice screen = MouseInfo.getPointerInfo().getDevice();
        Rectangle r = screen.getDefaultConfiguration().getBounds();
        int x = (r.width - window.getWidth()) / 2 + r.x;
        int y = (r.height - window.getHeight()) / 2 + r.y;
        window.setLocation(x, y);
    }

    /**
     * Formats a tooltip with linebreaks.
     *
     * @param tooltip
     * @return the string
     */
    public static String formatTip(String tooltip) {
        String linebreakTooltip = "";
        String[] sArray = tooltip.split(" ");
        int oldTooltipSize = 0;

        for (int i = 0; i < sArray.length; i++) {
            linebreakTooltip += " " + sArray[i];
            // if a single line has gotten longer than 60 characters, a line
            // break
            // gets inserted
            if (sArray[i].contains("<br>")) {
                oldTooltipSize = linebreakTooltip.length();
            } else if (!(linebreakTooltip.length() - oldTooltipSize < 60)) {
                linebreakTooltip += "<br>";
                oldTooltipSize = linebreakTooltip.length();
            }
        }

        return "<html>" + linebreakTooltip + "</html>";

    }

    /**
     * Enable osx fullscreen.
     *
     * @param window the window
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void enableOSXFullscreen(Window window) {
        try {
            Class util = Class.forName("com.apple.eawt.FullScreenUtilities");
            Class params[] = new Class[]{Window.class, Boolean.TYPE};
            Method method = util.getMethod("setWindowCanFullScreen", params);
            method.invoke(util, window, true);
        } catch (Exception e) {
            //do nothing cause there is nothing to do :D
        }
    }

    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Could not set System LookAndFeel.");
            e.printStackTrace();
        }
    }

}
