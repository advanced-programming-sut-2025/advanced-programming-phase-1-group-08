package com.Graphic.model.Places;


import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.Tile;

import java.util.HashMap;

//import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.model.HelpersClass.Color_Eraser.RED;


public  class ShippingBin extends Items {
    private static final int width=1;
    private static final int height=1;
    private static final int coinNeeded=250;
    private static final int woodNeeded=150;
    private static int remindInShop=1;

    private int topLeftX;
    private int topLeftY;

    public HashMap<Items, Integer> binContents=new HashMap<>();


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

    public static ShippingBin isNearShippingBin() {
        int [] dirx = {0, 0, 1, 1, 1,-1,-1,-1};
        int [] diry = {1,-1, 0, 1,-1, 0, 1,-1};

        for (int i = 0 ; i<dirx.length ; i++) {
                Tile tile =null; //getTileByCoordinates(App.currentGame.currentPlayer.getPositionX() +dirx[i], App.currentGame.currentPlayer.getPositionY() + diry[i]);
                if (tile == null) {
                    continue;
                }
                if (tile.getGameObject() instanceof ShippingBin) {
                    return (ShippingBin) tile.getGameObject();
                }
        }
        return null;
    }

    @Override
    public String getIcon() {
        return "Mohamadreza/Shipping Bin.png";
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return remindInShop;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        remindInShop = amount;
    }

    @Override
    public int getSellPrice() {
        return 0;
    }
    public String getName() {
        return "Shipping Bin";
    }

    @Override
    public String getInventoryIconPath() {
        return "Mohamadreza/Shipping Bin.png";
    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 250;
    }

    @Override
    public void setX(int x) {
        topLeftX = x;
    }
    public void setY(int y) {
        topLeftY = y;
    }
}
