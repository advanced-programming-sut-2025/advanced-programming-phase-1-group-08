package com.Graphic.model;

import com.Graphic.model.Enum.ItemType.MarketItemType;
import com.Graphic.model.HelpersClass.Result;
import com.Graphic.model.Places.MarketItem;

import java.util.*;

import static com.Graphic.Controller.MainGame.GameControllerLogic.isNeighbor;
import static com.Graphic.model.App.*;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;


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


    public void addXP(int XP) {
        this.XP += XP;
        updateLevel();
    }
    public void reduceXP(int XP) {
        this.XP -= XP;
        updateLevel();
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
        if (FriendshipLevel == 2 && getXP() >= 300 && !BOUQUETBought) {
            setXP(300);
        }
        if (FriendshipLevel == 3 && getXP() >= 400 && SUCCESSFULPropose) {
//            marry();
            setXP(getXP() - 400);
        }
        if (FriendshipLevel == 3 && getXP() >= 400 && !SUCCESSFULPropose) {
            setXP(400);
        }


        if (FriendshipLevel == 3 && getXP() < 0) {
            decreaseLevel();
            setXP(300 + getXP());
            BOUQUETBought = false;
        }
        if (FriendshipLevel == 2 && getXP() < 0) {
            decreaseLevel();
            setXP(200 + getXP());
        }
        if (FriendshipLevel == 1 && getXP() < 0) {
            decreaseLevel();
            setXP(100 + getXP());
        }
        if (FriendshipLevel == 0 && getXP() < 0)
            setXP(0);

        if (FriendshipLevel == 4)
            setXP(0);


    }
    public int getLevel() {
        updateLevel();
        return FriendshipLevel;
    }
    public void increaseLevel() {
        if (FriendshipLevel < 3)
            FriendshipLevel++;
    }
    public void decreaseLevel() {
        if (FriendshipLevel < 4)
            FriendshipLevel--;
    }
    public String printInfo() {
        updateLevel();
        return player1.getNickname() + " & " + player2.getNickname() + " | Level: " + getLevel() + " | XP: " + XP;
    }

    // LEVEL ZERO TASKS
//    public Result talk(String text) {
//
//        User me = currentGame.currentPlayer;
//        User other;
//        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
////        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
////            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Talk to " + other.getNickname() + "!"+RESET);
////        }
//
//        addXP(10);
//        if (currentGame.currentPlayer.getSpouse() != null)
//            if (currentGame.currentPlayer.getSpouse().equals(other)) {
//                currentGame.currentPlayer.increaseHealth(50);
//                currentGame.currentPlayer.getSpouse().increaseHealth(50);
//            }
//
//        updateLevel();
//
//        Set<User> key = new HashSet<>(Arrays.asList(me, other));
//        currentGame.conversations.putIfAbsent(key, new ArrayList<>());
//        currentGame.conversations.get(key).add(new MessageHandling(me, other, text));
//
//        return new Result(true, GREEN+"You Sent a Message to " + other.getNickname() + "."+RESET);
//    }
    public Result talkingHistory() {
        Set<User> key = new HashSet<>(Arrays.asList(player1, player2));
        List<MessageHandling> messages = currentGame.conversations.getOrDefault(key, new ArrayList<>());
        System.out.println("📜 Chat between " + player1.getNickname() + " and " + player2.getNickname() + ":");
        for (MessageHandling m : messages) {
            m.print();
        }
        return new Result(true, GREEN+"Successfully Displayed."+RESET);
    }

    // LEVEL ONE TASKS
