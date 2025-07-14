package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ItemType.MarketType;

public class Shear extends Tools {

    public static final int coinNeeded = 1000;
    private static int remindInShop = 1;

    public Shear(){
        super("Shear");
    }



    public void use (){}

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

    public static int getCoinNeeded() {
        return coinNeeded;
    }
    public static int getRemindInshop() {
        return remindInShop;
    }

    @Override
    public int healthCost() { // TODO
        return 0;
    }

    @Override
    public String getInventoryIconPath() {
        return "Erfan/Tools/Shears.png";
    }

    @Override
    public String getName() {
        return "Shear";
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
