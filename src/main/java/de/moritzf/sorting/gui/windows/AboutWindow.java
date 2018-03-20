package de.moritzf.sorting.gui.windows;

import de.moritzf.sorting.gui.components.LatexPanel;
import de.moritzf.sorting.gui.components.MonoTextArea;


import java.awt.BorderLayout;
import java.awt.Container;
import java.io.*;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;



/**
 * @author Moritz Floeter
 * <p>
 * The Class AboutWindow. This class represents the about window of the
 * program.
 * <p>
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

public class AboutWindow extends JFrame {

    private static final long serialVersionUID = -8640373993891704839L;

    private static String VERSION_FILE = "about-version.txt";
    private static String COPYRIGHT_FILE = "about-copyright.txt";

    /**
     * Instantiates a new about window.
     *
     * @param parent the parent
     */
    public AboutWindow(JFrame parent) {
        super("About");
        this.setSize(600, 400);
        Container mainpane = this.getContentPane();
        mainpane.setLayout(new BorderLayout());

        String versionInformation = null;
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream(VERSION_FILE)))) {

            versionInformation = buffer.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }


        String copyrightInformation = null;
        try (BufferedReader buffer = new BufferedReader(
                new InputStreamReader(this.getClass().getResourceAsStream(COPYRIGHT_FILE)))) {

            copyrightInformation = buffer.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        mainpane.add(new LatexPanel(versionInformation), BorderLayout.NORTH);
        mainpane.add(new JScrollPane(new MonoTextArea(copyrightInformation)), BorderLayout.CENTER);

        this.setLocationRelativeTo(parent);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

}
