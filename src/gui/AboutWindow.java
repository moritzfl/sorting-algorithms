package gui;

import gui.components.MonoTextArea;
import gui.components.LaTeXPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import static java.nio.charset.Charset.forName;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class AboutWindow. This class represents the about window of the
 *         program.
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

public class AboutWindow extends JFrame {

    private static final long serialVersionUID = -8640373993891704839L;

    private static String versionPath = AboutWindow.class.getResource("about-version.txt").getPath();
    private static String copyrightPath = AboutWindow.class.getResource("about-copyright.txt").getPath();

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
        try {
            versionInformation = new String(Files.readAllBytes(new File(versionPath).toPath()), forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        String copyrightInformation = null;
        try {
            copyrightInformation = new String(Files.readAllBytes(new File(copyrightPath).toPath()), forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainpane.add(new LaTeXPanel(versionInformation, 21), BorderLayout.NORTH);
        mainpane.add(new JScrollPane(new MonoTextArea(copyrightInformation)), BorderLayout.CENTER);

        this.setLocationRelativeTo(parent);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
