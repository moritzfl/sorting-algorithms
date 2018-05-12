package de.moritzf.sorting.gui.components;

import de.moritzf.sorting.logic.sorting.HeapSort;
import de.moritzf.sorting.logic.sorting.HeapStep;

import javax.swing.*;
import java.awt.*;

public class HeapSortProtocolPane extends JPanel implements ResizableComponent {

  private HeapSort algorithm;

  public HeapSortProtocolPane(HeapSort heapSort) {
    this.setLayout(new GridLayout(0, 1));
    this.algorithm = heapSort;
    this.generateProtocol();
  }

  @Override
  public void resetScale() {}

  @Override
  public void increaseScale() {}

  @Override
  public void decreaseScale() {}

  public void generateProtocol() {
    this.removeAll();
    for (HeapStep step : algorithm.getProtocol()) {
      if (step.getRootNode() != null) {
        this.add(new TreeNodePane(step.getRootNode()));
      }
    }
  }
}
