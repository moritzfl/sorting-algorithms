package de.moritzf.sorting.gui.components;

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
 * A JComponent displaying a tree, given by a {@link TreeNode}.
 *
 * @author Moritz Floeter
 */
public class TreeNodePane extends JComponent {
    private TreeLayout<TreeNode> treeLayout;

    private TreeForTreeLayout<TreeNode> getTree() {
        return treeLayout.getTree();
    }

    private Iterable<TreeNode> getChildren(TreeNode parent) {
        return getTree().getChildren(parent);
    }

    private Rectangle2D.Double getBoundsOfNode(TreeNode node) {
        return treeLayout.getNodeBounds().get(node);
    }

    private TreeNode rootNode;

    /**
     * Specifies the tree to be displayed by passing in a {@link TreeLayout} for that tree. @param
     * treeNode the tree node
     */
    public TreeNodePane(TreeNode treeNode, double scale) {
        this.rootNode = treeNode;
        this.scale = scale;
        TreeForTreeLayout<TreeNode> layout = TreeForTreeLayoutFactory.create(treeNode);
        // create the layout
        double gapBetweenLevels = 30;
        double gapBetweenNodes = 15;
        DefaultConfiguration<TreeNode> configuration =
                new DefaultConfiguration<>(gapBetweenLevels * this.scale, gapBetweenNodes * this.scale);

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
    private double scale = 1;

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

    private void paintNode(Graphics graphic, TreeNode treeNode) {
        // draw the box in the background
        graphic.setColor(BOX_COLOR);
        Rectangle2D.Double box = getBoundsOfNode(treeNode);

        TreeNodeExtentProvider extentProvider = new TreeNodeExtentProvider();
        double aboveTextSize = extentProvider.getAboveNodeHeight(treeNode);

        graphic.fillRoundRect(
                (int) box.x,
                (int) (box.y + aboveTextSize),
                (int) box.width - 1,
                (int) (box.height - aboveTextSize - 1),
                (int) ((double) ARC_SIZE * scale),
                (int) ((double) ARC_SIZE * scale));
        graphic.setColor(BORDER_COLOR);
        graphic.drawRoundRect(
                (int) box.x,
                (int) (box.y + aboveTextSize),
                (int) box.width - 1,
                (int) (box.height - aboveTextSize - 1),
                (int) ((double) ARC_SIZE * scale),
                (int) ((double) ARC_SIZE * scale));

        // Draw the box content
        int x = (int) (box.x + (double) ARC_SIZE * scale / 2d);
        int y = (int) (box.y + (double) ARC_SIZE * scale / 2d);

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
                            (int) (20d * scale),
                            TeXConstants.UNIT_PIXEL,
                            (int) (80d * scale),
                            TeXConstants.ALIGN_CENTER);
            aboveNodeIcon.paintIcon(this, graphic, x, y);
        }

        TeXFormula nodeFormula = new TeXFormula(text);
        TeXIcon nodeIcon =
                nodeFormula.createTeXIcon(
                        TeXConstants.STYLE_DISPLAY, (int) (20d * scale), TeXConstants.UNIT_PIXEL, (int) (80 * scale), TeXConstants.ALIGN_CENTER);
        nodeIcon.paintIcon(this, graphic, x, y + (int) aboveTextSize);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintEdges(g, getTree().getRoot());

        for (TreeNode treeNode : treeLayout.getNodeBounds().keySet()) {
            paintNode(g, treeNode);
        }
    }


    private static class TreeForTreeLayoutFactory {

        /**
         * Create tree for tree layout.
         *
         * @param rootNode the root node
         * @return the tree for tree layout
         */
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
                            (int) (20d * scale),
                            TeXConstants.UNIT_PIXEL,
                            (int) (80d * scale),
                            TeXConstants.ALIGN_CENTER);

            return icon.getTrueIconWidth() + ARC_SIZE;
        }

        public double getHeight(TreeNode treeNode) {

            String text = treeNode.getValue().toString();
            String aboveText = null;
            if (text.contains("%begin-above-node")) {
                String[] textParts = text.split("%begin-above-node");
                text = textParts[0];
            }

            TeXFormula formula = new TeXFormula(LatexUtil.normalizeTexExpression(text));
            TeXIcon icon =
                    formula.createTeXIcon(
                            TeXConstants.STYLE_DISPLAY,
                            (int) (20d * scale),
                            TeXConstants.UNIT_PIXEL,
                            (int) (80d * scale),
                            TeXConstants.ALIGN_CENTER);

            return icon.getTrueIconHeight() + (double) ARC_SIZE * scale + getAboveNodeHeight(treeNode);
        }

        /**
         * Get above node height double.
         *
         * @param treeNode the tree node
         * @return the double
         */
        public double getAboveNodeHeight(TreeNode treeNode) {
            String text = treeNode.getValue().toString();
            String aboveText = null;
            if (text.contains("%begin-above-node")) {
                String[] textParts = text.split("%begin-above-node");
                aboveText = textParts[1];
            }

            TeXFormula formula = new TeXFormula(LatexUtil.normalizeTexExpression(text));

            double aboveTextHeight = 0;

            if (aboveText != null) {
                TeXIcon aboveTexIcon =
                        formula.createTeXIcon(
                                TeXConstants.STYLE_DISPLAY,
                                (int) (20d * scale),
                                TeXConstants.UNIT_PIXEL,
                                (int) (80d * scale),
                                TeXConstants.ALIGN_CENTER);
                aboveTextHeight = aboveTexIcon.getTrueIconHeight() + ARC_SIZE * scale;
            }

            return aboveTextHeight;
        }
    }
}
