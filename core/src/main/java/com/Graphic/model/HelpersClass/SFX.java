package com.Graphic.model.HelpersClass;

public enum SFX {

    test ("test","SFX/name.wav"),
    ;

    public final String name;
    public final String path;

    private SFX(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
