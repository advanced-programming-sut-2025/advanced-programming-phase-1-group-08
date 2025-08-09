package com.Graphic.model.ClientServer;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Controller.MainGame.Marketing;
import com.Graphic.Main;
import com.Graphic.View.GameMenus.MarketMenu;
import com.Graphic.View.LoginMenu;
import com.Graphic.View.MainMenu;
import com.Graphic.View.PlayGameMenu;
import com.Graphic.View.RegisterMenu;
import com.Graphic.model.Animall.Animal;
import com.Graphic.model.Animall.BarnOrCage;
import com.Graphic.model.Enum.ItemType.AnimalType;
import com.Graphic.model.Enum.ItemType.BackPackType;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.Menu;
import com.Graphic.model.Items;
import com.Graphic.model.User;
import com.badlogic.gdx.Gdx;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.model.Enum.Commands.CommandType.*;

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
                if (! Main.getClient().getRequests().isEmpty()) {
                    System.out.println(2);
                    message = Main.getClient().getRequests().poll();
                    sendMessage(message);
                    line = in.readUTF();
                    System.out.println(line);
                    message = JSONUtils.fromJson(line);
                    handleMessage(message);
                }
                if (Main.getClient().isRunning()) {
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
