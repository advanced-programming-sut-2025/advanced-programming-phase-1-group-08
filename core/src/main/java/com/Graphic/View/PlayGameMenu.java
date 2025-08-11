package com.Graphic.View;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.model.App;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Game;
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

import java.io.IOException;
import java.util.HashMap;

import static com.badlogic.gdx.Input.Keys.ENTER;

public class PlayGameMenu implements Screen, AppMenu {
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
            Label titleLabel = new Label("PLAY GAME", Main.getSkin(), "title");
            mainTable.add(titleLabel).padBottom(80).row();
        }

        Table menuContainer = new Table();
        menuContainer.background(Main.getSkin().getDrawable("window"));
        menuContainer.pad(80);

        TextButton profileMenu = new TextButton("Profile", Main.getSkin());
        TextButton newGameButton = new TextButton("New Game", Main.getSkin());
        TextButton loadGameButton = new TextButton("Load Game", Main.getSkin());
        TextButton joinGameButton = new TextButton("Join Game", Main.getSkin());
        TextButton backButton = new TextButton("Back", Main.getSkin());

        float buttonWidth = 300;
        float buttonHeight = 60;
        float buttonPadding = 15;

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    newGame();
                } catch (Exception e) {
                    System.err.println("Error starting new game: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        profileMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Main.getMain().setScreen(new ProfileMenu());
                    System.out.println("Starting new game...");
                } catch (Exception e) {
                    System.err.println("Error starting new game: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLoadGameDialog();
            }
        });

        joinGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    joinGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenu());
            }
        });

        menuContainer.add(profileMenu).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(newGameButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(loadGameButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(joinGameButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(backButton).width(buttonWidth).height(buttonHeight).row();

        mainTable.add(menuContainer);
        stage.addActor(mainTable);
    }


    private void showLoadGameDialog() {
        Window loadWindow = new Window("Load Game", Main.getSkin());
        loadWindow.setMovable(true);
        loadWindow.setModal(true);

        TextButton closeButton = new TextButton("X", Main.getSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadWindow.remove();
            }
        });
        loadWindow.getTitleTable().add(closeButton).padRight(0);

        Table contentTable = new Table();
        contentTable.pad(20);

        ScrollPane scrollPane = new ScrollPane(null, Main.getSkin());
        Table saveList = new Table();

        for (int i = 1; i <= 3; i++) {
            TextButton saveSlot = new TextButton("Save Slot " + i + " - Empty", Main.getSkin());
            final int slotNumber = i;
            saveSlot.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Loading save slot " + slotNumber);
                    loadWindow.remove();
                    try {
                        Main.getMain().setScreen(GameMenu.getInstance());
                    } catch (Exception e) {
                        System.err.println("Error loading game: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            saveList.add(saveSlot).width(400).height(50).padBottom(10).row();
        }

        scrollPane.setWidget(saveList);
        contentTable.add(scrollPane).width(450).height(300);

        loadWindow.add(contentTable);
        loadWindow.setSize(500, 400);
        loadWindow.setPosition(
            (stage.getWidth() - loadWindow.getWidth()) / 2,
            (stage.getHeight() - loadWindow.getHeight()) / 2
        );

        stage.addActor(loadWindow);
    }

    private void showJoinGameDialog() {
        Window joinWindow = new Window("Join Game", Main.getSkin());
        joinWindow.setMovable(true);
        joinWindow.setModal(true);

        TextButton closeButton = new TextButton("X", Main.getSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinWindow.remove();
            }
        });
        joinWindow.getTitleTable().add(closeButton).padRight(0);

        Table contentTable = new Table();
        contentTable.pad(20);

        Label ipLabel = new Label("Server IP:", Main.getSkin());
        TextField ipField = new TextField("", Main.getSkin());
        ipField.setMessageText("Enter server IP...");

        Label portLabel = new Label("Port:", Main.getSkin());
        TextField portField = new TextField("", Main.getSkin());
        portField.setMessageText("Enter port...");

        TextButton joinButton = new TextButton("Join", Main.getSkin());
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String ip = ipField.getText();
                String port = portField.getText();
                System.out.println("Joining server: " + ip + ":" + port);
                joinWindow.remove();
                try {
                    Main.getMain().setScreen(GameMenu.getInstance());
                } catch (Exception e) {
                    System.err.println("Error joining game: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        contentTable.add(ipLabel).padBottom(5).row();
        contentTable.add(ipField).width(300).height(40).padBottom(15).row();
        contentTable.add(portLabel).padBottom(5).row();
        contentTable.add(portField).width(300).height(40).padBottom(20).row();
        contentTable.add(joinButton).width(150).height(50);

        joinWindow.add(contentTable);
        joinWindow.setSize(400, 350);
        joinWindow.setPosition(
            (stage.getWidth() - joinWindow.getWidth()) / 2,
            (stage.getHeight() - joinWindow.getHeight()) / 2
        );

        stage.addActor(joinWindow);
    }

    private void newGame() {
        TextField field = new TextField("", Main.getSkin());
        field.setPosition(100 , 200);
        TextButton button = new TextButton("", Main.getSkin());
        button.setPosition(100 , 100);
        field.setMessageText("Enter ID...");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                HashMap<String, Object> body = new HashMap<>();
                body.put("id", field.getText());
                body.put("Player", Main.getClient().getPlayer());
                Main.getClient().getRequests().add(new Message(CommandType.NEW_GAME, body));
                field.remove();
            }
        });
        stage.addActor(field);
        stage.addActor(button);

    }
    private void joinGame()  {
        TextField field = new TextField("", Main.getSkin());
        field.setPosition(100 , 200);
        TextButton button = new TextButton("", Main.getSkin());
        button.setPosition(100 , 100);
        field.setMessageText("Enter ID...");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                HashMap<String ,Object> body = new HashMap<>();
                body.put("id", field.getText());
                body.put("Player" , Main.getClient().getPlayer());
                Main.getClient().getRequests().add(new Message(CommandType.JOIN_GAME, body));
                field.remove();
            }
        });
        stage.addActor(field);
        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (Main.getClient().getCurrentMenu() != Menu.PlayGameMenu) {
            Main.getMain().setScreen(GameMenu.getInstance());
        }
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

    @Override
    public Stage getStage() {
        return stage;
    }
}