//    public Result rateGifts(){
//        Scanner scanner = new Scanner(System.in);
//        int rate = scanner.nextInt();
//        if (!(rate >= 1 && rate <= 5)) {
//            System.out.println(RED + "Just Enter a Digit! (1 to 5)" + RESET);
//            return new Result(false, RED + "Just Enter a Digit! (1 to 5)" + RESET);
//        }
//
//        int x = ((rate - 3) * 30) + 15;
//        setXP(getXP() + x);
//        updateLevel();
//
//        return new Result(true, GREEN+"Rated Successfully."+RESET);
//    }
//    public Result sendGifts(String username, String item, int amount) {
//        User other;
//        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
//        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
//        Inventory otherInventory = other.getBackPack().inventory;
//
////        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
////            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Send Gift to " + other.getNickname() + "!"+RESET);
////        }
//        if (getLevel() < 1) {
//
//            return new Result(false, RED+"You can't Send Gifts in your Current Friendship Level."+RESET);
//        }
//
//        Items items = AllFromDisplayNames(item);
//
//
//
//        if (myInventory.Items.containsKey(items)) {
//            int x = myInventory.Items.get(items);
//            if (x < amount) {
//                return new Result(false , RED + "Not Enough Item!" + RESET);
//            }
//            else
//                myInventory.Items.compute(items, (k,v) -> v - amount);
//        }
//        else
//            return new Result(false, RED+"You Don't Have it to Give!"+RESET);
//        myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
//
//        if (otherInventory.Items.containsKey(items)) {
//
//            myInventory.Items.compute(items, (k,v) -> v + amount);
//            if (currentGame.currentPlayer.getSpouse() != null)
//                if (currentGame.currentPlayer.getSpouse().equals(other)) {
//                    currentGame.currentPlayer.increaseHealth(50);
//                    currentGame.currentPlayer.getSpouse().increaseHealth(50);
//                }
//            return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
//
//        }
//
//        otherInventory.Items.put(items, amount);
//
//        if (currentGame.currentPlayer.getSpouse() != null)
//            if (currentGame.currentPlayer.getSpouse().equals(other)) {
//                currentGame.currentPlayer.increaseHealth(50);
//                currentGame.currentPlayer.getSpouse().increaseHealth(50);
//            }
//        // اکس پی ها جای دیگه اد شده
//
//        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
//    }

    // LEVEL TWO TASKS
//    public Result Hug() {
//
//        User other;
//        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
////        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
////            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Hug " + other.getNickname() + "!"+RESET);
////        }
//        if (getLevel() < 2) {
//            return new Result(false, "You can't Hug in your Current Friendship Level.");
//        }
//
//
//
//        addXP(60);
//        if (currentGame.currentPlayer.getSpouse() != null)
//            if (currentGame.currentPlayer.getSpouse().equals(other)) {
//                currentGame.currentPlayer.increaseHealth(50);
//                currentGame.currentPlayer.getSpouse().increaseHealth(50);
//            }
//        updateLevel(); // to get Updated
//        return new Result(true, "You Hugged " + other.getNickname() + ".");
//    }
//    public Result buyFlowers() {
//        User other;
//        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
//        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
//        Inventory otherInventory = other.getBackPack().inventory;
//
//        //if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) return new Result(false, RED+"You Should Get Closer in Order to Buy Flower for " + other.getNickname() + "!"+RESET);
//        if (getLevel() < 2) return new Result(false, "You can't Buy Flower in your Current " + "Friendship Level" + ".");
//        if (getXP() < 300) return new Result(false, "You Have to Reach 300 XPs to Give Bouquet!");
//
//
//        MarketItem marketItem = new MarketItem(MarketItemType.Bouquet);
//        if (myInventory.Items.containsKey(marketItem)) {
//            myInventory.Items.compute(marketItem , (k,v) -> v-1);
//            myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
//        }
//        else {
//            return new Result(false, "You Don't Have Bouquet to Give!");
//        }
//
//
//
//        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
//            if (entry instanceof MarketItem) {
//                if (((MarketItem)entry).getType().equals(MarketItemType.Bouquet)) {
//                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
//                    return new Result(true, "You Gave Bouquet to " + other.getNickname());
//                }
//            }
//        }
//
//        otherInventory.Items.put(new MarketItem(MarketItemType.Bouquet), 1);
//
//        BOUQUETBought = true;
//        updateLevel();
//
//        if (currentGame.currentPlayer.getSpouse() != null)
//            if (currentGame.currentPlayer.getSpouse().equals(other)) {
//                currentGame.currentPlayer.increaseHealth(50);
//                currentGame.currentPlayer.getSpouse().increaseHealth(50);
//            }
//
//        return new Result(true, "You Gave Bouquet to " + other.getNickname());
//    }

    // LEVEL THREE TASKS
