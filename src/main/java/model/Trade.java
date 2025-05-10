package model;

import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.ForagingCropsType;
import model.Enum.AllPlants.TreesProductType;
import model.Enum.Commands.TradeMenuCommands;
import model.Enum.ItemType.MarketItemType;
import model.Plants.AllCrops;
import model.Plants.ForagingCrops;
import model.Plants.TreesProdct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.App.*;
import static model.App.currentPlayer;
import static model.Color_Eraser.RED;
import static model.Color_Eraser.RESET;

public class Trade {
    public static List<User> tradeUsers = new ArrayList<>();

    private User accountParty; // طرف حساب ترید کدوم پلیره؟
    // تابع make offer و receive offer

    public static Result checkTradeRequest(String input, char P_or_T) { // TODO make id   چک کن اگه موقع قبول کردن آفر فقططط آفر پول نداشت ارور بده
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
        else {
            username = TradeMenuCommands.tradeTI.getMatcher(input).group("username");
            type = TradeMenuCommands.tradeTI.getMatcher(input).group("type");
            itemName = TradeMenuCommands.tradeP.getMatcher(input).group("item");
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

        if (currentPlayer.getUsername().equals(username))
            return new Result(false, RED+"You Can't Trade With Yourself!"+RESET);

        boolean userFound = false;
        for (User p: players) {
            if (p.getUsername().equals(username)){
                userFound = true;
                break;
            }
        }
        if (!userFound)
            return new Result(false, RED+"User Not Found!"+RESET);
        if (!(type.trim().equalsIgnoreCase("offer") || type.trim().equalsIgnoreCase("request")))
            return new Result(false, RED+"Type Doesn't Match!"+RESET);
        if (P_or_T == 'p' && type.trim().equalsIgnoreCase("request") && price > currentPlayer.getMoney())
            return new Result(false, RED+"Not Enough Money For this Request!"+RESET);


        Inventory myInventory = currentPlayer.getBackPack().inventory;

        if ((P_or_T == 'p' && type.trim().equalsIgnoreCase("offer")) || P_or_T == 't') {
            for (Map.Entry<Items, Integer> entry : myInventory.Items.entrySet()) {
                if (entry.getKey() instanceof MarketItem) {
                    if (items instanceof MarketItem ) {
                        MarketItemType marketItemType = ((MarketItem) items).getType();
                        if (marketItemType.equals(((MarketItem) entry.getKey()).getType())) {
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof AllCrops) {
                    if (items instanceof AllCrops ) {
                        CropsType cropsType = ((AllCrops) items).getType();
                        if (cropsType.equals(((AllCrops) entry.getKey()).getType())) {
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof ForagingCrops) {
                    if (items instanceof ForagingCrops ) {
                        ForagingCropsType foragingCropsType = ((ForagingCrops) items).getType();
                        if (foragingCropsType.equals(((ForagingCrops) entry.getKey()).getType())) {
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

                else if (entry.getKey() instanceof TreesProdct) {
                    if (items instanceof TreesProdct ) {
                        TreesProductType treesProductType = ((TreesProdct) items).getType();
                        if (treesProductType.equals(((TreesProdct) entry.getKey()).getType())) {
                            if (entry.getValue() < amount)
                                return new Result(false, RED+"Not Enough Item!"+RESET);
                        }
                    }
                }

            }
        }


        return new Result(true, "");
    }
}
