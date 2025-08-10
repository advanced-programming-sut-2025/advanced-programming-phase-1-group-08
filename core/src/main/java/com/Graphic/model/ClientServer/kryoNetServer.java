package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

public class kryoNetServer {
    private Server server;

    private HashMap<Integer , ClientConnectionThread> threads = new HashMap<>();


    public kryoNetServer() throws Exception {
        server = new Server(1024 * 1024 , 1024 * 1024);
        server.start();
        Network.register(server);
        server.bind(Network.TCP_PORT, Network.UDP_PORT);

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection)  {
                System.out.println("Client connected: " + connection.getID());

                try {
                    //connection.sendTCP(new Message(CommandType.REDUCE_BARN_CAGE , new HashMap<>()));
                    ClientConnectionThread connectionThread = new ClientConnectionThread(connection);
                    connectionThread.start();
                    //threads.put(connection.getID(), connectionThread);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    //Message message = (Message) object;
                    //threads.get(connection.getID()).enqueueMessage(message);
                }
            }
        });
    }
}
