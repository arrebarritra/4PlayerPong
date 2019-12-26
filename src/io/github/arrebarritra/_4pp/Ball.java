package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.enums.ID;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import io.github.arrebarritra._4pp.enums.Team;
import io.github.arrebarritra._4pp.tools.Sound;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * Ball object
 */
public class Ball extends GameObject {

    private final Game game;
    private final GameTracker tracker;
    private final Score score;
    private int bounceVel = 5;
    private int serveVel = 5;

    public Ball(int x, int y, ID id, Handler handler, Game game, GameTracker tracker, Score score) {
        super(x, y, id, handler);
        this.height = 16;
        this.width = 16;
        this.game = game;
        this.color = Color.white;
        this.tracker = tracker;
        this.score = score;

        switch (tracker.getNextServer()) {
            case Player1:
                velX = -serveVel;
                velY = 3;
                break;
            case Player2:
                velX = serveVel;
                velY = -3;
                break;
            case Player3:
                velX = -3;
                velY = -serveVel;
                break;
            case Player4:
                velX = 3;
                velY = serveVel;
                break;
            default:
                break;
        }
    }

    @Override
    public void tick() {
        this.setX(x + velX);
        this.setY(y + velY);

        detectCollision();

        //Handles ball going into goal
        if (x < (0 - width) || x > Game.WIDTH || y < (0 - height) || y > Game.HEIGHT) {
            if (x < 0 - width || x > Game.WIDTH) {
                Sound.playSound("peep");
                if (x < 0 - width) {
                    tracker.setLastConceded(PlayerNumber.Player1);
                } else if (x > Game.WIDTH) {
                    tracker.setLastConceded(PlayerNumber.Player2);
                }
                score.incrementTeam2Score();
                tracker.setLastTeamScored(Team.TEAM2);
                tracker.setLastScorer();
            } else if (y < 0 - height || y > Game.HEIGHT) {
                Sound.playSound("peep");
                if (y < 0 - height) {
                    tracker.setLastConceded(PlayerNumber.Player3);
                } else if (y > Game.WIDTH) {
                    tracker.setLastConceded(PlayerNumber.Player4);
                }
                tracker.setLastTeamScored(Team.TEAM1);
                tracker.setLastScorer();
                score.incrementTeam1Score();
                
            }

            if (score.getTeam1Score() < 10 && score.getTeam2Score() < 10) {
                game.setServing(true);
            }

            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(this.color);
        g.fillRect(x, y, this.width, this.height);
    }

    /**
     * Detects if ball has collided with another object and changes velocity
     * depending on that
     */
    private void detectCollision() {
        for (int index = 0; index < handler.object.size(); index++) {
            GameObject tempObject = handler.object.get(index);
            
            //Handles collision with player
            if (tempObject.getId() == ID.Player) {
                Player tempPlayer = (Player) tempObject;
                if (this.getBounds().intersects(tempPlayer.getBounds())) {
                    Sound.playSound("beep");
                    if (tempPlayer.playerNumber.getTeam() == Team.TEAM1) {
                        if (tempPlayer.playerNumber == PlayerNumber.Player1) {
                            this.setX(tempPlayer.getX() + tempPlayer.getWidth());
                        } else if (tempPlayer.playerNumber == PlayerNumber.Player2) {
                            this.setX(tempPlayer.getX() - this.height);
                        }

                        this.velY = (int) ((this.getBounds().getCenterY() - tempObject.getBounds().getCenterY()) / 3);
                        if (this.velX < 0) {
                            this.velX = bounceVel;
                        } else if (this.velX > 0) {
                            this.velX = -bounceVel;
                        }
                    } else if (tempPlayer.playerNumber.getTeam() == Team.TEAM2) {
                        if (tempPlayer.playerNumber == PlayerNumber.Player3) {
                            this.setY(tempPlayer.getY() + tempPlayer.getHeight());
                        } else if (tempPlayer.playerNumber == PlayerNumber.Player4) {
                            this.setY(tempPlayer.getY() - this.height);
                        }

                        this.velX = (int) ((this.getBounds().getCenterX() - tempObject.getBounds().getCenterX()) / 3);
                        if (this.velY < 0) {
                            this.velY = bounceVel;
                        } else if (this.velY > 0) {
                            this.velY = -bounceVel;
                        }
                    }

                    if (tempPlayer.playerNumber.isAi()) {
                        tempPlayer.generateNewRandomPoint();
                    }

                    if (tracker.getLastTouch() != tempPlayer.playerNumber) {
                        tempPlayer.getPlayerTracker().incrementTouches();
                    }
                    tracker.setLastTouch(tempPlayer.playerNumber);
                    this.color = tempObject.getColor();
                }
            } 
            //Handles collision with corner blocks
            else if (tempObject.getId() == ID.Block) {
                if (this.getBounds().intersects(tempObject.getBounds())) {
                    Sound.playSound("plop");

                    if (Math.abs(this.getBounds().getCenterX() - tempObject.getBounds().getCenterX()) > Math.abs(this.getBounds().getCenterY() - tempObject.getBounds().getCenterY())) {
                        this.velX *= -1;
                    } else if (Math.abs(this.getBounds().getCenterX() - tempObject.getBounds().getCenterX()) < Math.abs(this.getBounds().getCenterY() - tempObject.getBounds().getCenterY())) {
                        this.velY *= -1;
                    } else if (Math.abs(this.getBounds().getCenterX() - tempObject.getBounds().getCenterX()) == Math.abs(this.getBounds().getCenterY() - tempObject.getBounds().getCenterY())) {
                        this.velY *= -1;
                        this.velX *= -1;
                    }

                    this.setX(this.prevX);
                    this.setY(this.prevY);
                }
            }
        }
    }
}
