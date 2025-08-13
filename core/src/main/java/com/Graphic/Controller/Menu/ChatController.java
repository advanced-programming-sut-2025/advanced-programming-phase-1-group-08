package com.Graphic.Controller.Menu;

import com.Graphic.Main;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.User;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;

public class ChatController {


    public void requestForSendChat(String sender , String receiver , String message) {
        HashMap<String , Object> chat = new HashMap<>();
        chat.put("sender", sender);
        chat.put("receiver", receiver);
        chat.put("message", message);
        Main.getClient().getRequests().add(new Message(CommandType.REQUEST_FOR_GET_CHAT, chat));
    }

    public void sendPublicMessage(String sender , String message) {
        HashMap<String , Object> chat = new HashMap<>();
        chat.put("sender", sender);
        chat.put("message", message);
        if (checkTag(message)) {
            Main.getClient().getRequests().add(new Message(CommandType.TAG, chat));
        }
        else {
            Main.getClient().getRequests().add(new Message(CommandType.PUBLIC_CHAT, chat));
        }
    }

    public boolean checkTag(String message) {
        for (User user : Main.getClient().getLocalGameState().getPlayers()) {
            String name ="@" + user.getUsername();
            if (message.contains(name)) {
                return true;
            }
        }
        return false;
    }
}
