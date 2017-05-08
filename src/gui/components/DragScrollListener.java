package gui.components;

import java.awt.*;

import java.awt.event.*;

import java.awt.geom.Point2D;

import java.beans.PropertyChangeEvent;

import java.beans.PropertyChangeListener;

import java.util.ArrayList;

import javax.swing.*;

/* Note: This class has ENTIRELY been taken from 
* http://www.algosome.com/articles/java-swing-iphone-drag-component.html
* (online as of 31.05.15)*/

/**
 * Listener to allow for iPhone like drag scrolling of a Component within a
 * JScrollPane. @author Greg Cope
 * <p>
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

public class DragScrollListener implements MouseListener, MouseMotionListener {

    // flags used to turn on/off draggable scrolling directions

    /**
     * The Constant DRAGABLE_HORIZONTAL_SCROLL_BAR.
     */
    public static final int DRAGABLE_HORIZONTAL_SCROLL_BAR = 1;

    /**
     * The Constant DRAGABLE_VERTICAL_SCROLL_BAR.
     */
    public static final int DRAGABLE_VERTICAL_SCROLL_BAR = 2;

    // defines the intensite of automatic scrolling.

    /**
     * The scrolling intensity.
     */
    private int scrollingIntensity = 10;

    // value used to descrease scrolling intensity during animation

    /**
     * The damping.
     */
    private double damping = 0.05;

    // indicates the number of milliseconds between animation updates.

    /**
     * The animation speed.
     */
    private int animationSpeed = 20;

    // Animation timer

    /**
     * The animation timer.
     */
    private javax.swing.Timer animationTimer = null;

    // the time of the last mouse drag event

    /**
     * The last drag time.
     */
    private long lastDragTime = 0;

    /**
     * The last drag point.
     */
    private Point lastDragPoint = null;

    // animation rates

    /**
     * The pixels per msx.
     */
    private double pixelsPerMSX;

    /**
     * The pixels per msy.
     */
    private double pixelsPerMSY;

    // flag which defines the draggable scroll directions

    /**
     * The scroll bar mask.
     */
    private int scrollBarMask = DRAGABLE_HORIZONTAL_SCROLL_BAR | DRAGABLE_VERTICAL_SCROLL_BAR;

    // the draggable component

    /**
     * The draggable component.
     */
    private final Component draggableComponent;

    // the JScrollPane containing the component - programmatically determined.

    /**
     * The scroller.
     */
    private JScrollPane scroller = null;

    // the default cursor

    /**
     * The default cursor.
     */
    private Cursor defaultCursor;

    // List of drag speeds used to calculate animation speed

    // Uses the Point2D class to represent speeds rather than locations

    /**
     * The drag speeds.
     */
    private java.util.List<Point2D.Double> dragSpeeds = new ArrayList<Point2D.Double>();

    /**
     * Instantiates a new drag scroll listener.
     *
     * @param c the c
     */
    public DragScrollListener(Component c) {

        draggableComponent = c;

        defaultCursor = draggableComponent.getCursor();

        draggableComponent.addPropertyChangeListener(new PropertyChangeListener() {

            @Override

            public void propertyChange(PropertyChangeEvent arg0) {

                setScroller();

                defaultCursor = draggableComponent.getCursor();

            }

        });

        setScroller();

    }

    /**
     * Sets the scroller.
     */
    private void setScroller() {

        Component c = getParentScroller(draggableComponent);

        if (c != null) {

            scroller = (JScrollPane) c;

        } else {

            scroller = null;

        }

    }

    /**
     * Sets the Draggable elements - the Horizontal or Vertical Direction. One
     * <p>
     * can use a bitmasked 'or' (HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR ).
     *
     * @param mask One of HORIZONTAL_SCROLL_BAR, VERTICAL_SCROLL_BAR, or
     *             HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR
     */

    public void setDraggableElements(int mask) {

        scrollBarMask = mask;

    }

    /**
     * Sets the scrolling intensity - the default value being 5. Note, that this
     * has an
     * <p>
     * inverse relationship to intensity (1 has the biggest difference, higher
     * numbers having
     * <p>
     * less impact).
     *
     * @param intensity The new intensity value (Note the inverse relationship).
     */

    public void setScrollingIntensity(int intensity) {

        scrollingIntensity = intensity;

    }

    /**
     * Sets how frequently the animation will occur in milliseconds. Default
     * <p>
     * value is 30 milliseconds. 60+ will get a bit flickery.
     *
     * @param timing The timing, in milliseconds.
     */

    public void setAnimationTiming(int timing) {

        animationSpeed = timing;

    }

    /**
     * Sets the animation damping.
     *
     * @param damping The new value
     */

    public void setDamping(double damping) {

        this.damping = damping;

    }

    /**
     * Empty implementation.
     *
     * @param e the e
     */

    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Empty implementation.
     *
     * @param e the e
     */

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Mouse pressed implementation.
     *
     * @param e the e
     */

    public void mousePressed(MouseEvent e) {

        if (animationTimer != null && animationTimer.isRunning()) {

            animationTimer.stop();

        }

        draggableComponent.setCursor(new Cursor(Cursor.MOVE_CURSOR));

        dragSpeeds.clear();

        lastDragPoint = e.getPoint();

    }

    /**
     * Mouse released implementation. This determines if further animation is
     * necessary and launches the appropriate times.
     *
     * @param e the e
     */

    public void mouseReleased(MouseEvent e) {

        draggableComponent.setCursor(defaultCursor);

        if (scroller == null) {

            return;

        }

        // make sure the mouse ended in a dragging event

        long durationSinceLastDrag = System.currentTimeMillis() - lastDragTime;

        if (durationSinceLastDrag > 20) {

            return;

        }

        // get average speed for last few drags

        pixelsPerMSX = 0;

        pixelsPerMSY = 0;

        int j = 0;

        for (int i = dragSpeeds.size() - 1; i >= 0 && i > dragSpeeds.size() - 6; i--, j++) {

            pixelsPerMSX += dragSpeeds.get(i).x;

            pixelsPerMSY += dragSpeeds.get(i).y;

        }

        pixelsPerMSX /= -(double) j;

        pixelsPerMSY /= -(double) j;

        // start the timer

        if (Math.abs(pixelsPerMSX) > 0 || Math.abs(pixelsPerMSY) > 0) {

            animationTimer = new javax.swing.Timer(animationSpeed, new ScrollAnimator());

            animationTimer.start();

        }

    }

    /**
     * Empty implementation.
     *
     * @param e the e
     */

    public void mouseClicked(MouseEvent e) {
    }

    /**
     * MouseDragged implementation. Sets up timing and frame animation.
     *
     * @param e the e
     */

    public void mouseDragged(MouseEvent e) {

        if (scroller == null) {

            return;

        }

        Point p = e.getPoint();

        int diffx = p.x - lastDragPoint.x;

        int diffy = p.y - lastDragPoint.y;

        lastDragPoint = e.getPoint();

        // scroll the x axis

        if ((scrollBarMask & DRAGABLE_HORIZONTAL_SCROLL_BAR) != 0) {

            getHorizontalScrollBar().setValue(getHorizontalScrollBar().getValue() - diffx);

        }

        // the Scrolling affects mouse locations - offset the last drag point to
        // compensate

        lastDragPoint.x = lastDragPoint.x - diffx;

        // scroll the y axis

        if ((scrollBarMask & DRAGABLE_VERTICAL_SCROLL_BAR) != 0) {

            getVerticalScrollBar().setValue(getVerticalScrollBar().getValue() - diffy);

        }

        // the Scrolling affects mouse locations - offset the last drag point to
        // compensate

        lastDragPoint.y = lastDragPoint.y - diffy;

        // add a drag speed

        dragSpeeds.add(new Point2D.Double(

                (e.getPoint().x - lastDragPoint.x),

                (e.getPoint().y - lastDragPoint.y)));

        lastDragTime = System.currentTimeMillis();

    }

    /**
     * Empty.
     *
     * @param e the e
     */

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Private inner class which accomplishes the animation.
     *
     * @author Greg Cope
     */

    private class ScrollAnimator implements ActionListener {

        /**
         * Performs the animation through the setting of the JScrollBar values.
         *
         * @param e the e
         */

        public void actionPerformed(ActionEvent e) {

            // damp the scrolling intensity

            pixelsPerMSX -= pixelsPerMSX * damping;

            pixelsPerMSY -= pixelsPerMSY * damping;

            // check to see if timer should stop.

            if (Math.abs(pixelsPerMSX) < 0.01 && Math.abs(pixelsPerMSY) < 0.01) {

                animationTimer.stop();

                return;

            }

            // calculate new X value

            int nValX = getHorizontalScrollBar().getValue() + (int) (pixelsPerMSX * scrollingIntensity);

            int nValY = getVerticalScrollBar().getValue() + (int) (pixelsPerMSY * scrollingIntensity);

            // Deal with out of scroll bounds

            if (nValX <= 0) {

                nValX = 0;

            } else if (nValX >= getHorizontalScrollBar().getMaximum()) {

                nValX = getHorizontalScrollBar().getMaximum();

            }

            if (nValY <= 0) {

                nValY = 0;

            } else if (nValY >= getVerticalScrollBar().getMaximum()) {

                nValY = getVerticalScrollBar().getMaximum();

            }

            // Check again to see if timer should stop

            if ((nValX == 0 || nValX == getHorizontalScrollBar().getMaximum()) && Math.abs(pixelsPerMSY) < 1) {

                animationTimer.stop();

                return;

            }

            if ((nValY == 0 || nValY == getVerticalScrollBar().getMaximum()) && Math.abs(pixelsPerMSX) < 1) {

                animationTimer.stop();

                return;

            }

            // Set new values

            if ((scrollBarMask & DRAGABLE_HORIZONTAL_SCROLL_BAR) != 0) {

                getHorizontalScrollBar().setValue(nValX);

            }

            if ((scrollBarMask & DRAGABLE_VERTICAL_SCROLL_BAR) != 0) {

                getVerticalScrollBar().setValue(nValY);

            }

        }

    }

    /**
     * Utility to retrieve the Horizontal Scroll Bar.
     *
     * @return the horizontal scroll bar
     */

    private JScrollBar getHorizontalScrollBar() {

        return scroller.getHorizontalScrollBar();

    }

    /**
     * Utility to retrieve the Vertical Scroll Bar.
     *
     * @return the vertical scroll bar
     */

    private JScrollBar getVerticalScrollBar() {

        return scroller.getVerticalScrollBar();

    }

    /**
     * Gets the parent scroller.
     *
     * @param c the c @return the parent scroller
     */

    private Component getParentScroller(Component c) {

        Container parent = c.getParent();

        if (parent != null && parent instanceof Component) {

            Component parentC = (Component) parent;

            if (parentC instanceof JScrollPane) {

                return parentC;

            } else {

                return getParentScroller(parentC);

            }

        }

        return null;

    }

    /**
     * Testing main method as an SSCCE.
     *
     * @param args the arguments
     */

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Drawer dr = new Drawer();

        JScrollPane pane = new JScrollPane(dr);

        DragScrollListener dl = new DragScrollListener(dr);

        dr.addMouseListener(dl);

        dr.addMouseMotionListener(dl);

        pane.setPreferredSize(new Dimension(300, 300));

        frame.getContentPane().add(pane);

        frame.pack();

        frame.setVisible(true);

        pane.getHorizontalScrollBar().setValue(10);

    }

    /**
     * Testing inner class that draws several squares of randomly selected
     * colors.
     *
     * @author Greg Cope
     */

    public static class Drawer extends JPanel {

        /**
         * The Constant serialVersionUID.
         */
        static final long serialVersionUID = 43214321L;

        /**
         * The width.
         */
        int width = 10000;

        /**
         * The height.
         */
        int height = 5000;

        /**
         * The colors.
         */
        Color[][] colors;

        /**
         * Constructs the JPanel and random colors.
         */

        public Drawer() {

            super();

            setPreferredSize(new Dimension(width, height));

            colors = new Color[width / 100][height / 100];

            for (int i = 0; i < colors.length; i++) {

                for (int j = 0; j < colors[i].length; j++) {

                    int r = (int) ((255) * Math.random());

                    int g = (int) ((255) * Math.random());

                    int b = (int) ((255) * Math.random());

                    colors[i][j] = new Color(r, g, b, 150);

                }

            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override

        public void paintComponent(Graphics g) {

            super.paintComponent(g);

            for (int i = 0; i < width; i += 100) {

                for (int j = 0; j < height; j += 100) {

                    g.setColor(colors[i / 100][j / 100]);

                    g.fillRect(i + 5, j + 5, 95, 95);

                }

            }

        }

    }

}
