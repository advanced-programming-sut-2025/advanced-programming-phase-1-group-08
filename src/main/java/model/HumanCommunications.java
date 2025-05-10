package model;

import model.Enum.AllPlants.ForagingCropsType;
import model.Plants.ForagingCrops;

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
    //TODO وقتی مکانیزم کاهش لول رو زدی یادت باشه این بولین های اول رو فالس کنی با کم شدن لول
    public void updateLevel() {
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
    }
    public int getLevel() {
        updateLevel();
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
    public Result talk(String text) {

        User me = currentPlayer;
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Talk to " + other.getNickname() + "!"+RESET);
        }

        addXP(10);

        Set<User> key = new HashSet<>(Arrays.asList(me, other));
        conversations.putIfAbsent(key, new ArrayList<>());
        conversations.get(key).add(new MessageHandling(me, other, text));

        return new Result(true, GREEN+"You Sent a Message to " + other + "."+RESET);
    }
    public Result talkingHistory() {
        Set<User> key = new HashSet<>(Arrays.asList(player1, player2));
        List<MessageHandling> messages = conversations.getOrDefault(key, new ArrayList<>());
        System.out.println("📜 Chat between " + player1.getNickname() + " and " + player2.getNickname() + ":");
        for (MessageHandling m : messages) {
            m.print();
        }
        return new Result(true, GREEN+"Successfully Displayed."+RESET);
    }
//    public Result trade(String type, String iGive, int iGiveAmount, String iGet, int iGetAmount) {
//        User me = currentPlayer;
//        User other;
//        if (player1.getUsername().equals(currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
//        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
//            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Send Trade Offer to " + other.getNickname() + "!"+RESET);
//        }
//
//
//    } //TODO اگه بازیکن مورد نظر وجود نداشت پیغام مناسب چاپ بشه و اگه مقدار یا آیتم نامعتبر بودن ارور بده
//
//    // LEVEL ONE TASKS
//    public Result sendGifts(String username, String item, int amount) {
//        User other;
//        if (player1.getUsername().equals(currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
//        Inventory myInventory = currentPlayer.getBackPack().inventory;
//        Inventory otherInventory = other.getBackPack().inventory;
//
//        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
//            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Send Gift to " + other.getNickname() + "!"+RESET);
//        }
//        if (getLevel() < 1) {
//
//            return new Result(false, RED+"You can't Send Gifts in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
//        }
//
//        //TODO تو کوله پشتیت پیداش کن- برش دار- بده بهش- مجبورش کن ریت کنه
//    }

    // LEVEL TWO TASKS
    public Result Hug() {

        User me = currentPlayer;
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Hug " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 2) {
            return new Result(false, RED+"You can't Hug in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }



        addXP(60);
        getLevel(); // to get Updated
        return new Result(true, GREEN+"You Hugged " + other.getNickname() + "."+RESET);
    }
    public Result buyFlowers() {
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should Get Closer in Order to Buy Flower for " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 2) {
            return new Result(false, RED+"You can't Buy Flower in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }

        boolean IHaveDandelions = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry instanceof ForagingCrops) {
                if (((ForagingCrops) entry).getType().equals(ForagingCropsType.Dandelion)) {
                    IHaveDandelions = true;
                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                    break;
                }
            }
        }
        if (!IHaveDandelions)
            return new Result(false, RED+"You Don't Have Dandelions to Give!"+RESET);


        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
            if (entry instanceof ForagingCrops) {
                if (((ForagingCrops)entry).getType().equals(ForagingCropsType.Dandelion)) {
                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
                    return new Result(true, GREEN+"You Gave Dandelions to " + other.getNickname()+RESET);
                }
            }
        }

        otherInventory.Items.put(new ForagingCrops(ForagingCropsType.Dandelion), 1);
        return new Result(true, GREEN+"You Gave Dandelions to " + other.getNickname()+RESET);
    }

    // LEVEL THREE TASKS
    public Result propose() {
        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (currentPlayer.getGender().equals(other.getGender()))
            return new Result(false, RED+"No Gay Marriage in Iran!"+RESET);

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, PURPLE+"Get Closer Honey!"+RESET);
        }

        if (getLevel() < 3)
            return new Result(false, RED+"You Can't Propose in your Current Friendship Level."+RESET);

        boolean IHaveRing = false;
//        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
//            if (entry instanceof ?????) {
//                if (((????????) entry).getType().equals(MarketItem.WeddingRing)) {
//                    IHaveRing = true;
//                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1);
//                    if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
//                    break;
//                }
//            }
//        }
        if (!IHaveRing)
            return new Result(false, RED+"You Don't Have Ring to Propose!"+RESET);


        return new Result(true, GREEN+"You Proposed Successfully!"+RESET);

        //TODO
    }

    // LEVEL FOUR
    public Result marry() { //spouse!!!
        if (!SUCCESSFULPropose)
            return new Result(false, RED+"Your Propose Hasn't been Accepted Yet!"+RESET);

        User other;
        if (player1.getUsername().equals(currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory otherInventory = other.getBackPack().inventory;

//        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
//            if (entry instanceof MarketItem) {
//                if (((MarketItem)entry).getType().equals(MarketItem.WeddingRing)) {
//                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
//                    return new Result(false, "HAPPY MARRIAGE!");
//                }
//            }
//        }


        //todo خط پایینو ادیت کن
        otherInventory.Items.put(new ForagingCrops(ForagingCropsType.Dandelion), 1);
        return new Result(true, GREEN+"You Gave Dandelions to " + other.getNickname()+RESET);


        //FriendshipLevel = 4;
        //TODO زمین ها و پولهاشون مال هردو میشه


    }

    // TODO اون پاراگراف بین سطح های دوستی و تعاملات
}
