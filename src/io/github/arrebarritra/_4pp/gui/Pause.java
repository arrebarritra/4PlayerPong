package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.Game;
import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * Pause menu which is rendered on top of game when paused
 */
public class Pause {

    Label paused = new Label("Paused", HorzAlignment.MIDDLE, VertAlignment.MIDDLE, 40, Game.WIDTH / 2, Game.HEIGHT / 2, 0, 0, null, null, Color.WHITE);
    Label resume = new Label("Press ESC to resume", HorzAlignment.MIDDLE, VertAlignment.MIDDLE, 20, Game.WIDTH / 2, paused.getBounds().y + 2 * paused.getBounds().height, 0, 0, null, null, Color.WHITE);
    Label leave = new Label("Press SHIFT to leave", HorzAlignment.MIDDLE, VertAlignment.MIDDLE, 20, Game.WIDTH / 2, resume.getBounds().y + 2 * resume.getBounds().height, 0, 0, null, null, Color.WHITE);

    public void render(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        paused.render(g);
        resume.render(g);
        leave.render(g);
    }
}
