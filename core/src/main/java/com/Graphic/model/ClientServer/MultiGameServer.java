package com.Graphic.model.ClientServer;

import com.Graphic.Main;
import com.Graphic.model.App;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.Game;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import static com.Graphic.model.App.games;

public class MultiGameServer {

    private final static String SERVER_IP = "localhost";
    private final static int SERVER_PORT = 8080;
    private final Object gameStateLock = new Object();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Waiting for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();

            handleNewClient(clientSocket);
        }
    }

    public static void handleNewClient(Socket clientSocket) {
        try {

            new ClientConnectionThread(clientSocket).start();

        }
        catch (Exception e) {
            e.printStackTrace();
            //djskhkshff
        }
    }


}
