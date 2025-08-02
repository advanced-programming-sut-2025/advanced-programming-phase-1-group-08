package com.Graphic.View;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.Align;

import java.io.IOException;
import java.util.Scanner;

public class MainMenu implements Screen, AppMenu {
    private Stage stage;
    private Texture backgroundTexture;
    private Table mainTable;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setupBackground();
        setupUI();
    }

    private void setupBackground() {
        backgroundTexture = new Texture(Gdx.files.internal("Skin/back.png"));
        TextureRegion textureRegion = new TextureRegion(backgroundTexture);
        textureRegion.setRegion(0, 0, 1628, 1090);
        Image backgroundImage = new Image(new TextureRegionDrawable(textureRegion));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
    }

    private void setupUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        if (Gdx.files.internal("craftacular-mockup.png").exists()) {
            Image logo = new Image(new Texture(Gdx.files.internal("craftacular-mockup.png")));
            mainTable.add(logo).size(325, 325).padBottom(50).row();
        } else {
            Label titleLabel = new Label("STARDEW VALLEY", Main.getSkin(), "title");
            mainTable.add(titleLabel).padBottom(80).row();
        }

        Table menuContainer = new Table();
        menuContainer.background(Main.getSkin().getDrawable("window"));
        menuContainer.pad(80);

        TextButton playButton = new TextButton("Play Game", Main.getSkin());
        TextButton craftingButton = new TextButton("Crafting Guide", Main.getSkin());
        TextButton marketButton = new TextButton("Market", Main.getSkin());
        TextButton profileButton = new TextButton("Profile", Main.getSkin());
        TextButton avatarButton = new TextButton("Avatar", Main.getSkin());
        TextButton exitButton = new TextButton("Exit", Main.getSkin());

        float buttonWidth = 300;
        float buttonHeight = 60;
        float buttonPadding = 15;

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new ProfileMenu());
            }
        });

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(GameMenu.getInstance());
            }
        });

        craftingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showCraftingGuide();
            }
        });

        marketButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Opening market...");
            }
        });

        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new AvatarMenu());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menuContainer.add(playButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(craftingButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(marketButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(profileButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(avatarButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(exitButton).width(buttonWidth).height(buttonHeight).row();

        mainTable.add(menuContainer);
        stage.addActor(mainTable);
    }

    private void showCraftingGuide() {
        Window craftingWindow = new Window("Crafting Guide", Main.getSkin());
        craftingWindow.setMovable(true);
        craftingWindow.setModal(true);

        TextButton closeButton = new TextButton("X", Main.getSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                craftingWindow.remove();
            }
        });
        craftingWindow.getTitleTable().add(closeButton).padRight(0);

        ScrollPane scrollPane = new ScrollPane(null, Main.getSkin());
        Table contentTable = new Table();
        contentTable.add(new Label("Crafting recipes will be shown here", Main.getSkin())).pad(20);
        scrollPane.setWidget(contentTable);

        craftingWindow.add(scrollPane).width(600).height(400).pad(20);
        craftingWindow.setSize(650, 500);
        craftingWindow.setPosition(
            (stage.getWidth() - craftingWindow.getWidth()) / 2,
            (stage.getHeight() - craftingWindow.getHeight()) / 2
        );

        stage.addActor(craftingWindow);
    }

    private void showProfileWindow() {
        Window profileWindow = new Window("Profile", Main.getSkin());
        profileWindow.setMovable(true);
        profileWindow.setModal(true);

        TextButton closeButton = new TextButton("X", Main.getSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                profileWindow.remove();
            }
        });
        profileWindow.getTitleTable().add(closeButton).padRight(0);

        Table contentTable = new Table();
        contentTable.pad(20);
        contentTable.add(new Label("Username: Player", Main.getSkin())).align(Align.left).row();
        contentTable.add(new Label("Level: 1", Main.getSkin())).align(Align.left).padTop(10).row();
        contentTable.add(new Label("Gold: 500", Main.getSkin())).align(Align.left).padTop(10).row();
        contentTable.add(new Label("Games Played: 0", Main.getSkin())).align(Align.left).padTop(10).row();

        profileWindow.add(contentTable).expand().fill();
        profileWindow.setSize(400, 300);
        profileWindow.setPosition(
            (stage.getWidth() - profileWindow.getWidth()) / 2,
            (stage.getHeight() - profileWindow.getHeight()) / 2
        );

        stage.addActor(profileWindow);
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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
    }

//    @Override
//    public void check(Scanner scanner) throws IOException {
//
//    }

    @Override
    public Stage getStage() {return stage;}
}
