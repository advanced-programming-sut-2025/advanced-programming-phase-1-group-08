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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private final Queue<Message> diffQueue = new LinkedList<>();

    // بالایی ها همه باید اصلاح بشن

    private final GameState gameState = new GameState();
    public final transient HashMap<User , Connection> connections = new HashMap<>();
    private boolean gameStarted = false;
    private String name;
    private String Id;
    private String Password;
    private boolean isPrivate;
    private boolean isVisible;
    private User Creator;
    private final transient ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    //private ArrayList<User> players;

    public Game () {

    }

    public Game(String name, String Id ,  String password, boolean isPrivate, boolean isVisible , User creator) {
        this.name = name;
        this.Id = Id;
        this.Password = password;
        this.isPrivate = isPrivate;
        this.isVisible = isVisible;
        this.Creator = creator;
        //this.players = new ArrayList<>();
        gameState.getPlayers().add(Creator);

        scheduler.schedule(() -> {
            if (this.getPlayers().isEmpty() || (this.getPlayers().get(0).getUsername().equals(creator.getUsername()) && this.getPlayers().size() == 1)) {
                App.gamesActive.remove(this);
            }
        } , 5 , TimeUnit.SECONDS);
    }



    public synchronized void addPlayer(User u , Connection connection) throws IOException {

        gameState.getPlayers().add(u);
        connections.put(u , connection);

        if (gameState.getPlayers().size() == 4 && !gameStarted) {
            gameStarted = true;
            ServerHandler serverHandler = ServerHandler.getInstance();
            serverHandler.setGame(this);
            serverHandler.start();
            HashMap<String , Object> body = new HashMap<>();
            body.put("Players" , gameState.getPlayers());
            for (Map.Entry<User , Connection> entry : connections.entrySet()) {
                entry.getValue().sendTCP(new Message(CommandType.GAME_START ,  body));
            }
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public synchronized Queue<Message> getDiffQueue() {
        System.out.println("a");
        return diffQueue;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public User getCreator() {
        return Creator;
    }
    public ArrayList<User> getPlayers() {
        return gameState.getPlayers();
    }
    public void setCreator(User u) {
        Creator = u;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return Id;
    }
    public String getPassword() {
        return Password;
    }
    public boolean isPrivate() {
        return isPrivate;
    }




}
