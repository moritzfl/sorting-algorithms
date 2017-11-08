package de.moritzf.sorting.gui.windows;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import javax.swing.*;


/**
 * @author Moritz Floeter
 *         <p>
 *         Window displaying a pdf document.
 *         Code fragments taken from https://stackoverflow.com/a/4437985
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
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>. */
public class PdfViewerWindow extends JFrame{

    /**
     * Instantiates a new Pdf viewer window.
     *
     * @param filePath the file path
     */
    public PdfViewerWindow(String filePath) {

        SwingController controller = new SwingController();
        SwingViewBuilder factory = new SwingViewBuilder(controller);

        JPanel viewerComponentPanel = factory.buildViewerPanel();

        // add interactive mouse link annotation support via callback
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(viewerComponentPanel);

        // Now that the GUI is all in place, we can try opening a PDF
        controller.openDocument(filePath);

        controller.setToolBarVisible(false);
        controller.setUtilityPaneVisible(true);

        // show the component
        this.pack();
        this.setVisible(true);
    }
}