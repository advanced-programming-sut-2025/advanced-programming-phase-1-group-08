package com.Graphic.model.ClientServer;

import com.Graphic.Main;
import com.Graphic.View.MainMenu;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.User;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Game {

    private final static String SERVER_IP = "localhost";
    private final static int SERVER_PORT = 8080;
    private GameState localGameState = new GameState();


    public static void main(String[] args) {

        try(Socket socket = new Socket(SERVER_IP , SERVER_PORT)) {
            ClientWork clientWork = new ClientWork();
            clientWork.connect(SERVER_IP , SERVER_PORT);

            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("StardewValley");
            config.setWindowedMode(1920, 1080);
            config.useVsync(true);
            config.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
            new Lwjgl3Application(new Main(clientWork), config);

            new Thread(clientWork).start();

        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create() {

    }
}
