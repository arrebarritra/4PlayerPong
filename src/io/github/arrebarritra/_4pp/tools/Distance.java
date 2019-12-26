package io.github.arrebarritra._4pp.tools;

import java.awt.Point;

/**
 * 
 * Distance calculator
 */
public class Distance {
    /**
     * Calculates distance between two point using pythagoras theorem
     * @param p1 First point
     * @param p2 Second point
     * @return Distance between p1 and p2
     */
    public static int calculateDistance(Point p1, Point p2){
        return (int) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }
}
