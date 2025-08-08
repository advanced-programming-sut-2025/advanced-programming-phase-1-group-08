package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Items;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.Graphic.model.Game;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class KryoNetServer {
    private Server server;
    private Game game; // مرجع به گیم که قبلاً داشتی
    // نگهداری mapping connectionId -> User
    private ConcurrentHashMap<Integer, User> connectionUserMap = new ConcurrentHashMap<>();

    public KryoNetServer(Game game) throws IOException {
        this.game = game;
        server = new Server();
        Network.register(server);
        server.start();
        server.bind(Network.TCP_PORT, Network.UDP_PORT);

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Client connected: " + connection.getID());
                // می‌تونی اینجا منتظر authentification یا ارسال USER از کلاینت باشی
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected: " + connection.getID());
                User u = connectionUserMap.remove(connection.getID());
                if (u != null) {
                    // کار پاکسازی روی بازی اگر لازم بود
                }
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message message = (Message) object;
                    try {
                        handleMessage(connection, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void handleMessage(Connection connection, Message message) throws IOException {
        switch (message.commandType) {
            case GAME_START -> {
                // اگر کلاینت در ابتدا player خودش رو فرستاد، اینجا ذخیره کن
                User player = message.get("Player");
                if (player != null) {
                    connectionUserMap.put(connection.getID(), player);
                }
                // broadcast initial players to همه
                Message reply = new Message(CommandType.GAME_START);
                reply.put("Players", game.getGameState().getPlayers());
                connection.sendTCP(reply);
            }
            case GET_DIFF -> {
                User player = message.get("Player");
                // در نسخه قدیمی تو sendMessage(game.getStateFromPlayer(Player));
                Message response = new Message(CommandType.GET_DIFF);
                response.put("State", game.getStateFromPlayer(player));
                connection.sendTCP(response);
            }
            case MOVE_IN_FARM -> {
                // تبدیل controller.checkWalking(message , game) به خروجی که باید ارسال بشه
                Object diff = com.Graphic.Controller.MainGame.GameControllerLogic.checkWalking(message , game);
                if (diff != null) {
                    // broadcast یا فقط برای آن کانکشن
                    server.sendToAllTCP(diff);
                }
            }
            case BUY -> {
                com.Graphic.Controller.MainGame.Marketing.getInstance().payForBuy(message, game);
                com.Graphic.Controller.MainGame.Marketing.getInstance().reduceProductInMarket(message, game);
                Message m = com.Graphic.Controller.MainGame.Marketing.getInstance().addItemToInventory(message);
                connection.sendTCP(m);
            }
            case CHANGE_INVENTORY -> {
                User player = message.get("Player");
                Items items = message.get("Item");
                int amount = (Integer) message.get("amount");
                for (User user : game.getGameState().getPlayers()) {
                    if (user.getUsername().trim().equals(player.getUsername().trim())) {
                        if (user.getBackPack().inventory.Items.containsKey(items)) {
                            user.getBackPack().inventory.Items.compute(items,(k,v) -> v + amount);
                            if (user.getBackPack().inventory.Items.get(items) == 0) {
                                Main.getClient(null).getPlayer().getBackPack().inventory.Items.remove(items);
                            }
                        }
                        else {
                            user.getBackPack().inventory.Items.put(items,amount);
                        }
                        Message body = new Message(CommandType.CHANGE_INVENTORY);
                        body.put("Item", items);
                        body.put("amount", amount);
                        // broadcast this change to all players (یا فقط به بازیکن مربوطه)
                        server.sendToAllTCP(body);
                    }
                }
            }
            // ... بقیه case ها را مثل بالا تبدیل کن (CALL به controller/marketing و سپس send)
            default -> {
                System.out.println("Unhandled command: " + message.commandType);
            }
        }
    }

    // helper: ارسال به همه
    public void broadcast(Message msg) {
        server.sendToAllTCP(msg);
    }

    public void stop() {
        server.stop();
    }
}
