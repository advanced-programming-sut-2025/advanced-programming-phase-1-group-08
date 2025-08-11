package com.Graphic.model;

import com.Graphic.model.ClientServer.GameState;
import com.Graphic.model.ClientServer.Message;
//import com.Graphic.model.ClientServer.PlayerHandler;
import com.Graphic.model.ClientServer.ServerHandler;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Enum.SecurityQuestions;
import com.Graphic.model.SaveData.PasswordHashUtil;
import com.Graphic.model.SaveData.UserStorage;
import com.esotericsoftware.kryonet.Connection;


import java.io.IOException;
import java.util.*;

public class Game {

    public Map<Set<User>, List<MessageHandling>> conversations = new HashMap<>();
    public Map<Set<User>, List<Trade>> trades = new HashMap<>();
    public ArrayList<HumanCommunications> friendships = new ArrayList<>();



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

        List<User> users = UserStorage.loadUsers();
        users.add(newUser);
        try {
            com.Graphic.model.SaveData.UserStorage.saveUsers(users);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // بالایی ها همه باید اصلاح بشن

    private GameState gameState = new GameState();
    public final HashMap<User , Connection> connections = new HashMap<>();
    private final Map<String , Integer> lastSentIndex = new HashMap<>();
    private boolean gameStarted = false;
    private Queue<Message> diffQueue = new LinkedList<>();
    private Message noDiff;

    public Game() {
        HashMap<String , Object> body = new HashMap<>();
        body.put("No Diff", "No Diff");
        noDiff = new Message(CommandType.NO_DIFF , body);
    }


    public synchronized void addPlayer(User u , Connection connection) throws IOException {

        gameState.getPlayers().add(u);
        connections.put(u , connection);

        if (gameState.getPlayers().size() == 4 && !gameStarted) {
            gameStarted = true;
            ServerHandler serverHandler = ServerHandler.getInstance(this);
            HashMap<String , Object> body = new HashMap<>();
            body.put("Players" , gameState.getPlayers());
            for (Map.Entry<User , Connection> entry : connections.entrySet()) {
                entry.getValue().sendTCP(new Message(CommandType.GAME_START ,  body));
            }
        }
    }


//    public  Message getStateFromPlayer(User u) {
//
//        int lastSent = lastSentIndex.get(u.getUsername());
//
//        if (diffQueue.size() > lastSent) {
//            System.out.println(u.getUsername() + "lastSent: " + lastSent + "Size: " + diffQueue.size());
//            int idx = 0;
//            for (Message message : diffQueue) {
//                if (idx == lastSent) {
//                    lastSentIndex.put(u.getUsername(), lastSent + 1);
//                    return message;
//                }
//                idx ++;
//            }
//        }
//
//        return noDiff;
//
//    }

    public synchronized GameState getGameState() {
        return gameState;
    }

    public synchronized Queue<Message> getDiffQueue() {
        System.out.println("a");
        return diffQueue;
    }
}
