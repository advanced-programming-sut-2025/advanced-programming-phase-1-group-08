package com.Graphic.model.Enum;

public enum GameTexturePath {

    map ("Erfan/map.png"),
    Book ("Ariyo/Shane_Icon.png"),
    Clock ("Erfan/Clock/Clock2.png"),
    Cloud ("Erfan/Lightning/cloud2.png"),
    Lightning ("Erfan/Lightning/light.png"),
    GreenHouse ("Erfan/greenhouse.png"),
    CloudShadow ("Erfan/Lightning/shadow.png"),
    ;


    private final String path;

    GameTexturePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
