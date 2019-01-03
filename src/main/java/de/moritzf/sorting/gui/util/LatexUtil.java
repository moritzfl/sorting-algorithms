package de.moritzf.sorting.gui.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LatexUtil {

    public static String normalizeTexExpression(String expression) {

        return normalize(expression);
    }

    private static String normalize(String expression) {
        if (expression != null) {

            //insert linebreak between multiple math-environments
            expression = expression.replaceAll(Pattern.quote("$") + "\\s*" + Pattern.quote("$"),
                    Matcher.quoteReplacement("$\n\\\\\n$"));
        }

        return expression;
    }


}
