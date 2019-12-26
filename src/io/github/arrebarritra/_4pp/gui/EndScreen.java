package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.Game;
import io.github.arrebarritra._4pp.GameObject;
import io.github.arrebarritra._4pp.Handler;
import io.github.arrebarritra._4pp.Player;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.Score;
import io.github.arrebarritra._4pp.tools.Settings;
import io.github.arrebarritra._4pp.enums.GameState;
import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.ID;
import io.github.arrebarritra._4pp.enums.MenuState;
import io.github.arrebarritra._4pp.enums.PlayState;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import io.github.arrebarritra._4pp.tools.ColorTools;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import io.github.arrebarritra._4pp.tools.DynamicArray;

/**
 * Screen showing stats after game is finished
 */
public class EndScreen implements MouseListener {

    private Game game;
    private Handler handler;
    private Score score;
    private Menu menu;
    private PlayerNumber mostGoals, mostTouches, mostMissedPasses, mostMissedServes;
    private int mostGoalsAmount = 0, mostTouchesAmount = 0, mostMissedPassesAmount = 0, mostMissedServesAmount = 0;
    private DynamicArray<String> statTextArray = new DynamicArray<>();
    private DynamicArray<Color> statColorArray = new DynamicArray<>();
    private Label winnerLabel;
    private LabelRow statsLabels;
    private Button back;
    private String winMessage = "";
    private Color winColor;
    private Settings settings;

    public EndScreen(Game game, Handler handler, Score score, Menu menu, Settings settings) {
        this.game = game;
        this.handler = handler;
        this.score = score;
        this.menu = menu;
        this.settings = settings;

        back = new Button("Back", HorzAlignment.MIDDLE, VertAlignment.BOTTOM, 40, Game.WIDTH / 2, Game.HEIGHT - 40, 20, 20, Color.WHITE, null, Color.BLACK);
    }

    public void reset() {
        mostGoals = null;
        mostTouches = null;
        mostMissedPasses = null;
        mostMissedServes = null;
        mostGoalsAmount = 0;
        mostTouchesAmount = 0;
        mostMissedPassesAmount = 0;
        mostMissedServesAmount = 0;
    }

    /**
     * Called when initialised. Gets all stats that are going to be displayed.
     */
    public void getStats() {
        if (score.getTeam1Score() == 10) {
            this.winMessage = "Team 1 Won";
            this.winColor = ColorTools.averageColor(new Color[]{PlayerNumber.Player1.getColor(), PlayerNumber.Player2.getColor()});
        } else if (score.getTeam2Score() == 10) {
            this.winMessage = "Team 2 Won";
            this.winColor = ColorTools.averageColor(new Color[]{PlayerNumber.Player3.getColor(), PlayerNumber.Player4.getColor()});
        }
        winnerLabel = new Label(winMessage, HorzAlignment.MIDDLE, VertAlignment.BOTTOM, 30, Game.WIDTH / 2, Game.HEIGHT / 2 - 10, 0, 0, null, null, winColor);

        for (int index = 0; index < handler.object.size(); index++) {
            GameObject tempObject = handler.object.get(index);
            if (tempObject.getId() == ID.Player) {
                Player tempPlayer = (Player) tempObject;
                if (tempPlayer.getPlayerTracker().getGoals() > mostGoalsAmount) {
                    mostGoals = tempPlayer.playerNumber;
                    mostGoalsAmount = tempPlayer.getPlayerTracker().getGoals();
                }
                if (tempPlayer.getPlayerTracker().getTouches() > mostTouchesAmount) {
                    mostTouches = tempPlayer.playerNumber;
                    mostTouchesAmount = tempPlayer.getPlayerTracker().getTouches();
                }
                if (tempPlayer.getPlayerTracker().getMissedPasses() > mostMissedPassesAmount) {
                    mostMissedPasses = tempPlayer.playerNumber;
                    mostMissedPassesAmount = tempPlayer.getPlayerTracker().getMissedPasses();
                }
                if (tempPlayer.getPlayerTracker().getMissedServes() > mostMissedServesAmount) {
                    mostMissedServes = tempPlayer.playerNumber;
                    mostMissedServesAmount = tempPlayer.getPlayerTracker().getMissedServes();
                }
            }
        }

        if (mostGoals != null) {
            String name = mostGoals.getName();
            statTextArray.add("Most goals: " + name + " - " + mostGoalsAmount);
            statColorArray.add(mostGoals.getColor());
        }
        if (mostTouches != null) {
            String name = mostTouches.getName();
            statTextArray.add("Most touches: " + name + " - " + mostTouchesAmount);
            statColorArray.add(mostTouches.getColor());
        }
        if (mostMissedPasses != null) {
            String name = mostMissedPasses.getName();
            statTextArray.add("Most missed passes: " + name + " - " + mostMissedPassesAmount);
            statColorArray.add(mostMissedPasses.getColor());
        }
        if (mostMissedServes != null) {
            String name = mostMissedServes.getName();
            statTextArray.add("Most missed serves: " + name + " - " + mostMissedServesAmount);
            statColorArray.add(mostMissedServes.getColor());
        }
        String[] statTextStaticArray = new String[statTextArray.size()];
        Color[] statColorStaticArray = new Color[statColorArray.size()];

        for (int i = 0; i < statTextArray.size(); i++) {
            statTextStaticArray[i] = statTextArray.get(i);
            statColorStaticArray[i] = statColorArray.get(i);
        }
        statsLabels = new LabelRow(statTextStaticArray, statColorStaticArray, VertAlignment.TOP, HorzAlignment.MIDDLE, VertAlignment.TOP, 20, Game.WIDTH / 2, Game.HEIGHT / 2, 0, 0, 10, null, null);
    }

    public void tick() {
    }

    public void render(Graphics2D g) {
        winnerLabel.render(g);
        statsLabels.render(g);
        back.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (game.getGameState() == GameState.GAME && game.getPlayState() == PlayState.END) {
            if (back.mouseOver(me.getPoint())) {
                game.resetGame();
                game.setGameState(GameState.MENU);
                menu.setMenuState(MenuState.MAIN);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
