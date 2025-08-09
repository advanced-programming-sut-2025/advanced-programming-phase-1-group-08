package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Menu;
import com.Graphic.model.User;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientWork  {
    private GameState localGameState;
    private volatile boolean running = false;
    private boolean exit = false;
    private boolean isWorkingWithOtherClient = false;
    private Client2ServerThread client2ServerThread;
    private BlockingQueue<Message> requests = new LinkedBlockingQueue();
    private User Player;
    private Menu currentMenu;
    private com.esotericsoftware.kryonet.Client client;
    private Connection connection;

    public ClientWork(String serverIp , int tcpPort) throws IOException {
        client = new Client();
        Network.register(client);
        client.start();

        client.addListener(new Listener() {

            public void connected(Connection connection) {
                ClientWork.this.connection = connection;
            }

            public void disconnected(Connection connection) {
                ClientWork.this.connection = null;
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    handleMessage((Message) object);
                }
            }
        });

        client.connect(5000 , serverIp , tcpPort);

    }

    public void handleMessage(Message message) {

    }

    public void sendMessage(Message message) {

    }



    public boolean isExit() {
        return exit;
    }

    public boolean isWorkingWithOtherClient() {
        return isWorkingWithOtherClient;
    }


    public synchronized BlockingQueue<Message> getRequests() {
        return requests;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public User getPlayer() {
        return Player;
    }

    public void setPlayer(User player) {
        this.Player = player;
    }

    public GameState getLocalGameState() {
        if (localGameState == null) {
            localGameState = new GameState();
        }
        return localGameState;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }
    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

}
