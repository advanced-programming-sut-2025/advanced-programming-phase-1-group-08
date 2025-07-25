package com.Graphic.View;

import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.HelpersClass.Result;
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

public class RegisterMenu implements Screen, AppMenu {
    private Stage stage;
    private Texture backgroundTexture;
    private RegisterController controller;

    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private TextField nicknameField;
    private TextField emailField;
    private SelectBox<String> genderBox;
    private SelectBox<String> securityQuestionBox;
    private TextField securityAnswerField;
    private TextField confirmAnswerField;

    private Label usernameStatus;
    private Label passwordStatus;
    private Label emailStatus;
    private Label messageLabel;

    private Window suggestionWindow;

    public RegisterMenu() {
        controller = new RegisterController();
    }

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
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        Label titleLabel = new Label("REGISTER", Main.getSkin(), "title");
        mainTable.add(titleLabel).padBottom(30).row();

        Table registerContainer = new Table();
        registerContainer.background(Main.getSkin().getDrawable("window"));
        registerContainer.pad(30);

        ScrollPane scrollPane = new ScrollPane(registerContainer, Main.getSkin());
        scrollPane.setFadeScrollBars(false);

        createFormFields(registerContainer);

        mainTable.add(scrollPane).width(500).height(600);
        stage.addActor(mainTable);

