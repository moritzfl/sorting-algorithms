package de.moritzf.sorting.gui.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.moritzf.sorting.gui.components.IntegerField;
import de.moritzf.sorting.logic.util.InputGeneration;


/**
 * @author Moritz Floeter
 *         <p>
 *         The Class RandomArrayGenerator.
 *         <p>
 *         This class provides the gui for creating a randomized array.
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
public class RandomArrayGeneratorWindow extends JFrame implements ActionListener {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -2004873389331204943L;

    /**
     * The number count.
     */
    private IntegerField numberCount = new IntegerField(2, AlgorithmSelection.maxCount);

    /**
     * The max number value.
     */
    private IntegerField maxNumberValue = new IntegerField(0, AlgorithmSelection.maxValue);

    /**
     * The min number value.
     */
    private IntegerField minNumberValue = new IntegerField(0, AlgorithmSelection.maxValue);

    /**
     * The generate btn.
     */
    private JButton generateBtn = new JButton("<html>&nbsp; <br>Generate<br> &nbsp;</html>");

    /**
     * The cancel btn.
     */
    private JButton cancelBtn = new JButton("<html> &nbsp; <br>Cancel<br> &nbsp;</html>");

    /**
     * The selection.
     */
    private AlgorithmSelection selection;

    /**
     * Instantiates a new random array generator.
     *
     * @param selection the selection
     */
    public RandomArrayGeneratorWindow(AlgorithmSelection selection) {
        super("Generate numerical sequence");

        this.selection = selection;

        JPanel mainpanel = new JPanel(new BorderLayout());
        JPanel parameters = new JPanel();
        JPanel buttons = new JPanel();

        parameters.setLayout(new GridLayout(0, 1));
        mainpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        buttons.setLayout(new FlowLayout());
        buttons.add(cancelBtn);
        buttons.add(generateBtn);
        mainpanel.add(buttons, BorderLayout.SOUTH);

        generateBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
        this.getContentPane().add(mainpanel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // default values
        minNumberValue.setText("0");
        maxNumberValue.setText("99");
        numberCount.setText("10");

        parameters.setBorder(BorderFactory.createTitledBorder("Selection"));
        parameters.add(new JLabel("Note: only numbers ≥ 0 are allowed"));
        parameters.add(
                new JLabel("<html>Number of elements<br>" + "<i> must be below " + AlgorithmSelection.maxCount + "</i></html>"));
        parameters.add(numberCount);
        parameters.add(new JLabel("Min. value"));
        parameters.add(minNumberValue);
        parameters
                .add(new JLabel("<html>Max. value<br>" + "<i> must be below " + AlgorithmSelection.maxValue + "<i></html>"));
        parameters.add(maxNumberValue);
        mainpanel.add(parameters, BorderLayout.CENTER);

        this.pack();
        this.setResizable(false);

        /*
         * Sets location to the place where the calling selection window is and
         * disables + hides the selection window
         */
        this.setLocationRelativeTo(selection);
        selection.setVisible(false);
        selection.setEnabled(false);
        this.setVisible(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        boolean close = true;

        if (e.getSource().equals(generateBtn)) {
            numberCount.validateValue();
            minNumberValue.validateValue();
            maxNumberValue.validateValue();
            int count = numberCount.getInt();
            int min = minNumberValue.getInt();
            int max = maxNumberValue.getInt();

            /*
             * If the input is valid, the String gets generated and set in the
             * selection window from which this instance of the
             * RandomArrayGenerator was called. Otherwise - if the input is not
             * valid, an according message gets displayed and the
             * RandomArrayGenerator does not close.
             */

            if (!(max < min)) {
                selection.setInput(InputGeneration.generate(max, min, count));
            } else {
                JOptionPane.showMessageDialog(null,
                        "<html>Min. value must be below max. value.</html>");
                close = false;
            }
        }
        /*
         * when the RandomArrayGenerator closes, the selection-window from where
         * it was called gets reactivated
         */
        if (close) {
            selection.setEnabled(true);
            selection.setVisible(true);
            this.dispose();
        }

    }

}
