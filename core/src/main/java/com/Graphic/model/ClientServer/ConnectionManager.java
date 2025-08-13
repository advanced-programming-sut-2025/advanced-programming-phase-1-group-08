package com.Graphic.model.ClientServer;

import com.Graphic.model.Enum.Commands.CommandType;
import com.Graphic.model.User;
import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages the mapping between connections and users
 * Thread-safe implementation using ConcurrentHashMap
 */
public class ConnectionManager {

    private static ConnectionManager instance;

    // Thread-safe maps for connection management
    private final Map<Connection, User> connectionUserMap;
    private final Map<String, Connection> usernameConnectionMap;
    private final Map<Integer, Connection> connectionIdMap;

    private ConnectionManager() {
        // Use ConcurrentHashMap for thread safety
        connectionUserMap = new ConcurrentHashMap<>();
        usernameConnectionMap = new ConcurrentHashMap<>();
        connectionIdMap = new ConcurrentHashMap<>();
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Add a new connection-user mapping
     * @param connection The client connection
     * @param user The authenticated user
     */
    public synchronized void addConnection(Connection connection, User user) {
        if (connection == null || user == null) {
            System.err.println("Cannot add null connection or user");
            return;
        }

        // Remove any existing connection for this user (in case of reconnection)
        Connection existingConnection = usernameConnectionMap.get(user.getUsername());
        if (existingConnection != null && existingConnection != connection) {
            removeConnection(existingConnection);
        }

        connectionUserMap.put(connection, user);
        usernameConnectionMap.put(user.getUsername(), connection);
        connectionIdMap.put(connection.getID(), connection);

        System.out.println("Added connection for user: " + user.getUsername() + " (ID: " + connection.getID() + ")");
    }

    /**
     * Remove a connection
     * @param connection The connection to remove
     */
    public synchronized void removeConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        User user = connectionUserMap.get(connection);
        if (user != null) {
            usernameConnectionMap.remove(user.getUsername());
            System.out.println("Removed connection for user: " + user.getUsername());
        }

        connectionUserMap.remove(connection);
        connectionIdMap.remove(connection.getID());
    }

    /**
     * Get user by connection
     * @param connection The connection
     * @return The associated user, or null if not found
     */
    public User getUserByConnection(Connection connection) {
        if (connection == null) {
            return null;
        }
        return connectionUserMap.get(connection);
    }

    /**
     * Get connection by username
     * @param username The username
     * @return The associated connection, or null if not found
     */
    public Connection getConnectionByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return usernameConnectionMap.get(username);
    }

    /**
     * Get connection by connection ID
     * @param connectionId The connection ID
     * @return The connection, or null if not found
     */
    public Connection getConnectionById(int connectionId) {
        return connectionIdMap.get(connectionId);
    }

    /**
     * Update user for a connection (used when username changes)
     * @param connection The connection
     * @param oldUsername The old username
     * @param updatedUser The updated user object
     */
    public synchronized void updateUser(Connection connection, String oldUsername, User updatedUser) {
        if (connection == null || updatedUser == null) {
            System.err.println("Cannot update with null connection or user");
            return;
        }

        // Remove old username mapping if it exists
        if (oldUsername != null && !oldUsername.equals(updatedUser.getUsername())) {
            usernameConnectionMap.remove(oldUsername);
        }

        // Update mappings
        connectionUserMap.put(connection, updatedUser);
        usernameConnectionMap.put(updatedUser.getUsername(), connection);

        System.out.println("Updated user mapping: " + oldUsername + " -> " + updatedUser.getUsername());
    }

    /**
     * Check if a user is online
     * @param username The username to check
     * @return true if the user is online, false otherwise
     */
    public boolean isUserOnline(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        Connection connection = usernameConnectionMap.get(username);
        return connection != null && connection.isConnected();
    }

    /**
     * Get all online users
     * @return Map of all connections and their users
     */
    public Map<Connection, User> getAllConnections() {
        return new HashMap<>(connectionUserMap);
    }

    /**
     * Get all online usernames
     * @return Map of usernames to connections
     */
    public Map<String, Connection> getAllUsernames() {
        return new HashMap<>(usernameConnectionMap);
    }

    /**
     * Get the number of active connections
     * @return The number of active connections
     */
    public int getConnectionCount() {
        return connectionUserMap.size();
    }

    /**
     * Send a message to a specific user
     * @param username The username to send to
     * @param message The message to send
     * @return true if message was sent, false if user not found or not online
     */
    public boolean sendToUser(String username, Message message) {
        Connection connection = getConnectionByUsername(username);
        if (connection != null && connection.isConnected()) {
            connection.sendTCP(message);
            return true;
        }
        return false;
    }

    /**
     * Send a message to all connected users
     * @param message The message to broadcast
     */
    public void broadcastToAll(Message message) {
        for (Connection connection : connectionUserMap.keySet()) {
            if (connection.isConnected()) {
                connection.sendTCP(message);
            }
        }
    }

    /**
     * Send a message to all connected users except one
     * @param excludeConnection The connection to exclude
     * @param message The message to broadcast
     */
    public void broadcastToAllExcept(Connection excludeConnection, Message message) {
        for (Connection connection : connectionUserMap.keySet()) {
            if (connection != excludeConnection && connection.isConnected()) {
                connection.sendTCP(message);
            }
        }
    }

    /**
     * Clear all connections (used when server shuts down)
     */
    public synchronized void clearAll() {
        System.out.println("Clearing all " + connectionUserMap.size() + " connections");

        // Notify all users about server shutdown if needed
        HashMap<String, Object> body = new HashMap<>();
        body.put("message", "Server is shutting down");
        Message shutdownMessage = new Message(CommandType.DISCONNECT, body);

        for (Connection connection : connectionUserMap.keySet()) {
            try {
                connection.sendTCP(shutdownMessage);
                connection.close();
            } catch (Exception e) {
                // Ignore errors during shutdown
            }
        }

        connectionUserMap.clear();
        usernameConnectionMap.clear();
        connectionIdMap.clear();
    }

    /**
     * Print current connection status (for debugging)
     */
    public void printStatus() {
        System.out.println("=== Connection Manager Status ===");
        System.out.println("Active connections: " + connectionUserMap.size());

        for (Map.Entry<Connection, User> entry : connectionUserMap.entrySet()) {
            Connection conn = entry.getKey();
            User user = entry.getValue();
            System.out.println("  - User: " + user.getUsername() +
                " | Connection ID: " + conn.getID() +
                " | Connected: " + conn.isConnected());
        }

        System.out.println("================================");
    }
}
