package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.tools.Settings;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.enums.ID;
import io.github.arrebarritra._4pp.enums.Team;
import java.awt.Color;
import java.util.Random;

/**
 * Tracks all game statistics
 */
public class GameTracker {

    Random r = new Random();
    private PlayerNumber lastScorer, lastScorerTeam1, lastScorerTeam2, lastTouch, lastTouchTeam1, lastTouchTeam2, lastConceded, lastConcededTeam1, lastConcededTeam2, nextServer;
    private Team lastTeamScored;
    private String HUDMessage;
    private Color messageColor;

    private Handler handler;
    private Settings settings;

    public GameTracker(Handler handler, Settings settings) {
        this.handler = handler;
        this.settings = settings;
        nextServer = PlayerNumber.Player1;
    }

    public PlayerNumber getLastScorer() {
        return lastScorer;
    }

    /**
     * Sets lastScorer and lastScorerTeam[TeamNumber] if there is a scorer, and
     * sets nextServer
     */
    public void setLastScorer() {
        if (this.lastTeamScored == Team.TEAM1) {
            if (this.lastTouch != null && this.lastTouch.getTeam() == Team.TEAM1) {
                this.lastScorer = this.lastTouchTeam1;
                this.lastScorerTeam2 = this.lastTouchTeam1;
                this.nextServer = this.lastTouchTeam1;
            } else {
                this.lastScorer = null;
                int random = r.nextInt(2);
                this.nextServer = PlayerNumber.values()[random];
            }
        } else if (this.lastTeamScored == Team.TEAM2) {
            if (this.lastTouch != null && this.lastTouch.getTeam() == Team.TEAM2) {
                this.lastScorer = this.lastTouchTeam2;
                this.lastScorerTeam2 = this.lastTouchTeam2;
                this.nextServer = this.lastTouchTeam2;
            } else {
                this.lastScorer = null;
                int random = r.nextInt(2) + 2;
                this.nextServer = PlayerNumber.values()[random];
            }
        }

        this.setHUDMessage();
        this.lastTouch = null;
        this.lastTouchTeam1 = null;
        this.lastTouchTeam2 = null;
    }

    public PlayerNumber getLastScorerTeam1() {
        return lastScorerTeam1;
    }

    public PlayerNumber getLastScorerTeam2() {
        return lastScorerTeam2;
    }

    public PlayerNumber getLastTouch() {
        return lastTouch;
    }

    /**
     * Sets lastTouch and lastTouch[TeamNumber]
     *
     * @param lastTouch PlayerNumber of the last player to touch the ball
     */
    public void setLastTouch(PlayerNumber lastTouch) {
        this.lastTouch = lastTouch;

        if (lastTouch == PlayerNumber.Player1 || lastTouch == PlayerNumber.Player2) {
            this.lastTouchTeam1 = this.lastTouch;
        } else if (lastTouch == PlayerNumber.Player3 || lastTouch == PlayerNumber.Player4) {
            this.lastTouchTeam2 = this.lastTouch;
        }
    }

    public PlayerNumber getLastTouchTeam1() {
        return lastTouchTeam1;
    }

    public PlayerNumber getLastTouchTeam2() {
        return lastTouchTeam2;
    }

    public PlayerNumber getLastConceded() {
        return lastConceded;
    }

    /**
     * Sets lastTouch and lastTouch[TeamNumber]
     *
     * @param lastConceded PlayerNumber of the last player to concede a goal
     */
    public void setLastConceded(PlayerNumber lastConceded) {
        this.lastConceded = lastConceded;

        if (lastConceded == PlayerNumber.Player1 || lastConceded == PlayerNumber.Player2) {
            this.lastConcededTeam1 = this.lastConceded;
        } else if (lastConceded == PlayerNumber.Player3 || lastConceded == PlayerNumber.Player4) {
            this.lastConcededTeam2 = this.lastConceded;
        }
    }

    public PlayerNumber getLastConcededTeam1() {
        return lastConcededTeam1;
    }

    public PlayerNumber getLastConcendedTeam2() {
        return lastConcededTeam2;
    }

    public PlayerNumber getNextServer() {
        return nextServer;
    }

    public Team getLastTeamScored() {
        return lastTeamScored;
    }

    public void setLastTeamScored(Team lastTeamScored) {
        this.lastTeamScored = lastTeamScored;
    }

    public String getScoreMessage() {
        return HUDMessage;
    }

    /**
     * Sets a message in the HUD after a goal has been scored
     */
    private void setHUDMessage() {
        if (this.lastScorer != null) {
            this.HUDMessage = lastScorer.getName() + " scored!";

            for (int index = 0; index < handler.object.size(); index++) {
                GameObject tempObject = handler.object.get(index);
                if (tempObject.getId() == ID.Player) {
                    Player tempPlayer = (Player) tempObject;

                    if (tempPlayer.playerNumber == this.lastScorer) {
                        tempPlayer.getPlayerTracker().incrementGoals();
                        this.messageColor = tempPlayer.getColor();
                    }
                }
            }
        } else {
            if (lastTouch != null) {
                this.HUDMessage = lastConceded.getName() + " missed a pass off their teammate!";

                for (int index = 0; index < handler.object.size(); index++) {
                    GameObject tempObject = handler.object.get(index);
                    if (tempObject.getId() == ID.Player) {
                        Player tempPlayer = (Player) tempObject;

                        if (tempPlayer.playerNumber == this.lastConceded) {
                            tempPlayer.getPlayerTracker().incrementMissedPasses();
                            this.messageColor = tempPlayer.getColor();
                        }
                    }
                }
            } else {
                this.HUDMessage = lastConceded.getName() + " missed the serve!";

                for (int index = 0; index < handler.object.size(); index++) {
                    GameObject tempObject = handler.object.get(index);
                    if (tempObject.getId() == ID.Player) {
                        Player tempPlayer = (Player) tempObject;

                        if (tempPlayer.playerNumber == this.lastConceded) {
                            tempPlayer.getPlayerTracker().incrementMissedServes();
                            this.messageColor = tempPlayer.getColor();
                        }
                    }
                }
            }
        }
    }

    public Color getMessageColor() {
        return messageColor;
    }
}
