package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.tools.Settings;
import io.github.arrebarritra._4pp.enums.GameState;
import io.github.arrebarritra._4pp.enums.ID;
import io.github.arrebarritra._4pp.enums.MenuState;
import io.github.arrebarritra._4pp.enums.PlayState;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.enums.Team;
import io.github.arrebarritra._4pp.gui.Menu;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * Handles all keyboard input
 */
public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private final Game game;
    private final Menu menu;
    private final Settings settings;

    public KeyInput(Handler handler, Game game, Menu menu, Settings settings) {
        this.handler = handler;
        this.game = game;
        this.menu = menu;
        this.settings = settings;
    }

    /**
     * Changes control for currently selected player and control, and checks
     * whether the desired key is available.
     *
     * @param key Desired key for control
     * @param index Index of control
     */
    public void changeControl(int key, int index) {
        boolean isInvalidKey = false;

        if (key == KeyEvent.VK_ESCAPE || !(Character.isLetterOrDigit(key) || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)) {

            isInvalidKey = true;
        }
//        for (int i = 0; i < PlayerNumber.values().length; i++) {
//            if (isInvalidKey) {
//                break;
//            }
//            if (PlayerNumber.values()[i].getControls()[0] == key || PlayerNumber.values()[i].getControls()[1] == key) {
//                isInvalidKey = true;
//            }
//        }

        if (!isInvalidKey) {
            menu.getPlayerSelected().setControl(key, index);
            for (int i = 0; i < PlayerNumber.values().length; i++) {
                if (PlayerNumber.values()[i] == menu.getPlayerSelected()) {
                    if (menu.getControlSelected()[0]) {
                        PlayerNumber.values()[i].setControl(key, 0);
                    } else if (menu.getControlSelected()[1]) {
                        PlayerNumber.values()[i].setControl(key, 1);
                    }
                }
            }
        }
    }

    public void keyPressed(KeyEvent ke) {
        int key = ke.getExtendedKeyCode();

        //Handles game key presses
        if (game.getGameState() == GameState.GAME) {
            if (key == KeyEvent.VK_ESCAPE) {
                if (game.getPlayState() == PlayState.PLAYING) {
                    game.setPlayState(PlayState.PAUSED);
                } else if (game.getPlayState() == PlayState.PAUSED) {
                    game.setPlayState(PlayState.PLAYING);
                }
            }
            if (key == KeyEvent.VK_SHIFT) {
                if (game.getPlayState() == PlayState.PAUSED) {
                    game.resetGame();
                    game.setGameState(GameState.MENU);
                    menu.setMenuState(MenuState.MAIN);
                }
            }
            //Handles paddle movement keypresses
            if (game.getPlayState() == PlayState.PLAYING) {
                for (int index = 0; index < handler.object.size(); index++) {
                    GameObject tempObject = handler.object.get(index);
                    if (tempObject.getId() == ID.Player) {
                        Player tempPlayer = (Player) tempObject;

                        if (!tempPlayer.playerNumber.isAi()) {
                            if (tempPlayer.playerNumber.getTeam() == Team.TEAM1) {
                                if (key == tempPlayer.playerNumber.getControls()[0]) {
                                    tempPlayer.setVelY(-5);
                                    tempPlayer.keyDown[0] = true;
                                } else if (key == tempPlayer.playerNumber.getControls()[1]) {
                                    tempPlayer.setVelY(5);
                                    tempPlayer.keyDown[1] = true;
                                }
                            } else if (tempPlayer.playerNumber.getTeam() == Team.TEAM2) {
                                if (key == tempPlayer.playerNumber.getControls()[0]) {
                                    tempPlayer.setVelX(-5);
                                    tempPlayer.keyDown[0] = true;
                                } else if (key == tempPlayer.playerNumber.getControls()[1]) {
                                    tempObject.setVelX(5);
                                    tempPlayer.keyDown[1] = true;
                                }
                            }
                        }
                    }
                }
            }
        } else if (game.getGameState() == GameState.MENU) {
            //Handles name changes
            if (menu.isNameEditable()) {
                StringBuilder nameString = new StringBuilder(menu.getPlayerSelected().getName());

                if (Character.isLetterOrDigit(key) && settings.getProp(menu.getPlayerSelected().toString() + "name").length() < 10) {
                    menu.getPlayerSelected().setName(nameString.append(ke.getKeyChar()).toString());
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    try {
                        menu.getPlayerSelected().setName(nameString.deleteCharAt(nameString.length() - 1).toString());
                    } catch (Exception ex) {
                    }
                }
            } //Handles control changes
            else if (menu.isControlEditable()) {
                if (menu.getControlSelected()[0]) {
                    this.changeControl(key, 0);
                    menu.setControlSelected(new boolean[]{false, true});
                } else if (menu.getControlSelected()[1]) {
                    this.changeControl(key, 1);
                    menu.setControlSelected(new boolean[]{false, false});
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getExtendedKeyCode();

        //Handles keyreleased in game and makes players stop moving
        for (int index = 0; index < handler.object.size(); index++) {
            GameObject tempObject = handler.object.get(index);
            if (tempObject.getId() == ID.Player) {
                Player tempPlayer = (Player) tempObject;
                if (key == tempPlayer.playerNumber.getControls()[0]) {
                    tempPlayer.keyDown[0] = false;
                } else if (key == tempPlayer.playerNumber.getControls()[1]) {
                    tempPlayer.keyDown[1] = false;
                }
                if (tempPlayer.playerNumber.getTeam() == Team.TEAM1) {
                    if (!tempPlayer.keyDown[0] && !tempPlayer.keyDown[1]) {
                        tempObject.setVelY(0);
                    }
                } else if (tempPlayer.playerNumber.getTeam() == Team.TEAM2) {
                    if (!tempPlayer.keyDown[0] && !tempPlayer.keyDown[1]) {
                        tempObject.setVelX(0);
                    }
                }
            }
        }
    }
}
