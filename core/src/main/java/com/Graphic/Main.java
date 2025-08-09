package com.Graphic;

import com.Graphic.View.LoginMenu;
import com.Graphic.model.ClientServer.KryoNetClient;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.IOException;

public class Main extends Game {

    private static Main main;
    private static Batch batch;
    private static Skin skin;
    public static Skin newSkin;
    private static KryoNetClient client;

    // مقادیر پیش‌فرض (در صورت ندادن آی‌پی/پورت)
    private static final String DEFAULT_IP = "127.0.0.1";
    private static final int DEFAULT_PORT = 8080;

    public Main(KryoNetClient client) {
        System.out.println("Main Constructor");
        Main.client = client;
        if (client == null) {
            System.err.println("⚠️ KryoNetClient is NULL! Networking will be disabled.");
        }
    }

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("Skin/craftacular-ui.json"));

        // اگر کلاینت داریم، سعی می‌کنیم وصل بشیم
        if (client != null) {
            try {
                String serverIp = System.getProperty("server.ip", DEFAULT_IP);
                int serverPort = Integer.parseInt(System.getProperty("server.port", String.valueOf(DEFAULT_PORT)));

                System.out.println("🌐 Connecting to server: " + serverIp + ":" + serverPort);
                client.initFromArgs(serverIp, serverPort);
                try {
                    client.startWorkWithServer(); // این الان موجوده و IOException پرتاب می‌کنه
                } catch (IOException e) {
                    e.printStackTrace();
                    // fallback: آگاه کردن کاربر یا رفتن به حالت آفلاین
                }

                System.out.println("✅ Connected to server successfully.");
            } catch (Exception e) {
                System.err.println("❌ Failed to connect to server: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ No KryoNetClient instance provided, running in offline mode.");
        }

        // صفحه شروع بازی
        main.setScreen(new LoginMenu());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
    }

    public static Batch getBatch() {
        return batch;
    }

    public static Skin getSkin() {
        return skin;
    }

    public static Main getMain() {
        return main;
    }

    public static synchronized KryoNetClient getClient() {
        return client;
    }

    public static Skin getNewSkin() {
        if (newSkin == null) {
            newSkin = new Skin(Gdx.files.internal("Mohamadreza/newSkin.json"));
        }
        return newSkin;
    }
}
