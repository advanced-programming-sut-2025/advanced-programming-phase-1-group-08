package model;

import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.ForagingCropsType;
import model.Enum.AllPlants.TreesProductType;
import model.Enum.Commands.TradeMenuCommands;
import model.Enum.ItemType.AnimalProductType;
import model.Enum.ItemType.MarketItemType;
import model.Places.MarketItem;
import model.Plants.*;

import java.util.*;
import java.util.regex.Matcher;

import static Controller.GameController.isNeighbor;
import static java.lang.Math.floor;
import static java.lang.Math.random;
import static model.App.*;
import static model.Color_Eraser.*;

public class Trade {

    private final User sender;
    private final User receiver;

    private final String senderGivesWhat;
    private final String receiverGivesWhat;
    private boolean responded = false;
    private int id;
    private final char senderGivesPorT;
    private final char receiverGivesPorT;
    private final int senderAmount;
    private final int receiverAmount;

    public Trade(User from, User to, char senderGivesPorT, char receiverGivesPorT, String senderGivesWhat, String receiverGivesWhat, int senderAmount, int receiverAmount) {
        this.sender = from;
        this.receiver = to;
        this.senderGivesWhat = senderGivesWhat;
        this.receiverGivesWhat = receiverGivesWhat;
        this.senderGivesPorT = senderGivesPorT;
        this.receiverGivesPorT = receiverGivesPorT;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.id = (int) (random()*100);
    }

    public static Trade findTradeByID (int id) {
        Trade foundTrade = null;
        for (Map.Entry<Set<User>, List<Trade>> entry : currentGame.trades.entrySet()) {
            List<Trade> tradeList = entry.getValue();
            for (Trade trade : tradeList) {
                if (trade.getId() == id) {
                    foundTrade = trade;
                    break;
                }
            }
            if (foundTrade != null) break;
        }
        return foundTrade;
    }

