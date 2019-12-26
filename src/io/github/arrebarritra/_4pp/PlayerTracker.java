package io.github.arrebarritra._4pp;

/**
 * 
 * Tracks all player statistics
 */
public class PlayerTracker {

    private int goals, touches, missedPasses, missedServes;

    public PlayerTracker() {
    }

    public void reset() {
        goals = 0;
        touches = 0;
        missedPasses = 0;
        missedServes = 0;
    }

    public int getGoals() {
        return goals;
    }

    public void incrementGoals() {
        goals++;
    }

    public int getTouches() {
        return touches;
    }

    public void incrementTouches() {
        touches++;
    }

    public int getMissedPasses() {
        return missedPasses;
    }

    public void incrementMissedPasses() {
        missedPasses++;
    }

    public int getMissedServes() {
        return missedServes;
    }

    public void incrementMissedServes() {
        missedServes++;
    }
}
