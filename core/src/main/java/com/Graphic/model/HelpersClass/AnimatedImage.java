package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

public class AnimatedImage extends Image {

    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;

    public AnimatedImage(float speed, Array<TextureRegion> frames, Animation.PlayMode playMode) {
        super(new Animation<TextureRegion>(speed, frames, playMode).getKeyFrame(0));
        this.animation = new Animation<TextureRegion>(speed, frames, playMode);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        setDrawable(new TextureRegionDrawable(animation.getKeyFrame(stateTime, true)));
    }
}

