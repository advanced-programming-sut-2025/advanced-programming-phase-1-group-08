package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

public class kryoNetServer {
    private Server server;
    private HashMap<Integer, ClientConnectionThread> threads = new HashMap<>();
    private MenuMessageHandler menuHandler;  // NEW: Menu handler
    private ConnectionManager connectionManager;  // NEW: Connection manager

    public kryoNetServer() throws Exception {
        server = new Server(1024 * 1024 * 10, 1024 * 1024 * 10);
        server.start();
        Network.register(server);
        server.bind(Network.TCP_PORT, Network.UDP_PORT);

        // Initialize handlers
        menuHandler = MenuMessageHandler.getInstance();
        connectionManager = ConnectionManager.getInstance();

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                System.out.println("Client connected: " + connection.getID());

                try {
                    ClientConnectionThread connectionThread = new ClientConnectionThread(connection);
                    connectionThread.start();
                    threads.put(connection.getID(), connectionThread);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println("Client disconnected: " + connection.getID());

                // Clean up connection
                connectionManager.removeConnection(connection);

                // Remove thread if exists
                ClientConnectionThread thread = threads.remove(connection.getID());
                if (thread != null) {
                    thread.interrupt();
                }
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Message) {
                    Message message = (Message) object;

                    // Check if this is a menu message that should be handled directly
                    if (isMenuMessage(message)) {
                        try {
                            handleMenuMessage(connection, message);
                        } catch (Exception e) {
                            e.printStackTrace();
                            sendError(connection, "Server error: " + e.getMessage());
                        }
                    } else {
                        // Pass to game thread if exists
                        ClientConnectionThread thread = threads.get(connection.getID());
                        if (thread != null) {
                            thread.enqueueMessage(message);
                        }
                    }
                }
            }
        });

        System.out.println("Server started on TCP:" + Network.TCP_PORT + " UDP:" + Network.UDP_PORT);
    }

    /**
     * Check if message is a menu-related message
     */
    private boolean isMenuMessage(Message message) {
        CommandType type = message.getCommandType();
        return type == CommandType.GET_USER_INFO ||
            type == CommandType.CHANGE_USERNAME ||
            type == CommandType.CHANGE_NICKNAME ||
            type == CommandType.CHANGE_EMAIL ||
            type == CommandType.CHANGE_PASSWORD ||
            type == CommandType.GET_AVATAR_SETTINGS ||
            type == CommandType.SAVE_AVATAR_SETTINGS ||
            type == CommandType.RESET_AVATAR_SETTINGS ||
            type == CommandType.GET_USER_DATA ||
            type == CommandType.GET_CRAFTING_DATA ||
            type == CommandType.GET_MARKET_DATA ||
            type == CommandType.PURCHASE_ITEM ||
            type == CommandType.LOGOUT ||
            type == CommandType.DISCONNECT ||
            type == CommandType.GENERATE_RANDOM_PASS;
    }

    /**
     * Handle menu messages directly
     */
    private void handleMenuMessage(Connection connection, Message message) {
        // Some messages need special handling
        switch (message.getCommandType()) {
            case DISCONNECT -> {
                menuHandler.handleMenuMessage(connection, message);
                connection.close();
            }
            case LOGOUT -> {
                menuHandler.handleMenuMessage(connection, message);
                // Remove from active connections but keep connection open
                connectionManager.removeConnection(connection);
            }
            default -> {
                // Let menu handler process the message
                menuHandler.handleMenuMessage(connection, message);
            }
        }
    }

    /**
     * Send error message to client
     */
    private void sendError(Connection connection, String errorMessage) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("error", errorMessage);
        Message response = new Message(CommandType.ERROR, body);
        connection.sendTCP(response);
    }

    /**
     * Shutdown server gracefully
     */
    public void shutdown() {
        System.out.println("Shutting down server...");

        // Clear all connections
        connectionManager.clearAll();

        // Stop all threads
        for (ClientConnectionThread thread : threads.values()) {
            thread.interrupt();
        }
        threads.clear();

        // Stop server
        server.stop();
        server.close();

        System.out.println("Server shutdown complete");
    }

    public Server getServer() {
        return server;
    }
}
