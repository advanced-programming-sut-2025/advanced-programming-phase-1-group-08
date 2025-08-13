package com.Graphic.View;

import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Controller.Menu.LobbyController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Game;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import java.awt.*;
import java.util.ArrayList;


public class LobbyMenu implements Screen , AppMenu {

    private LobbyController controller;
    private static LobbyMenu lobbyMenu;
    private Window createLobbyWindow;
    private Window activeLobbies;
    private Window lobbyInformation;
    private Stage stage;
    private Skin skin;
    private TextField NameField;
    private TextField IdField;
    private TextField PasswordField;
    private TextField PassForJoin;
    private CheckBox privateCheck;
    private CheckBox publicCheck;
    private CheckBox visible;
    private CheckBox invisible;
    private ButtonGroup<CheckBox> privateBoxGroup;
    private ButtonGroup<CheckBox> visibleBoxGroup;
    private TextButton create;
    private TextButton back;
    private Label passLabel;
    //private ArrayList<Game> activeGames = new ArrayList<>();
    private int height = 48;


    public void createLobby() {
        createLobbyWindow = new Window("Lobby" , skin , "default");
        createLobbyWindow.setSize((float) Gdx.graphics.getWidth() /3, Gdx.graphics.getHeight() - 2 * height);
        createLobbyWindow.setPosition(0,2 * height);
        Table mainTable = new Table();
        setUpTableInCreateLobby(mainTable);
        createLobbyWindow.add(mainTable).expand().fill();
        stage.addActor(createLobbyWindow);
    }

    public void setUpTableInCreateLobby(Table mainTable) {
        mainTable.defaults().pad(30);
        Label nameLabel = new Label("Name" , skin);
        mainTable.add(nameLabel).height(height);
        NameField = new com.badlogic.gdx.scenes.scene2d.ui.TextField("" , skin);
        NameField.setMessageText("Enter a name for lobby");
        mainTable.add(NameField).height(height).expandX().row();

        privateCheck = new CheckBox("Private", skin);
        publicCheck = new CheckBox("Public", skin);
        privateBoxGroup = new ButtonGroup<>(privateCheck, publicCheck);
        privateBoxGroup.setMaxCheckCount(1);
        privateBoxGroup.setMinCheckCount(1);
        publicCheck.setChecked(true);
        mainTable.add(privateCheck).height(height);
        mainTable.add(publicCheck).height(height).row();

        passLabel = new Label("Password" , skin);
        mainTable.add(passLabel).height(height);
        PasswordField = new TextField("" , skin);
        PasswordField.setMessageText("Enter a password for lobby");
        mainTable.add(PasswordField).height(height).expandX().row();
        passLabel.setVisible(false);
        PasswordField.setVisible(false);

        visible = new CheckBox("Visible", skin);
        invisible = new CheckBox("Invisible", skin);
        visibleBoxGroup = new ButtonGroup<>(visible , invisible);
        visibleBoxGroup.setMaxCheckCount(1);
        visibleBoxGroup.setMinCheckCount(1);
        visible.setChecked(true);
        mainTable.add(visible).height(height);
        mainTable.add(invisible).height(height).row();

        Label createLobbyLabel = new Label("Create Lobby", skin);
        create = new TextButton("", skin);
        create.add(createLobbyLabel).center();
        mainTable.add(create).height(height).row();

        listenerForPrivateCheck();
        listenerForPublicCheck();
        listenerForCreateButton();


    }

