package com.Graphic.model.ClientServer;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.View.MainMenu;
import com.Graphic.View.PlayGameMenu;
import com.Graphic.View.RegisterMenu;
import com.badlogic.gdx.Gdx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static com.Graphic.model.Enum.Commands.CommandType.GET_DIFF;
import static com.Graphic.model.Enum.Commands.CommandType.LOGGED_IN;

public class Client2ServerThread extends Thread{

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String update;

    public Client2ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }


    @Override
    public void run() {
        String line = null;
        Message message = null;
        HashMap<String,Object> body = new HashMap<>();
        body.put("null","null");
        Message diff = new Message(GET_DIFF, body);
        update = JSONUtils.toJson(diff);
        while (true) {
            try {
                if (! Main.getClient(null).getRequests().isEmpty()) {
                    System.out.println(2);
                    sendMessage(Main.getClient(null).getRequests().poll());
                    line = in.readUTF();
                    message = JSONUtils.fromJson(line);
                    handleMessage(message);
                }
                if (Main.getClient(null).isRunning()) {
                    sendMessage(diff);
                    line = in.readUTF();
                    message = JSONUtils.fromJson(line);
                    handleMessage(message);
                }

                Thread.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void handleMessage(Message message) throws Exception {
        switch (message.getCommandType()) {
            case LOGIN_SUCCESS -> {
                System.out.println("Login success");
                HashMap<String,Object> body = new HashMap<>();
                body.put("null","null");
                sendMessage(new Message(LOGGED_IN , body));
                Main.getMain().setScreen(new PlayGameMenu());
                Main.getClient(null).setPlayer(message.getFromBody("Player"));
            }
            case INDEX -> {
                Main.getClient(null).setIndex(message.getIntFromBody("index"));
            }
            case GENERATE_RANDOM_PASS -> {
                Gdx.app.postRunnable(() -> {
                    RegisterMenu.getInstance().setPasswordField(message);
                });
            }
        }
    }

    public void sendMessage(Message message) throws Exception {
        if (message.getCommandType() == GET_DIFF) {
            out.writeUTF(update);
            out.flush();
        }
        else {
            out.writeUTF(JSONUtils.toJson(message));
            out.flush();
        }
    }
}
