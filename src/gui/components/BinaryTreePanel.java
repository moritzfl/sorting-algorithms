package gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class BinaryTreePanel. This panel renders a string-array
 *         as a binary tree The array gets displayed as follows: a[0]->rootnode,
 *         a[1]->left child of root, a[2]->right child of root, a[3]->left child of
 *         a[1], a[4]->right child of a[1], a[5]->left child of a[2], ...
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

public class BinaryTreePanel extends JPanel {

    private static final long serialVersionUID = 1661954419258085501L;

    /**
     * The tree array.
     */
    private String[] treeArray;

    /**
     * FONT. defines the font which is used in the BinaryTreePanel. This should
     * always be a Monospace Font.
     */
    public static final Font FONT = new Font("Courier", 0, 20);

    /**
     * Instantiates a new binary tree panel.
     *
     * @param treeArray the tree array @param width the width @param height the
     *                  height
     */
    public BinaryTreePanel(String[] treeArray, int width, int height) {
        // set the colors
        this.setBackground(Color.white);
        this.setForeground(Color.black);

        // set the size
        this.setMinimumSize(new Dimension(width, height));
        this.setPreferredSize(new Dimension(width, height));

        this.treeArray = treeArray;

    }

    /**
     * Gets the font width. Should only be used on monospace fonts as the width
     * of other fonts varies depending on the character.
     *
     * @return the font width
     */
    public static double getFontWidth() {
        return new JPanel().getFontMetrics(BinaryTreePanel.FONT).stringWidth("A");
    }

    /**
     * Gets the font height.
     *
     * @return the font height
     */
    public static double getFontHeight() {
        return new JPanel().getFontMetrics(BinaryTreePanel.FONT).getHeight();
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    protected void paintComponent(Graphics g) {
        // paint over the background, get width and height of the panel
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        // the height of the tree is calculated (in rows)
        int treeHeight = (int) (Math.floor(Math.log(this.treeArray.length) / Math.log(2)));
        drawNode(g, 0, width, 0, height / (treeHeight + 1), 0);
    }

    /**
     * Draw node. Draws a node and all its children by recursively calling this
     * method.
     *
     * @param graphic the graphic @param minX the min x @param maxX the max
     *                x @param yCoordinate the y coordinate @param rowHeight the row
     *                height @param nodeNumber the node number
     */
    private void drawNode(Graphics graphic, int minX, int maxX, int yCoordinate, int rowHeight, int nodeNumber) {
        String nodeText = treeArray[nodeNumber];

        graphic.setFont(FONT);
        FontMetrics fm = graphic.getFontMetrics();
        int width = fm.stringWidth(nodeText);
        int height = fm.getHeight();

        graphic.drawString(nodeText, (minX + maxX) / 2 - width / 2, yCoordinate + rowHeight / 2);

        int leftnode = -1;
        if (treeArray.length > 2 * (nodeNumber + 1) - 1) {
            leftnode = 2 * (nodeNumber + 1) - 1;
        }

        int rightnode = -1;
        if (treeArray.length > 2 * (nodeNumber + 1)) {
            rightnode = 2 * (nodeNumber + 1);
        }

        if (leftnode > -1) {
            // if the left node is not empty a line is drawn to the left child
            graphic.drawLine((minX + maxX) / 2, yCoordinate + rowHeight / 2 + 2, (minX + (minX + maxX) / 2) / 2,
                    yCoordinate + rowHeight + rowHeight / 2 - height);
            // the draw-Method is rendered recursively to paint the left child
            drawNode(graphic, minX, (minX + maxX) / 2, yCoordinate + rowHeight, rowHeight, leftnode);
        }
        if (rightnode > -1) {
            // if the right node is not empty a line is drawn to the right child
            graphic.drawLine((minX + maxX) / 2, yCoordinate + rowHeight / 2 + 2, (maxX + (minX + maxX) / 2) / 2,
                    yCoordinate + rowHeight + rowHeight / 2 - height);
            // the draw-Method is rendered recursively to paint the right child
            drawNode(graphic, (minX + maxX) / 2, maxX, yCoordinate + rowHeight, rowHeight, rightnode);
        }
    }

}
