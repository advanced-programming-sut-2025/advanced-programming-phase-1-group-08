package com.Graphic.model.Places;


import com.Graphic.model.App;
import com.Graphic.model.Items;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.MapThings.Tile;

import java.util.HashMap;

import static com.Graphic.Controller.MainGame.GameControllerLogic.getTileByCoordinates;
import static com.Graphic.model.HelpersClass.Color_Eraser.RED;


public class ShippingBin extends GameObject {
    private static final int width=1;
    private static final int height=1;
    private static final int coinNeeded=250;
    private static final int woodNeeded=150;
    private static int remindInShop=1;

    private final int topLeftX;
    private final int topLeftY;

    public HashMap<Items, Integer> binContents=new HashMap<>();

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
        return RED + "S";
    }
}
