package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.enums.ID;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/** 
 * Superclass for all game objects
 */
public abstract class GameObject {

    protected ID id;
    protected int x, y;
    protected int width, height;
    protected int prevX, prevY;
    protected int velX, velY;
    protected Color color;
    Handler handler;

    public GameObject(int x, int y, ID id, Handler handler) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.handler = handler;

        handler.addObject(this);
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
    
    public Point getLocation(){
        return new Point(x, y);
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.prevX = this.x;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.prevY = this.y;
        this.y = y;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
