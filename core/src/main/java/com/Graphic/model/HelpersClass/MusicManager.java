package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class MusicManager {

    private static MusicManager instance;
    private Music backgroundMusic;

    private ObjectMap<MusicType, Music> musics = new ObjectMap<>();


    private MusicManager() {}

    public void changeMusic(MusicType type) {

        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }

        if (!musics.containsKey(type))
            musics.put(type, type.getMusic());

        backgroundMusic = musics.get(type);
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
    }

    public static MusicManager getInstance() {
        if (instance == null)
            instance = new MusicManager();
        return instance;
    }

    public void play() {
        if (backgroundMusic == null) return;

        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
    }
    public void pause() {
        if (backgroundMusic != null) backgroundMusic.pause();
    }
    public void dispose() {
        if (backgroundMusic != null) backgroundMusic.dispose();
    }
    public void setVolume(float volume) {
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(MathUtils.clamp(volume, 0f, 1f));
        }
    }
}