        setupValidationListeners();
    }

    private void createFormFields(Table container) {
        float fieldWidth = 300;
        float labelWidth = 120;

        container.add(new Label("Username:", Main.getSkin())).width(labelWidth).align(Align.left);
        usernameField = new TextField("", Main.getSkin());
        usernameField.setMessageText("4-10 chars, start with letter");
        container.add(usernameField).width(fieldWidth).padRight(10);
        usernameStatus = new Label("", Main.getSkin(), "dim");
        container.add(usernameStatus).width(100).row();
        container.add().padBottom(10).row();

        container.add(new Label("Password:", Main.getSkin())).width(labelWidth).align(Align.left);
        passwordField = new TextField("", Main.getSkin());
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Min 8 chars");
        container.add(passwordField).width(fieldWidth).padRight(10);
        TextButton randomPassButton = new TextButton("Random", Main.getSkin());
        container.add(randomPassButton).row();

        passwordStatus = new Label("", Main.getSkin(), "dim");
        container.add().padBottom(5);
        container.add(passwordStatus).colspan(2).align(Align.left).row();
        container.add().padBottom(10).row();

        container.add(new Label("Confirm:", Main.getSkin())).width(labelWidth).align(Align.left);
        confirmPasswordField = new TextField("", Main.getSkin());
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        confirmPasswordField.setMessageText("Re-enter password");
        container.add(confirmPasswordField).width(fieldWidth).colspan(2).row();
        container.add().padBottom(10).row();

        container.add(new Label("Nickname:", Main.getSkin())).width(labelWidth).align(Align.left);
        nicknameField = new TextField("", Main.getSkin());
        nicknameField.setMessageText("Display name");
        container.add(nicknameField).width(fieldWidth).colspan(2).row();
        container.add().padBottom(10).row();

        container.add(new Label("Email:", Main.getSkin())).width(labelWidth).align(Align.left);
        emailField = new TextField("", Main.getSkin());
        emailField.setMessageText("your@email.com");
        container.add(emailField).width(fieldWidth).padRight(10);
        emailStatus = new Label("", Main.getSkin(), "dim");
        container.add(emailStatus).row();
        container.add().padBottom(10).row();

        container.add(new Label("Gender:", Main.getSkin())).width(labelWidth).align(Align.left);
        genderBox = new SelectBox<>(Main.getSkin());
        genderBox.setItems("Select Gender", "Male", "Female");
        container.add(genderBox).width(fieldWidth).colspan(2).row();
        container.add().padBottom(20).row();

        container.add(new Label("Security Question:", Main.getSkin())).colspan(3).align(Align.left).padBottom(5).row();
        securityQuestionBox = new SelectBox<>(Main.getSkin());
        securityQuestionBox.setItems(
            "Select a question...",
            "What's My Favorite Animal?",
            "What's My Favorite Food?",
            "What's My Favorite Movie?",
            "What's My Favorite Book?",
            "What's My Favorite Game?"
        );
        container.add(securityQuestionBox).colspan(3).width(fieldWidth + 100).row();
        container.add().padBottom(10).row();

        container.add(new Label("Answer:", Main.getSkin())).width(labelWidth).align(Align.left);
        securityAnswerField = new TextField("", Main.getSkin());
        securityAnswerField.setMessageText("Your answer");
        container.add(securityAnswerField).width(fieldWidth).colspan(2).row();
        container.add().padBottom(10).row();

        container.add(new Label("Confirm Answer:", Main.getSkin())).width(labelWidth).align(Align.left);
        confirmAnswerField = new TextField("", Main.getSkin());
        confirmAnswerField.setMessageText("Re-enter answer");
        container.add(confirmAnswerField).width(fieldWidth).colspan(2).row();
        container.add().padBottom(20).row();

        Table buttonTable = new Table();
        TextButton registerButton = new TextButton("Register", Main.getSkin());
        TextButton loginButton = new TextButton("Go to Login", Main.getSkin());
        TextButton exitButton = new TextButton("Exit", Main.getSkin());

        buttonTable.add(registerButton).width(150).padRight(10);
        buttonTable.add(loginButton).width(150).padRight(10);
        buttonTable.add(exitButton).width(100);

        container.add(buttonTable).colspan(3).padBottom(10).row();

        messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true);
        container.add(messageLabel).colspan(3).width(400).row();

        setupButtonListeners(registerButton, loginButton, exitButton, randomPassButton);
    }

    private void setupValidationListeners() {
        usernameField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText().trim();
                if (username.length() > 0) {
                    try {
                        Result result = controller.validateUsername(username);
                        if (result.IsSuccess()) {
                            usernameStatus.setText("✓");
                            usernameStatus.setColor(0.3f, 1, 0.3f, 1);
                        } else {
                            usernameStatus.setText("✗");
                            usernameStatus.setColor(1, 0.3f, 0.3f, 1);
                        }
                    } catch (IOException e) {
                        usernameStatus.setText("!");
                    }
                } else {
                    usernameStatus.setText("");
                }
            }
        });

        passwordField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String password = passwordField.getText();
                if (password.length() > 0) {
                    String error = RegisterController.passIsStrong(password);
                    if (error == null) {
                        passwordStatus.setText("Strong password ✓");
                        passwordStatus.setColor(0.3f, 1, 0.3f, 1);
                    } else {
                        passwordStatus.setText(error);
                        passwordStatus.setColor(1, 0.3f, 0.3f, 1);
                    }
                } else {
                    passwordStatus.setText("");
                }
            }
        });

        emailField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String email = emailField.getText().trim();
                if (email.length() > 0) {
                    Result result = controller.validateEmail(email);
                    if (result.IsSuccess()) {
                        emailStatus.setText("✓");
                        emailStatus.setColor(0.3f, 1, 0.3f, 1);
                    } else {
                        emailStatus.setText("✗");
                        emailStatus.setColor(1, 0.3f, 0.3f, 1);
                    }
                } else {
                    emailStatus.setText("");
                }
            }
        });
    }

    private void setupButtonListeners(TextButton registerButton, TextButton loginButton,
                                      TextButton exitButton, TextButton randomPassButton) {

        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                attemptRegistration();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.LoginMenu;
                Main.getMain().setScreen(new LoginMenu());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        randomPassButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String randomPass = RegisterController.generateRandomPass();
                passwordField.setText(randomPass);
                confirmPasswordField.setText(randomPass);
                showMessage("Generated password: " + randomPass + "\nMake sure to save it!", false);
            }
        });
    }

    private void attemptRegistration() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String nickname = nicknameField.getText().trim();
        String email = emailField.getText().trim();
        String gender = genderBox.getSelected();
        int questionIndex = securityQuestionBox.getSelectedIndex();
        String answer = securityAnswerField.getText().trim();
        String confirmAnswer = confirmAnswerField.getText().trim();

        try {
            Result result = controller.validateUsername(username);
            if (!result.IsSuccess()) {
                if (result.toString().contains("already taken")) {
                    showUsernameSuggestion(username);
                    return;
                }
                showMessage(result.toString(), true);
                return;
            }

            result = controller.validatePassword(password, confirmPassword);
            if (!result.IsSuccess()) {
                showMessage(result.toString(), true);
                return;
            }

            result = controller.validateEmail(email);
            if (!result.IsSuccess()) {
                showMessage(result.toString(), true);
                return;
            }

            result = controller.validateNickname(nickname);
            if (!result.IsSuccess()) {
                showMessage(result.toString(), true);
                return;
            }

            if (gender.equals("Select Gender")) {
                showMessage("Please select your gender!", true);
                return;
            }

            if (questionIndex == 0) {
                showMessage("Please select a security question!", true);
                return;
            }

            if (!answer.equals(confirmAnswer)) {
                showMessage("Security answers don't match!", true);
                return;
            }

            if (answer.isEmpty()) {
                showMessage("Please provide a security answer!", true);
                return;
            }

            SecurityQuestions secQuestion = SecurityQuestions.values()[questionIndex - 1];
            result = controller.completeRegistration(username, password, nickname,
                email, gender.toLowerCase(),
                secQuestion, answer);

            if (result.IsSuccess()) {
                showMessage(result.toString(), false);
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        App.currentMenu = Menu.MainMenu;
                        Main.getMain().setScreen(new MainMenu());
                    }
                }, 2);
            } else {
                showMessage(result.toString(), true);
            }

        } catch (IOException e) {
            showMessage("Registration error: " + e.getMessage(), true);
        }
    }

    private void showUsernameSuggestion(String takenUsername) {
        suggestionWindow = new Window("Username Taken", Main.getSkin());
        suggestionWindow.setModal(true);
        suggestionWindow.setMovable(true);

        Table content = new Table();
        content.pad(20);

        Label messageLabel = new Label("Username '" + takenUsername + "' is already taken!", Main.getSkin());
        content.add(messageLabel).padBottom(10).row();

        try {
            String suggestion = RegisterController.SuggestUsername(takenUsername);
            Label suggestionLabel = new Label("Suggested: " + suggestion, Main.getSkin(), "bold");
            content.add(suggestionLabel).padBottom(20).row();

            Table buttonTable = new Table();
            TextButton useButton = new TextButton("Use Suggestion", Main.getSkin());
            TextButton cancelButton = new TextButton("Cancel", Main.getSkin());

            useButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    usernameField.setText(suggestion);
                    suggestionWindow.remove();
                }
            });

            cancelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    suggestionWindow.remove();
                }
            });

            buttonTable.add(useButton).width(150).padRight(10);
            buttonTable.add(cancelButton).width(100);
            content.add(buttonTable);

        } catch (IOException e) {
            content.add(new Label("Error generating suggestion", Main.getSkin()));
        }

        suggestionWindow.add(content);
        suggestionWindow.pack();
        suggestionWindow.setPosition(
            (stage.getWidth() - suggestionWindow.getWidth()) / 2,
            (stage.getHeight() - suggestionWindow.getHeight()) / 2
        );

        stage.addActor(suggestionWindow);
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

//    @Override
//    public void check(Scanner scanner) throws IOException {
//    }
    public Stage getStage() {
        return stage;
    }
}
