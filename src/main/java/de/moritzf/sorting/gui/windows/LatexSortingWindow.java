package de.moritzf.sorting.gui.windows;

import de.moritzf.sorting.gui.components.LatexPanel;
import de.moritzf.sorting.logic.sorting.SortingAlgorithm;

import javax.swing.*;

/**
 * Class for displaying sorting algorithms by using their latex-protocol.
 */
public class LatexSortingWindow extends AbstractSortingWindow {

    private LatexPanel protocolComponent = new LatexPanel();

    /**
     * Instantiates a new sorting window.
     *
     * @param parent    the parent window.
     * @param algorithm the sorting algorithm
     */
    public LatexSortingWindow(JFrame parent, SortingAlgorithm algorithm) {
        super(parent, algorithm);
        this.setProtocolComponent(protocolComponent);
        this.renderProtocol();
    }

    @Override
    protected void renderProtocol() {
        System.out.println(algorithm.protocol2LaTeX());
        this.protocolComponent.setExpression(algorithm.protocol2LaTeX());
    }
}
