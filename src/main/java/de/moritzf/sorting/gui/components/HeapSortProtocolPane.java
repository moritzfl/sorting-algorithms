package de.moritzf.sorting.gui.components;

import de.moritzf.sorting.logic.sorting.algorithms.HeapSort;
import de.moritzf.sorting.logic.sorting.steps.HeapStep;

import javax.swing.*;

import java.awt.*;
import java.util.Arrays;

/**
 * Class for rendering the heap sort protocol.
 *
 * @author Moritz Floeter
 */
public class HeapSortProtocolPane extends JPanel implements ResizableComponent {

    private HeapSort algorithm;
    private JPanel protocolPanel;
    private double scale = 1d;

    /**
     * Instantiates a new Heap sort protocol pane.
     *
     * @param heapSort the heap sort
     */
    public HeapSortProtocolPane(HeapSort heapSort) {

        this.setLayout(new BorderLayout());
        protocolPanel = new JPanel();
        this.setBackground(Color.white);
        protocolPanel.setBackground(Color.white);
        protocolPanel.setLayout(new BoxLayout(protocolPanel, BoxLayout.Y_AXIS));

        this.add(protocolPanel, BorderLayout.NORTH);
        this.algorithm = heapSort;
        this.generateProtocol();
    }


    private void addToProtocol(Component comp) {
        JPanel pushLeft = new JPanel();
        pushLeft.setBackground(Color.white);
        pushLeft.setLayout(new BorderLayout());
        pushLeft.add(comp, BorderLayout.WEST);
        protocolPanel.add(pushLeft);
    }

    /**
     * Generate protocol.
     */
    public void generateProtocol() {
        protocolPanel.removeAll();

        for (int i = 0; i < this.algorithm.getProtocol().size(); i++) {
            HeapStep step = this.algorithm.getProtocol().get(i);

            if (i == 0) {
                String heapType = "Maxheap";
                if (!algorithm.isMaxHeap()) {
                    heapType = "Minheap";
                }
                addToProtocol(
                        new LatexPanel("\\huge{\\text{\\underline{Heap Creation (" + heapType + ")}}} ", scale));
                addToProtocol(new LatexPanel("\\text{original binary tree}", scale));
            }
            if (i == 1) {
                addToProtocol(new LatexPanel("\\text{\\rhd \\lhd: heapified element} ", scale));
            }

            if (step.getCurrentNode() == -1) {
                addToProtocol(new LatexPanel("\\huge{\\text{\\underline{Sorting}}} ", scale));
            }

            if (step.getSortedNumbers().size() > 0) {
                String listAsString = Arrays.toString(step.getSortedNumbers().toArray());
                addToProtocol(new LatexPanel("sorted\\, array: " + listAsString, scale));
            }

            if (step.getRootNode() != null) {
                addToProtocol(new TreeNodePane(step.getRootNode(), scale));
                addToProtocol(new LatexPanel("..................................", scale));
            } else {
                addToProtocol(new LatexPanel("\\text{finished sorting!} ", scale));
            }
        }
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
        this.generateProtocol();
    }

    @Override
    public double getScale() {
        return this.scale;
    }
}