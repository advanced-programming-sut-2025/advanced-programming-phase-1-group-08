package com.Graphic.View.GameMenus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TransitionScreen implements Screen {

    private final Game game;
    private final Screen fromScreen;
    private final Screen toScreen;
    private final float duration;
    private float time = 0f;
    private boolean finished = false;
    private boolean toScreenShown = false;

    private SpriteBatch batch;
    private Texture blackTexture;

    public TransitionScreen(Game game, Screen fromScreen, Screen toScreen, float duration) {
        this.game = game;
        this.fromScreen = fromScreen;
        this.toScreen = toScreen;
        this.duration = duration;

        batch = new SpriteBatch();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        blackTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render(float delta) {
        time += delta;

        float halfDuration = duration / 2f;
        float alpha;

        if (time < halfDuration) {
            fromScreen.render(delta);
            alpha = time / halfDuration;
        } else if (!finished) {
            if (!toScreenShown) {
                toScreen.show();
                toScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                toScreenShown = true;
            }
            toScreen.render(delta);
            alpha = 1f - ((time - halfDuration) / halfDuration);

            if (time >= duration) {
                finished = true;
                game.setScreen(toScreen);
            }
        } else {
            toScreen.render(delta);
            return;
        }

        // رسم لایه سیاه
        Gdx.gl.glEnable(GL20.GL_BLEND);
        batch.begin();
        batch.setColor(0, 0, 0, alpha);
        batch.draw(blackTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);
        batch.end();
    }

    @Override
    public void resize(int width, int height) { }
    @Override public void show() { }
    @Override public void hide() { }
    @Override public void pause() { }
    @Override public void resume() { }

    @Override
    public void dispose() {
        batch.dispose();
        blackTexture.dispose();
    }
}
