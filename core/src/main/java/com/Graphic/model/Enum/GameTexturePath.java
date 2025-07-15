package com.Graphic.model.Enum;

public enum GameTexturePath {

    Clock ("Erfan/Clock/EmptyClock.png"),
    ;


    private final String path;

    GameTexturePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
