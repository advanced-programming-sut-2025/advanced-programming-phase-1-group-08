package model.OtherItem;

import Controller.GameController;
import model.DateHour;
import model.Enum.ItemType.CraftType;
import model.Items;
import model.MapThings.GameObject;
import model.MapThings.Tile;
import model.Plants.ForagingCrops;
import model.Plants.ForagingSeeds;
import model.Plants.GiantProduct;
import model.Plants.Tree;

import java.util.HashMap;

public class CraftingItem extends Items {
    private CraftType craftType;
    private int x;
    private int y;
    private HashMap<Items , HashMap<DateHour, Integer>> buffer=new HashMap<Items, HashMap<DateHour, Integer>>();

    public CraftingItem(CraftType craftType) {
        this.craftType = craftType;
    }


    public CraftType getCraftType() {
        return craftType;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public HashMap<Items, HashMap<DateHour, Integer>> getBuffer() {
        return buffer;
    }

    @Override
    public void turnByTurnAutomaticTask() {

        if (this.craftType.equals(CraftType.Scarecrow))
            this.setProtect(8);
        if (this.craftType.equals(CraftType.DeluxeScarecrow))
            this.setProtect(12);
    }
    public void setProtect (int r) {


    }
}
