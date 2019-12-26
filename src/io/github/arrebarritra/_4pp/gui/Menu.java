package io.github.arrebarritra._4pp.gui;

import io.github.arrebarritra._4pp.Game;
import io.github.arrebarritra._4pp.Handler;
import io.github.arrebarritra._4pp.tools.Settings;
import io.github.arrebarritra._4pp.enums.GameState;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.enums.HorzAlignment;
import io.github.arrebarritra._4pp.enums.MenuState;
import io.github.arrebarritra._4pp.enums.VertAlignment;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 
 * Main menu of the game
 */
public class Menu implements MouseListener {

    private Game game;
    private Handler handler;
    private MenuState menuState = MenuState.MAIN;
    private PlayerNumber playerSelected = PlayerNumber.Player1;
    private boolean[] controlSelected = {false, false};
    private boolean nameEditable, controlEditable;
    private ButtonRow row;
    private Button start, save, back, playerName, scrollLeft, scrollRight, c1, c2, aiToggle, frameShowToggle, frameShowText, aaToggle, aaText, soundToggle, soundText;
    private Settings settings;
    private long now, lastTime;

    public Menu(Game game, Handler handler, Settings settings) {
        this.game = game;
        this.handler = handler;
        this.settings = settings;

        //Main menu
        row = new ButtonRow(new String[]{"Play", "Options", "Quit"}, VertAlignment.MIDDLE, HorzAlignment.MIDDLE, VertAlignment.TOP, 40, Game.WIDTH / 2, Game.HEIGHT / 2, 10, 10, 30, Color.WHITE, Color.WHITE, Color.BLACK);

        //Setup
        playerName = new Button(settings.getProp(playerSelected.toString() + "name") + " ", "                                   ", HorzAlignment.MIDDLE, VertAlignment.MIDDLE, 40, Game.WIDTH / 2, Game.HEIGHT / 2, 20, 10, null, Color.WHITE, playerSelected.getColor());
        scrollLeft = new Button("<", HorzAlignment.RIGHT, VertAlignment.MIDDLE, 40, playerName.getBounds().x, Game.HEIGHT / 2, 20, 10, null, Color.WHITE, playerSelected.getColor());
        scrollRight = new Button(">", HorzAlignment.LEFT, VertAlignment.MIDDLE, 40, playerName.getBounds().x + playerName.getBounds().width, Game.HEIGHT / 2, 20, 10, null, Color.WHITE, playerSelected.getColor());
        c1 = new Button(KeyEvent.getKeyText(playerSelected.getControls()[0]), HorzAlignment.RIGHT, VertAlignment.TOP, 30, Game.WIDTH / 2, playerName.getBounds().y + playerName.getBounds().height, 20, 20, null, null, playerSelected.getColor());
        c2 = new Button(KeyEvent.getKeyText(playerSelected.getControls()[1]), HorzAlignment.LEFT, VertAlignment.TOP, 30, Game.WIDTH / 2, playerName.getBounds().y + playerName.getBounds().height, 20, 20, null, null, playerSelected.getColor());
        aiToggle = new Button("AI", HorzAlignment.MIDDLE, VertAlignment.BOTTOM, 30, Game.WIDTH / 2, playerName.getBounds().y - playerName.getBounds().height, 20, 20, null, Color.WHITE, playerSelected.getColor());
        start = new Button("Start", HorzAlignment.LEFT, VertAlignment.MIDDLE, 30, Game.WIDTH / 2 + 10, c1.getBounds().y + c1.getBounds().height + 2 * playerName.getBounds().height, 20, 20, Color.WHITE, null, Color.BLACK);
        back = new Button("Back", HorzAlignment.RIGHT, VertAlignment.MIDDLE, 30, Game.WIDTH / 2 - 10, c1.getBounds().y + c1.getBounds().height + 2 * playerName.getBounds().height, 20, 20, Color.WHITE, null, Color.BLACK);

        now = System.currentTimeMillis();
        lastTime = System.currentTimeMillis();

        //Options
        save = new Button("Save", HorzAlignment.LEFT, VertAlignment.MIDDLE, 30, Game.WIDTH / 2 + 10, c1.getBounds().y + c1.getBounds().height + 2 * playerName.getBounds().height, 20, 20, Color.WHITE, null, Color.BLACK);

        frameShowToggle = new Button("", HorzAlignment.LEFT, VertAlignment.BOTTOM, 30, back.x, Game.HEIGHT / 2 - 40, 20, 10, null, Color.WHITE, Color.WHITE);
        aaToggle = new Button("", HorzAlignment.LEFT, VertAlignment.MIDDLE, 30, back.x, Game.HEIGHT / 2, 20, 10, null, Color.WHITE, Color.WHITE);
        soundToggle = new Button("", HorzAlignment.LEFT, VertAlignment.TOP, 30, back.x, Game.HEIGHT / 2 + 40, 20, 10, null, Color.WHITE, Color.WHITE);
        frameShowText = new Button("Show frame counter", HorzAlignment.LEFT, VertAlignment.BOTTOM, 30, frameShowToggle.x + frameShowToggle.getBounds().width + 10, Game.HEIGHT / 2 - 40, 20, 10, null, null, Color.WHITE);
        aaText = new Button("Anti-Aliasing", HorzAlignment.LEFT, VertAlignment.MIDDLE, 30, aaToggle.x + aaToggle.getBounds().width + 10, Game.HEIGHT / 2, 20, 10, null, null, Color.WHITE);
        soundText = new Button("Sound", HorzAlignment.LEFT, VertAlignment.TOP, 30, soundToggle.x + soundToggle.getBounds().width + 10, Game.HEIGHT / 2 + 40, 20, 10, null, null, Color.WHITE);
    }

