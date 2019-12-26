package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.enums.ID;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.enums.Team;
import io.github.arrebarritra._4pp.tools.Clamp;
import io.github.arrebarritra._4pp.tools.Distance;
import java.awt.Graphics2D;

/**
 * Player object
 */
public class Player extends GameObject {

    public PlayerNumber playerNumber;
    private PlayerTracker playerTracker;
    boolean[] keyDown = {false, false};
    int randomPoint;

    public Player(int x, int y, ID id, Handler handler, PlayerNumber playerNumber) {
        super(x, y, id, handler);

        this.playerNumber = playerNumber;
        playerTracker = new PlayerTracker();

        if (playerNumber.isAi()) {
            generateNewRandomPoint();
        }

        if (playerNumber.getTeam() == Team.TEAM1) {
            this.height = 64;
            this.width = 16;

            if (this.playerNumber == PlayerNumber.Player1) {
                this.color = PlayerNumber.Player1.getColor();
            } else if (this.playerNumber == PlayerNumber.Player2) {
                this.color = PlayerNumber.Player2.getColor();
            }
        } else if (playerNumber.getTeam() == Team.TEAM2) {
            this.height = 16;
            this.width = 64;
            if (this.playerNumber == PlayerNumber.Player3) {
                this.color = PlayerNumber.Player3.getColor();
            } else if (this.playerNumber == PlayerNumber.Player4) {
                this.color = PlayerNumber.Player4.getColor();
            }
        }
    }

    public PlayerTracker getPlayerTracker() {
        return playerTracker;
    }

    public void setPlayerTracker(PlayerTracker playerTracker) {
        this.playerTracker = playerTracker;
    }

    @Override
    public void tick() {
        boolean ballExists = false;

        //Controls AI movement
        if (this.playerNumber.isAi()) {
            for (int index = 0; index < handler.object.size(); index++) {
                GameObject tempObject = handler.object.get(index);

                if (tempObject.getId() == ID.Ball) {
                    ballExists = true;
                    if (Distance.calculateDistance(this.getLocation(), tempObject.getLocation()) < Game.HEIGHT * 2 / 3) {
                        if (this.playerNumber.getTeam() == Team.TEAM1) {
                            this.aiMovePlayer((int) tempObject.getBounds().getCenterY());
                        } else if (this.playerNumber.getTeam() == Team.TEAM2) {
                            this.aiMovePlayer((int) tempObject.getBounds().getCenterX());
                        }
                    } else {
                        if (this.playerNumber.getTeam() == Team.TEAM1) {
                            if (Math.abs(Game.HEIGHT / 2 - (this.getBounds().getY() + randomPoint)) > 64) {
                                this.aiMovePlayer(Game.HEIGHT / 2);
                            } else {
                                this.setVelY(0);
                            }
                        } else if (this.playerNumber.getTeam() == Team.TEAM2) {
                            if (Math.abs(Game.WIDTH / 2 - (this.getBounds().getX() + randomPoint)) > 64) {
                                this.aiMovePlayer(Game.WIDTH / 2);
                            } else {
                                this.setVelX(0);
                            }
                        }
                    }
                }
            }
            if (ballExists == false) {
                if (this.playerNumber.getTeam() == Team.TEAM1) {
                    this.aiMovePlayer(Game.HEIGHT / 2);
                } else if (this.playerNumber.getTeam() == Team.TEAM2) {
                    this.aiMovePlayer(Game.WIDTH / 2);
                }
            }
        }

        this.setX(x + velX);
        this.setY(y + velY);

        if (this.playerNumber.getTeam() == Team.TEAM1) {
            y = Clamp.clamp(y, 52, Game.HEIGHT - (this.height + 52));
        } else if (this.playerNumber.getTeam() == Team.TEAM2) {
            x = Clamp.clamp(x, 52, Game.WIDTH - (this.width + 52));
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(this.color);
        g.fillRect(x, y, this.width, this.height);
    }

    /**
     * Sets the velocity of paddle if it is an AI towards the target location
     *
     * @param targetLocation The target location for the AI, either x or y
     * depending on the plane the AI moves in.
     */
    private void aiMovePlayer(int targetLocation) {
        if (this.playerNumber.getTeam() == Team.TEAM1) {
            if (Math.abs(targetLocation - (this.getBounds().getY() + randomPoint)) < 5) {
                this.setVelY(0);
            } else if (targetLocation > (this.getBounds().getY() + randomPoint)) {
                this.setVelY(4);
            } else if (targetLocation < (this.getBounds().getY() + randomPoint)) {
                this.setVelY(-4);
            }
        } else if (this.playerNumber.getTeam() == Team.TEAM2) {
            if (Math.abs(targetLocation - (this.getBounds().getX() + randomPoint)) < 5) {
                this.setVelX(0);
            } else if (targetLocation > (this.getBounds().getX() + randomPoint)) {
                this.setVelX(4);
            } else if (targetLocation < (this.getBounds().getX() + randomPoint)) {
                this.setVelX(-4);
            }
        }
    }

    /**
     * Generates a new random point on the paddle if the current object is an AI
     */
    public void generateNewRandomPoint() {
        randomPoint = (int) (Math.random() * 64);
    }
}
