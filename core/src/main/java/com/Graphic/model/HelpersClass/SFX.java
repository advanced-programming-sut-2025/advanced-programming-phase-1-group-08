package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public enum SFX {

    test (Gdx.audio.newSound(Gdx.files.internal("SFX/name.wav"))),
    ;

    private final Sound sound;

    private SFX(Sound sound) {

        this.sound = sound;
    }


    public Sound getSound() {

        return sound;
    }
}
