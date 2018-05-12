package de.moritzf.sorting.gui.components;

/*
 * [The "BSD license"]
 * Copyright (c) 2011, abego Software GmbH, Germany (http://www.abego.org)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the abego Software GmbH nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.JComponent;

import de.moritzf.sorting.gui.util.LatexUtil;
import de.moritzf.sorting.logic.sorting.TreeNode;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 * A JComponent displaying a tree of TextInBoxes, given by a {@link TreeLayout}.
 *
 * @author Udo Borkowski (ub@abego.org)
 */
public class TreeNodePane extends JComponent implements ResizableComponent {
  private final TreeLayout<TreeNode> treeLayout;

  private TreeForTreeLayout<TreeNode> getTree() {
    return treeLayout.getTree();
  }

  private Iterable<TreeNode> getChildren(TreeNode parent) {
    return getTree().getChildren(parent);
  }

  private Rectangle2D.Double getBoundsOfNode(TreeNode node) {
    return treeLayout.getNodeBounds().get(node);
  }

  /** Specifies the tree to be displayed by passing in a {@link TreeLayout} for that tree. */
  public TreeNodePane(TreeNode treeNode) {
    TreeForTreeLayout<TreeNode> layout = TreeForTreeLayoutFactory.create(treeNode);
    // create the layout
    double gapBetweenLevels = 50;
    double gapBetweenNodes = 15;
    DefaultConfiguration<TreeNode> configuration =
        new DefaultConfiguration<>(gapBetweenLevels, gapBetweenNodes);

    TreeLayout<TreeNode> treeLayout =
        new TreeLayout<>(layout, new TreeNodeExtentProvider(), configuration);
    this.treeLayout = treeLayout;
    Dimension size = treeLayout.getBounds().getBounds().getSize();
    setPreferredSize(size);
  }

  // -------------------------------------------------------------------
  // painting

  private static final int ARC_SIZE = 20;
  private static final Color BOX_COLOR = Color.white;
  private static final Color BORDER_COLOR = Color.black;

  private void paintEdges(Graphics g, TreeNode parent) {
    if (!getTree().isLeaf(parent)) {
      Rectangle2D.Double b1 = getBoundsOfNode(parent);
      double x1 = b1.getCenterX();
      double y1 = b1.getCenterY();
      for (TreeNode child : getChildren(parent)) {
        Rectangle2D.Double b2 = getBoundsOfNode(child);
        g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(), (int) b2.getCenterY());

        paintEdges(g, child);
      }
    }
  }

  private void paintBox(Graphics g, TreeNode treeNode) {
    // draw the box in the background
    g.setColor(BOX_COLOR);
    Rectangle2D.Double box = getBoundsOfNode(treeNode);
    g.fillRoundRect(
        (int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);
    g.setColor(BORDER_COLOR);
    g.drawRoundRect(
        (int) box.x, (int) box.y, (int) box.width - 1, (int) box.height - 1, ARC_SIZE, ARC_SIZE);

    // Draw the box content
    int x = (int) box.x + ARC_SIZE / 2;
    int y = (int) box.y + ARC_SIZE / 2;

    String text = treeNode.getValue().toString();
    String aboveNode = null;
    if (text.contains("%begin-above-node")) {
      String[] textParts = text.split("%begin-above-node");
      text = textParts[0];
      aboveNode = textParts[1];
    }
    TeXIcon aboveNodeIcon;
    if (aboveNode != null) {
      TeXFormula aboveNodeFormula = new TeXFormula(aboveNode);
      aboveNodeIcon =
          aboveNodeFormula.createTeXIcon(
              TeXConstants.STYLE_DISPLAY,
              20,
              TeXConstants.UNIT_PIXEL,
              80,
              TeXConstants.ALIGN_CENTER);
      aboveNodeIcon.paintIcon(this, g, x, y - ARC_SIZE / 2 - 20);
    }

    TeXFormula nodeFormula = new TeXFormula(text);
    TeXIcon nodeIcon =
        nodeFormula.createTeXIcon(
            TeXConstants.STYLE_DISPLAY, 20, TeXConstants.UNIT_PIXEL, 80, TeXConstants.ALIGN_CENTER);
    nodeIcon.paintIcon(this, g, x, y);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    paintEdges(g, getTree().getRoot());

    for (TreeNode textInBox : treeLayout.getNodeBounds().keySet()) {
      paintBox(g, textInBox);
    }
  }

  @Override
  public void resetScale() {}

  @Override
  public void increaseScale() {}

  @Override
  public void decreaseScale() {}

  private static class TreeForTreeLayoutFactory {

    public static TreeForTreeLayout<TreeNode> create(TreeNode rootNode) {
      DefaultTreeForTreeLayout<TreeNode> tree = new DefaultTreeForTreeLayout<>(rootNode);
      appendChildren(tree, rootNode);
      return tree;
    }

    private static void appendChildren(DefaultTreeForTreeLayout<TreeNode> tree, TreeNode parent) {
      for (TreeNode child : (List<TreeNode>) parent.getChildren()) {
        tree.addChild(parent, child);
        appendChildren(tree, child);
      }
    }
  }

  private class TreeNodeExtentProvider implements NodeExtentProvider<TreeNode> {

    @Override
    public double getWidth(TreeNode treeNode) {
      String text = treeNode.getValue().toString();
      if (text.contains("%begin-above-node")) {
        String[] textParts = text.split("%begin-above-node");
        text = textParts[0];
      }

      TeXFormula formula = new TeXFormula(LatexUtil.normalizeTexExpression(text));
      TeXIcon icon =
          formula.createTeXIcon(
              TeXConstants.STYLE_DISPLAY,
              20,
              TeXConstants.UNIT_PIXEL,
              80,
              TeXConstants.ALIGN_CENTER);

      return icon.getTrueIconWidth() + ARC_SIZE;
    }

    @Override
    public double getHeight(TreeNode treeNode) {

      String text = treeNode.getValue().toString();
      if (text.contains("%begin-above-node")) {
        String[] textParts = text.split("%begin-above-node");
        text = textParts[0];
      }

      TeXFormula formula = new TeXFormula(LatexUtil.normalizeTexExpression(text));
      TeXIcon icon =
          formula.createTeXIcon(
              TeXConstants.STYLE_DISPLAY,
              20,
              TeXConstants.UNIT_PIXEL,
              80,
              TeXConstants.ALIGN_CENTER);

      return icon.getTrueIconHeight() + ARC_SIZE;
    }
  }
}
