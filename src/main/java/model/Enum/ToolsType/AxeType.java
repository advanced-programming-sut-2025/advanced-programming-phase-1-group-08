package model.Enum.ToolsType;

import model.App;
import model.Enum.ItemType.BarnORCageType;
import model.Enum.ItemType.BarsAndOreType;
import model.Inventory;
import model.Items;
import model.OtherItem.BarsAndOres;
import model.ToolsPackage.Axe;
import model.Enum.ItemType.BarsAndOreType.*;

import java.util.Iterator;
import java.util.Map;

import static model.Enum.ItemType.BarsAndOreType.*;

public enum AxeType {

    primaryAxe  ("Primary Axe", -5 , 1 , 0 , null),

    copperyAxe  ("Coppery Axe", -4 , 1 , 2000 , CopperBar) ,

    ironAxe     ("Iron Axe",    -3 , 1 , 5000 , IronBar),

    goldenAxe   ("Golden Axe",  -2 , 1 , 10000 , GoldBar),

    iridiumAxe  ("Iridium Axe", -1 , 1 , 25000 , IridiumBar);

    private final int energyCost;
    private final String displayName;
    private final int initialLimit;
    private final int Price;
    private final BarsAndOreType BarsAndOreType;

    AxeType (String displayName, int energyCost , int initialLimit , int Price , BarsAndOreType BarsAndOreType) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.initialLimit = initialLimit;
        this.Price = Price;
        this.BarsAndOreType = BarsAndOreType;
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


    public static AxeType getNextType(AxeType axeType) {
        return switch (axeType) {
            case primaryAxe -> copperyAxe;
            case copperyAxe -> ironAxe;
            case ironAxe -> goldenAxe;
            case goldenAxe -> iridiumAxe;
            case null, default -> null;
        };
    }

    public static boolean checkIngredient(AxeType axeType) {
        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
        BarsAndOres barsAndOres= new BarsAndOres(axeType.BarsAndOreType);
        if (inventory.Items.containsKey(barsAndOres)) {
            Integer value=inventory.Items.get(barsAndOres);
            if (value >= 5 && App.currentGame.currentPlayer.getMoney() >= axeType.getPrice()) {
                inventory.Items.put(barsAndOres,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }
}
