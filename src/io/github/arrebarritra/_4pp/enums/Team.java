package io.github.arrebarritra._4pp.enums;

import io.github.arrebarritra._4pp.tools.ColorTools;
import io.github.arrebarritra._4pp.enums.PlayerNumber;
import java.awt.Color;

public enum Team {
    TEAM1 {
        public Color getColor() {
            return ColorTools.averageColor(new Color[]{PlayerNumber.Player1.getColor(), PlayerNumber.Player2.getColor()});
        }
    },
    TEAM2{
        public Color getColor() {
            return ColorTools.averageColor(new Color[]{PlayerNumber.Player3.getColor(), PlayerNumber.Player4.getColor()});
        }
    };
    
    public Color getColor(){
        return null;
    }
}
