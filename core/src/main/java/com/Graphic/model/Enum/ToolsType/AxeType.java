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

    primaryAxe  ("Primary Axe", -5 , 1 , 0 , null, "Erfan/Tools/Axe/Axe.png"),

    copperyAxe  ("Coppery Axe", -4 , 1 , 2000 , CopperBar, "Erfan/Tools/Axe/Copper_Axe.png") ,

    ironAxe     ("Iron Axe",    -3 , 1 , 5000 , IronBar, "Erfan/Tools/Axe/Steel_Axe.png"),

    goldenAxe   ("Golden Axe",  -2 , 1 , 10000 , GoldBar, "Erfan/Tools/Axe/Gold_Axe.png"),

    iridiumAxe  ("Iridium Axe", -1 , 1 , 25000 , IridiumBar, "Erfan/Tools/Axe/Iridium_Axe.png");

    private final int energyCost;
    private final String displayName;
    private final int initialLimit;
    private final int Price;
    private final String iconPath;
    private final BarsAndOreType BarsAndOreType;

    AxeType (String displayName, int energyCost , int initialLimit , int Price , BarsAndOreType BarsAndOreType, String iconPath) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.initialLimit = initialLimit;
        this.Price = Price;
        this.BarsAndOreType = BarsAndOreType;
        this.iconPath = iconPath;
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
