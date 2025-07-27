package com.Graphic.model.HelpersClass;

public enum SFX {

    //test ("test","SFX/name.wav"),
    explosion("explosion","Music/explosion.wav");
    ;

    public final String name;
    public final String path;

    SFX(String name, String path) {
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
