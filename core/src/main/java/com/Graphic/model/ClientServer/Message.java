package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;

import java.util.HashMap;

public class Message {

    public CommandType commandType;
    public HashMap<String, Object> body = new HashMap<>();

    // empty constructor لازم Kryo
    public Message() {}

    public Message(CommandType commandType) {
        this.commandType = commandType;
    }

    public void put(String key, Object value) {
        body.put(key, value);
    }

    public <T> T get(String key) {
        return (T) body.get(key);
    }
}
