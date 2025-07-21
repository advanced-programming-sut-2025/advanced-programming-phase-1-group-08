package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class AnimatedImage extends Image {

    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;

    private static Array<TextureRegion> getAnimation(SampleAnimation sampleAnimation) {
        Texture sheet = TextureManager.get(sampleAnimation.getPath());
        TextureRegion[][] tmp = TextureRegion.split(sheet, sampleAnimation.getWidth(), sampleAnimation.getHeight());

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < sampleAnimation.getFrameNumber(); i++)
            frames.add(tmp[0][i]);
        return frames;
    }

    public AnimatedImage(float speed, Array<TextureRegion> frames, Animation.PlayMode playMode) {
        super(new Animation<TextureRegion>(speed, frames, playMode).getKeyFrame(0));
        this.animation = new Animation<TextureRegion>(speed, frames, playMode);
    }
    public AnimatedImage(float speed, SampleAnimation sampleAnimation, Animation.PlayMode playMode) {

        super(new Animation<TextureRegion>(speed, getAnimation(sampleAnimation), playMode).getKeyFrame(0));
        this.animation = new Animation<TextureRegion>(speed, getAnimation(sampleAnimation), playMode);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        setDrawable(new TextureRegionDrawable(animation.getKeyFrame(stateTime, true)));
    }
}

