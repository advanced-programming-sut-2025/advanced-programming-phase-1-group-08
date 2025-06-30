package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class SFXManager {

    private static SFXManager instance;
    public HashMap<String, Sound> sounds;


    public SFXManager getInstance () {
        if (instance == null) {
            instance = new SFXManager();
            instance.sounds = new HashMap<>();
            loadSounds();
        }
        return instance;
    }

    private void loadSounds() {
        for (SFX sfx : SFX.values())
            sounds.put(sfx.name, Gdx.audio.newSound(Gdx.files.internal(sfx.path)));
    }
    public void play (SFX sfxName) {
        Sound sound = sounds.get(sfxName.name);
        if (sound != null) // && SettingMenuView.getInstance().getSfxCheckBox().isChecked()) // TODO  میشه هم اول تایع گذاشت که دیگه تولید الکی هم نکنه
            sound.play();
        else
            Gdx.app.log("SFXManager", "Sound not found: " + sfxName);
    }
    public void dispose() {
        for (Sound sound : sounds.values())
            sound.dispose();
    }
}
