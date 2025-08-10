package com.Graphic.model.ClientServer;

import com.Graphic.Main;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Client extends Game {

    private final static String SERVER_IP = "localhost";
    private final static int SERVER_PORT = 8080;
    private GameState localGameState = new GameState();


    public static void main(String[] args) {

        try {
            ClientWork clientWork = new ClientWork("127.0.0.1",Network.TCP_PORT , Network.UDP_PORT);
            Thread.sleep(1000);
            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("StardewValley");
            config.setWindowedMode(1920, 1080);
            config.useVsync(true);
            config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
            new Lwjgl3Application(new Main(clientWork), config);

        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create() {

    }
}
