/*
 *
 *     Copyright (C) 2016  Moritz Flöter
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.moritzf.sorting.gui.components;

import de.moritzf.sorting.gui.util.LatexUtil;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The Class LatexPanel. A JPanel that displays LaTeX expressions.
 *
 * @author Moritz Floeter.
 */
public class LatexPanel extends JPanel implements ResizableComponent {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -7654861252773688599L;

  /** The Constant default font size. */
  private static final float DEFAULT_FONT_SIZE = 21;

  /** The textsize for the latex-rendering. */
  private float fontSizeTex = DEFAULT_FONT_SIZE;

  /** The expression. */
  private String expression;

  /**
   * Instantiates a new LaTeX panel.
   *
   * @param expression the expression @param tooltip the tooltip
   */
  public LatexPanel(String expression) {
    super();
    this.expression = LatexUtil.normalizeTexExpression(expression);
    this.setBackground(Color.white);
    this.render();
  }

  /** Instantiates a new Latex panel. */
  public LatexPanel() {
    this(null);
  }

  /**
   * Sets the latex expression and renders it.
   *
   * @param expression the expression
   */
  public void setExpression(String expression) {
    this.expression = LatexUtil.normalizeTexExpression(expression);
    this.render();
  }

  /**
   * Gets the latex expression that is currently displayed.
   *
   * @return the expression
   */
  public String getExpression() {
    return this.expression;
  }


  /** Reset font size. */
  public void resetFontSize() {

    this.fontSizeTex = DEFAULT_FONT_SIZE;

    refresh();
  }

  /** Refresh. */
  public void refresh() {
    this.setExpression(this.expression);
  }

  /** Renders the formula. */
  private void render() {
    String expressionUsed = "null";
    if (this.expression != null) {
      expressionUsed = this.expression;
    }

    try {
      // create a formula
      TeXFormula formula = new TeXFormula(expressionUsed);

      TeXIcon texIcon =
          formula.createTeXIcon(
              TeXConstants.STYLE_DISPLAY,
              fontSizeTex,
              TeXConstants.UNIT_PIXEL,
              80,
              TeXConstants.ALIGN_LEFT);
      this.removeAll();
      this.add(new JLabel(texIcon));

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.validate();
    this.repaint();
  }

  @Override
  public void resetScale() {
    this.fontSizeTex = DEFAULT_FONT_SIZE;
    this.refresh();
  }

    @Override
    public void increaseScale() {
        if (fontSizeTex < 70) {
            this.fontSizeTex = this.fontSizeTex * 1.2f;
        }
        refresh();
    }

    @Override
    public void decreaseScale() {
        if (fontSizeTex > 15) {
            this.fontSizeTex = this.fontSizeTex * 0.8f;
        }
        refresh();
    }
}
