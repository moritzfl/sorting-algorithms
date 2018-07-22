package de.moritzf.sorting.gui.windows;

import de.moritzf.sorting.gui.components.HeapSortProtocolPane;
import de.moritzf.sorting.logic.sorting.algorithms.HeapSort;

import javax.swing.*;

/** Class for displaying the heap sort algorithm. */
public class HeapSortSortingWindow extends AbstractSortingWindow {

    private HeapSortProtocolPane protocolComponent;

  /**
   * Instantiates a new {@link HeapSortSortingWindow}.
   *
   * @param parent the parent
   * @param algorithm the algorithm
   */
  public HeapSortSortingWindow(JFrame parent, HeapSort algorithm) {
        super(parent, algorithm);
        this.protocolComponent = new HeapSortProtocolPane(algorithm);
        this.setProtocolComponent(protocolComponent);
        this.renderProtocol();
    }

    @Override
    protected void renderProtocol() {
        this.protocolComponent.generateProtocol();
    }
}

