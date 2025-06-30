package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum MusicType {

    Experience ("Music/Experience.mp3"),
    ;


    String path;


    MusicType(String path) {

        this.path = path;
    }


    public Music getMusic() {

        return Gdx.audio.newMusic(Gdx.files.internal(this.path));
    }
}
