package com.trilogyed.levelupconsumerqueue.util.messages;


import java.util.Objects;

public class LevelUpEntry {

    private String levelUp;

    public String getLevelUp() {
        return levelUp;
    }

    public void setLevelUp(String levelUp) {
        this.levelUp = levelUp;
    }

    @Override
    public String toString() {
        return "LevelUpEntry{" +
                "levelUp='" + levelUp + '\'' +
                '}';
    }
}
