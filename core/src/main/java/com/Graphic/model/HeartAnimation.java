package com.Graphic.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HeartAnimation {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float alpha;
    private float scale;
    private float time;
    private float duration;
    private float rotation;
    private float rotationSpeed;

    public boolean isFinished() {
        return time >= duration;
    }

    public HeartAnimation(Texture texture, float x, float y) {
        this.texture = texture;
        this.position = new Vector2(x, y);

        // سرعت تصادفی
        float angle = (float) (Math.random() * Math.PI / 2 + Math.PI / 4); // 45 تا 135 درجه
        float speed = 50 + (float)(Math.random() * 50); // سرعت بین 50 تا 100
        this.velocity = new Vector2((float)Math.cos(angle), (float)Math.sin(angle)).scl(speed);

        this.alpha = 1f;
        this.scale = 0.8f + (float)(Math.random() * 0.4f); // مقیاس بین 0.8 تا 1.2
        this.time = 0f;
        this.duration = 1.2f + (float)(Math.random() * 0.4f); // بین 1.2 تا 1.6 ثانیه
        this.rotation = (float)(Math.random() * 360f);
        this.rotationSpeed = (float)(Math.random() * 60f - 30f); // چرخش آهسته چپ یا راست
    }

    public void update(float delta) {
        time += delta;
        position.mulAdd(velocity, delta);
        rotation += rotationSpeed * delta;

        float progress = time / duration;
        alpha = 1f - progress;  // محو شدن
        scale = scale * (1f - 0.1f * delta); // کم‌کم کوچکتر شدن
    }

    public void render(Batch batch) {
        Color oldColor = batch.getColor();
        batch.setColor(1f, 1f, 1f, alpha);
        batch.draw(texture,
            position.x, position.y,
            texture.getWidth() / 2f, texture.getHeight() / 2f,
            texture.getWidth(), texture.getHeight(),
            scale, scale,
            rotation,
            0, 0, texture.getWidth(), texture.getHeight(),
            false, false);
        batch.setColor(oldColor);
    }
}
