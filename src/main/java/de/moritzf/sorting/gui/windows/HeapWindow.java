/*
 * 
 */
package de.moritzf.sorting.gui.windows;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.moritzf.sorting.gui.components.HeapSortPanelExtended;
import de.moritzf.sorting.logic.sorting.HeapSort;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class HeapWindow. A gui class for displaying heapsort and perfoming steps
 *         with that algorithm.
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
public class HeapWindow extends SortingWindowSubstructure implements ActionListener {

    private static final long serialVersionUID = -7777097415364226306L;

    /**
     * The heapsort instance being represented by the window.
     */
    private HeapSort hsort;

    /**
     * Instantiates a new heapsort window.
     *
     * @param parent the parent window.
     * @param hsort the hsort.
     */
    public HeapWindow(JFrame parent, HeapSort hsort) {
        super(parent);
        this.setTitle(hsort.getName());
        this.hsort = hsort;
        nextStepBtn.addActionListener(this);
        undoStepBtn.addActionListener(this);
        allStepsBtn.addActionListener(this);
        reset.addActionListener(this);
        exportBtn.addActionListener(this);
        infoBtn.addActionListener(this);

        this.addToProtocol(new HeapSortPanelExtended(hsort.getStep(hsort.getProtocolSize() - 1)));

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nextStepBtn)) {
            handleNextStep();
        } else if (e.getSource().equals(allStepsBtn)) {
            handleAllSteps();
        } else if (e.getSource().equals(undoStepBtn)) {
            handleUndo();
        } else if (e.getSource().equals(reset)) {
            handleReset();
        } else if (e.getSource().equals(exportBtn)) {
            handleExport();
        } else if (e.getSource().equals(infoBtn)) {
            handleInfo();
        }
    }

    private void handleInfo() {
        try {
            Desktop.getDesktop().open(new File(hsort.getPdfInfoFilePath()));
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(this,
                    "<html> Failed to open algorithm's info file.<br> Make sure you have an pdf-reader installed.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleExport() {
        if (this.hsort.getInputSize() > 20) {
            int reply = JOptionPane.showConfirmDialog(this,
                    "<html> You are trying to export the protocol for a large input (>20 elements). <br>"
                            + " It is possible to create a *.tex file. However LaTeX is a format which is only meant <br>"
                            + " for information that actually fits on paper. <br>"
                            + " Therefore imperfect layout and formatting issues may occur when compiling the pdf."
                            + "<br> <br> Would you like to continue? </html>",
                    "Arraysize = Overkill", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                de.moritzf.sorting.io.SaveFile.saveLaTeX(this, this.hsort.protocol2LaTeX());
            }
        } else {
            de.moritzf.sorting.io.SaveFile.saveLaTeX(this, this.hsort.protocol2LaTeX());
        }
    }

    private void handleReset() {
        this.hsort.reset();
        while (this.protocolListPnl.getComponentCount() > 1) {
            this.removeLastElementFromProtocol();
        }
        this.validate();
        this.repaint();
    }

    private void handleUndo() {
        if (hsort.undoStep()) {
            this.removeLastElementFromProtocol();
            this.validate();
            this.repaint();
        } else {
            JOptionPane.showMessageDialog(this,
                    "<html>Can't undo step because there is nothing more to undo...</html>", "Layer-8-Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleNextStep() {
        if (hsort.doStep()) {
            this.addToProtocol(new HeapSortPanelExtended(hsort.getStep(hsort.getProtocolSize() - 1)));
            this.validate();
            this.repaint();
            this.scrollToBottom();
            // a message gets displayed if no step was performed.
        } else {
            JOptionPane.showMessageDialog(this, "<html>Finished sorting - no further steps possible</html>",
                    "Layer-8-Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleAllSteps() {
        hsort.doAllSteps();
        for (int i = this.protocolListPnl.getComponentCount(); i < hsort.getProtocolSize(); i++) {
            this.addToProtocol(new HeapSortPanelExtended(hsort.getStep(i)));
        }
        this.validate();
        this.repaint();
    }

}
