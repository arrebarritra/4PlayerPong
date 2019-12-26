package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.enums.ID;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * Block object
 */
public class Block extends GameObject {

    public Block(int x, int y, ID id, Handler handler) {
        super(x, y, id, handler);

        this.height = 104;
        this.width = 104;
        this.color = Color.white;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(this.color);
        g.fillRect(x, y, width, height);
    }

}
