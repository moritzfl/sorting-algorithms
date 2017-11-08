/*
 * 
 */
package de.moritzf.sorting.gui.windows;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.moritzf.sorting.gui.components.LaTeXPanel;
import de.moritzf.sorting.io.SaveFile;
import de.moritzf.sorting.logic.sorting.SortingAlgorithm;

/**
 * @author Moritz Floeter
 * <p>
 * The Class SelectionWindow. This class serves the graphical
 * representation of the selectionsort algorithm in a gui frame. From
 * here all important actions concerning the sorting algorithm can be
 * performed and displayed.
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
public class SortingWindow extends SortingWindowSubstructure implements ActionListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 7326001445728823589L;

    private static final float SMALLMATRIX_FONT_SIZE = 30;
    private static final float NORMAL_FONT_SIZE = 21;

    private boolean smallmatrix;

    /**
     * The sorting algorithm.
     */
    private SortingAlgorithm algorithm;

    /**
     * Instantiates a new sorting window.
     *
     * @param parent    the parent window.
     * @param algorithm the sorting algorithm
     */
    public SortingWindow(JFrame parent, SortingAlgorithm algorithm) {
        super(parent);
        this.setTitle(algorithm.getName());
        this.algorithm = algorithm;
        nextStepBtn.addActionListener(this);
        undoStepBtn.addActionListener(this);
        allStepsBtn.addActionListener(this);
        reset.addActionListener(this);
        infoBtn.addActionListener(this);
        exportBtn.addActionListener(this);

        if (algorithm.getStepLimit() != -1 && algorithm.getStepLimit() < algorithm.getInputSize()) {
            allStepsBtn.setText("<html> &nbsp; <br>Do " + algorithm.getStepLimit() + " Steps <br> &nbsp; <html>");
        }

        this.smallmatrix = (this.algorithm.step2LaTeX(0).contains("\\begin{smallmatrix}"));

        this.addLastStepToProtocol();
    }

    private float getFontSize() {
        float fontSize = NORMAL_FONT_SIZE;
        if (smallmatrix) {
            fontSize = SMALLMATRIX_FONT_SIZE;
        }
        return fontSize;
    }

    private void addLastStepToProtocol() {

        this.addToProtocol(new LaTeXPanel(algorithm.step2LaTeX(algorithm.getProtocolSize() - 1), this.getFontSize()));
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
        } else if (e.getSource().equals(undoStepBtn)) {
            handleUndo();
        } else if (e.getSource().equals(reset)) {
            handleReset();
        } else if (e.getSource().equals(allStepsBtn)) {
            handleAllSteps();
        } else if (e.getSource().equals(exportBtn)) {
            handleExport();
        } else if (e.getSource().equals(infoBtn)) {
            handleInfo();
        }
    }

    private void handleNextStep() {
        boolean stepDone = algorithm.doStep();
        if (stepDone) {
            this.addLastStepToProtocol();
            this.validate();
            this.repaint();
            this.scrollToBottom();
        } else {
            JOptionPane.showMessageDialog(this,
                    "<html>Finished sorting - no further steps possible</html>", "Layer-8-Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUndo() {
        boolean stepUndone = algorithm.undoStep();
        if (stepUndone) {
            this.removeLastElementFromProtocol();
            this.validate();
            this.repaint();
        } else {
            JOptionPane.showMessageDialog(this,
                    "<html>Can't undo step because there is nothing more to undo...</html>", "Layer-8-Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleReset() {
        this.algorithm.reset();
        while (this.protocolListPnl.getComponentCount() > 1) {
            this.removeLastElementFromProtocol();
        }
        this.validate();
        this.repaint();
    }

    private void handleAllSteps() {
        int oldSize = algorithm.getProtocolSize();

        if (algorithm.getStepLimit() == -1 || algorithm.getStepLimit() >= algorithm.getInputSize()) {
            algorithm.doAllSteps();
        } else {
            boolean stepDone = true;
            for (int i = 0; i < algorithm.getStepLimit() && stepDone; i++) {
                stepDone = algorithm.doStep();
                System.out.println(i);
            }
        }

        for (int i = oldSize; i < algorithm.getProtocolSize(); i++) {
            this.addToProtocol(
                    new LaTeXPanel(algorithm.step2LaTeX(i), this.getFontSize()));
        }

        this.validate();
        this.repaint();
    }

    private void handleInfo() {

        String infoPath = this.algorithm.getPdfInfoFilePath();

        try {
            Desktop.getDesktop().open(new File(infoPath));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "<html> Failed to open algorithm's info file.<br> Make sure you have an pdf-reader installed.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void handleExport() {
        if (this.algorithm.getInputSize() > 20) {
            int reply = JOptionPane.showConfirmDialog(this,
                    "<html> You are trying to export the protocol for a large input (>20 elements). <br>"
                            + " It is possible to create a *.tex file. However LaTeX is a format which is only meant <br>"
                            + " for information that actually fits on paper. <br>"
                            + " Therefore imperfect layout and formatting issues may occur when compiling the pdf."
                            + "<br> <br> Would you like to continue? </html>",
                    "Arraysize = Overkill", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                SaveFile.saveLaTeX(this, this.algorithm.protocol2LaTeX());
            }
        } else {
            SaveFile.saveLaTeX(this, this.algorithm.protocol2LaTeX());
        }
    }

}
