package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;

import java.util.HashMap;

public class Message {
    private CommandType commandType;
    private HashMap<String , Object> body;

    public Message() {

    }

    public Message(CommandType commandType, HashMap<String , Object> body) {
        this.commandType = commandType;
        this.body = body;
    }

    public HashMap<String , Object> getBody() {
        return body;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    public int getIntFromBody(String fieldName) {
        return (int) ((double) ((Double) body.get(fieldName.trim())));
    }

}