    public void listenerForPrivateCheck() {
        privateCheck.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                passLabel.setVisible(true);
                PasswordField.setVisible(true);
            }
        });
        publicCheck.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                passLabel.setVisible(false);
                PasswordField.setVisible(false);
            }
        });
    }
    public void listenerForPublicCheck() {
        publicCheck.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                passLabel.setVisible(false);
                PasswordField.setVisible(false);
            }
        });
    }

    public void listenerForCreateButton() {
        create.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (NameField.getText() == null) {
                    Dialog dialog = Marketing.getInstance().createDialogError();
                    Label error = new Label("Please enter a name for lobby", skin);
                    Marketing.getInstance().addDialogToTable(dialog , error , LobbyMenu.this);
                    dialog.setPosition(100 , 100);
                    dialog.setSize(500 , 48);
                }
                else if (privateCheck.isChecked()) {
                    if (PasswordField.getText() == null) {
                        Dialog dialog = Marketing.getInstance().createDialogError();
                        Label error = new Label("Please enter a password for lobby", skin);
                        Marketing.getInstance().addDialogToTable(dialog , error , LobbyMenu.getInstance());
                        dialog.setPosition(100 , 100);
                        dialog.setSize(500 , 48);
                    }
                    else {
                        controller.requestForCreateGame(NameField.getText() , PasswordField.getText() , visible.isChecked());
                    }
                }
                else {
                    controller.requestForCreateGame(NameField.getText() , PasswordField.getText() , visible.isChecked());
                }
            }
        });
    }

    public void showActiveGames(ArrayList<Game> games) {
        activeLobbies = new Window("Active Games" , skin , "default");
        activeLobbies.setSize((float) Gdx.graphics.getWidth() /3 - 40, Gdx.graphics.getHeight() - 2 * height - 40);
        activeLobbies.setPosition((float) Gdx.graphics.getWidth() /3 , 2 * height + 40);
        Table mainTable = new Table();
        ScrollPane pane = new ScrollPane(mainTable , skin);
        pane.setFadeScrollBars(false);
        setUpActiveGames(mainTable  , pane , games);
        stage.addActor(activeLobbies);
    }

    public void setUpActiveGames(Table mainTable , ScrollPane pane , ArrayList<Game> games) {
        mainTable.top().padTop(20);
        for (Game game : games) {
            TextButton gameButton = new TextButton(game.getName() , skin);
            mainTable.add(gameButton).size((float) Gdx.graphics.getWidth() / 3 , height);
            mainTable.row();
            listenerForActiveGames(gameButton , game);
        }
        pane.setWidget(mainTable);

        activeLobbies.add(pane).width((float) Gdx.graphics.getWidth() / 3 ).height(Gdx.graphics.getHeight() - 2 * height);
        activeLobbies.pack();
        activeLobbies.setSize((float) Gdx.graphics.getWidth() /3 - 10, Gdx.graphics.getHeight() - 2 * height - 10);
        activeLobbies.setPosition((float) Gdx.graphics.getWidth() /3 , 2 * height + 10);

    }

    public void listenerForActiveGames(TextButton button , Game game) {
        button.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                showGameInfo(game);
            }
        });
    }

    public void showGameInfo(Game game) {
        System.out.println(game.isPrivate());
        lobbyInformation = new Window("Lobby Information", skin , "default");
        Table infoTable = new Table();
        lobbyInformation.setPosition(((float) (2 * Gdx.graphics.getWidth()) / 3 ), 2 * height);
        lobbyInformation.setSize((float) Gdx.graphics.getWidth() /3, Gdx.graphics.getHeight() - 2 * height);

        lobbyInformation.add(infoTable).center().expand().fill();
        infoTable.defaults().size(100);
        infoTable.top().padTop(20);

        Label name = new Label("Name: " + game.getName(), skin);
        name.setColor(Color.RED);
        name.setAlignment(Align.center);
        infoTable.add(name).expandX().fillX().row();

        Label number = new Label("Number: " + game.getPlayers().size(), skin);
        number.setColor(Color.RED);
        number.setAlignment(Align.center);
        infoTable.add(number).expandX().fillX().row();

        Label players = new Label("Players: " + showUsernamesOfPlayers(game), skin);
        players.setColor(Color.RED);
        players.setAlignment(Align.center);
        infoTable.add(players).expandX().fillX().row();

        if (controller.playerIsInGame(game)) {
            TextButton leave = new TextButton("Leave", skin);
            infoTable.add(leave).center().width((float) Gdx.graphics.getWidth() / 3 - 40).height(2 * height).padBottom(100).row();
            leave.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.requestFoLeaveLobby(game);
                }
            });
        }

        else {
            TextButton join = new TextButton("Join", skin);
            infoTable.add(join).center().width((float) Gdx.graphics.getWidth() / 3 - 40).height(2 * height).row();
            if (game.isPrivate()) {
                infoTable.add(PassForJoin).center().width((float) Gdx.graphics.getWidth() / 3 - 40).height(2 * height).row();
            }

            join.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.requestForJoinLobby(game , PassForJoin.getText());
                }
            });
        }

        if (game.getCreator().getUsername().equals(Main.getClient().getPlayer().getUsername())) {
            TextButton start = new TextButton("Start", skin);
            infoTable.add(start).center().width((float) Gdx.graphics.getWidth() / 3 - 40).height(2 * height).row();
            start.addListener(new ChangeListener() {

                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    controller.requestForStartLobby(game);
                }
            });
        }


        stage.addActor(lobbyInformation);
    }

    private String showUsernamesOfPlayers(Game game) {
        StringBuilder sb = new StringBuilder();
        for (User user : game.getPlayers()) {
            sb.append(user.getUsername()).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public void createBackButton() {
        TextButton backButton = new TextButton("Back", skin);
        backButton.setPosition(0 , 0);
        backButton.setSize((float) Gdx.graphics.getWidth() / 3 , 2 * height);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Main.getClient().setCurrentMenu(Menu.PlayGameMenu);
            }
        });
        stage.addActor(backButton);
    }

    public void createRefreshButton() {
        TextButton refreshButton = new TextButton("Refresh", skin);
        refreshButton.setPosition((float) Gdx.graphics.getWidth() / 3 , 0);
        refreshButton.setSize((float) Gdx.graphics.getWidth() / 3 , 2 * height);

        refreshButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                controller.Refresh();
            }
        });
        stage.addActor(refreshButton);
    }

    public void createSearchButton() {
        TextButton searchButton = new TextButton("Search", skin);
        searchButton.setPosition((float) 2 *  Gdx.graphics.getWidth() / 3 , 0);
        searchButton.setSize((float) Gdx.graphics.getWidth() / 6 , 2 * height);
        searchButton.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if (IdField.getText() != null) {
                    controller.requestForFindInvisibleLobby(IdField.getText());
                }
            }
        });
        stage.addActor(searchButton);
    }

    public void createIdField() {
        IdField = new TextField("", skin);
        IdField.setMessageText("Enter ID");
        IdField.setSize((float) Gdx.graphics.getWidth() / 6 , 2 * height);
        IdField.setPosition((float) 2 *  Gdx.graphics.getWidth() / 3 + (float) Gdx.graphics.getWidth() / 6 , 0);
        stage.addActor(IdField);
    }

    public void createPassFieldForJoin(Table table) {

    }

    public static LobbyMenu getInstance() {
        if (lobbyMenu == null) {
            lobbyMenu = new LobbyMenu();
        }
        return lobbyMenu;
    }





    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
        controller = new LobbyController();
        PassForJoin = new TextField("Enter Pass", skin);
        createBackButton();
        createRefreshButton();
        //activeGames = Main.getClient().getPlayer().getGamesActiveInLobby();
        createLobby();
        createIdField();
        createSearchButton();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(v);
        stage.draw();

        if (Main.getClient().getCurrentMenu() != Menu.LabbyMenu) {
            Main.getMain().setScreen(Main.getClient().getCurrentMenu().getScreen());
        }
    }

    @Override
    public void resize(int i, int i1) {

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

    }

    @Override
    public Stage getStage() {
        return stage;
    }
}
