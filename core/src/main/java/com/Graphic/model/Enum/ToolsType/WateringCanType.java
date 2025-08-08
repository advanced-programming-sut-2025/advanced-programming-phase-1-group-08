package com.Graphic.model.Enum.ToolsType;

import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.BarsAndOreType;
import com.Graphic.model.Inventory;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.ToolsPackage.Axe;
import com.Graphic.model.ToolsPackage.WateringCan;
import static com.Graphic.model.Enum.ItemType.BarsAndOreType.*;

public enum WateringCanType {

    PrimaryWateringCan  ("Primary Watering Can",-5, 40, 0,
        null, "Erfan/Tools/Watering_Can/Watering_Can.png"),

    CopperyWateringCan  ("Coppery Watering Can",-4, 55, 2000,
        CopperBar, "Erfan/Tools/Watering_Can/Copper_Watering_Can.png"),

    IronWateringCan     ("Iron Watering Can",   -3, 70, 5000,
        IronBar, "Erfan/Tools/Watering_Can/Steel_Watering_Can.png"),

    GoldenWateringCan   ("Golden Watering Can", -2, 85, 10000,
        GoldBar, "Erfan/Tools/Watering_Can/Gold_Watering_Can.png"),

    IridiumWateringCan  ("Iridium Watering Can",-1, 100, 25000,
        IridiumBar, "Erfan/Tools/Watering_Can/Iridium_Watering_Can.png");

    private final String displayName;
    private final String iconPath;
    private final int energyCost;
    private final int capacity;
    private final int Price;
    private final BarsAndOreType barsAndOreType;

    WateringCanType(String displayName, int energyCost, int capacity , int Price , BarsAndOreType barsAndOreType, String iconPath) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.iconPath = iconPath;
        this.capacity = capacity;
        this.Price = Price;
        this.barsAndOreType = barsAndOreType;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public int getCapacity  () {

        return capacity;
    }
    public String getDisplayName () {

        return this.displayName;
    }
    public String getIconPath() {
        return iconPath;

    }
    public int getPrice () {
        return Price;
    }

    public static WateringCanType getWateringCanType(WateringCanType wateringCanType) {
        return switch (wateringCanType) {
            case PrimaryWateringCan ->  CopperyWateringCan;
            case CopperyWateringCan ->  IronWateringCan;
            case IronWateringCan ->  GoldenWateringCan;
            case GoldenWateringCan -> IridiumWateringCan;
            case null, default -> null;
        };
    }

    public static boolean checkIngredient(WateringCanType wateringCanType) {
//        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
//        BarsAndOres barsAndOres= new BarsAndOres(wateringCanType.barsAndOreType);
//        if (inventory.Items.containsKey(barsAndOres)) {
//            Integer value=inventory.Items.get(barsAndOres);
//            if (value >= 5 && App.currentGame.currentPlayer.getMoney() >= wateringCanType.getPrice()) {
//                inventory.Items.put(barsAndOres,value-5);
//                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
//                return true;
//            }
//            return false;
//        }
        return false;
    }

}
