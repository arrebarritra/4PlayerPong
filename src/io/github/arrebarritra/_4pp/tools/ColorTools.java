package io.github.arrebarritra._4pp.tools;

import java.awt.Color;

/**
 * Utility methods for colours
 */
public class ColorTools {

    /**
     * Finds average colour of colour array
     *
     * @param colorArray Array of colours
     * @return Average colour of colorArray
     */
    public static Color averageColor(Color[] colorArray) {
        int r = 0, g = 0, b = 0;

        for (Color color : colorArray) {
            r += Math.pow(color.getRed(), 2);
            g += Math.pow(color.getGreen(), 2);
            b += Math.pow(color.getBlue(), 2);
        }

        r = (int) Math.sqrt(r / colorArray.length);
        g = (int) Math.sqrt(g / colorArray.length);
        b = (int) Math.sqrt(b / colorArray.length);

        return new Color(r, g, b);
    }
}
