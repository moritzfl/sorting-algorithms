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

import de.moritzf.sorting.gui.util.SaveFileUtil;
import de.moritzf.sorting.logic.sorting.SortingAlgorithm;

/**
 * This class serves as a template for the graphical representation of any algorithm that can be
 * divided into steps in a gui frame. From here all important actions concerning the sorting
 * algorithm can be performed and displayed.
 *
 * @author Moritz Floeter
 */
public abstract class AbstractSortingWindow extends AbstractSortingWindowSubstructure
        implements ActionListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 7326001445728823589L;

    /**
     * The sorting algorithm.
     */
    protected SortingAlgorithm algorithm;

    /**
     * Instantiates a new sorting window.
     *
     * @param parent    the parent window.
     * @param algorithm the sorting algorithm
     */
    public AbstractSortingWindow(JFrame parent, SortingAlgorithm algorithm) {
        super(parent);
        this.setTitle(algorithm.getName());
        this.algorithm = algorithm;
        nextStepBtn.addActionListener(this);
        undoStepBtn.addActionListener(this);
        allStepsBtn.addActionListener(this);
        reset.addActionListener(this);
        infoBtn.addActionListener(this);
        exportBtn.addActionListener(this);
    }

    protected abstract void renderProtocol();

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
            this.renderProtocol();
            this.validate();
            this.repaint();
            this.scrollToBottom();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "<html>Finished sorting - no further steps possible</html>",
                    "Layer-8-Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUndo() {
        boolean stepUndone = algorithm.undoStep();
        if (stepUndone) {
            this.renderProtocol();
            this.scrollToTop();
            this.scrollToBottom();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "<html>Can't undo step because there is nothing more to undo...</html>",
                    "Layer-8-Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleReset() {
        this.algorithm.reset();
        this.renderProtocol();
        this.validate();
        this.repaint();
    }

    private void handleAllSteps() {

        boolean stepDone = true;
        while (stepDone) {
            stepDone = algorithm.doStep();
        }

        this.renderProtocol();

        this.validate();
        this.repaint();
    }

    private void handleInfo() {

        String infoPath = this.algorithm.getPdfInfoFilePath();

        try {
            Desktop.getDesktop().open(new File(infoPath));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "<html> Failed to open algorithm's info file.<br> Make sure you have a pdf-reader installed.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleExport() {
        if (this.algorithm.getInputSize() > 20) {
            int reply =
                    JOptionPane.showConfirmDialog(
                            this,
                            "<html> You are trying to export the protocol for a large input (>20 elements). <br>"
                                    + " It is possible to create a *.tex file. However LaTeX is a format which is only meant <br>"
                                    + " for information that actually fits on paper. <br>"
                                    + " Therefore imperfect layout and formatting issues may occur when compiling the pdf."
                                    + "<br> <br> Would you like to continue? </html>",
                            "Arraysize = Overkill",
                            JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                SaveFileUtil.saveLaTeX(this, this.algorithm.protocol2LaTeX());
            }
        } else {
            SaveFileUtil.saveLaTeX(this, this.algorithm.protocol2LaTeX());
        }
    }
}