    public void tick() {
    }

    public void render(Graphics2D g) {
        now = System.currentTimeMillis();
        if (menuState == MenuState.MAIN) {
            row.render(g);
        }
        if (menuState == MenuState.SETUP) {
            if (playerSelected.isAi()) {
                aiToggle.setBackgroundColor(Color.WHITE);
            } else {
                aiToggle.setBackgroundColor(null);
            }
            aiToggle.setTextColor(playerSelected.getColor());
            aiToggle.render(g);

            scrollLeft.setTextColor(playerSelected.getColor());
            scrollLeft.render(g);

            scrollRight.setTextColor(playerSelected.getColor());
            scrollRight.render(g);

            if ((now - lastTime) / 500 < 1 || !nameEditable) {
                playerName.setText(playerSelected.getName() + " ");
            } else if ((now - lastTime) / 500 < 2) {
                playerName.setText(playerSelected.getName() + "|");
            } else {
                lastTime = now;
            }

            playerName.setTextColor(playerSelected.getColor());
            playerName.render(g);

            if (!playerSelected.isAi()) {
                if (playerSelected.getControls()[0] != 0) {
                    c1.setText(KeyEvent.getKeyText(playerSelected.getControls()[0]));
                } else {
                    c1.setText("Set");
                }
                if (controlSelected[0]) {
                    c1.setTextColor(playerSelected.getColor());
                } else {
                    c1.setTextColor(Color.GRAY);
                }
                c1.render(g);

                if (playerSelected.getControls()[1] != 0) {
                    c2.setText(KeyEvent.getKeyText(playerSelected.getControls()[1]));
                } else {
                    c2.setText("Set");
                }
                if (controlSelected[1]) {
                    c2.setTextColor(playerSelected.getColor());
                } else {
                    c2.setTextColor(Color.GRAY);
                }
                c2.render(g);
            }
            start.render(g);
            back.render(g);
        }
        if (menuState == MenuState.OPTIONS) {
            if (Boolean.valueOf(settings.getProp("frameCounterOn"))) {
                frameShowToggle.setBackgroundColor(Color.WHITE);
            } else {
                frameShowToggle.setBackgroundColor(null);
            }
            if (Boolean.valueOf(settings.getProp("frameCounterOn"))) {
                frameShowToggle.setBackgroundColor(Color.WHITE);
            } else {
                frameShowToggle.setBackgroundColor(null);
            }
            if (Boolean.valueOf(settings.getProp("AA"))) {
                aaToggle.setBackgroundColor(Color.WHITE);
            } else {
                aaToggle.setBackgroundColor(null);
            }
            if (Boolean.valueOf(settings.getProp("sound"))) {
                soundToggle.setBackgroundColor(Color.WHITE);
            } else {
                soundToggle.setBackgroundColor(null);
            }

            frameShowToggle.render(g);
            frameShowText.render(g);
            aaToggle.render(g);
            aaText.render(g);
            soundToggle.render(g);
            soundText.render(g);
            back.render(g);
            save.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (game.getGameState() == GameState.MENU) {
            if (menuState == MenuState.MAIN) {
                if (row.buttons[0].mouseOver(me.getPoint())) {
                    this.setMenuState(MenuState.SETUP);
                } else if (row.buttons[1].mouseOver(me.getPoint())) {
                    this.setMenuState(MenuState.OPTIONS);
                } else if (row.buttons[2].mouseOver(me.getPoint())) {
                    System.exit(0);
                }
            } else if (menuState == MenuState.SETUP) {
                if (aiToggle.mouseOver(me.getPoint())) {
                    if (playerSelected.isAi()) {
                        playerSelected.setAi(false);
                        playerSelected.setName(Settings.getProp(playerSelected.toString() + "name"));
                    } else {
                        playerSelected.setAi(true);
                        playerSelected.setName(Settings.getProp(playerSelected.toString() + "AIname"));
                    }
                }

                if (playerName.mouseOver(me.getPoint())) {
                    if (!playerSelected.isAi()) {
                        nameEditable = true;
                    }
                } else if (scrollLeft.mouseOver(me.getPoint())) {
                    playerSelected = playerSelected.getPrevious();
                    playerName.setOutlineColor(playerSelected.getColor());
                } else if (scrollRight.mouseOver(me.getPoint())) {
                    playerSelected = playerSelected.getNext();
                } else {
                    nameEditable = false;
                    playerName.setOutlineColor(Color.WHITE);
                }
                if (nameEditable) {
                    playerName.setOutlineColor(playerSelected.getColor());
                }

                if (c1.mouseOver(me.getPoint())) {
                    controlEditable = true;
                    controlSelected = new boolean[]{true, false};
                } else if (c2.mouseOver(me.getPoint())) {
                    controlEditable = true;
                    controlSelected = new boolean[]{false, true};
                } else {
                    controlEditable = false;
                    controlSelected = new boolean[]{false, false};
                }

                if (start.mouseOver(me.getPoint())) {
                    settings.save();
                    game.newGame();
                    game.setGameState(GameState.GAME);
                }

                if (back.mouseOver(me.getPoint())) {
                    this.setMenuState(MenuState.MAIN);
                }
            } else if (menuState == MenuState.OPTIONS) {
                if(frameShowToggle.mouseOver(me.getPoint())){
                    boolean frameShow = !Boolean.valueOf(settings.getProp("frameCounterOn"));
                    settings.setProp("frameCounterOn", Boolean.toString(frameShow));
                }
                if (aaToggle.mouseOver(me.getPoint())) {
                    boolean aa = !Boolean.valueOf(settings.getProp("AA"));
                    settings.setProp("AA", Boolean.toString(aa));
                }
                if (soundToggle.mouseOver(me.getPoint())) {
                    boolean sound = !Boolean.valueOf(settings.getProp("sound"));
                    settings.setProp("sound", Boolean.toString(sound));
                }
                if (save.mouseOver(me.getPoint())) {
                    settings.save();
                }
                if (back.mouseOver(me.getPoint())) {
                    this.setMenuState(MenuState.MAIN);
                }
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

    public MenuState getMenuState() {
        return menuState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public PlayerNumber getPlayerSelected() {
        return playerSelected;
    }

    public void setPlayerSelected(PlayerNumber playerSelected) {
        this.playerSelected = playerSelected;
    }

    public boolean[] getControlSelected() {
        return controlSelected;
    }

    public void setControlSelected(boolean[] controlSelected) {
        this.controlSelected = controlSelected;
    }

    public boolean isNameEditable() {
        return nameEditable;
    }

    public boolean isControlEditable() {
        return controlEditable;
    }

}
