package com.Graphic.View;

import com.Graphic.Controller.Menu.ChatController;
import com.Graphic.Main;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;

import java.util.ArrayList;
import java.util.Map;

import static com.badlogic.gdx.Input.Keys.ENTER;

public class ChatMenu implements Screen , AppMenu {

    private ChatController controller;

    private Stage stage;
    //private String name;
    private Table mainTable;
    private Skin skin;
    private int width = 400;
    private int height = 96;
    private ArrayList<String> messages;
    private TextField messageField;
    private ScrollPane scrollPane;
    private Table messagesTable;
    private User chatting;




    public void createChatChoice() {
        mainTable = new Table();

        for (User player : Main.getClient().getLocalGameState().getPlayers()) {
            TextButton button = new TextButton(player.getUsername(), skin);
            mainTable.add(button).width(width).height(height).row();
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    getMessageFromUser(player.getUsername());
                    chatting = player;
                }
            });
        }

        TextButton publicChat = new TextButton("Public", skin);
        mainTable.add(publicChat).width(width).height(height).row();
        publicChat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                chatting = null;
                getMessageFromUser("Public");
            }
        });
    }

    public void getMessageFromUser(String name) {
        for (Map.Entry<String , ArrayList<String>> entry : Main.getClient().getPlayer().getPrivateChats().entrySet()) {
            if (entry.getKey().equals(name)) {
                messages = entry.getValue();
                return;
            }
        }
        messages = Main.getClient().getPlayer().getPublicChats();
    }

    public void createChatField() {
        TextField.TextFieldStyle chatStyle = new TextField.TextFieldStyle();
        chatStyle.font = new BitmapFont();
        chatStyle.fontColor = Color.WHITE;
        chatStyle.background = null;

        messageField = new TextField("", chatStyle);
        messageField.setMessageText("Message");
        messageField.setPosition((float) Gdx.graphics.getWidth()/2 + 5 , 4 * height + 300);
        messageField.setSize((float) Gdx.graphics.getWidth() / 4 , height);


    }

    public void showChatHistory(Table historyTable) {
        scrollPane.clear();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(1, 1, 1, 0.2f)); // سفید با 20% شفافیت (کمرنگ)
        pixmap.fill();
        Texture whiteTexture = new Texture(pixmap);
        pixmap.dispose();

        NinePatch patch = new NinePatch(whiteTexture, 1, 1, 1, 1);
        NinePatchDrawable lightWhiteBg = new NinePatchDrawable(patch);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();
        labelStyle.background = lightWhiteBg;


        for (String message : messages) {
            Label label = new Label(message, labelStyle);
            historyTable.add(label).width((float) Gdx.graphics.getWidth() / 4).height((float) height / 2).row();
        }
    }



    @Override
    public void show() {
        controller = new ChatController();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));
        createChatField();

        messagesTable = new Table();

        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        scrollStyle.vScrollKnob = skin.getDrawable("default-scroll"); // نوار اسکرول
        scrollStyle.vScroll = skin.getDrawable("default-scroll"); // پس‌زمینه نوار
        scrollStyle.background = null;

        scrollPane = new ScrollPane(messagesTable, scrollStyle);
        scrollPane.setPosition((float) Gdx.graphics.getWidth()/2 + 5, 310);
        scrollPane.setSize(Gdx.graphics.getWidth() / 4 , 4 * height);

        stage.addActor(scrollPane);
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.getBatch().begin();
        Main.getBatch().draw(TextureManager.get("ChatBox.png") ,
            (float) Gdx.graphics.getWidth() / 2 , 300 , Gdx.graphics.getWidth() / 2 , 5 * height);
        Main.getBatch().end();

        if (Gdx.input.isKeyJustPressed(ENTER)) {
            if (!messageField.getText().isEmpty()) {
                if (chatting != null) {
                    for (Map.Entry<String , ArrayList<String>> entry : Main.getClient().getPlayer().getPrivateChats().entrySet()) {
                        if (entry.getKey().equals(chatting.getUsername())) {
                            entry.getValue().add(messageField.getText());
                            controller.requestForSendChat(Main.getClient().getPlayer().getUsername() , chatting.getUsername() , messageField.getText());
                        }
                    }
                }
                else {
                    Main.getClient().getPlayer().getPublicChats().add(messageField.getText());
                    controller.sendPublicMessage(Main.getClient().getPlayer().getUsername() , messageField.getText());
                }
            }
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
