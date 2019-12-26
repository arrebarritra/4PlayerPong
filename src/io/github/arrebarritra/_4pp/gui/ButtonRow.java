package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

/**
 * Row of several button objects
 */
public class ButtonRow {

    protected Button[] buttons;
    Color backgroundColor, outlineColor, textColor;

    public ButtonRow(String[] text, VertAlignment rowAlignment, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, int componentYGap, Color backgroundColor, Color outlineColor, Color textColor) {
        buttons = new Button[text.length];
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.outlineColor = outlineColor;

        Font RobotoRegular = null;
        try {
            RobotoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("res" + File.separator + "fonts" + File.separator + "Roboto-Regular.ttf")).deriveFont((float) fontSize);
        } catch (FontFormatException | IOException ex) {
        }
        Canvas c = new Canvas();
        FontMetrics fontMetrics = c.getFontMetrics(RobotoRegular);
        int fontHeight = fontMetrics.getAscent() - fontMetrics.getDescent();

        int height = fontHeight + (yGap * 2);

        String stringWidth = null;
        int stringWidthAmount = 0;
        for (int i = 0; i < buttons.length; i++) {
            if (fontMetrics.stringWidth(text[i]) > stringWidthAmount) {
                stringWidth = text[i];
                stringWidthAmount = fontMetrics.stringWidth(text[i]);
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            if (rowAlignment == VertAlignment.TOP) {
                buttons[i] = new Button(text[i], stringWidth, xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i), xGap, yGap, backgroundColor, outlineColor, textColor);
            } else if (rowAlignment == VertAlignment.BOTTOM) {
                buttons[i] = new Button(text[i], stringWidth, xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i) - (((height + componentYGap) * buttons.length) - componentYGap), xGap, yGap, backgroundColor, outlineColor, textColor);
            } else if (rowAlignment == VertAlignment.MIDDLE) {
                buttons[i] = new Button(text[i], stringWidth, xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i) - ((((height + componentYGap) * buttons.length) - componentYGap) / 2), xGap, yGap, backgroundColor, outlineColor, textColor);
            }
        }
    }

    public void tick() {
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].render(g);
        }
    }
}
