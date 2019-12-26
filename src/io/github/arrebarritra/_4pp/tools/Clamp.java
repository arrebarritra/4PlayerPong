package io.github.arrebarritra._4pp.tools;

public class Clamp {

    /**
     * Utility method which checks whether number is within bounds. Returns the
     * value if it is within bounds, returns the minimum value if it's lower
     * than the minimum and returns the maximum value if it is higher than the
     * maximum value
     *
     * @param var Value to check
     * @param min Minimum value
     * @param max Maximum value
     * @return var if in bounds, min if lower than bound, max if higher than
     * bounds
     */
    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min) {
            return var = min;
        } else {
            return var;
        }
    }
}
