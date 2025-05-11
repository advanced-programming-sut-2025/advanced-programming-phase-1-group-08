package model.Places;

import Controller.GameController;
import model.App;
import model.Items;
import model.MapThings.GameObject;
import model.MapThings.Tile;

import java.util.ArrayList;

public class ShippingBin extends GameObject {
    private static final int width=1;
    private static final int height=1;
    private static final int coinNeeded=250;
    private static final int woodNeeded=150;
    private static int remindInShop=1;

    private final int topLeftX;
    private final int topLeftY;

    public ArrayList<Items> binContents=new ArrayList<>();

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
        int [] dirx={0,0,1,1,1,-1,-1,-1};
        int [] diry={1,-1,0,1,-1,0,1,-1};
        GameController gameController=new GameController();

        for (int x = App.currentPlayer.getPositionX() ; x<App.currentPlayer.getPositionX()+ dirx.length ; x++) {
            for (int y=App.currentPlayer.getPositionY() ; y<App.currentPlayer.getPositionY()+ diry.length ; y++) {
                Tile tile=gameController.getTileByCoordinates(x, y);
                if (tile == null) {
                    continue;
                }
                if (tile.getGameObject() instanceof ShippingBin) {
                    return (ShippingBin) tile.getGameObject();
                }
            }
        }
        return null;
    }
}
