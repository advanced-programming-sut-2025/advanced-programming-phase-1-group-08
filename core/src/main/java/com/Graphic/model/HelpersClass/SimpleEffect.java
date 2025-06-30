package com.Graphic.model.HelpersClass;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class SimpleEffect {

    float x, y;
    float stateTime = 0f;

    static ObjectMap<EffectType, Animation<TextureRegion>> animations = new ObjectMap<>();

    public SimpleEffect(float x, float y, EffectType type) {
        this.x = x;
        this.y = y;
        loadAnimationIfNeeded(type);
    }

    private void loadAnimationIfNeeded(EffectType type) {
        if (!animations.containsKey(type)) {
            Array<TextureRegion> frames = new Array<>();
            for (int i = 0; i < type.frameCount; i++) {
                Texture texture = new Texture(type.basePath + i + ".png");
                frames.add(new TextureRegion(texture));
            }
            Animation<TextureRegion> animation = new Animation<>(type.speed / type.frameCount, frames, Animation.PlayMode.NORMAL);
            animations.put(type, animation);
        }
    }

    public boolean isFinished(EffectType type) {
        return animations.get(type).isAnimationFinished(stateTime);
    }

    public void draw(SpriteBatch batch, float delta, EffectType type) {
        stateTime += delta;
        TextureRegion frame = animations.get(type).getKeyFrame(stateTime, false);
        batch.draw(frame, x, y);
    }
}

