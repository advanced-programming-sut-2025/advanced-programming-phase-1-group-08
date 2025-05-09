package model.Places;

import model.MapThings.GameObject;

public class ShippingBin extends GameObject {
    private static final int width=1;
    private static final int height=1;
    private static final int coinNeeded=250;
    private static final int woodNeeded=150;
    private static int remindInShop=1;

    private final int topLeftX;
    private final int topLeftY;

    public ShippingBin(int topLeftX , int topLeftY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
    }

    public int getTopLeftX() {
        return topLeftX;
    }
    public int getTopLeftY() {
        return topLeftY;
    }
    public static int getWidth() {
        return width;
    }
    public static int getHeight() {
        return height;
    }
    public static int getCoinNeeded() {
        return coinNeeded;
    }
    public static int getWoodNeeded() {
        return woodNeeded;
    }

    public static int getRemindInShop() {
        return remindInShop;
    }

    public static void setRemindInShop() {
        ShippingBin.remindInShop = 1;
    }
    public static void incrementRemindInShop(int amount) {
        ShippingBin.remindInShop += amount;
    }
}
