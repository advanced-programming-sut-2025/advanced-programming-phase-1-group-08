package com.Graphic.model.ClientServer;

import com.Graphic.model.User;
import com.Graphic.model.SaveData.UserDataBase;
import com.Graphic.model.Enum.Commands.CommandType;
import com.esotericsoftware.kryonet.Connection;

import java.util.HashMap;
import java.util.Random;


public class MenuMessageHandler {

    private static MenuMessageHandler instance;
    private Random random;

    private MenuMessageHandler() {
        random = new Random();
    }

    public static MenuMessageHandler getInstance() {
        if (instance == null) {
            instance = new MenuMessageHandler();
        }
        return instance;
    }


    public void handleMenuMessage(Connection connection, Message message) {
        try {
            switch (message.getCommandType()) {
                case GET_USER_INFO -> handleGetUserInfo(connection, message);
                case CHANGE_USERNAME -> handleChangeUsername(connection, message);
                case CHANGE_NICKNAME -> handleChangeNickname(connection, message);
                case CHANGE_EMAIL -> handleChangeEmail(connection, message);
                case CHANGE_PASSWORD -> handleChangePassword(connection, message);

                case GET_AVATAR_SETTINGS -> handleGetAvatarSettings(connection, message);
                case SAVE_AVATAR_SETTINGS -> handleSaveAvatarSettings(connection, message);
                case RESET_AVATAR_SETTINGS -> handleResetAvatarSettings(connection, message);

                case GET_USER_DATA -> handleGetUserData(connection, message);
                case GET_CRAFTING_DATA -> handleGetCraftingData(connection, message);
                case GET_MARKET_DATA -> handleGetMarketData(connection, message);
                case PURCHASE_ITEM -> handlePurchaseItem(connection, message);
                case LOGOUT -> handleLogout(connection, message);
                case DISCONNECT -> handleDisconnect(connection, message);

                case GENERATE_RANDOM_PASS -> handleGenerateRandomPass(connection, message);
            }
        } catch (Exception e) {
            sendError(connection, "Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void handleGetUserInfo(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not found");
            return;
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("username", user.getUsername());
        body.put("nickname", user.getNickname());
        body.put("email", user.getEmail());
        body.put("maxGold", String.valueOf(user.getMax_point()));  // Changed from getMaxGold()
        body.put("gamesPlayed", String.valueOf(user.getGames_played()));  // Changed from getGamesPlayed()

        Message response = new Message(CommandType.USER_INFO, body);
        connection.sendTCP(response);
    }

    private void handleChangeUsername(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        String newUsername = request.getFromBody("newUsername");

        // Validate new username
        if (newUsername == null || newUsername.trim().isEmpty()) {
            sendError(connection, "Username cannot be empty");
            return;
        }

        if (newUsername.length() < 4 || newUsername.length() > 10) {
            sendError(connection, "Username must be 4-10 characters");
            return;
        }

        // Check if username already exists
        if (UserDataBase.findUserByUsername(newUsername) != null) {
            sendError(connection, "Username already taken");
            return;
        }

        // Update username
        String oldUsername = user.getUsername();
        user.setUsername(newUsername);

        // Update in database
        try {
            UserDataBase.updateUser(oldUsername, user);
            // Update in connection manager
            ConnectionManager.getInstance().updateUser(connection, oldUsername, user);
            sendSuccess(connection, "Username changed successfully");
        } catch (Exception e) {
            sendError(connection, "Failed to update username");
        }
    }

    private void handleChangeNickname(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        String newNickname = request.getFromBody("newNickname");

        if (newNickname == null || newNickname.trim().isEmpty()) {
            sendError(connection, "Nickname cannot be empty");
            return;
        }

        user.setNickname(newNickname);

        try {
            UserDataBase.updateUser(user.getUsername(), user);
            sendSuccess(connection, "Nickname changed successfully");
        } catch (Exception e) {
            sendError(connection, "Failed to update nickname");
        }
    }

    private void handleChangeEmail(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        String newEmail = request.getFromBody("newEmail");

        // Validate email format
        if (newEmail == null || !newEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            sendError(connection, "Invalid email format");
            return;
        }

        user.setEmail(newEmail);

        try {
            UserDataBase.updateUser(user.getUsername(), user);
            sendSuccess(connection, "Email changed successfully");
        } catch (Exception e) {
            sendError(connection, "Failed to update email");
        }
    }

    private void handleChangePassword(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        String oldPassword = request.getFromBody("oldPassword");
        String newPassword = request.getFromBody("newPassword");

        // Verify old password
        if (!user.getPassword().equals(oldPassword)) {
            sendError(connection, "Incorrect current password");
            return;
        }

        // Validate new password
        if (newPassword == null || newPassword.length() < 8) {
            sendError(connection, "Password must be at least 8 characters");
            return;
        }

        user.setPassword(newPassword);

        try {
            UserDataBase.updateUser(user.getUsername(), user);
            sendSuccess(connection, "Password changed successfully");
        } catch (Exception e) {
            sendError(connection, "Failed to update password");
        }
    }


    private void handleGetAvatarSettings(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        HashMap<String, Object> body = new HashMap<>();
        // Default values since these methods don't exist in User yet
        body.put("avatarIndex", "0");  // Default avatar
        body.put("red", "1.0");  // Default color
        body.put("green", "1.0");
        body.put("blue", "1.0");

        Message response = new Message(CommandType.AVATAR_SETTINGS, body);
        connection.sendTCP(response);
    }

    private void handleSaveAvatarSettings(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        try {
            // Since avatar methods don't exist, just save to database
            // You can add these fields to User later
            UserDataBase.updateUser(user.getUsername(), user);
            sendSuccess(connection, "Avatar settings saved");
        } catch (Exception e) {
            sendError(connection, "Failed to save avatar settings");
        }
    }

    private void handleResetAvatarSettings(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        try {
            // Since avatar methods don't exist, just send default values
            UserDataBase.updateUser(user.getUsername(), user);

            HashMap<String, Object> body = new HashMap<>();
            body.put("avatarIndex", "0");
            body.put("red", "1.0");
            body.put("green", "1.0");
            body.put("blue", "1.0");

            Message response = new Message(CommandType.AVATAR_SETTINGS, body);
            connection.sendTCP(response);
        } catch (Exception e) {
            sendError(connection, "Failed to reset avatar settings");
        }
    }


    private void handleGetUserData(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        HashMap<String, Object> body = new HashMap<>();
        body.put("username", user.getUsername());
        body.put("gold", String.valueOf(user.getMoney()));
        // Calculate level based on abilities
        int totalAbility = user.getFarmingAbility() + user.getFishingAbility() +
            user.getForagingAbility() + user.getMiningAbility();
        int level = totalAbility / 100;  // Simple level calculation
        body.put("level", String.valueOf(level));

        Message response = new Message(CommandType.USER_DATA, body);
        connection.sendTCP(response);
    }

    private void handleGetCraftingData(Connection connection, Message request) {
        StringBuilder craftingData = new StringBuilder();

        craftingData.append("Chest: Wood x50;");
        craftingData.append("Fence: Wood x2;");
        craftingData.append("Scarecrow: Wood x50, Coal x1, Fiber x20;");
        craftingData.append("Bee House: Wood x40, Coal x8, Iron Bar x1, Maple Syrup x1;");
        craftingData.append("Keg: Wood x30, Copper Bar x1, Iron Bar x1, Oak Resin x1;");
        craftingData.append("Preserves Jar: Wood x50, Stone x40, Coal x8;");
        craftingData.append("Cheese Press: Wood x45, Stone x45, Hardwood x10, Copper Bar x1;");
        craftingData.append("Loom: Wood x60, Fiber x30, Pine Tar x1;");
        craftingData.append("Oil Maker: Slime x50, Hardwood x20, Gold Bar x1;");
        craftingData.append("Recycling Machine: Wood x25, Stone x25, Iron Bar x1;");

        HashMap<String, Object> body = new HashMap<>();
        body.put("craftingData", craftingData.toString());

        Message response = new Message(CommandType.CRAFTING_DATA, body);
        connection.sendTCP(response);
    }

    private void handleGetMarketData(Connection connection, Message request) {
        // Get market items - this should come from your game data
        StringBuilder marketData = new StringBuilder();

        // Example market items (replace with actual game data)
        marketData.append("Parsnip Seeds,20,parsnip_seeds;");
        marketData.append("Cauliflower Seeds,80,cauliflower_seeds;");
        marketData.append("Potato Seeds,50,potato_seeds;");
        marketData.append("Tulip Bulb,20,tulip_bulb;");
        marketData.append("Kale Seeds,70,kale_seeds;");
        marketData.append("Jazz Seeds,30,jazz_seeds;");
        marketData.append("Garlic Seeds,40,garlic_seeds;");
        marketData.append("Wheat Seeds,10,wheat_seeds;");
        marketData.append("Radish Seeds,40,radish_seeds;");
        marketData.append("Backpack Upgrade,2000,backpack;");
        marketData.append("Copper Watering Can,2000,copper_watering_can;");
        marketData.append("Fishing Rod,500,fishing_rod;");

        HashMap<String, Object> body = new HashMap<>();
        body.put("marketData", marketData.toString());

        Message response = new Message(CommandType.MARKET_DATA, body);
        connection.sendTCP(response);
    }

    private void handlePurchaseItem(Connection connection, Message request) {
        User user = getUserFromConnection(connection);
        if (user == null) {
            sendError(connection, "User not authenticated");
            return;
        }

        String itemId = request.getFromBody("itemId");

        // Get item price (this should come from your item database)
        int itemPrice = getItemPrice(itemId);

        if (user.getMoney() < itemPrice) {
            HashMap<String, Object> body = new HashMap<>();
            body.put("reason", "Not enough gold");
            Message response = new Message(CommandType.PURCHASE_FAILED, body);
            connection.sendTCP(response);
            return;
        }

        // Deduct money
        user.increaseMoney(-itemPrice);  // Use increaseMoney with negative value
        // TODO: Add item to user inventory based on your item system

        try {
            UserDataBase.updateUser(user.getUsername(), user);

            HashMap<String, Object> body = new HashMap<>();
            body.put("itemId", itemId);
            body.put("remainingGold", String.valueOf(user.getMoney()));

            Message response = new Message(CommandType.PURCHASE_SUCCESS, body);
            connection.sendTCP(response);
        } catch (Exception e) {
            sendError(connection, "Purchase failed");
        }
    }

    private void handleLogout(Connection connection, Message request) {
        User user = getUserFromConnection(connection);

        if (user != null) {
            // Save user data before logout
            try {
                UserDataBase.updateUser(user.getUsername(), user);
            } catch (Exception e) {
                System.err.println("Failed to save user data on logout: " + e.getMessage());
            }

            // Remove user from active connections
            ConnectionManager.getInstance().removeConnection(connection);
        }

        // Send logout confirmation
        Message response = new Message(CommandType.LOGOUT, new HashMap<>());
        connection.sendTCP(response);
    }

    private void handleDisconnect(Connection connection, Message request) {
        handleLogout(connection, request);
        // Connection will be closed by the caller
    }


    private void handleGenerateRandomPass(Connection connection, Message request) {
        String randomPass = generateRandomPassword();

        HashMap<String, Object> body = new HashMap<>();
        body.put("Password", randomPass);  // Note: capital P to match your RegisterMenu

        Message response = new Message(CommandType.GENERATE_RANDOM_PASS, body);
        connection.sendTCP(response);
    }

    private String generateRandomPassword() {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*";
        String allChars = upperChars + lowerChars + numbers + specialChars;

        StringBuilder password = new StringBuilder();

        // Ensure at least one of each type
        password.append(upperChars.charAt(random.nextInt(upperChars.length())));
        password.append(lowerChars.charAt(random.nextInt(lowerChars.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Fill rest randomly
        for (int i = 4; i < 12; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the password
        String shuffled = shuffleString(password.toString());
        return shuffled;
    }

    private String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }

    private User getUserFromConnection(Connection connection) {
        return ConnectionManager.getInstance().getUserByConnection(connection);
    }

    private int getItemPrice(String itemId) {
        // Return item price based on itemId
        // This is a simplified example - replace with your actual pricing system
        switch (itemId) {
            case "parsnip_seeds": return 20;
            case "cauliflower_seeds": return 80;
            case "potato_seeds": return 50;
            case "tulip_bulb": return 20;
            case "kale_seeds": return 70;
            case "jazz_seeds": return 30;
            case "garlic_seeds": return 40;
            case "wheat_seeds": return 10;
            case "radish_seeds": return 40;
            case "backpack": return 2000;
            case "copper_watering_can": return 2000;
            case "fishing_rod": return 500;
            default: return 100;
        }
    }

    private void sendError(Connection connection, String errorMessage) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("error", errorMessage);
        Message response = new Message(CommandType.ERROR, body);
        connection.sendTCP(response);
    }

    private void sendSuccess(Connection connection, String successMessage) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("message", successMessage);
        Message response = new Message(CommandType.SUCCESS, body);
        connection.sendTCP(response);
    }
}
