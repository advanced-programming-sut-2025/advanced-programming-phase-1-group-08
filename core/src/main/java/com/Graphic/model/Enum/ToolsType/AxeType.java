package com.Graphic.model.Enum.ToolsType;

import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.BarsAndOreType;
import com.Graphic.model.Inventory;
import com.Graphic.model.Items;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.ToolsPackage.Axe;
import com.Graphic.model.Enum.ItemType.BarsAndOreType.*;

import static com.Graphic.model.Enum.ItemType.BarsAndOreType.*;

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
