package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Row of label objects
 */
public class LabelRow {

    protected Label[] labels;

    public LabelRow(String[] text, VertAlignment rowAlignment, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, int componentYGap, Color backgroundColor, Color outlineColor, Color textColor) {
        labels = new Label[text.length];

        labels[0] = new Label(text[0], xAlignment, yAlignment, fontSize, x, y, xGap, yGap, backgroundColor, outlineColor, textColor);
        int height = labels[0].getBounds().height;

        for (int i = 0; i < labels.length; i++) {
            if (rowAlignment == VertAlignment.TOP) {
                labels[i] = new Button(text[i], xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i), xGap, yGap, backgroundColor, outlineColor, textColor);
            } else if (rowAlignment == VertAlignment.BOTTOM) {
                labels[i] = new Button(text[i], xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i) - (((height + componentYGap) * labels.length) - componentYGap), xGap, yGap, backgroundColor, outlineColor, textColor);
            } else if (rowAlignment == VertAlignment.MIDDLE) {
                labels[i] = new Button(text[i], xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i) - ((((height + componentYGap) * labels.length) - componentYGap) / 2), xGap, yGap, backgroundColor, outlineColor, textColor);
            }
        }
    }
    
    public LabelRow(String[] text, Color[] textColor, VertAlignment rowAlignment, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, int componentYGap, Color backgroundColor, Color outlineColor) {
        labels = new Label[text.length];

        labels[0] = new Label(text[0], xAlignment, yAlignment, fontSize, x, y, xGap, yGap, backgroundColor, outlineColor, textColor[0]);
        int height = labels[0].getBounds().height;

        for (int i = 0; i < labels.length; i++) {
            if (rowAlignment == VertAlignment.TOP) {
                labels[i] = new Button(text[i], xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i), xGap, yGap, backgroundColor, outlineColor, textColor[i]);
            } else if (rowAlignment == VertAlignment.BOTTOM) {
                labels[i] = new Button(text[i], xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i) - (((height + componentYGap) * labels.length) - componentYGap), xGap, yGap, backgroundColor, outlineColor, textColor[i]);
            } else if (rowAlignment == VertAlignment.MIDDLE) {
                labels[i] = new Button(text[i], xAlignment, yAlignment, fontSize, x, y + ((height + componentYGap) * i) - ((((height + componentYGap) * labels.length) - componentYGap) / 2), xGap, yGap, backgroundColor, outlineColor, textColor[i]);
            }
        }
    }
    
    public void tick() {
    }

    public void render(Graphics2D g) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].render(g);
        }
    }
}
