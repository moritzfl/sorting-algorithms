package de.moritzf.sorting.gui.components;

import de.moritzf.sorting.logic.sorting.HeapSort;
import de.moritzf.sorting.logic.sorting.HeapStep;

import javax.swing.*;

import java.awt.*;
import java.util.Arrays;

public class HeapSortProtocolPane extends JPanel implements ResizableComponent {

  private HeapSort algorithm;
  private JPanel protocolPanel;

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

  @Override
  public void resetScale() {}

  @Override
  public void increaseScale() {}

  @Override
  public void decreaseScale() {}

  private void addToProtocol(Component comp) {
    JPanel pushLeft = new JPanel();
    pushLeft.setBackground(Color.white);
    pushLeft.setLayout(new BorderLayout());
    pushLeft.add(comp, BorderLayout.WEST);
    protocolPanel.add(pushLeft);
  }

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
            new LatexPanel("\\huge{\\text{\\underline{Heap Creation (" + heapType + ")}}} "));
        addToProtocol(new LatexPanel("\\text{original binary tree}"));
      }
      if (i == 1){
        addToProtocol(new LatexPanel("\\text{\\rhd \\lhd: element that is to be heapified} "));
      }

      if (step.getCurrentNode() == -1) {
        addToProtocol(new LatexPanel("\\huge{\\text{\\underline{Sorting}}} "));
      }

      if (step.getSortedNumbers().size() > 0) {
        String listAsString = Arrays.toString(step.getSortedNumbers().toArray());
        addToProtocol(new LatexPanel("sorted\\, array: " + listAsString));
      }

      if (step.getRootNode() != null) {
        addToProtocol(new TreeNodePane(step.getRootNode()));
        addToProtocol(new LatexPanel(".................................."));
      } else {
        addToProtocol(new LatexPanel("\\text{finished sorting!} "));
      }
    }
  }
}
