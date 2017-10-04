package de.moritzf.sorting.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import static java.nio.charset.Charset.*;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class SaveFile.
 *         Provides functionality for saving files. It is used to save latex files but
 *         can easily be used to save other textbased files aswell if that is needed
 *         in the future.
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
public class SaveFile {


    private static String templatePath = SaveFile.class.getResource("export-template.txt").getPath();

    /**
     * Saves the String passed to it as LaTeX document
     *
     * @param parent the parent
     * @param body   the body
     */
    public static void saveLaTeX(JFrame parent, String body) {
        saveFile(parent, "tex", insertIntoTemplate(body));
    }

    /**
     * Save file. Shows a window that lets you choose a path for saving a file
     * and saves the text contained in the body-parameter in that path.
     *
     * @param parent the parent
     * @param type   the type
     * @param body   the body
     */
    public static void saveFile(JFrame parent, String type, String body) {
        FileWriter writer = null;
        try {
            File fileToSave = showSaveFileDialog(parent, type);
            if (fileToSave != null) {
                fileToSave.createNewFile();
                writer = new FileWriter(fileToSave.getAbsolutePath(), false);
                writer.write(body);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "An error occured while writing the file",
                    "Developer has no clue why this happened", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                //nothing to do here...
            }

        }
    }

    /**
     * Show save file dialog.
     *
     * @param parent the parent
     * @param type   the type
     * @return the file
     */
    private static File showSaveFileDialog(JFrame parent, String type) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save " + type + "-file");

        File fileToSave = null;
        boolean overwrite = false;

        do {
            int userSelection = fileChooser.showSaveDialog(parent);

            //only store a path if the user wanted to save the path
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileToSave = fileChooser.getSelectedFile();
            } else {
                fileToSave = null;
            }

            //append file-extension if not included in path
            if (fileToSave != null
                    && !fileToSave.getAbsolutePath().endsWith("." + type)) {
                fileToSave = new File(fileToSave.getAbsolutePath() + "." + type);
            }

            //ask before overwriting existing files
            if (fileToSave != null && fileToSave.exists()) {
                int reply = JOptionPane
                        .showConfirmDialog(
                                parent,
                                "<html> File already exists. Do you want to replace the existing file?</html>",
                                "File already exists.",
                                JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    overwrite = true;
                } else {
                    overwrite = false;
                }
            }

        } while (fileToSave != null && (fileToSave.exists() && !overwrite));

        return fileToSave;
    }

    private static String insertIntoTemplate(String body) {
        String template = null;
        try {
            template = new String(Files.readAllBytes(new File(templatePath).toPath()), forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return template.replace("%\\{body}", body);
    }
}
