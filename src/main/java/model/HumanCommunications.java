package model;

import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.ForagingCropsType;
import model.Enum.AllPlants.TreesProductType;
import model.Enum.ItemType.MarketItemType;
import model.Places.MarketItem;
import model.Plants.AllCrops;
import model.Plants.ForagingCrops;
import model.Plants.TreesProdct;

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

    public void addXP(int XP) {
        this.XP += XP;
        updateLevel();
    }
    public void reduceXP(int XP) {
        this.XP -= XP;
        updateLevel();
    }


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


        if (FriendshipLevel == 3 && getXP() < 0) {
            decreaseLevel();
            setXP(300 - getXP());
            BOUQUETBought = false;
        }
        if (FriendshipLevel == 2 && getXP() < 0) {
            decreaseLevel();
            setXP(200 - getXP());
        }
        if (FriendshipLevel == 1 && getXP() < 0) {
            decreaseLevel();
            setXP(100 - getXP());
        }
        if (FriendshipLevel == 0 && getXP() < 0)
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
    public void printInfo() {
        updateLevel();
        System.out.println(player1.getNickname() + " <-> " + player2.getNickname() + " | FriendshipLevel: " + getLevel() + " | FriendshipXP: " + XP);
    }

    // LEVEL ZERO TASKS
    public Result talk(String text) {

        User me = currentGame.currentPlayer;
        User other;
        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Talk to " + other.getNickname() + "!"+RESET);
        }

        addXP(10);
        if (currentGame.currentPlayer.getSpouse().equals(other)) addXP(50);
        updateLevel();

        Set<User> key = new HashSet<>(Arrays.asList(me, other));
        currentGame.conversations.putIfAbsent(key, new ArrayList<>());
        currentGame.conversations.get(key).add(new MessageHandling(me, other, text));

        return new Result(true, GREEN+"You Sent a Message to " + other + "."+RESET);
    }
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
    public Result rateGifts(){
        Scanner scanner = new Scanner(System.in);
        int rate = scanner.nextInt();
        if (!(rate >= 1 && rate <= 5))
            return new Result(false, RED+"Just Enter a Digit! (1 to 5)"+RESET);

        int x = ((rate - 3) * 30) + 15;
        setXP(getXP() + x);
        updateLevel();

        return new Result(true, GREEN+"Rated Successfully."+RESET);
    }
    public Result sendGifts(String username, String item, int amount) {
        User other;
        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should " + RED+"Get Closer"+RESET + " in Order to Send Gift to " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 1) {

            return new Result(false, RED+"You can't Send Gifts in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }

        Items items = AllFromDisplayNames(item);

        boolean IHaveThat = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry.getKey() instanceof MarketItem) {
                if (items instanceof MarketItem ) {
                    MarketItemType marketItemType = ((MarketItem) items).getType();
                    if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {

                        if (entry.getValue() < amount)
                            return new Result(false, RED+"Not Enough Item!"+RESET);

                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        break;
                    }
                }
            }
            else if (entry.getKey() instanceof AllCrops) {
                if (items instanceof AllCrops ) {
                    CropsType cropsType = ((AllCrops) items).getType();
                    if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {

                        if (entry.getValue() < amount)
                            return new Result(false, RED+"Not Enough Item!"+RESET);

                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        break;
                    }
                }
            }
            else if (entry.getKey() instanceof ForagingCrops) {
                if (items instanceof ForagingCrops ) {
                    ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                    if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {

                        if (entry.getValue() < amount)
                            return new Result(false, RED+"Not Enough Item!"+RESET);

                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        break;
                    }
                }
            }
            else if (entry.getKey() instanceof TreesProdct) {
                if (items instanceof TreesProdct ) {
                    TreesProductType treesProductType = ((TreesProdct) items).getType();
                    if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {

                        if (entry.getValue() < amount)
                            return new Result(false, RED+"Not Enough Item!"+RESET);

                        IHaveThat = true;
                        myInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        break;
                    }
                }
            }
        }


        myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);



        if (!IHaveThat)
            return new Result(false, RED+"You Don't Have it to Give!"+RESET);



        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
            if (entry.getKey() instanceof MarketItem) {
                if (items instanceof MarketItem ) {
                    MarketItemType marketItemType = ((MarketItem) items).getType();
                    if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
            else if (entry.getKey() instanceof AllCrops) {
                if (items instanceof AllCrops ) {
                    CropsType cropsType = ((AllCrops) items).getType();
                    if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
            else if (entry.getKey() instanceof ForagingCrops) {
                if (items instanceof ForagingCrops ) {
                    ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                    if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
            else if (entry.getKey() instanceof TreesProdct) {
                if (items instanceof TreesProdct ) {
                    TreesProductType treesProductType = ((TreesProdct) items).getType();
                    if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                        otherInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
                    }
                }
            }
        }


        otherInventory.Items.put(items, amount);

        if (currentGame.currentPlayer.getSpouse().equals(other)) addXP(50); // بقیش جای دیگه اد شده

        return new Result(true, GREEN+"You Sent it to " + other.getNickname()+RESET);
    }

    // LEVEL TWO TASKS
    public Result Hug() {

        User other;
        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
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
        if (currentGame.currentPlayer.getSpouse().equals(other)) addXP(50);
        updateLevel(); // to get Updated
        return new Result(true, GREEN+"You Hugged " + other.getNickname() + "."+RESET);
    }
    public Result buyFlowers() {
        User other;
        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, RED+"You Should Get Closer in Order to Buy Flower for " + other.getNickname() + "!"+RESET);
        }
        if (getLevel() < 2) {
            return new Result(false, RED+"You can't Buy Flower in your Current " + RED+"Friendship Level"+RESET + "."+RESET);
        }

        boolean IHaveBouquet = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem) entry).getType().equals(MarketItemType.Bouquet)) {
                    IHaveBouquet = true;
                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    if (entry.getValue() == 0) myInventory.Items.remove(entry.getKey());
                    break;
                }
            }
        }
        if (!IHaveBouquet)
            return new Result(false, RED+"You Don't Have Bouquet to Give!"+RESET);


        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem)entry).getType().equals(MarketItemType.Bouquet)) {
                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
                    return new Result(true, GREEN+"You Gave Bouquet to " + other.getNickname()+RESET);
                }
            }
        }

        otherInventory.Items.put(new MarketItem(MarketItemType.Bouquet), 1);

        setXP(300);
        BOUQUETBought = true;
        updateLevel();

        return new Result(true, GREEN+"You Gave Bouquet to " + other.getNickname()+RESET);
    }

    // LEVEL THREE TASKS
    public Result propose() {
        User other;
        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;
        Inventory otherInventory = other.getBackPack().inventory;

        if (currentGame.currentPlayer.getGender().equalsIgnoreCase("female"))
            return new Result(false, RED+"Proposal is a Guy job!"+RESET);
        if (currentGame.currentPlayer.getGender().equalsIgnoreCase(other.getGender()))
            return new Result(false, RED+"No Gay Marriage in Islamic Village!"+RESET);

        if (!isNeighbor(player1.getPositionX(), player1.getPositionY(), player2.getPositionX(), player2.getPositionY())) {
            return new Result(false, PURPLE+"Get Closer Honey!"+RESET);
        }

        if (getLevel() < 3)
            return new Result(false, RED+"You Can't Propose in your Current Friendship Level."+RESET);

        boolean IHaveRing = false;
        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem) entry).getType().equals(MarketItemType.WeddingRing)) {
                    IHaveRing = true;
//                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1); ممکنه قبول نکنه اینجا جاش نیست
                    break;
                }
            }
        }
        myInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);

        if (!IHaveRing)
            return new Result(false, RED+"You Don't Have Ring to Propose!"+RESET);

        Marriage.sendProposal(currentGame.currentPlayer, other);
        return new Result(true, GREEN+"You Proposed Successfully!"+RESET);
    }

    // LEVEL FOUR
    public void marry() { //spouse!!!
        if (!SUCCESSFULPropose) {
            System.out.println(RED+"Your Propose Hasn't been Accepted Yet!"+RESET);
            return;
        }

        User other;
        if (player1.getUsername().equals(currentGame.currentPlayer.getUsername()))
            other = player2;
        else
            other = player1;

        currentGame.currentPlayer.setSpouse(other);
        other.setSpouse(currentGame.currentPlayer);

        Inventory otherInventory = other.getBackPack().inventory;
        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;

        FriendshipLevel = 4;
        setXP(0);
        int totalMoney = currentGame.currentPlayer.getMoney() + other.getMoney();
        currentGame.currentPlayer.setMoney(totalMoney/2);
        other.setMoney(totalMoney/2);

        for (Map.Entry <Items , Integer> entry : myInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem)entry).getType().equals(MarketItemType.WeddingRing)) {
                    myInventory.Items.put(entry.getKey(), entry.getValue() - 1);
                    break;
                }
            }
        }

        for (Map.Entry <Items , Integer> entry : otherInventory.Items.entrySet() ) {
            if (entry instanceof MarketItem) {
                if (((MarketItem)entry).getType().equals(MarketItemType.WeddingRing)) {
                    otherInventory.Items.put(entry.getKey(), entry.getValue() + 1);
                    return;
                }
            }
        }


        otherInventory.Items.put(new MarketItem(MarketItemType.WeddingRing), 1);

    }

}
