package model.ToolsPackage;

import model.Enum.ToolsType.HoeType;
import model.Tile;

import java.util.ArrayList;

public class Hoe extends Tools {

    public static ArrayList<Tile> plowedTile = new ArrayList<>();

    public Hoe() {
        super("Hoe", 0);
    }

    public HoeType hoeType=HoeType.primaryHoe;

    public void use () {}


}
