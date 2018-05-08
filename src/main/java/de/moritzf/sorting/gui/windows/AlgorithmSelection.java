package de.moritzf.sorting.gui.windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.moritzf.sorting.logic.sorting.*;
import org.jdesktop.swingx.JXTextField;

import de.moritzf.sorting.logic.util.InputGeneration;

/**
 * The Class AlgorithmSelection.
 *
 * @author Moritz Floeter
 * <p>
 * The Class AlgorithmSelection. This class is the one where it all
 * starts. It provides gui that the user sees right after the
 * software is launched. Here the user can select the algorithm which he
 * wants to use and input the array on which it should be used.
 * <p>
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class AlgorithmSelection extends JFrame implements ActionListener {

    /**
     * The startbtn.
     */
    JButton startbtn = new JButton("<html>Start</html>");

    /**
     * The Constant maxCount. Defines the maximum number of elements that are to be sorted.
     */
    protected static final int maxCount = 100;

    /**
     * The Constant maxValue. Defines the maximum value that one element that is to be sorted can have.
     */
    protected static final int maxValue = 2147483646;

    /**
     * The arrayInputField. Takes user input as comma separated value list of numbers.
     */
    JXTextField arrayInputField = new JXTextField();


    /**
     * The algorithmSelectionBox. Dropdown for user selection of the algorithm
     */
    JComboBox<SelectionItem> algorithmSelectionBox = new JComboBox<SelectionItem>(SelectionItem.values());

    /**
     * The randomArrayGenerationButton. Starts generation of a random array as input for the sorting algorithm.
     */
    JButton randomArrayGenerationButton = new JButton("<html>Random sequence</html>");

    /**
     * The aboutButton. Displays about dialog.
     */
    JButton aboutButton = new JButton("About");

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 4107930542363651070L;


    /**
     * Instantiates a new algorithm selection window.
     */
    public AlgorithmSelection() {
        super("Sorting Algorithms");

        Container mainpane = this.getContentPane();
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        JPanel mainelements = new JPanel();
        mainpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainelements.setBorder(BorderFactory.createEtchedBorder(1));
        mainpanel.add(mainelements, BorderLayout.CENTER);
        mainpane.add(mainpanel);
        Box bottom = new Box(1);
        bottom.add(aboutButton);
        JPanel bottomBorder = new JPanel();
        bottomBorder.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomBorder.setLayout(new BorderLayout());
        bottomBorder.add(bottom, BorderLayout.EAST);
        mainpanel.add(bottomBorder, BorderLayout.SOUTH);
        mainelements.setLayout(new BorderLayout());

        // Actionlisteners get added to buttons
        this.startbtn.addActionListener(this);
        this.randomArrayGenerationButton.addActionListener(this);
        this.aboutButton.addActionListener(this);

        this.algorithmSelectionBox.addActionListener(this);

        arrayInputField.addKeyListener(new InputListener());


        /*
         * create the panel where the algorithm selection and the startbutton
         * gets displayed
         */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(algorithmSelectionBox, BorderLayout.CENTER);
        buttonPanel.add(startbtn, BorderLayout.EAST);
        mainelements.add(buttonPanel, BorderLayout.SOUTH);

        /*
         * create instructions for the user input and the button for generating
         * a random input string
         */
        JPanel northpanel = new JPanel(new BorderLayout());
        northpanel.add(randomArrayGenerationButton, BorderLayout.EAST);
        northpanel.add(new JLabel("Enter the number sequence that you want to sort:"), BorderLayout.WEST);
        mainelements.add(northpanel, BorderLayout.NORTH);

        // create form for textinput
        JPanel txtpnl = new JPanel();
        txtpnl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        txtpnl.setLayout(new BorderLayout());
        txtpnl.add(arrayInputField, BorderLayout.CENTER);
        mainelements.add(txtpnl, BorderLayout.CENTER);
        arrayInputField.setPrompt("Please enter positive numbers separated by comma (z.B.: 1,2,5,4...)");

        // Setting a few window attributes
        this.pack();
        int originalHeight = (int) this.getSize().getHeight();
        this.setSize(new Dimension(600, originalHeight));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        GeneralGuiFunctions.centerWindow(this);

        this.setResizable(false);
        this.validate();
        this.repaint();
        this.setVisible(true);
    }

    /**
     * Sets the input.
     *
     * @param input the new input
     */
    public void setInput(String input) {
        arrayInputField.setText(input);
        this.validate();
        this.repaint();
    }

    /**
     * Removes invalid chars from the input.
     *
     * @param text the text
     * @return the string
     */
    public static String removeInvalidChars(String text) {
        String returnText = text;
        for (int i = 0; i < returnText.length(); i++) {
            char c = returnText.charAt(i);
            if (!((c >= '0') && (c <= '9')) && (c != ',')) {
                returnText = returnText.replace(Character.toString(returnText.charAt(i)), "");
                i--;
            }
        }
        return returnText;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(startbtn)) {
            handleStart();
        } else if (e.getSource().equals(randomArrayGenerationButton)) {
            // The calling instance gets passed to the new window so that the
            // arraygenerator can fill the according field with a random array
            new RandomArrayGeneratorWindow(this);
        } else if (e.getSource().equals(aboutButton)) {
            new AboutWindow(this);
        }

    }


    private void handleStart() {
        int[] input = null;

        if (!arrayInputField.getText().isEmpty()) {
            String inputString = AlgorithmSelection.removeInvalidChars(arrayInputField.getText());
            input = InputGeneration.arrayFromString(inputString);
        }

        if (input == null) {
            JOptionPane.showMessageDialog(this, "You should enter a few numbers first ...", "Layer-8-Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (input.length < 2) {
            JOptionPane.showMessageDialog(this,
                    "<html>Fun Fact: <br>" + "Arrays containing only one element are already sorted.</html>",
                    "Der Fehler sitzt vor dem Bildschirm", JOptionPane.INFORMATION_MESSAGE);
        } else if (input.length > AlgorithmSelection.maxCount) {
            JOptionPane.showMessageDialog(this,
                    "<html>The array used as input contains more than" + maxCount
                            + " elements. <br> <br> Because every step has to be displayed and the number of "
                            + " steps increases with the input, <br> sind "
                            + " only arrays with a length as high as 100 are allowed.<br> <br>"
                            + "But seriously: you did not really want to look at that protocol in detail and"
                            + "<br>just tried to cause a software crash ;)</html>",
                    "Error: Are you serious?", JOptionPane.INFORMATION_MESSAGE);
        } else {
            SelectionItem selection = (SelectionItem) this.algorithmSelectionBox.getSelectedItem();

            switch (selection) {
                case BUBBLE_SORT:
                    new LatexSortingWindow(this, new BubbleSort(input));
                    break;
                case HEAP_SORT_MAX:
                    new HeapSortSortingWindow(this, new HeapSort(input, true));
                    break;
                case HEAP_SORT_MIN:
                    new HeapSortSortingWindow(this, new HeapSort(input, false));
                    break;
                case QUICK_SORT:
                    new LatexSortingWindow(this, new QuickSort(input));
                    break;
                case RADIX_SORT:
                    new LatexSortingWindow(this, new RadixSort(input));
                    break;
                case SELECTION_SORT:
                    new LatexSortingWindow(this, new SelectionSort(input));
                    break;
                case INSERTION_SORT:
                    new LatexSortingWindow(this, new InsertionSort(input));
                    break;
                case SHELL_SORT_2N:
                    new LatexSortingWindow(this, new ShellSort(input));
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                            this.algorithmSelectionBox.getSelectedItem()
                                    + " should start now. This algorithm has however not been implemented yet.",
                            "Developer was lazy", JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        }
    }


    /**
     * The class for receiving input events in this kind of window
     * (AlgorithmSelection). When an input event occurs, the appropriate method
     * is invoked.
     */
    private class InputListener implements KeyListener {

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            // invalid input events get consumed
            if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_COMMA))) {
                getToolkit().beep();
                e.consume();
            }
            if (c == KeyEvent.VK_COMMA) {
                /*
                 * when the last input was a comma, we do not need another
                 * one... only one comma is used to seperate numbers after all.
                 */
                if (arrayInputField.getText().isEmpty() || arrayInputField.getText().endsWith(",")) {
                    e.consume();
                    getToolkit().beep();
                }
            }

        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(KeyEvent e) {
            // nothing to do here

        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        public void keyReleased(KeyEvent e) {
            // nothing to do here

        }

    }

    private enum SelectionItem {
        BUBBLE_SORT("Bubblesort"),
        SELECTION_SORT("Selectionsort"),
        RADIX_SORT("Radixsort"),
        QUICK_SORT("Quicksort"),
        HEAP_SORT_MIN("Heapsort (Minheap)"),
        HEAP_SORT_MAX("Heapsort (Maxheap)"),
        INSERTION_SORT("Insertionsort"),
        SHELL_SORT_2N("Shellsort ( steplengths=[... 8,4,2,1] )");

        private final String selectionValue;

        SelectionItem(String selectionValue) {
            this.selectionValue = selectionValue;
        }

        public String toString() {
            return this.selectionValue;
        }


    }


}
