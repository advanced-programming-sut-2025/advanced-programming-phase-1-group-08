package com.Graphic.View;

import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.SaveData.SessionManager;
import com.Graphic.model.SaveData.UserDataBase;
import com.Graphic.model.User;
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
import com.badlogic.gdx.utils.Timer;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LoginMenu implements Screen, AppMenu {
    private Stage stage;
    private Texture backgroundTexture;
    private LoginController controller;
    private TextField usernameField;
    private TextField passwordField;
    private CheckBox stayLoggedInCheckbox;
    private Label messageLabel;

    private Window forgotPasswordWindow;
    private TextField forgotUsernameField;

    public LoginMenu() {
        controller = new LoginController();
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

        Label titleLabel = new Label("LOGIN", Main.getSkin(), "title");
        mainTable.add(titleLabel).padBottom(40).row();

        Table loginContainer = new Table();
        loginContainer.background(Main.getSkin().getDrawable("window"));
        loginContainer.pad(40);

        Label usernameLabel = new Label("Username:", Main.getSkin());
        usernameField = new TextField("", Main.getSkin());
        usernameField.setMessageText("Enter username");

        Label passwordLabel = new Label("Password:", Main.getSkin());
        passwordField = new TextField("", Main.getSkin());
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Enter password");


        TextButton loginButton = new TextButton("Login", Main.getSkin());
        TextButton forgotPasswordButton = new TextButton("Forgot Password?", Main.getSkin());
        TextButton signUpButton = new TextButton("Create New Account", Main.getSkin());

        messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);

        float fieldWidth = 300;
        float buttonWidth = 200;

        loginContainer.add(usernameLabel).align(Align.left).padBottom(5).row();
        loginContainer.add(usernameField).width(fieldWidth).padBottom(15).row();

        loginContainer.add(passwordLabel).align(Align.left).padBottom(5).row();
        loginContainer.add(passwordField).width(fieldWidth).padBottom(15).row();

        loginContainer.add(stayLoggedInCheckbox).align(Align.left).padBottom(20).row();

        loginContainer.add(loginButton).width(buttonWidth).padBottom(10).row();
        loginContainer.add(forgotPasswordButton).width(buttonWidth).padBottom(10).row();
        loginContainer.add(signUpButton).width(buttonWidth).padBottom(20).row();

        loginContainer.add(messageLabel).padTop(10).row();

        mainTable.add(loginContainer);
        stage.addActor(mainTable);

        setupListeners(loginButton, forgotPasswordButton, signUpButton);

        TextButton exitButton = new TextButton("Exit", Main.getSkin());
        exitButton.setPosition(stage.getWidth() - exitButton.getWidth() - 10,
            stage.getHeight() - exitButton.getHeight() - 10);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton);
    }

    private void setupListeners(TextButton loginButton, TextButton forgotPasswordButton,
                                TextButton signUpButton) {

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                boolean stayLoggedIn = stayLoggedInCheckbox.isChecked();

                if (username.isEmpty() || password.isEmpty()) {
                    showMessage("Please fill all fields!", true);
                    return;
                }

                try {
                    Result result = controller.LoginRes(username, password);

                    if (result.IsSuccess()) {
                        User user = UserDataBase.findUserByUsername(username);
                        if (user != null) {
                            App.currentUser = user;
                            SessionManager.saveSession(user.getUsername(), stayLoggedIn);
                        }
                        App.currentMenu = Menu.MainMenu;
                        Main.getMain().setScreen(new MainMenu());
                    } else {
                        showMessage(result.toString(), true);
                    }
                } catch (IOException e) {
                    showMessage("Login failed: " + e.getMessage(), true);
                }
            }
        });

        forgotPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showForgotPasswordDialog();
            }
        });

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.RegisterMenu;
                //Main.getMain().setScreen(new RegisterMenu());
            }
        });
    }

    private void showForgotPasswordDialog() {
        forgotPasswordWindow = new Window("Forgot Password", Main.getSkin());
        forgotPasswordWindow.setModal(true);
        forgotPasswordWindow.setMovable(true);

        showUsernameStep();
    }

    private void showUsernameStep() {
        forgotPasswordWindow.clear();

        Table content = new Table();
        content.pad(20);

        Label instructionLabel = new Label("Enter your username:", Main.getSkin());
        forgotUsernameField = new TextField("", Main.getSkin());
        forgotUsernameField.setMessageText("Username");

        TextButton submitButton = new TextButton("Submit", Main.getSkin());
        TextButton cancelButton = new TextButton("Cancel", Main.getSkin());

        content.add(instructionLabel).padBottom(10).row();
        content.add(forgotUsernameField).width(250).padBottom(20).row();

        Table buttonTable = new Table();
        buttonTable.add(submitButton).width(100).padRight(10);
        buttonTable.add(cancelButton).width(100);
        content.add(buttonTable).row();

        Label forgotMessageLabel = new Label("", Main.getSkin());
        forgotMessageLabel.setAlignment(Align.center);
        forgotMessageLabel.setColor(1, 0.3f, 0.3f, 1);
        content.add(forgotMessageLabel).padTop(10);

        forgotPasswordWindow.add(content);
        forgotPasswordWindow.pack();
        forgotPasswordWindow.setPosition(
            (stage.getWidth() - forgotPasswordWindow.getWidth()) / 2,
            (stage.getHeight() - forgotPasswordWindow.getHeight()) / 2
        );

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = forgotUsernameField.getText().trim();
                if (username.isEmpty()) {
                    forgotMessageLabel.setText("Please enter username!");
                    return;
                }

                try {
                    Result result = controller.checkUsernameForPasswordReset(username);
                    if (result.IsSuccess()) {
                        showSecurityQuestionStep(result.toString());
                    } else {
                        forgotMessageLabel.setText(result.toString());
                    }
                } catch (IOException e) {
                    forgotMessageLabel.setText("Error: " + e.getMessage());
                }
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.cancelPasswordReset();
                forgotPasswordWindow.remove();
            }
        });

        stage.addActor(forgotPasswordWindow);
    }

    private void showSecurityQuestionStep(String question) {
        forgotPasswordWindow.clear();

        Table content = new Table();
        content.pad(20);

        Label titleLabel = new Label("Security Question", Main.getSkin(), "bold");
        Label questionLabel = new Label(question, Main.getSkin());
        questionLabel.setWrap(true);

        TextField answerField = new TextField("", Main.getSkin());
        answerField.setMessageText("Your answer");

        TextButton submitButton = new TextButton("Submit", Main.getSkin());
        TextButton backButton = new TextButton("Back", Main.getSkin());

        content.add(titleLabel).padBottom(10).row();
        content.add(questionLabel).width(300).padBottom(15).row();
        content.add(answerField).width(250).padBottom(20).row();

        Table buttonTable = new Table();
        buttonTable.add(submitButton).width(100).padRight(10);
        buttonTable.add(backButton).width(100);
        content.add(buttonTable).row();

        Label messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        messageLabel.setColor(1, 0.3f, 0.3f, 1);
        content.add(messageLabel).padTop(10);

        forgotPasswordWindow.add(content);
        forgotPasswordWindow.pack();

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String answer = answerField.getText().trim();
                if (answer.isEmpty()) {
                    messageLabel.setText("Please enter your answer!");
                    return;
                }

                Result result = controller.verifySecurityAnswer(answer);
                if (result.IsSuccess()) {
                    showNewPasswordStep();
                } else {
                    messageLabel.setText(result.toString());
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            showUsernameStep();
                        }
                    }, 2f);
                }
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.cancelPasswordReset();
                showUsernameStep();
            }
        });
    }

    private void showNewPasswordStep() {
        forgotPasswordWindow.clear();

        Table content = new Table();
        content.pad(20);

        Label titleLabel = new Label("Set New Password", Main.getSkin(), "bold");

        TextField passwordField = new TextField("", Main.getSkin());
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("New password");

        TextField confirmField = new TextField("", Main.getSkin());
        confirmField.setPasswordMode(true);
        confirmField.setPasswordCharacter('*');
        confirmField.setMessageText("Confirm password");

        TextButton setPasswordButton = new TextButton("Set Password", Main.getSkin());
        TextButton randomButton = new TextButton("Generate Random", Main.getSkin());
        TextButton cancelButton = new TextButton("Cancel", Main.getSkin());

        Table suggestionsTable = new Table();
        suggestionsTable.setVisible(false);

        content.add(titleLabel).padBottom(15).row();
        content.add(new Label("Enter new password:", Main.getSkin())).padBottom(5).row();
        content.add(passwordField).width(250).padBottom(10).row();
        content.add(confirmField).width(250).padBottom(15).row();

        Table buttonTable = new Table();
        buttonTable.add(setPasswordButton).width(120).padRight(5);
        buttonTable.add(randomButton).width(120);
        content.add(buttonTable).padBottom(10).row();

        content.add(suggestionsTable).padBottom(10).row();
        content.add(cancelButton).width(100).row();

        Label messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        messageLabel.setWrap(true);
        content.add(messageLabel).width(300).padTop(10);

        forgotPasswordWindow.add(content);
        forgotPasswordWindow.pack();


        setPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String password = passwordField.getText();
                String confirm = confirmField.getText();

                if (password.isEmpty() || confirm.isEmpty()) {
                    messageLabel.setColor(1, 0.3f, 0.3f, 1);
                    messageLabel.setText("Please fill both fields!");
                    return;
                }

                if (!password.equals(confirm)) {
                    messageLabel.setColor(1, 0.3f, 0.3f, 1);
                    messageLabel.setText("Passwords don't match!");
                    return;
                }

                try {
                    Result result = controller.setNewPassword(password);
                    if (result.IsSuccess()) {
                        messageLabel.setColor(0.3f, 1, 0.3f, 1);
                        messageLabel.setText(result.toString());
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                controller.cancelPasswordReset();
                                forgotPasswordWindow.remove();
                            }
                        }, 2f);
                    } else {
                        messageLabel.setColor(1, 0.3f, 0.3f, 1);
                        messageLabel.setText(result.toString());
                    }
                } catch (IOException e) {
                    messageLabel.setColor(1, 0.3f, 0.3f, 1);
                    messageLabel.setText("Error: " + e.getMessage());
                }
            }
        });

        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                suggestionsTable.clear();
                suggestionsTable.setVisible(true);

                Label suggestLabel = new Label("Password Suggestions:", Main.getSkin(), "bold");
                suggestionsTable.add(suggestLabel).padBottom(10).row();

                List<String> suggestions = controller.generateRandomPasswordSuggestions(3);
                for (String suggestion : suggestions) {
                    TextButton useButton = new TextButton(suggestion, Main.getSkin());
                    useButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            passwordField.setText(suggestion);
                            confirmField.setText(suggestion);
                            suggestionsTable.setVisible(false);
                        }
                    });
                    suggestionsTable.add(useButton).width(200).padBottom(5).row();
                }

                forgotPasswordWindow.pack();
            }
        });

        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.cancelPasswordReset();
                forgotPasswordWindow.remove();
            }
        });
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
