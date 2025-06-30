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
    static ObjectMap<String, Animation<TextureRegion>> animations = new ObjectMap<>();
    static ObjectMap<String, Integer> frameCounts = new ObjectMap<>();
    static float defaultSpeed = 0.5f;

    public SimpleEffect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static void loadAnimation(String effectName, String basePath, int frameCount, float speed) {
        if (!animations.containsKey(effectName)) {
            Array<TextureRegion> frames = new Array<>();
            for (int i = 0; i < frameCount; i++) {
                Texture texture = new Texture(basePath + i + ".png");
                frames.add(new TextureRegion(texture));
            }
            animations.put(effectName, new Animation<>(speed / frameCount, frames, Animation.PlayMode.NORMAL));
            frameCounts.put(effectName, frameCount);
        }
    }

    // Method to check if the effect is finished
    public boolean isFinished(String effectName) {
        Animation<TextureRegion> animation = animations.get(effectName);
        return animation.isAnimationFinished(stateTime);
    }

    // Draw the effect on screen
    public void draw(SpriteBatch batch, float delta, String effectName) {
        stateTime += delta;
        Animation<TextureRegion> animation = animations.get(effectName);
        TextureRegion frame = animation.getKeyFrame(stateTime, false);
        batch.draw(frame, x, y);
    }

    // Load a default effect with specified parameters (for convenience)
    public static void loadDefaultEffect(String effectName, String basePath, int frameCount) {
        loadAnimation(effectName, basePath, frameCount, defaultSpeed);
    }

    // Getter for animation frame count (optional)
    public static int getFrameCount(String effectName) {
        return frameCounts.get(effectName, 0);
    }
}
