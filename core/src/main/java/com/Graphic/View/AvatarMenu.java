package com.Graphic.View;

import com.Graphic.Controller.Menu.AvatarController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Menu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.Align;

public class AvatarMenu implements Screen , AppMenu{
    private Stage stage;
    private Texture backgroundTexture;
    private AvatarController controller;

    private Texture[] avatarTextures;
    private int selectedAvatarIndex;
    private Image currentAvatarImage;
    private Label avatarNameLabel;
    private Label messageLabel;

    private Slider redSlider, greenSlider, blueSlider;
    private Label redLabel, greenLabel, blueLabel;
    private Image colorPreview;

    public AvatarMenu() {
        controller = new AvatarController();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        setupBackground();
        loadAvatarTextures();
        setupUI();

        selectedAvatarIndex = controller.getCurrentAvatarIndex();
        updateAvatarDisplay();
    }

    private void setupBackground() {
        backgroundTexture = new Texture(Gdx.files.internal("Skin/back.png"));
        TextureRegion textureRegion = new TextureRegion(backgroundTexture);
        textureRegion.setRegion(0, 0, 1628, 1090);
        Image backgroundImage = new Image(new TextureRegionDrawable(textureRegion));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
    }

    private void loadAvatarTextures() {
        avatarTextures = new Texture[6];
        for (int i = 0; i < 6; i++) {
            String path = String.format("avatars/avatar_%d.png", i + 1);
            if (Gdx.files.internal(path).exists()) {
                avatarTextures[i] = new Texture(Gdx.files.internal(path));
            } else {
                avatarTextures[i] = new Texture(Gdx.files.internal("Skin/craftacular-ui.png"));
            }
        }
    }

    private void setupUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label titleLabel = new Label("AVATAR CUSTOMIZATION", Main.getSkin(), "title");
        mainTable.add(titleLabel).padBottom(40).colspan(2).row();

        Table avatarSection = new Table();
        avatarSection.background(Main.getSkin().getDrawable("window"));
        avatarSection.pad(120);

        currentAvatarImage = new Image(avatarTextures[selectedAvatarIndex]);
        currentAvatarImage.setSize(300, 300);
        avatarSection.add(currentAvatarImage).size(150, 150).padBottom(10).row();

        avatarNameLabel = new Label("Avatar " + (selectedAvatarIndex + 1), Main.getSkin());
        avatarSection.add(avatarNameLabel).padBottom(20).row();

        Table navTable = new Table();
        TextButton prevButton = new TextButton("<", Main.getSkin());
        TextButton nextButton = new TextButton(">", Main.getSkin());

        prevButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatarIndex = (selectedAvatarIndex - 1 + avatarTextures.length) % avatarTextures.length;
                updateAvatarDisplay();
            }
        });

        nextButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedAvatarIndex = (selectedAvatarIndex + 1) % avatarTextures.length;
                updateAvatarDisplay();
            }
        });

        navTable.add(prevButton).width(50).padRight(20);
        navTable.add(nextButton).width(50);
        avatarSection.add(navTable).padBottom(20).row();

        Label galleryLabel = new Label("Available Avatars:", Main.getSkin());
        avatarSection.add(galleryLabel).padBottom(10).row();

        Table galleryTable = new Table();
        for (int i = 0; i < avatarTextures.length; i++) {
            final int index = i;
            ImageButton avatarButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(avatarTextures[i])));
            avatarButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedAvatarIndex = index;
                    updateAvatarDisplay();
                }
            });

            galleryTable.add(avatarButton).size(60, 60).pad(5);
            if ((i + 1) % 3 == 0) galleryTable.row();
        }
        avatarSection.add(galleryTable).row();

        mainTable.add(avatarSection).padRight(30);

        Table colorSection = new Table();
        colorSection.background(Main.getSkin().getDrawable("window"));
        colorSection.pad(80);

        Label colorLabel = new Label("Color Customization", Main.getSkin(), "bold");
        colorSection.add(colorLabel).colspan(3).padBottom(20).row();

        colorPreview = new Image(Main.getSkin().getDrawable("white"));
        colorPreview.setSize(300, 300);
        colorSection.add(colorPreview).size(100, 100).colspan(3).padBottom(20).row();

        float[] currentColor = controller.getCurrentColor();

        colorSection.add(new Label("Red:", Main.getSkin())).width(75);
        redSlider = new Slider(0, 100, 1, false, Main.getSkin());
        redSlider.setValue(currentColor[0] * 100);
        colorSection.add(redSlider).width(200).padRight(10);
        redLabel = new Label(String.valueOf((int)redSlider.getValue()), Main.getSkin());
        colorSection.add(redLabel).width(40).row();

        colorSection.add(new Label("Green:", Main.getSkin())).width(75);
        greenSlider = new Slider(0, 100, 1, false, Main.getSkin());
        greenSlider.setValue(currentColor[1] * 100);
        colorSection.add(greenSlider).width(200).padRight(10);
        greenLabel = new Label(String.valueOf((int)greenSlider.getValue()), Main.getSkin());
        colorSection.add(greenLabel).width(40).row();

        colorSection.add(new Label("Blue:", Main.getSkin())).width(75);
        blueSlider = new Slider(0, 100, 1, false, Main.getSkin());
        blueSlider.setValue(currentColor[2] * 100);
        colorSection.add(blueSlider).width(200).padRight(10);
        blueLabel = new Label(String.valueOf((int)blueSlider.getValue()), Main.getSkin());
        colorSection.add(blueLabel).width(40).row();

        ChangeListener colorChangeListener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateColorPreview();
            }
        };

        redSlider.addListener(colorChangeListener);
        greenSlider.addListener(colorChangeListener);
        blueSlider.addListener(colorChangeListener);

        colorSection.add(new Label("Presets:", Main.getSkin())).colspan(3).padTop(20).padBottom(10).row();

        Table presetTable = new Table();
        String[] presetNames = {"Default", "Red", "Green", "Blue", "Gold", "Purple"};
        float[][] presetColors = {
            {1, 1, 1},      // Default (white)
            {1, 0.2f, 0.2f}, // Red
            {0.2f, 1, 0.2f}, // Green
            {0.2f, 0.2f, 1}, // Blue
            {1, 0.8f, 0},    // Gold
            {0.8f, 0.2f, 1}  // Purple
        };

        for (int i = 0; i < presetNames.length; i++) {
            final int index = i;
            TextButton presetButton = new TextButton(presetNames[i], Main.getSkin());
            presetButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    redSlider.setValue(presetColors[index][0] * 255);
                    greenSlider.setValue(presetColors[index][1] * 255);
                    blueSlider.setValue(presetColors[index][2] * 255);
                    updateColorPreview();
                }
            });
            presetTable.add(presetButton).width(150).pad(2);
            if ((i + 1) % 3 == 0) presetTable.row();
        }

        colorSection.add(presetTable).colspan(3).row();

        mainTable.add(colorSection).row();

        Table buttonTable = new Table();
        TextButton applyButton = new TextButton("Apply Changes", Main.getSkin());
        TextButton resetButton = new TextButton("Reset", Main.getSkin());
        TextButton backButton = new TextButton("Back", Main.getSkin());

        applyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveChanges();
            }
        });

        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetToDefaults();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.MainMenu;
                Main.getMain().setScreen(new MainMenu());
            }
        });

        buttonTable.add(applyButton).width(400).padRight(10);
        buttonTable.add(resetButton).width(250).padRight(10);
        buttonTable.add(backButton).width(250);

        mainTable.add(buttonTable).colspan(2).padTop(30).row();

        messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        mainTable.add(messageLabel).colspan(2).padTop(10);

        stage.addActor(mainTable);

        updateColorPreview();
    }

    private void updateAvatarDisplay() {
        currentAvatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(avatarTextures[selectedAvatarIndex])));
        avatarNameLabel.setText("Avatar " + (selectedAvatarIndex + 1));
    }

    private void updateColorPreview() {
        float r = redSlider.getValue() / 255f;
        float g = greenSlider.getValue() / 255f;
        float b = blueSlider.getValue() / 255f;

        colorPreview.setColor(r, g, b, 1);

        redLabel.setText(String.valueOf((int)redSlider.getValue()));
        greenLabel.setText(String.valueOf((int)greenSlider.getValue()));
        blueLabel.setText(String.valueOf((int)blueSlider.getValue()));
    }

    private void saveChanges() {
        float r = redSlider.getValue() / 255f;
        float g = greenSlider.getValue() / 255f;
        float b = blueSlider.getValue() / 255f;

        boolean success = controller.saveAvatarSettings(selectedAvatarIndex, r, g, b);

        if (success) {
            showMessage("Avatar settings saved successfully!", false);
        } else {
            showMessage("Failed to save avatar settings!", true);
        }
    }

    private void resetToDefaults() {
        selectedAvatarIndex = 0;
        redSlider.setValue(255);
        greenSlider.setValue(255);
        blueSlider.setValue(255);

        updateAvatarDisplay();
        updateColorPreview();

        showMessage("Reset to default settings", false);
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setColor(1, 0.3f, 0.3f, 1);
        } else {
            messageLabel.setColor(0.3f, 1, 0.3f, 1);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        for (Texture texture : avatarTextures) {
            if (texture != null) {
                texture.dispose();
            }
        }
    }

    @Override
    public Stage getStage() {
        return null;
    }
}
