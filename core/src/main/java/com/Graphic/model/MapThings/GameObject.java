package com.Graphic.model.MapThings;

public class GameObject {

    private char Charactor;

    public char getCharactor() {
        return Charactor;
    }
    public void setCharactor(char Charactor) {
        this.Charactor = Charactor;
    }

    public void startDayAutomaticTask() {}
    public void turnByTurnAutomaticTask() {}

    public String getIcon() {
        return "";
    }
}