    public User getReceiver() {
        return receiver;
    }
    public User getSender(){ return sender; }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }

    public void print() {
        System.out.println("Trade ID: " + id + "\n Trade Content: " + sender.getNickname() + " Sends " + senderGivesWhat + " to " + receiver.getNickname() + " to Get " + receiverGivesWhat);
    }

    public boolean isBetween(User u1, User u2) {
        return (sender.equals(u1) && receiver.equals(u2)) || (sender.equals(u2) && receiver.equals(u1));
    }

    public static Result CheckTradeRespond(String input, int ID) {
        if (input.equalsIgnoreCase("exit")) {
            return new Result(false, RED+"OK!"+RESET);
        }

        Matcher matcher = TradeMenuCommands.tradeResponse.getMatcher(input);
        if (matcher == null)
            return new Result(false, RED+"Please Respond in Correct Format!"+RESET);

        String response = matcher.group("response");

        int id = Integer.parseInt(matcher.group("id"));
        if (findTradeByID(id) == null)
            return new Result(false, RED+"Wrong ID!"+RESET);
        if (!(id == ID))
            return new Result(false, RED+"Respond to THIS Trade, Not Other Trades!");

        Trade trade = findTradeByID(id);
        HumanCommunications f = getFriendship(currentGame.currentPlayer, trade.sender);
        if (f == null)
            return new Result(false, RED+"Friendship Not Found!"+RESET);

        if (response.equalsIgnoreCase("reject")) {
            f.reduceXP(30);
            return new Result(true, RED+"Rejected"+RESET + GREEN+" Successfully."+RESET);
        }

        else if (!response.equalsIgnoreCase("accept"))
            return new Result(false, RED+"Please Respond in Correct Format!"+RESET);

        // if Accepted:

        Inventory receiverInventory = currentGame.currentPlayer.getBackPack().inventory;
        char receiver_P_or_T = trade.receiverGivesPorT;

        Inventory senderInventory = trade.sender.getBackPack().inventory;


        if (!(receiver_P_or_T == 't' || receiver_P_or_T == 'p'))
            return new Result(false, RED+"Invalid Trade!"+RESET);



        if (receiver_P_or_T == 'p') { // این فقط برای اروره پایین خودش انجام میشه
            if (currentGame.currentPlayer.getMoney() < trade.receiverAmount) {
                f.reduceXP(30);
                return new Result(false, RED+"Not Enough Money to Accept!"+RESET);
            }
        }

        if (receiver_P_or_T == 't') {
            Items items = AllFromDisplayNames(trade.receiverGivesWhat);
            int amount = trade.receiverAmount;
            boolean iHave = false;
            for (Map.Entry<Items, Integer> entry : receiverInventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (items instanceof MarketItem ) {
                        MarketItemType marketItemType = ((MarketItem) items).getType();
                        if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                            iHave = true;
                            if (entry.getValue() < amount) {
                                f.reduceXP(30);
                                return new Result(false, RED + "Not Enough Item!" + RESET);
                            }
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }

                else if (entry.getKey() instanceof AllCrops) {
                    if (items instanceof AllCrops ) {
                        CropsType cropsType = ((AllCrops) items).getType();
                        if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                            iHave = true;
                            if (entry.getValue() < amount){
                                f.reduceXP(30);
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                            }
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingCrops) {
                    if (items instanceof ForagingCrops ) {
                        ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                        if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                            iHave = true;
                            if (entry.getValue() < amount) {
                                f.reduceXP(30);
                                return new Result(false, RED + "Not Enough Item!" + RESET);
                            }
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }

                else if (entry.getKey() instanceof TreesProdct) {
                    if (items instanceof TreesProdct ) {
                        TreesProductType treesProductType = ((TreesProdct) items).getType();
                        if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                            iHave = true;
                            if (entry.getValue() < amount) {
                                f.reduceXP(30);
                                return new Result(false, RED + "Not Enough Item!" + RESET);
                            }
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }
            }
            if (!iHave)
                return new Result(false, RED+"Item Not Found in Inventory!"+RESET);
            receiverInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);

            boolean done = false;
            for (Map.Entry<Items, Integer> entry : senderInventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (items instanceof MarketItem ) {
                        MarketItemType marketItemType = ((MarketItem) items).getType();
                        if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }

                else if (entry.getKey() instanceof AllCrops) {
                    if (items instanceof AllCrops ) {
                        CropsType cropsType = ((AllCrops) items).getType();
                        if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingCrops) {
                    if (items instanceof ForagingCrops ) {
                        ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                        if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }

                else if (entry.getKey() instanceof TreesProdct) {
                    if (items instanceof TreesProdct ) {
                        TreesProductType treesProductType = ((TreesProdct) items).getType();
                        if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }
            }

            if (!done)
                senderInventory.Items.put(AllFromDisplayNames(trade.receiverGivesWhat), amount);
        }
        if (receiver_P_or_T == 'p') {
            // تبادل پول
            currentGame.currentPlayer.increaseMoney(-trade.receiverAmount);
            trade.sender.increaseMoney(trade.receiverAmount);
        }


        // For Sender
        try {
            if (trade.senderGivesPorT == 'p') {
                currentGame.currentPlayer.increaseMoney(Integer.parseInt(trade.senderGivesWhat));
                trade.sender.increaseMoney(-(Integer.parseInt(trade.senderGivesWhat)));
            }
        } catch (Exception ee) {
            return new Result(false, RED+"Unknown Error"+RESET);
        }
        if (trade.senderGivesPorT == 't') {
            Items items = AllFromDisplayNames(trade.senderGivesWhat);
            int amount = trade.senderAmount;
            for (Map.Entry<Items, Integer> entry : senderInventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (items instanceof MarketItem ) {
                        MarketItemType marketItemType = ((MarketItem) items).getType();
                        if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }

                else if (entry.getKey() instanceof AllCrops) {
                    if (items instanceof AllCrops ) {
                        CropsType cropsType = ((AllCrops) items).getType();
                        if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingCrops) {
                    if (items instanceof ForagingCrops ) {
                        ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                        if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }

                else if (entry.getKey() instanceof TreesProdct) {
                    if (items instanceof TreesProdct ) {
                        TreesProductType treesProductType = ((TreesProdct) items).getType();
                        if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                            senderInventory.Items.put(entry.getKey(), entry.getValue() - amount);
                        }
                    }
                }
            }

            senderInventory.Items.entrySet().removeIf(entry -> entry.getValue()==null || entry.getValue() <= 0);

            boolean done = false;
            for (Map.Entry<Items, Integer> entry : receiverInventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (items instanceof MarketItem ) {
                        MarketItemType marketItemType = ((MarketItem) items).getType();
                        if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }

                else if (entry.getKey() instanceof AllCrops) {
                    if (items instanceof AllCrops ) {
                        CropsType cropsType = ((AllCrops) items).getType();
                        if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingCrops) {
                    if (items instanceof ForagingCrops ) {
                        ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                        if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }

                else if (entry.getKey() instanceof TreesProdct) {
                    if (items instanceof TreesProdct ) {
                        TreesProductType treesProductType = ((TreesProdct) items).getType();
                        if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                            receiverInventory.Items.put(entry.getKey(), entry.getValue() + amount);
                            done = true;
                        }
                    }
                }
            }
            if (!done)
                receiverInventory.Items.put(AllFromDisplayNames(trade.senderGivesWhat), amount);

        }

        f.addXP(50);
        return new Result(true, GREEN+"Accepted Successfully."+RESET);
    }

    public static Result checkTradeRequest(String input, char P_or_T) {
        String username;
        String type;
        String itemName;
        Items items;
        int amount;
        int price = 0;
        String targetItemName = null;
        int targetAmount = 0;
        Items targetItem;
        if (P_or_T == 'p') {
            username = TradeMenuCommands.tradeP.getMatcher(input).group("username");
            type = TradeMenuCommands.tradeP.getMatcher(input).group("type");
            itemName = TradeMenuCommands.tradeP.getMatcher(input).group("item");
            price = Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("price"));
            try {
                amount = Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("amount"));
                price = Integer.parseInt(TradeMenuCommands.tradeP.getMatcher(input).group("price"));
            } catch (Exception e) {
                return new Result(false, RED+"Enter Numbers in The Correct Format!"+RESET);
            }



            items = AllFromDisplayNames(itemName);
            if (items == null)
                return new Result(false, RED+"Incorrect Item Format!"+RESET);
        }
        else if (P_or_T == 't') {
            username = TradeMenuCommands.tradeTI.getMatcher(input).group("username");
            type = TradeMenuCommands.tradeTI.getMatcher(input).group("type");
            itemName = TradeMenuCommands.tradeTI.getMatcher(input).group("item");
            targetItemName = TradeMenuCommands.tradeTI.getMatcher(input).group("targetItem");
            try {
                amount = Integer.parseInt(TradeMenuCommands.tradeTI.getMatcher(input).group("amount"));
                targetAmount = Integer.parseInt(TradeMenuCommands.tradeTI.getMatcher(input).group("targetAmount"));
            } catch (Exception e) {
                return new Result(false, RED+"Enter Numbers in The Correct Format!"+RESET);
            }
            items = AllFromDisplayNames(itemName);
            targetItem = AllFromDisplayNames(targetItemName);
            if (items == null || targetItem == null)
                return new Result(false, RED+"Incorrect Item Format!"+RESET);
        }
        else
            return new Result(false, RED+"Wrong Trade Type!"+RESET);
        if (currentGame.currentPlayer.getUsername().equals(username))
            return new Result(false, RED+"You Can't Trade With Yourself!"+RESET);

        User mentionedPlayer = null;
        boolean userFound = false;
        for (User p: currentGame.players) {
            if (p.getUsername().equals(username)){
                userFound = true;
                mentionedPlayer = p;
                break;
            }
        }
        if (!userFound)
            return new Result(false, RED+"User Not Found!"+RESET);
        if (!isNeighbor(currentGame.currentPlayer.getPositionX(), currentGame.currentPlayer.getPositionY(), mentionedPlayer.getPositionX(), mentionedPlayer.getPositionY()))
            return new Result(false, RED+"Get Closer To Trade!"+RESET);
        if (!(type.trim().equalsIgnoreCase("offer") || type.trim().equalsIgnoreCase("request")))
            return new Result(false, RED+"type Doesn't Match!(offer / request)"+RESET);
        if (P_or_T == 'p' && type.trim().equalsIgnoreCase("request") && price > currentGame.currentPlayer.getMoney())
            return new Result(false, RED+"Not Enough Money For this Request!"+RESET);

        Inventory myInventory = currentGame.currentPlayer.getBackPack().inventory;

        if ((P_or_T == 'p' && type.trim().equalsIgnoreCase("offer")) || P_or_T == 't') {
            boolean found = false;
            for (Map.Entry<Items, Integer> entry : myInventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (items instanceof MarketItem ) {
                        MarketItemType marketItemType = ((MarketItem) items).getType();
                        if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }



                if (entry.getKey() instanceof Animalproduct) {
                    if (items instanceof Animalproduct ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof AllCrops) {
                    if (items instanceof AllCrops ) {
                        CropsType cropsType = ((AllCrops) items).getType();
                        if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingCrops) {
                    if (items instanceof ForagingCrops ) {
                        ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                        if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof TreesProdct) {
                    if (items instanceof TreesProdct ) {
                        TreesProductType treesProductType = ((TreesProdct) items).getType();
                        if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof TreeSource) {
                    if (items instanceof TreeSource ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingMinerals) {
                    if (items instanceof ForagingMinerals ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingSeeds) {
                    if (items instanceof ForagingSeeds ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof GiantProduct) {
                    if (items instanceof GiantProduct ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof MixedSeeds) {
                    if (items instanceof MixedSeeds ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof Wood) {
                    if (items instanceof Wood ) {
                        if (entry.getKey().getName().equals(itemName)) {
                            found = true;
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

            }
            if (!found)
                return new Result(false, RED+"You don't have it in Inventory!"+RESET);
        }


        return new Result(true, "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
