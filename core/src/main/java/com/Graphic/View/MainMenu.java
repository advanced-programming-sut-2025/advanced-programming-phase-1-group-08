package com.Graphic.View;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.model.App;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
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
import java.util.HashMap;
import java.util.Scanner;

public class MainMenu implements Screen, AppMenu {
    private static MainMenu instance;
    private Stage stage;
    private Texture backgroundTexture;
    private Table mainTable;

    // Windows for dialogs
    private Window craftingWindow;
    private Window marketWindow;
    private Label messageLabel;

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
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

        // Request user data when menu loads
        requestUserData();
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
        TextButton logoutButton = new TextButton("Logout", Main.getSkin());
        TextButton exitButton = new TextButton("Exit", Main.getSkin());

        float buttonWidth = 300;
        float buttonHeight = 60;
        float buttonPadding = 15;

        // Play Game Button
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.PlayGameMenu;
                Main.getClient().setCurrentMenu(Menu.PlayGameMenu);
                Main.getMain().setScreen(new PlayGameMenu());
            }
        });

        // Profile Button
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.ProfileMenu;
                Main.getClient().setCurrentMenu(Menu.ProfileMenu);
                Main.getMain().setScreen(ProfileMenu.getInstance());
            }
        });

        // Crafting Button - Request from server
        craftingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                requestCraftingData();
            }
        });

        // Market Button - Request from server
        marketButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                requestMarketData();
            }
        });

        // Avatar Button
        avatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.currentMenu = Menu.AvatarMenu;
                Main.getClient().setCurrentMenu(Menu.AvatarMenu);
                Main.getMain().setScreen(AvatarMenu.getInstance());
            }
        });

        // Logout Button
        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                performLogout();
            }
        });

        // Exit Button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Send disconnect message before exit
                HashMap<String, Object> body = new HashMap<>();
                Message message = new Message(CommandType.DISCONNECT, body);
                Main.getClient().getRequests().add(message);

                // Small delay then exit
                com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        Gdx.app.exit();
                    }
                }, 0.5f);
            }
        });

        menuContainer.add(playButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(craftingButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(marketButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(profileButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(avatarButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(logoutButton).width(buttonWidth).height(buttonHeight).padBottom(buttonPadding).row();
        menuContainer.add(exitButton).width(buttonWidth).height(buttonHeight).row();

        mainTable.add(menuContainer).row();

        // Message label for notifications
        messageLabel = new Label("", Main.getSkin());
        messageLabel.setAlignment(Align.center);
        mainTable.add(messageLabel).padTop(20);

        stage.addActor(mainTable);
    }

    private void requestUserData() {
        HashMap<String, Object> body = new HashMap<>();
        Message message = new Message(CommandType.GET_USER_DATA, body);
        Main.getClient().getRequests().add(message);
    }

    private void requestCraftingData() {
        HashMap<String, Object> body = new HashMap<>();
        Message message = new Message(CommandType.GET_CRAFTING_DATA, body);
        Main.getClient().getRequests().add(message);
        showMessage("Loading crafting data...", false);
    }

    private void requestMarketData() {
        HashMap<String, Object> body = new HashMap<>();
        Message message = new Message(CommandType.GET_MARKET_DATA, body);
        Main.getClient().getRequests().add(message);
        showMessage("Loading market data...", false);
    }

    private void performLogout() {
        HashMap<String, Object> body = new HashMap<>();
        Message message = new Message(CommandType.LOGOUT, body);
        Main.getClient().getRequests().add(message);

        // Clear local session
        App.currentUser = null;
        App.currentMenu = Menu.LoginMenu;
        Main.getClient().setCurrentMenu(Menu.LoginMenu);

        // Go to login screen
        Main.getMain().setScreen(new LoginMenu("", ""));
    }

    public void showCraftingGuide(Message message) {
        if (craftingWindow != null) {
            craftingWindow.remove();
        }

        craftingWindow = new Window("Crafting Guide", Main.getSkin());
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

        // Parse crafting data from message
        String craftingData = message.getFromBody("craftingData");
        if (craftingData != null && !craftingData.isEmpty()) {
            String[] recipes = craftingData.split(";");
            for (String recipe : recipes) {
                contentTable.add(new Label(recipe, Main.getSkin())).pad(10).align(Align.left).row();
            }
        } else {
            contentTable.add(new Label("No crafting recipes available", Main.getSkin())).pad(20);
        }

        scrollPane.setWidget(contentTable);

        craftingWindow.add(scrollPane).width(600).height(400).pad(20);
        craftingWindow.setSize(650, 500);
        craftingWindow.setPosition(
            (stage.getWidth() - craftingWindow.getWidth()) / 2,
            (stage.getHeight() - craftingWindow.getHeight()) / 2
        );

        stage.addActor(craftingWindow);
    }

    public void showMarket(Message message) {
        if (marketWindow != null) {
            marketWindow.remove();
        }

        marketWindow = new Window("Market", Main.getSkin());
        marketWindow.setMovable(true);
        marketWindow.setModal(true);

        TextButton closeButton = new TextButton("X", Main.getSkin());
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                marketWindow.remove();
            }
        });
        marketWindow.getTitleTable().add(closeButton).padRight(0);

        Table contentTable = new Table();
        contentTable.pad(20);

        // Parse market data from message
        String marketData = message.getFromBody("marketData");
        if (marketData != null && !marketData.isEmpty()) {
            // Parse and display market items
            String[] items = marketData.split(";");
            for (String item : items) {
                String[] parts = item.split(",");
                if (parts.length >= 3) {
                    Table itemRow = new Table();
                    itemRow.add(new Label(parts[0], Main.getSkin())).width(200).align(Align.left);
                    itemRow.add(new Label("Price: " + parts[1], Main.getSkin())).width(100);

                    TextButton buyButton = new TextButton("Buy", Main.getSkin());
                    final String itemId = parts[2];
                    buyButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            purchaseItem(itemId);
                        }
                    });
                    itemRow.add(buyButton).width(80);

                    contentTable.add(itemRow).padBottom(10).row();
                }
            }
        } else {
            contentTable.add(new Label("Market is currently closed", Main.getSkin()));
        }

        ScrollPane scrollPane = new ScrollPane(contentTable, Main.getSkin());
        marketWindow.add(scrollPane).width(500).height(400).pad(20);
        marketWindow.setSize(550, 500);
        marketWindow.setPosition(
            (stage.getWidth() - marketWindow.getWidth()) / 2,
            (stage.getHeight() - marketWindow.getHeight()) / 2
        );

        stage.addActor(marketWindow);
    }

    private void purchaseItem(String itemId) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("itemId", itemId);
        Message message = new Message(CommandType.PURCHASE_ITEM, body);
        Main.getClient().getRequests().add(message);
        showMessage("Processing purchase...", false);
    }

    public void handleResponse(Message message) {
        switch (message.getCommandType()) {
            case CRAFTING_DATA:
                showCraftingGuide(message);
                break;

            case MARKET_DATA:
                showMarket(message);
                break;

            case PURCHASE_SUCCESS:
                showMessage("Purchase successful!", false);
                if (marketWindow != null) {
                    marketWindow.remove();
                }
                requestMarketData(); // Refresh market data
                break;

            case PURCHASE_FAILED:
                showMessage("Purchase failed: " + message.getFromBody("reason"), true);
                break;

            case USER_DATA:
                // Update any UI elements with user data if needed
                String username = message.getFromBody("username");
                String gold = message.getFromBody("gold");
                if (username != null) {
                    showMessage("Welcome back, " + username + "!", false);
                }
                break;

            case ERROR:
                showMessage(message.getFromBody("error"), true);
                break;

            case SUCCESS:
                showMessage(message.getFromBody("message"), false);
                break;
        }
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        if (isError) {
            messageLabel.setColor(1, 0.3f, 0.3f, 1);
        } else {
            messageLabel.setColor(0.3f, 1, 0.3f, 1);
        }

        // Auto-hide message after 3 seconds
        com.badlogic.gdx.utils.Timer.schedule(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                messageLabel.setText("");
            }
        }, 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();

        // Check for menu changes
        if (Main.getClient().getCurrentMenu() != Menu.MainMenu) {
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

    @Override
    public Stage getStage() {
        return stage;
    }
}
