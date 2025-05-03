package model;

import Controller.GameController;
import Controller.GameController.*;

import java.util.*;

import static Controller.GameController.isNeighbor;
import static model.App.*;
import static model.Color_Eraser.*;

public class HumanCommunications {
    private User player1;
    private User player2;
    private int XP; // 100 for 1, 200 for 2, 300 for 3, 400 for 4
    private int FriendshipLevel;
    private boolean BOUQUETBought = false;
    private boolean SUCCESSFULPropose = false;

    public HumanCommunications(User player1, User player2) {
        this.player1 = player1;
        this.player2 = player2;
        XP = 0;
        FriendshipLevel = 0;
    }

    public boolean involves(User p) {
        return p.equals(player1) || p.equals(player2);
    }
    public boolean isBetween(User u1, User u2) {
        return (u1.equals(player1) && u2.equals(player2)) || (u1.equals(player2) && u2.equals(player1));
    }

    public int getXP() {
        return XP;
    }
    public void setXP(int XP) {
        this.XP = XP;
    }
    public void addXP(int XP) {this.XP += XP;}


    public User getPlayer1() {
        return player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public int getLevel() {
        if (FriendshipLevel == 0 && getXP() >= 100) {
            increaseLevel();
            setXP(getXP() - 100);
        }
        if (FriendshipLevel == 1 && getXP() >= 200) {
            increaseLevel();
            setXP(getXP() - 200);
        }
        if (FriendshipLevel == 2 && getXP() >= 300 && BOUQUETBought) {
            increaseLevel();
            setXP(getXP() - 300);
        }
        if (FriendshipLevel == 3 && getXP() >= 400 && SUCCESSFULPropose) {
            marry();
            setXP(getXP() - 400);
        }
        return FriendshipLevel;
    }
    public void increaseLevel() {
        if (FriendshipLevel < 3)
            FriendshipLevel++;
    }
    public void printInfo() {
        System.out.println(player1.getNickname() + " <-> " + player2.getNickname() + " | FriendshipLevel: " + getLevel() + " | FriendshipXP: " + XP);
    }

    // LEVEL ZERO TASKS
    public void talk(String text) {
        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            System.out.println("You Should " + RED+"Get Closer"+RESET + " in Order to Talk to " + player2 + "!");
            return;
        }

        addXP(10);

        Set<User> key = new HashSet<>(Arrays.asList(player1, player2));
        conversations.putIfAbsent(key, new ArrayList<>());
        conversations.get(key).add(new MessageHandling(player1, player2, text));

    }
    public void talkingHistory() {
        Set<User> key = new HashSet<>(Arrays.asList(player1, player2));
        List<MessageHandling> messages = conversations.getOrDefault(key, new ArrayList<>());
        System.out.println("📜 Chat between " + player1.getNickname() + " and " + player2.getNickname() + ":");
        for (MessageHandling m : messages) {
            m.print();
        }
    }
    public void trade() {

    }

    // LEVEL ONE TASKS
    public void sendGifts() {
        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            System.out.println("You Should " + RED+"Get Closer"+RESET + " in Order to Send Gift to " + player2 + "!");
            return;
        }
        if (getLevel() < 1) {
            System.out.println("You can't Send Gifts in your Current " + RED+"Friendship Level"+RESET + ".");
            return;
        }

        //TODO
    }

    // LEVEL TWO TASKS
    public void Hug() {
        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            System.out.println("You Should " + RED+"Get Closer"+RESET + " in Order to Hug " + player2 + "!");
            return;
        }
        if (getLevel() < 2) {
            System.out.println("You can't Hug in your Current " + RED+"Friendship Level"+RESET + ".");
            return;
        }

        System.out.println(GREEN+"You Hugged " + player2 + "."+RESET);
        addXP(60);
        getLevel(); // to get Updated
    }
    public void buyFlowers() {
        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            System.out.println("You Should " + RED+"Get Closer"+RESET + " in Order to Buy Flower for " + player2 + "!");
            return;
        }
        if (getLevel() < 2) {
            System.out.println("You can't Buy Flower in your Current " + RED+"Friendship Level"+RESET + ".");
            return;
        }
        //TODO
    }

    // LEVEL THREE TASKS
    public void propose() {
        // TODO جنسیت هاشونو چک کن

        if (getLevel() < 3) {
            System.out.println("You Can't Propose in your Current Friendship Level.");
            return;
        }
        //TODO
    }

    // LEVEL FOUR
    public void marry() {
        FriendshipLevel = 4;
        //TODO زمین ها و پولهاشون مال هردو میشه
    }

    // TODO اون پاراگراف بین سطح های دوستی و تعاملات
}
