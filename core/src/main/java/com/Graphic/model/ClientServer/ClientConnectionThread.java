package com.Graphic.model.ClientServer;

import com.Graphic.Controller.Menu.LoginController;
import com.Graphic.Controller.Menu.RegisterController;
import com.Graphic.Main;
import com.Graphic.model.Enum.Commands.CommandType;
import com.badlogic.gdx.Gdx;

import java.io.*;
import java.net.Socket;

public class ClientConnectionThread extends Thread {
    //این کلاس برای ارتباط بین سرور و کلاینت قبل از شروع بازی است
    //این ترد در طرف سرور هست
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;
    private LoginController controller;
    private RegisterController registerController;

    public ClientConnectionThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        controller = new LoginController();
        registerController = new RegisterController();
    }


    @Override
    public void run() {
        System.out.println("Client connected");
        while (true) {
            try {
                String line = in.readUTF();
                handleMessage(JSONUtils.fromJson(line));
                if (JSONUtils.fromJson(line).getCommandType() == CommandType.GAME_START) {
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
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
            case NEW_GAME -> {}
            case JOIN_GAME -> {}
            case LOADED_GAME -> {}

        }
    }

    public synchronized void sendMessage(Message message) throws IOException {
        out.writeUTF(JSONUtils.toJson(message));
        out.flush();
        System.out.println(JSONUtils.toJson(message));
    }
}
