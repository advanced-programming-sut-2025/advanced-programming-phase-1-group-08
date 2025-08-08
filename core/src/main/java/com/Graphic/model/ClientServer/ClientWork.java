package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.badlogic.gdx.Input.Keys.*;

public class ClientWork  {
    private Socket socket;
    private GameState localGameState;
    private int index;
    private volatile boolean running = false;
    private boolean exit = false;
    private boolean isWorkingWithOtherClient = false;
    private Client2ServerThread client2ServerThread;
    private Client2ClientThread client2ClientThread;
    private BlockingQueue<Message> requests = new LinkedBlockingQueue();
    private User Player;
    private Menu currentMenu;

    public void initFromArgs(String ip, int port) {
            try {
                System.out.println("Connecting to " + ip + ":" + port);
                socket = new Socket(ip, port);
                System.out.println("Connected to server.");
                this.client2ServerThread = new Client2ServerThread(socket);
                //this.client2ClientThread = new Client2ClientThread(port);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void startListening() {
        try {
            this.client2ClientThread.start();
        }
        catch (Exception e) {

        }
    }

    public void startWorkWithServer() throws Exception {
        try {
            this.client2ServerThread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            socket.close();
        }

    }


    public boolean isExit() {
        return exit;
    }

    public boolean isWorkingWithOtherClient() {
        return isWorkingWithOtherClient;
    }

    public void sendMessage(Message message) throws IOException {
        String result = JSONUtils.toJson(message);
        new DataOutputStream(socket.getOutputStream()).writeUTF(result);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public synchronized BlockingQueue<Message> getRequests() {
        //System.out.println("dsf");
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
        return localGameState;
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }
    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }

}
