package de.moritzf.sorting.gui.components;

import java.awt.Font;
import javax.swing.JTextArea;

/**
 * {@link JTextArea} that displays monospaced text.
 *
 * @author Moritz Floeter
 */
public class MonoTextArea extends JTextArea {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new mono text area.
     *
     * @param text the text
     */
    public MonoTextArea(String text) {
        super(text);
        Font courier = new Font("Courier", 0, 12);
        this.setFont(courier);
        this.setEditable(false);
        this.setLineWrap(true);
    }
}
