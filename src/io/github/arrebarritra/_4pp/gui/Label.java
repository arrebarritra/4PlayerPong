package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

/**
 * Label class which can be rendered with a graphics object
 */
public class Label {

    protected String text, textWidth;
    protected HorzAlignment xAlignment;
    protected VertAlignment yAlignment;
    protected int fontSize;
    protected int x, y, xGap, yGap, initX, initY;
    protected Color backgroundColor, outlineColor, textColor;
    protected Font RobotoRegular;
    protected FontMetrics fontMetrics;
    protected int fontHeight;
    protected Rectangle bounds;
    protected boolean displayed, customWidth;

    public Label(String text, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, Color backgroundColor, Color outlineColor, Color textColor) {
        this.text = text;
        if (text == null) {
            this.textWidth = "";
        } else {
            this.textWidth = text;
        }
        this.xAlignment = xAlignment;
        this.yAlignment = yAlignment;
        this.fontSize = fontSize;
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.xGap = xGap;
        this.yGap = yGap;
        this.backgroundColor = backgroundColor;
        this.outlineColor = outlineColor;
        this.textColor = textColor;
        this.customWidth = false;

        try {
            this.RobotoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("res" + File.separator + "fonts" + File.separator + "Roboto-Regular.ttf")).deriveFont((float) fontSize);
        } catch (FontFormatException | IOException ex) {
        }

        Canvas c = new Canvas();
        fontMetrics = c.getFontMetrics(RobotoRegular);
        fontHeight = fontMetrics.getAscent() - fontMetrics.getDescent();

        this.reAlign();
        this.setBounds(new Rectangle(this.x, this.y, fontMetrics.stringWidth(textWidth) + (xGap * 2), fontHeight + (yGap * 2)));
    }

    public Label(String text, String textWidth, HorzAlignment xAlignment, VertAlignment yAlignment, int fontSize, int x, int y, int xGap, int yGap, Color backgroundColor, Color outlineColor, Color textColor) {
        this.text = text;
        this.textWidth = textWidth;
        this.xAlignment = xAlignment;
        this.yAlignment = yAlignment;
        this.fontSize = fontSize;
        this.x = x;
        this.y = y;
        this.initX = x;
        this.initY = y;
        this.xGap = xGap;
        this.yGap = yGap;
        this.backgroundColor = backgroundColor;
        this.outlineColor = outlineColor;
        this.textColor = textColor;
        this.customWidth = true;

        try {
            this.RobotoRegular = Font.createFont(Font.TRUETYPE_FONT, new File("res" + File.separator + "fonts" + File.separator + "Roboto-Regular.ttf")).deriveFont((float) fontSize);
        } catch (FontFormatException | IOException ex) {
        }

        Canvas c = new Canvas();
        fontMetrics = c.getFontMetrics(RobotoRegular);
        fontHeight = fontMetrics.getAscent() - fontMetrics.getDescent();

        this.reAlign();
        this.setBounds(new Rectangle(this.x, this.y, fontMetrics.stringWidth(textWidth) + (xGap * 2), fontHeight + (yGap * 2)));
    }

    public void tick() {
    }

    public void render(Graphics2D g) {
        this.setBounds(new Rectangle(x, y, fontMetrics.stringWidth(textWidth) + (xGap * 2), fontHeight + (yGap * 2)));

        if (backgroundColor != null) {
            g.setColor(backgroundColor);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
        if (outlineColor != null) {
            g.setColor(outlineColor);
            g.setStroke(new BasicStroke(2));
            g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
        if (textColor != null) {
            g.setFont(RobotoRegular);
            g.setColor(textColor);
            g.drawString(text, x + (fontMetrics.stringWidth(textWidth) / 2) + xGap - (fontMetrics.stringWidth(text) / 2), y + fontHeight + yGap);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setLocation(Point point) {
        reAlign(point);
        this.setBounds(new Rectangle(x, y, bounds.x, bounds.y));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        if (!customWidth) {
            this.textWidth = text;
        }
        this.reAlign();
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public void reAlign() {
        switch (xAlignment) {
            case LEFT:
                this.x = initX;
                break;
            case RIGHT:
                this.x = initX - fontMetrics.stringWidth(textWidth) - (xGap * 2);
                break;
            case MIDDLE:
                this.x = initX - (fontMetrics.stringWidth(textWidth) / 2) - xGap;
                break;
            default:
                break;
        }
        switch (yAlignment) {
            case TOP:
                this.y = initY;
                break;
            case BOTTOM:
                this.y = initY - fontHeight - (yGap * 2);
                break;
            case MIDDLE:
                this.y = initY - (fontHeight / 2) - yGap;
                break;
            default:
                break;
        }
    }

    /**
     * Moves the label to point taking alignments into consideration.
     *
     * @param point Desired location of label.
     */
    public void reAlign(Point point) {
        switch (xAlignment) {
            case LEFT:
                this.x = point.x;
                break;
            case RIGHT:
                this.x = point.x - fontMetrics.stringWidth(textWidth) - (xGap * 2);
                break;
            case MIDDLE:
                this.x = point.x - (fontMetrics.stringWidth(textWidth) / 2) - xGap;
                break;
            default:
                break;
        }
        switch (yAlignment) {
            case TOP:
                this.y = point.y;
                break;
            case BOTTOM:
                this.y = point.y - fontHeight - (yGap * 2);
                break;
            case MIDDLE:
                this.y = point.y - (fontHeight / 2) - yGap;
                break;
            default:
                break;
        }
    }
}
