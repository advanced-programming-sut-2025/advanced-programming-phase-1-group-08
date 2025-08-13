package com.Graphic.Controller.Menu;

import com.Graphic.Main;
import com.Graphic.model.ClientServer.Message;
import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Game;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.User;

import java.util.HashMap;

public class LobbyController {

    public Result requestForCreateGame(String name , String password , boolean visible) {
        HashMap<String , Object> createGame = new HashMap<>();
        createGame.put("name", name);
        createGame.put("password", password);
        createGame.put("visible", visible);
        createGame.put("Creator" , Main.getClient().getPlayer());
        Main.getClient().getRequests().add(new Message(CommandType.CREATE_LOBBY, createGame));
        return new Result(true , "Lobby Successfully Created");
    }

    public void requestForJoinLobby(Game game , String pass) {
        HashMap<String , Object> joinGame = new HashMap<>();
        if (! game.isPrivate()) {
            joinGame.put("Player", Main.getClient().getPlayer());
            joinGame.put("Game", game.getId());
            Main.getClient().getRequests().add(new Message(CommandType.JOIN_LOBBY, joinGame));
        }
        else {
            if (pass != null) {
                joinGame.put("Player", Main.getClient().getPlayer());
                joinGame.put("Game", game.getId());
                joinGame.put("Pass", pass);
                Main.getClient().getRequests().add(new Message(CommandType.JOIN_LOBBY, joinGame));
            }
        }
    }

    public void requestFoLeaveLobby(Game game) {
        HashMap<String , Object> joinGame = new HashMap<>();
        joinGame.put("Player" , Main.getClient().getPlayer());
        joinGame.put("Game" , game.getId());
        Main.getClient().getRequests().add(new Message(CommandType.LEAVE_LOBBY , joinGame));
    }

    public boolean playerIsInGame(Game game) {
        for (User user : game.getPlayers()) {
            if (user.getUsername().equals(Main.getClient().getPlayer().getUsername())) {
                return true;
            }
        }
        return false;
    }

    public void requestForStartLobby(Game game) {
        HashMap<String , Object> startGame = new HashMap<>();
        startGame.put("Player" , Main.getClient().getPlayer());
        startGame.put("Game" , game.getId());
        Main.getClient().getRequests().add(new Message(CommandType.START_LOBBY , startGame));
    }

    public void Refresh() {
        HashMap<String , Object> refresh = new HashMap<>();
        refresh.put("null" , "null");
        Main.getClient().getRequests().add(new Message(CommandType.LIST_GAMES , refresh));
    }

    public void requestForFindInvisibleLobby(String id) {
        HashMap<String , Object> findInvisible = new HashMap<>();
        findInvisible.put("Id" , id);
        Main.getClient().getRequests().add(new Message(CommandType.FIND_INVISIBLE_LOBBY , findInvisible));
    }

}
