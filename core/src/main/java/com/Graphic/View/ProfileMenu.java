package com.Graphic.View;

import com.Graphic.Controller.Menu.ProfileController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.HelpersClass.TextureManager;
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

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ProfileMenu implements Screen, AppMenu {
    private static ProfileMenu instance;
    private Stage stage;
    private Texture backgroundTexture;
    private ProfileController controller;

    private Label usernameLabel;
    private Label nicknameLabel;
    private Label emailLabel;
    private Label pointsLabel;
    private Label gamesLabel;
    private Label messageLabel;

    private TextField newUsernameField;
    private TextField newNicknameField;
    private TextField newEmailField;
    private TextField oldPasswordField;
    private TextField newPasswordField;

    private TextButton editUsernameButton;
    private TextButton editNicknameButton;
    private TextButton editEmailButton;
    private TextButton changePasswordButton;

    public ProfileMenu() {
        controller = new ProfileController();
    }

    public static ProfileMenu getInstance() {
        if (instance == null) {
            instance = new ProfileMenu();
        }
        return instance;
    }

    @Override
    public void show() {
        instance = this;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setupBackground();
        setupUI();
        requestUserInfo();
    }

    private void setupBackground() {
        backgroundTexture = TextureManager.get("Skin/back.png");
        TextureRegion textureRegion = new TextureRegion(backgroundTexture);
        textureRegion.setRegion(0, 0, 1628, 1090);
        Image backgroundImage = new Image(new TextureRegionDrawable(textureRegion));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);
    }

    private void setupUI() {
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label titleLabel = new Label("PROFILE", Main.getSkin(), "title");
        mainTable.add(titleLabel).padBottom(30).row();

        Table profileContainer = new Table();
        profileContainer.background(Main.getSkin().getDrawable("window"));
        profileContainer.pad(40);

        Table infoSection = new Table();
        infoSection.add(new Label("User Information", Main.getSkin(), "bold")).colspan(2).padBottom(20).row();

        infoSection.add(new Label("Username: ", Main.getSkin())).align(Align.left).padRight(20);
        usernameLabel = new Label("Loading...", Main.getSkin());
        infoSection.add(usernameLabel).align(Align.left).padRight(50).row();

        infoSection.add(new Label("Nickname: ", Main.getSkin())).align(Align.left).padRight(20).padTop(10);
        nicknameLabel = new Label("Loading...", Main.getSkin());
        infoSection.add(nicknameLabel).align(Align.left).padRight(50).padTop(10).row();

        infoSection.add(new Label("Email: ", Main.getSkin())).align(Align.left).padRight(20).padTop(10);
        emailLabel = new Label("Loading...", Main.getSkin());
        infoSection.add(emailLabel).align(Align.left).padRight(50).padTop(10).row();

        infoSection.add(new Label("Max Gold: ", Main.getSkin())).align(Align.left).padRight(20).padTop(10);
        pointsLabel = new Label("Loading...", Main.getSkin(), "xp");
        infoSection.add(pointsLabel).align(Align.left).padRight(50).padTop(10).row();

        infoSection.add(new Label("Games Played: ", Main.getSkin())).align(Align.left).padRight(20).padTop(10);
        gamesLabel = new Label("Loading...", Main.getSkin());
        infoSection.add(gamesLabel).align(Align.left).padRight(50).padTop(10).row();

        profileContainer.add(infoSection).padBottom(30).row();

        profileContainer.add(new Image(Main.getSkin().getDrawable("white"))).fillX().height(2).padBottom(30).row();

        Table editSection = new Table();
        editSection.add(new Label("Edit Profile", Main.getSkin(), "bold")).colspan(3).padBottom(20).row();

        float fieldWidth = 300;
        float buttonWidth = 120;

        editSection.add(new Label("Username:", Main.getSkin())).align(Align.left).padRight(20).width(150);
        newUsernameField = new TextField("", Main.getSkin());
        newUsernameField.setMessageText("New username");
        editSection.add(newUsernameField).width(fieldWidth).padRight(20);
        editUsernameButton = new TextButton("Change", Main.getSkin());
        editSection.add(editUsernameButton).width(250).row();
        editSection.add().padBottom(10).row();

        editSection.add(new Label("Nickname:", Main.getSkin())).align(Align.left).padRight(20).width(150);
        newNicknameField = new TextField("", Main.getSkin());
        newNicknameField.setMessageText("New nickname");
        editSection.add(newNicknameField).width(fieldWidth).padRight(20);
        editNicknameButton = new TextButton("Change", Main.getSkin());
        editSection.add(editNicknameButton).width(250).row();
        editSection.add().padBottom(10).row();

        editSection.add(new Label("Email:", Main.getSkin())).align(Align.left).padRight(20).width(150);
        newEmailField = new TextField("", Main.getSkin());
        newEmailField.setMessageText("New email");
        editSection.add(newEmailField).width(fieldWidth).padRight(20);
        editEmailButton = new TextButton("Change", Main.getSkin());
        editSection.add(editEmailButton).width(250).row();
        editSection.add().padBottom(20).row();

        editSection.add(new Label("Change Password", Main.getSkin(), "bold")).colspan(3).padBottom(10).row();

        editSection.add(new Label("Old Password:", Main.getSkin())).align(Align.left).padRight(20).width(150);
        oldPasswordField = new TextField("", Main.getSkin());
        oldPasswordField.setPasswordMode(true);
        oldPasswordField.setPasswordCharacter('*');
        oldPasswordField.setMessageText("Current password");
        editSection.add(oldPasswordField).width(fieldWidth).colspan(2).row();
        editSection.add().padBottom(5).row();

        editSection.add(new Label("New Password:", Main.getSkin())).align(Align.left).padRight(20).width(150);
        newPasswordField = new TextField("", Main.getSkin());
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setMessageText("New password");
        editSection.add(newPasswordField).width(fieldWidth).padRight(20);
        TextButton randomButton = new TextButton("Random", Main.getSkin());
        editSection.add(randomButton).width(250).row();
        editSection.add().padBottom(10).row();

        changePasswordButton = new TextButton("Change Password", Main.getSkin());
        editSection.add(changePasswordButton).colspan(3).width(400).padBottom(20).row();

        profileContainer.add(editSection).row();

        messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true);
        profileContainer.add(messageLabel).width(600).padTop(10).row();

        TextButton backButton = new TextButton("Back to Main Menu", Main.getSkin());
        profileContainer.add(backButton).padTop(20).row();

        ScrollPane scrollPane = new ScrollPane(profileContainer, Main.getSkin());
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        mainTable.add(scrollPane).width(900).height(700);

        stage.addActor(mainTable);

        setupListeners(backButton, randomButton);
    }

    private void setupListeners(TextButton backButton, TextButton randomButton) {
        editUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newUsername = newUsernameField.getText().trim();
                if (newUsername.isEmpty()) {
                    showMessage("Please enter a username!", true);
                    return;
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("newUsername", newUsername);
                Message message = new Message(CommandType.CHANGE_USERNAME, body);
                Main.getClient().getRequests().add(message);
                showMessage("Changing username...", false);
            }
        });

        editNicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newNickname = newNicknameField.getText().trim();
                if (newNickname.isEmpty()) {
                    showMessage("Please enter a nickname!", true);
                    return;
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("newNickname", newNickname);
                Message message = new Message(CommandType.CHANGE_NICKNAME, body);
                Main.getClient().getRequests().add(message);
                showMessage("Changing nickname...", false);
            }
        });

        editEmailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newEmail = newEmailField.getText().trim();
                if (newEmail.isEmpty()) {
                    showMessage("Please enter an email!", true);
                    return;
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("newEmail", newEmail);
                Message message = new Message(CommandType.CHANGE_EMAIL, body);
                Main.getClient().getRequests().add(message);
                showMessage("Changing email...", false);
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String oldPassword = oldPasswordField.getText();
                String newPassword = newPasswordField.getText();

                if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                    showMessage("Please fill both password fields!", true);
                    return;
                }

                HashMap<String, Object> body = new HashMap<>();
                body.put("oldPassword", oldPassword);
                body.put("newPassword", newPassword);
                Message message = new Message(CommandType.CHANGE_PASSWORD, body);
                Main.getClient().getRequests().add(message);
                showMessage("Changing password...", false);
            }
        });

        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HashMap<String, Object> body = new HashMap<>();
                Message message = new Message(CommandType.GENERATE_RANDOM_PASS, body);
                Main.getClient().getRequests().add(message);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.MainMenu;
                Main.getClient().setCurrentMenu(Menu.MainMenu);
                Main.getMain().setScreen(new MainMenu());
            }
        });

        newPasswordField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = newPasswordField.getText();
                if (password.length() > 0) {
                    String error = RegisterController.passIsStrong(password);
                    if (error != null) {
                        messageLabel.setText(error);
                        messageLabel.setColor(1, 0.7f, 0, 1);
                    } else {
                        messageLabel.setText("Password is strong ✓");
                        messageLabel.setColor(0.3f, 1, 0.3f, 1);
                    }
                }
            }
        });
    }

    private void requestUserInfo() {
        HashMap<String, Object> body = new HashMap<>();
        Message message = new Message(CommandType.GET_USER_INFO, body);
        Main.getClient().getRequests().add(message);
    }

    public void updateUserInfo(Message message) {
        usernameLabel.setText(message.getFromBody("username"));
        nicknameLabel.setText(message.getFromBody("nickname"));
        emailLabel.setText(message.getFromBody("email"));
        pointsLabel.setText(message.getFromBody("maxGold"));
        gamesLabel.setText(message.getFromBody("gamesPlayed"));
    }

    public void handleResponse(Message message) {
        switch (message.getCommandType()) {
            case USER_INFO:
                updateUserInfo(message);
                break;
            case SUCCESS:
                showMessage(message.getFromBody("message"), false);
                requestUserInfo(); // Refresh user info after successful change
                clearFields();
                break;
            case ERROR:
                showMessage(message.getFromBody("error"), true);
                break;
            case GENERATE_RANDOM_PASS:
                String randomPass = message.getFromBody("password");
                newPasswordField.setText(randomPass);
                showMessage("Generated password: " + randomPass + "\nMake sure to save it!", false);
                break;
        }
    }

    private void clearFields() {
        newUsernameField.setText("");
        newNicknameField.setText("");
        newEmailField.setText("");
        oldPasswordField.setText("");
        newPasswordField.setText("");
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

        // Check for menu changes
        if (Main.getClient().getCurrentMenu() != Menu.ProfileMenu) {
            Main.getMain().setScreen(Main.getClient().getCurrentMenu().getScreen());
        }
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
    }

    public Stage getStage() {
        return stage;
    }
}
