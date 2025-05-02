package model.ToolsPackage;

import model.Enum.ToolsType.HoeType;
import model.Tile;

import java.util.ArrayList;

import static model.App.currentPlayer;

public class Hoe extends Tools {

    public static ArrayList<Tile> plowedTile = new ArrayList<>();

    private HoeType type;

    public Hoe (HoeType type) {
        super("Hoe", 0);
        this.type = type;
    }

    public void use () {}


    public void kir (){}
    public HoeType getType() {

        return type;
    }
    public void setType(HoeType type) {

        this.type = type;
    }

    public int healthCost() {

        if (currentPlayer.getLevelFarming() == 4)
            return this.type.getEnergyCost()+1;
        return this.type.getEnergyCost();
    }

}
