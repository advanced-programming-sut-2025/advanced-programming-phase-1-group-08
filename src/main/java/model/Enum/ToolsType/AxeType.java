package model.Enum.ToolsType;

import model.App;
import model.Enum.ItemType.BarsAndOreType;
import model.Inventory;
import model.Items;
import model.OtherItem.BarsAndOres;
import model.ToolsPackage.Axe;

import java.util.Iterator;
import java.util.Map;

public enum AxeType {

    primaryAxe  ("Primary Axe", -5 , 1 , 0),

    copperyAxe  ("Coppery Axe", -4 , 1 , 2000) ,

    ironAxe     ("Iron Axe",    -3 , 1 , 5000),

    goldenAxe   ("Golden Axe",  -2 , 1 , 10000),

    iridiumAxe  ("Iridium Axe", -1 , 1 , 25000);

    private final int energyCost;
    private final String displayName;
    private final int initialLimit;
    private final int Price;

    AxeType (String displayName, int energyCost , int initialLimit , int Price) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.initialLimit = initialLimit;
        this.Price = Price;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return Price;
    }


    public AxeType getNextType(AxeType axeType) {
        return switch (axeType) {
            case primaryAxe -> copperyAxe;
            case copperyAxe -> ironAxe;
            case ironAxe -> goldenAxe;
            case goldenAxe -> iridiumAxe;
            case null, default -> null;
        };
    }

    public static boolean checkIngredient(AxeType axeType) {
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        if (axeType.equals(AxeType.copperyAxe)) {

        }
    }
}
