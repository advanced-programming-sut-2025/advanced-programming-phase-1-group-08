package com.Graphic.model;

import com.Graphic.model.ClientServer.GameState;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.ClientServer.PlayerHandler;
import com.Graphic.model.ClientServer.ServerHandler;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.UserStorage;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Game {

    public Map<Set<User>, List<MessageHandling>> conversations = new HashMap<>();
    public Map<Set<User>, List<Trade>> trades = new HashMap<>();
    public ArrayList<HumanCommunications> friendships = new ArrayList<>();
    public ServerHandler serverHandler;


    public Menu currentMenu;




    public Menu getCurrentMenu() {
        return this.currentMenu;
    }

    public HumanCommunications getFriendship(User u1, User u2) {
        for (HumanCommunications f : friendships) {
            if (f.isBetween(u1, u2)) {
                return f;
            }
        }
        return null;
    }

    public static void AddNewUser(String username, String pass, String nickname, String email, String gender,
                                  SecurityQuestions secQ, String secA) throws IOException{

        String hashPASS = PasswordHashUtil.hashPassword(pass);

        User newUser = new User(
                username,
                nickname,
                email,
                gender,
                0,
                100,
                hashPASS,
                secQ,
                secA
        );
        System.out.println("hi-1");
        App.users.add(newUser);
        System.out.println("hi-2");
        App.currentUser = newUser;
        System.out.println("hi-3");

        List<User> users = UserStorage.loadUsers();
        System.out.println("hi1");
        users.add(newUser);
        System.out.println("hi2");
        try {
            com.Graphic.model.SaveData.UserStorage.saveUsers(users);
            System.out.println("hi3");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // بالایی ها همه باید اصلاح بشن

    private GameState gameState = new GameState();
    private final List<PlayerHandler> Players = new ArrayList<>();
    private final Map<String , Integer> lastSentIndex = new HashMap<>();
    private boolean gameStarted = false;
    private Queue<Message> diffQueue = new LinkedList<>();
    private Message noDiff;

    public Game() {
        HashMap<String , Object> body = new HashMap<>();
        body.put("No Diff", "No Diff");
        noDiff = new Message(CommandType.NO_DIFF , body);
        serverHandler = ServerHandler.getInstance();
    }


    public synchronized void addPlayer (User u , Socket socket) throws IOException {
        if (Players.size() >= 4) {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream() , true);
                out.println("Game is Full");
            } catch (IOException e) {

            }
        }

        PlayerHandler handler = new PlayerHandler(u , socket , this , Players.size() + 1);
        Players.add(handler);
        lastSentIndex.put(u.getUsername(), 0);
        gameState.getPlayers().add(u);

        // TODO اینو زحمتت کامل کن من نمیدونم چی میخواد
        serverHandler.Players.add(u);
        // TODO

        if (Players.size() == 4 && !gameStarted) {
            gameStarted = true;
            for (int i = 0 ; i < 4 ; i++) {
                gameState.getPlayers().get(i).topLeftX = i % 2;
                gameState.getPlayers().get(i).topLeftY = i / 2;
                new Thread(Players.get(i)).start();
            }

        }

    }


    public  Message getStateFromPlayer(User u) {

        int lastSent = lastSentIndex.get(u.getUsername());

        if (diffQueue.size() > lastSent) {
            int idx = 0;
            for (Message message : diffQueue) {
                if (idx == lastSent) {
                    lastSentIndex.put(u.getUsername(), lastSent + 1);
                    return message;
                }
                idx ++;
            }
        }

        return noDiff;

    }

    public synchronized GameState getGameState() {
        return gameState;
    }

    public synchronized Queue<Message> getDiffQueue() {
        return diffQueue;
    }
}
