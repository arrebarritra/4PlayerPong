package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.Game;
import io.github.arrebarritra._4pp.GameTracker;
import io.github.arrebarritra._4pp.Score;
import io.github.arrebarritra._4pp.tools.Settings;
import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.Team;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 
 * Heads up display. Displays information about the game.
 */
public class HUD {

    private final Game game;
    private final GameTracker tracker;
    private final Score score;
    Color team1AverageColor, team2AverageColor;
    Label messageLabel, serveLabel, team1ScoreLabel, team2ScoreLabel, dashLabel;
    Label fpsLabel = new Label("", HorzAlignment.LEFT, VertAlignment.TOP, 20, 0, 0, 0, 0, null, null, Color.green);

    public HUD(Game game, GameTracker tracker, Score score) {
        this.game = game;
        this.tracker = tracker;
        this.score = score;
    }

    /**
     * Called when initialised. Gets all text that is going to be displayed.
     */
    public void getStats() {
        messageLabel = new Label(tracker.getScoreMessage(), HorzAlignment.MIDDLE, VertAlignment.BOTTOM, 20, Game.WIDTH / 2, Game.HEIGHT / 2 - 40, 0, 0, null, null, tracker.getMessageColor());
        team1ScoreLabel = new Label(score.getTeam1Score() + " ", HorzAlignment.RIGHT, VertAlignment.MIDDLE, 30, Game.WIDTH / 2, Game.HEIGHT / 2, 0, 0, null, null, Team.TEAM1.getColor());
        dashLabel = new Label(":", HorzAlignment.MIDDLE, VertAlignment.MIDDLE, 30, Game.WIDTH / 2, Game.HEIGHT / 2, 0, 0, null, null, Color.WHITE);
        team2ScoreLabel = new Label(" " + score.getTeam2Score(), HorzAlignment.LEFT, VertAlignment.MIDDLE, 30, Game.WIDTH / 2, Game.HEIGHT / 2, 0, 0, null, null, Team.TEAM2.getColor());
    }

    public void tick() {
    }

    public void render(Graphics2D g) {
        if (game.isServing()) {
            messageLabel.render(g);
            g.setColor(tracker.getNextServer().getColor());
            int timerWidth = (int) (128 * (game.checkServeTimer() / 2000.0));
            g.setStroke(new BasicStroke(3f));
            g.drawLine(Game.WIDTH / 2 - timerWidth / 2, Game.HEIGHT / 2 + 32, Game.WIDTH / 2 + timerWidth / 2, Game.HEIGHT / 2 + 32);
        }
        
        if (Boolean.parseBoolean(Settings.getProp("frameCounterOn"))) {
            fpsLabel.setText(Integer.toString(game.getFps()));
            fpsLabel.render(g);
        }

        team1ScoreLabel.render(g);
        dashLabel.render(g);
        team2ScoreLabel.render(g);
    }
}
