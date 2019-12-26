package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Button class which can be rendered with a graphics object
 */
public class Button extends Label {

    public Button(String text, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, Color backgroundColor, Color outlineColor, Color textColor) {
        super(text, xAlignment, yAlignment, fontSize, x, y, xGap, yGap, backgroundColor, outlineColor, textColor);
    }

    public Button(String text, String textWidth, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, Color backgroundColor, Color outlineColor, Color textColor) {
        super(text, textWidth, xAlignment, yAlignment, fontSize, x, y, xGap, yGap, backgroundColor, outlineColor, textColor);
    }

    /**
     * Returns true if mouse is over button
     *
     * @param m Location of the mouse pointer
     * @return True if mouse is over button
     */
    public boolean mouseOver(Point m) {
        return bounds.intersects(new Rectangle(m.x, m.y, 1, 1));
    }
}
