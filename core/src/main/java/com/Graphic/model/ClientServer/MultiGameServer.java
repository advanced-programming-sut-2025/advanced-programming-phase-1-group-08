package com.Graphic.model.ClientServer;

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
    private Queue<String> diffQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Waiting for clients...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> handleNewClient(clientSocket)).start();
        }
    }

    public static void handleNewClient(Socket clientSocket) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

//            out.println("Enter your Username: ");
//            String username = in.readLine();
//            out.println("Enter your Nickname: ");
//            String nickName = in.readLine();
//
//            User Player = new User(username , nickName , "ali@gmail.com","man",
//                0,200, PasswordHashUtil.hashPassword("Ebrahim84?") , SecurityQuestions.FavoriteAnimal, "dog");
//
//            out.println("Enter gameId to join: ");
//            String gameId = in.readLine();
//            Game game = games.computeIfAbsent(gameId.trim() , id -> new Game());
//            game.addPlayer(Player , clientSocket);
        }
        catch (Exception e) {
            e.printStackTrace();
            //djskhkshff
        }
    }
}
