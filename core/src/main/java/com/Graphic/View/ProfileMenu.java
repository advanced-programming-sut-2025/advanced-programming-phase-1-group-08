package com.Graphic.View;

import com.Graphic.Controller.Menu.ProfileController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.App;
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
import java.util.Scanner;

public class ProfileMenu implements Screen, AppMenu {
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

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        setupBackground();
        setupUI();
        updateUserInfo();
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
        profileContainer.pad(30);

        Table infoSection = new Table();
        infoSection.add(new Label("User Information", Main.getSkin(), "bold")).colspan(2).padBottom(20).row();

        infoSection.add(new Label("Username: ", Main.getSkin())).align(Align.left).padRight(10);
        usernameLabel = new Label("", Main.getSkin());
        infoSection.add(usernameLabel).align(Align.left).row();

        infoSection.add(new Label("Nickname: ", Main.getSkin())).align(Align.left).padRight(10).padTop(10);
        nicknameLabel = new Label("", Main.getSkin());
        infoSection.add(nicknameLabel).align(Align.left).padTop(10).row();

        infoSection.add(new Label("Email: ", Main.getSkin())).align(Align.left).padRight(10).padTop(10);
        emailLabel = new Label("", Main.getSkin());
        infoSection.add(emailLabel).align(Align.left).padTop(10).row();

        infoSection.add(new Label("Max Gold: ", Main.getSkin())).align(Align.left).padRight(10).padTop(10);
        pointsLabel = new Label("", Main.getSkin(), "xp");
        infoSection.add(pointsLabel).align(Align.left).padTop(10).row();

        infoSection.add(new Label("Games Played: ", Main.getSkin())).align(Align.left).padRight(10).padTop(10);
        gamesLabel = new Label("", Main.getSkin());
        infoSection.add(gamesLabel).align(Align.left).padTop(10).row();

        profileContainer.add(infoSection).padBottom(30).row();

        profileContainer.add(new Image(Main.getSkin().getDrawable("white"))).fillX().height(2).padBottom(30).row();

        Table editSection = new Table();
        editSection.add(new Label("Edit Profile", Main.getSkin(), "bold")).colspan(3).padBottom(20).row();

        float fieldWidth = 200;
        float buttonWidth = 80;

        editSection.add(new Label("Username:", Main.getSkin())).align(Align.left).padRight(10);
        newUsernameField = new TextField("", Main.getSkin());
        newUsernameField.setMessageText("New username");
        editSection.add(newUsernameField).width(fieldWidth).padRight(10);
        editUsernameButton = new TextButton("Change", Main.getSkin());
        editSection.add(editUsernameButton).width(buttonWidth).row();
        editSection.add().padBottom(10).row();

        editSection.add(new Label("Nickname:", Main.getSkin())).align(Align.left).padRight(10);
        newNicknameField = new TextField("", Main.getSkin());
        newNicknameField.setMessageText("New nickname");
        editSection.add(newNicknameField).width(fieldWidth).padRight(10);
        editNicknameButton = new TextButton("Change", Main.getSkin());
        editSection.add(editNicknameButton).width(buttonWidth).row();
        editSection.add().padBottom(10).row();

        editSection.add(new Label("Email:", Main.getSkin())).align(Align.left).padRight(10);
        newEmailField = new TextField("", Main.getSkin());
        newEmailField.setMessageText("New email");
        editSection.add(newEmailField).width(fieldWidth).padRight(10);
        editEmailButton = new TextButton("Change", Main.getSkin());
        editSection.add(editEmailButton).width(buttonWidth).row();
        editSection.add().padBottom(20).row();

        editSection.add(new Label("Change Password", Main.getSkin(), "bold")).colspan(3).padBottom(10).row();

        editSection.add(new Label("Old Password:", Main.getSkin())).align(Align.left).padRight(10);
        oldPasswordField = new TextField("", Main.getSkin());
        oldPasswordField.setPasswordMode(true);
        oldPasswordField.setPasswordCharacter('*');
        oldPasswordField.setMessageText("Current password");
        editSection.add(oldPasswordField).width(fieldWidth).colspan(2).row();
        editSection.add().padBottom(5).row();

        editSection.add(new Label("New Password:", Main.getSkin())).align(Align.left).padRight(10);
        newPasswordField = new TextField("", Main.getSkin());
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        newPasswordField.setMessageText("New password");
        editSection.add(newPasswordField).width(fieldWidth).padRight(10);
        TextButton randomButton = new TextButton("Random", Main.getSkin());
        editSection.add(randomButton).width(buttonWidth).row();
        editSection.add().padBottom(10).row();

        changePasswordButton = new TextButton("Change Password", Main.getSkin());
        editSection.add(changePasswordButton).colspan(3).width(150).padBottom(20).row();

        profileContainer.add(editSection).row();

        messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true);
        profileContainer.add(messageLabel).width(400).padTop(10).row();

        TextButton backButton = new TextButton("Back to Main Menu", Main.getSkin());
        profileContainer.add(backButton).padTop(20).row();

        ScrollPane scrollPane = new ScrollPane(profileContainer, Main.getSkin());
        scrollPane.setFadeScrollBars(false);
        mainTable.add(scrollPane).width(600).height(650);

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

                try {
                    Result result = controller.changeUsername(newUsername);
                    showMessage(result.toString(), !result.IsSuccess());
                    if (result.IsSuccess()) {
                        updateUserInfo();
                        newUsernameField.setText("");
                    }
                } catch (IOException e) {
                    showMessage("Error: " + e.getMessage(), true);
                }
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

                try {
                    Result result = controller.changeNickname(newNickname);
                    showMessage(result.toString(), !result.IsSuccess());
                    if (result.IsSuccess()) {
                        updateUserInfo();
                        newNicknameField.setText("");
                    }
                } catch (IOException e) {
                    showMessage("Error: " + e.getMessage(), true);
                }
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

                try {
                    Result result = controller.changeEmail(newEmail);
                    showMessage(result.toString(), !result.IsSuccess());
                    if (result.IsSuccess()) {
                        updateUserInfo();
                        newEmailField.setText("");
                    }
                } catch (IOException e) {
                    showMessage("Error: " + e.getMessage(), true);
                }
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

                try {
                    Result result = controller.changePassword(newPassword, oldPassword);
                    showMessage(result.toString(), !result.IsSuccess());
                    if (result.IsSuccess()) {
                        oldPasswordField.setText("");
                        newPasswordField.setText("");
                    }
                } catch (IOException e) {
                    showMessage("Error: " + e.getMessage(), true);
                }
            }
        });

        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String randomPass = controller.generatePassword();
                newPasswordField.setText(randomPass);
                showMessage("Generated password: " + randomPass + "\nMake sure to save it!", false);
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.MainMenu;
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
                        messageLabel.setColor(1, 0.7f, 0, 1); // Orange for warning
                    } else {
                        messageLabel.setText("Password is strong ✓");
                        messageLabel.setColor(0.3f, 1, 0.3f, 1); // Green
                    }
                }
            }
        });
    }

    private void updateUserInfo() {
        usernameLabel.setText(controller.getCurrentUsername());
        nicknameLabel.setText(controller.getCurrentNickname());
        emailLabel.setText(controller.getCurrentEmail());
        pointsLabel.setText(String.valueOf(controller.getMaxPoints()));
        gamesLabel.setText(String.valueOf(controller.getGamesPlayed()));
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
    }

    @Override
    public void check(Scanner scanner) throws IOException {
    }
}
