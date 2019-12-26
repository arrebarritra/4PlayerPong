package io.github.arrebarritra._4pp.enums;

import io.github.arrebarritra._4pp.tools.Settings;
import java.awt.Color;

public enum PlayerNumber {

    Player1 {
                @Override
                public Color getColor() {
                    return Color.decode("#2196F3");
                }

                @Override
                public Team getTeam() {
                    return Team.TEAM1;
                }
            },
    Player2 {
                @Override
                public Color getColor() {
                    return Color.decode("#009688");
                }

                @Override
                public Team getTeam() {
                    return Team.TEAM1;
                }
            },
    Player3 {
                @Override
                public Color getColor() {
                    return Color.decode("#FFEB3B");
                }

                @Override
                public Team getTeam() {
                    return Team.TEAM2;
                }
            },
    Player4 {
                @Override
                public Color getColor() {
                    return Color.decode("#FF9800");
                }

                @Override
                public Team getTeam() {
                    return Team.TEAM2;
                }
            };
    
    private boolean ai;

    public abstract Color getColor();

    public abstract Team getTeam();

    public String getName() {
        if (isAi()) {
            return Settings.getProp(this.toString() + "AIname");
        } else {
            return Settings.getProp(this.toString() + "name");
        }
    }

    public void setName(String name) {
//        this.name = name;
        if (!isAi()) {
            Settings.setProp(this.toString() + "name", name);
        }
    }

    public int[] getControls() {
        return new int[]{Integer.parseInt(Settings.getProp(this.toString() + "Control1")), Integer.parseInt(Settings.getProp(this.toString() + "Control2"))};
    }

    public void setControls(int[] controls) {
        Settings.setProp(this.toString() + "Control1", Integer.toString(controls[0]));
        Settings.setProp(this.toString() + "Control2", Integer.toString(controls[1]));
    }

    public void setControl(int control, int index) {
        Settings.setProp(this.toString() + "Control" + (index + 1), Integer.toString(control));
    }

    public boolean isAi() {
        return Boolean.parseBoolean(Settings.getProp(this.toString() + "IsAi"));
    }

    public void setAi(boolean ai) {
        Settings.setProp(this.toString() + "IsAi", Boolean.toString(ai));
    }

    public PlayerNumber getNext() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public PlayerNumber getPrevious() {
        return values()[(((this.ordinal() - 1) % values().length) + values().length) % values().length];
    }
}
