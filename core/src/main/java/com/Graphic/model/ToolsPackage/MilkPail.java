package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ItemType.MarketType;

public class MilkPail extends Tools {

    public static final int coinNeeded=1000;
    public static int remindInShop=1;

    public MilkPail(){
        super("MilkPail");
    }

    @Override
    public String getName() {
        return "MilkPail";
    }


    public int getRemindInShop(MarketType marketType) {
        return remindInShop;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        remindInShop = amount;
    }

    public static void increaseRemindInShop(int amount) {
        remindInShop += amount;
    }


    @Override
    public int healthCost() { // TODO
        return 0;
    }
    @Override
    public String getInventoryIconPath() {
        return "Erfan/Tools/Milk_Pail.png";
    }

    @Override
    public String getIcon() {
        return "Mohamadreza/Milk_Pail.png";
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 1000;
    }
}