//    public Result propose() {
//        User other;
//        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
//            other = player2;
//        else
//            other = player1;
//
//        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
//        Inventory otherInventory = other.getBackPack().inventory;
//
//        if (currentGame.currentPlayer.getGender().equalsIgnoreCase("female"))
//            return new Result(false, RED+"Proposal is a Guy job!"+RESET);
//        if (currentGame.currentPlayer.getGender().equalsIgnoreCase(other.getGender()))
//            return new Result(false, RED+"No Gay Marriage in Islamic Village!"+RESET);
////        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
////            return new Result(false, PURPLE+"Get Closer Honey!"+RESET);
////        }
//
//        if (getLevel() < 3)
//            return new Result(false, RED+"You Can't Propose in your Current Friendship Level."+RESET);
//        if (getXP() < 300)
//            return new Result(false, RED+"You Have to Reach 400 XPs to Propose!"+RESET);
//
//        MarketItem marketItem = new MarketItem(MarketItemType.WeddingRing);
//        if (myInventory.Items.containsKey(marketItem)) {
////            myInventory.Items.compute(marketItem , (k,v) -> v-1); اینجا کم نشه!! //todo
////            myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
//        }
//        else {
//            return new Result(false, RED+"You Don't Have Wedding Ring to Propose!"+RESET);
//        }
//
//        Marriage.sendProposal(currentGame.currentPlayer, other);
//        return new Result(true, GREEN+"You Proposed Successfully!"+RESET);
//    }

//    // LEVEL FOUR
//    public Result marry() {
//        //current player -> wife
//        //other player -> man
//        if (!SUCCESSFULPropose) {
//            return new Result(false, RED+"Proposal Hasn't been Accepted Yet!"+RESET);
//        }
//
//        User man;
//        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
//            man = player2;
//        else
//            man = player1;
//
//        Inventory manInventory = man.getBackPack().inventory;
//        Inventory wifeInventory = currentGame.currentPlayer.getBackPack().inventory;
//
//        MarketItem marketItem = new MarketItem(MarketItemType.WeddingRing);
//        if (manInventory.Items.containsKey(marketItem)) {
//            manInventory.Items.compute(marketItem , (k,v) -> v-1);
//            manInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);
//        }
//        else {
//            return new Result(false, RED+"Sorry! He Lost the Ring, We have to Call it Off :("+RESET);
//        }
//        wifeInventory.Items.put(new MarketItem(MarketItemType.WeddingRing), 1);
//
//        currentGame.currentPlayer.setSpouse(man);
//        man.setSpouse(currentGame.currentPlayer);
//
//
//        FriendshipLevel = 4;
//        setXP(0);
//        int totalMoney = currentGame.currentPlayer.getMoney() + man.getMoney();
//        currentGame.currentPlayer.setMoney(-currentGame.currentPlayer.getMoney()); // اول صفرش کنم
//        currentGame.currentPlayer.setMoney(totalMoney/2);
//        man.setMoney(-man.getMoney()); // اول صفرش کنم
//        man.setMoney(totalMoney/2);
//
//        return new Result(true, GREEN+"Congrats! I Announce You Man and Wife:)"+RESET);
//    }

    public User getPlayer1() {
        return player1;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    public User getPlayer2() {
        return player2;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public int getFriendshipLevel() {
        return FriendshipLevel;
    }

    public void setFriendshipLevel(int friendshipLevel) {
        FriendshipLevel = friendshipLevel;
    }

    public boolean isBOUQUETBought() {
        return BOUQUETBought;
    }

    public void setBOUQUETBought(boolean BOUQUETBought) {
        this.BOUQUETBought = BOUQUETBought;
    }

    public boolean isSUCCESSFULPropose() {
        return SUCCESSFULPropose;
    }

    public void setSUCCESSFULPropose(boolean SUCCESSFULPropose) {
        this.SUCCESSFULPropose = SUCCESSFULPropose;
    }
}
