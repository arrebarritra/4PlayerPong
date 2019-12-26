package io.github.arrebarritra._4pp;

import io.github.arrebarritra._4pp.enums.PlayState;

/**
 * 
 * Tracks team scores
 */
public class Score {

    private int team1Score = 0, team2Score = 0;
    private Game game;

    public Score(Game game) {
        this.game = game;
    }

    public void reset() {
        team1Score = 0;
        team2Score = 0;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void incrementTeam1Score() {
        this.team1Score++;
        if (team1Score == 10) {
            game.setPlayState(PlayState.END);
        }
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void incrementTeam2Score() {
        this.team2Score++;
        if (team2Score == 10) {
            game.setPlayState(PlayState.END);
        }
    }
}
