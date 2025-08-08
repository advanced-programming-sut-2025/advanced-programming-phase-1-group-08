package com.Graphic.model.ClientServer;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.Graphic.Main;
import com.Graphic.model.User;

import java.io.IOException;

public class KryoNetClient {
    private Client client;
    private Main mainApp; // یا هر مرجعی که لازم داری برای هندل پیام‌ها

    public KryoNetClient(Main mainApp) throws IOException {
        this.mainApp = mainApp;
        client = new Client();
        Network.register(client);
        client.start();
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Connected to server. id=" + connection.getID());
                // در صورت نیاز میتونی اینجا Player info رو بفرستی
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Disconnected from server.");
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message msg = (Message) object;
                    handleIncoming(msg);
                }
            }
        });
    }

    public void connect(String host) throws IOException {
        client.connect(5000, host, Network.TCP_PORT, Network.UDP_PORT);
    }

    public void send(Message msg) {
        client.sendTCP(msg);
    }

    private void handleIncoming(Message msg) {
        switch (msg.commandType) {
            case GAME_START -> {
                // دریافت لیست بازیکنان و مقداردهی local state
                Object players = msg.get("Players");
                // mainApp یا localGameState رو آپدیت کن
            }
            case GET_DIFF -> {
                Object state = msg.get("State");
                // merge با localGameState
            }
            case CHANGE_INVENTORY -> {
                // پردازش
            }
            default -> {
                System.out.println("Unhandled client message: " + msg.commandType);
            }
        }
    }

    public void stop() {
        client.stop();
    }
}
