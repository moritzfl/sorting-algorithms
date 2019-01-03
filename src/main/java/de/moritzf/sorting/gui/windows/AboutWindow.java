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
 * This class represents the about window of the program.
 *
 * @author Moritz Floeter
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
        try (BufferedReader buffer =
                     new BufferedReader(
                             new InputStreamReader(this.getClass().getResourceAsStream(VERSION_FILE)))) {

            versionInformation = buffer.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        String copyrightInformation = null;
        try (BufferedReader buffer =
                     new BufferedReader(
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
