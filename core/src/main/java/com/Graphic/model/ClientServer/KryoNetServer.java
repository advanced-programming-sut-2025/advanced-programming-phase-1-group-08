package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.Items;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.Graphic.model.Game;
import com.Graphic.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

public class KryoNetServer {
    private Server server;

    private HashMap<Integer , ClientConnectionThread> threads = new HashMap<>();


    public KryoNetServer() throws Exception {
        server = new Server();
        Network.register(server);
        server.start();
        server.bind(Network.TCP_PORT, Network.UDP_PORT);

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection)  {
                System.out.println("Client connected: " + connection.getID());
                try {
                    ClientConnectionThread connectionThread = new ClientConnectionThread(connection);
                    threads.put(connection.getID(), connectionThread);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message message = (Message) object;
                    threads.get(connection.getID()).enqueueMessage(message);
                }
            }
        });
    }
}
