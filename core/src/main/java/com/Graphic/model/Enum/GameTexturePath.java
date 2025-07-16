package com.Graphic.model.Enum;

public enum GameTexturePath {

    Clock ("Erfan/Clock/Clock2.png"),
    Cloud ("Erfan/Lightning/cloud.png"),
    Light ("Erfan/Lightning/light.png"),
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
