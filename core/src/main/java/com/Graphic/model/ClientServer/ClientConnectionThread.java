package com.Graphic.model.ClientServer;

import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnectionThread extends Thread {
    //این کلاس برای ارتباط بین سرور و کلاینت قبل از شروع بازی است
    //این ترد در طرف سرور هست
    //private Socket clientSocket;
    //private DataInputStream in;
    //private DataOutputStream out;
    private LoginController controller;
    private RegisterController registerController;
    private Connection connection;
    private KryoNetServer server;
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    private Game game;


    public ClientConnectionThread(Connection connection) throws IOException {
//        this.clientSocket = clientSocket;
//        in = new DataInputStream(clientSocket.getInputStream());
//        out = new DataOutputStream(clientSocket.getOutputStream());
        this.connection = connection;
        controller = new LoginController();
        registerController = new RegisterController();
    }


    @Override
    public void run() {
        System.out.println("Client connected");
        while (true) {
            try {
                Message message = messageQueue.take();
                handleMessage(message);
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("Client disconnected");
    }

    public void enqueueMessage(Message message) {
        messageQueue.offer(message);
    }

    public synchronized void handleMessage(Message message) throws IOException {
        switch (message.getCommandType()) {
            case LOGIN -> {
                sendMessage(controller.LoginRes(message));
            }
            case SIGN_UP -> {
                System.out.println("SERVER");
                sendMessage(registerController.attemptRegistration(message));
            }
            case GENERATE_RANDOM_PASS -> {
                sendMessage(RegisterController.generateRandomPass());
            }
            case NEW_GAME -> {
                String id = message.getFromBody("id");
                User player = message.getFromBody("Player");
                for (Map.Entry<String , Game> entry : App.games.entrySet()) {
                    if (id.equals(entry.getKey())) {
                        //خطا بده بهش
                        System.out.println("Repeat");
                    }
                }
                System.out.println("No Repeat");
                App.games.put(id, new Game());
                App.games.get(id).addPlayer(player , connection );
            }
            case JOIN_GAME -> {
                String id = message.getFromBody("id");
                User player = message.getFromBody("Player");
                for (Map.Entry<String , Game> entry : App.games.entrySet()) {
                    if (id.equals(entry.getKey())) {
                        if (entry.getValue().getGameState().getPlayers().size() != 4) {
                            this.game = entry.getValue();
                            entry.getValue().addPlayer(player , connection);
                        }
                    }
                }
            }
            case LOADED_GAME -> {}

        }
    }

    public synchronized void sendMessage(Message message) throws IOException {
        connection.sendTCP(message);
        System.out.println(JSONUtils.toJson(message));
    }
}
