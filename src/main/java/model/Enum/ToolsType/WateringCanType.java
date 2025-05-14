package model.Enum.ToolsType;

import model.App;
import model.Enum.ItemType.BarsAndOreType;
import model.Inventory;
import model.OtherItem.BarsAndOres;
import model.ToolsPackage.Axe;
import model.ToolsPackage.WateringCan;
import static model.Enum.ItemType.BarsAndOreType.*;

public enum WateringCanType {

    PrimaryWateringCan  ("Primary Watering Can",-5, 40 , 0 , null),

    CopperyWateringCan  ("Coppery Watering Can",-4, 55 , 2000 , CopperBar),

    IronWateringCan     ("Iron Watering Can",   -3, 70 , 5000 , IronBar),

    GoldenWateringCan   ("Golden Watering Can", -2, 85 , 10000 , GoldBar),

    IridiumWateringCan  ("Iridium Watering Can",-1, 100 , 25000 , IridiumBar);

    private final String displayName;
    private final int energyCost;
    private final int capacity;
    private final int Price;
    private final BarsAndOreType barsAndOreType;

    WateringCanType(String displayName, int energyCost, int capacity , int Price , BarsAndOreType barsAndOreType) {
        this.displayName = displayName;
        this.energyCost = energyCost;
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
        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
        BarsAndOres barsAndOres= new BarsAndOres(wateringCanType.barsAndOreType);
        if (inventory.Items.containsKey(barsAndOres)) {
            Integer value=inventory.Items.get(barsAndOres);
            if (value >= 5 && App.currentGame.currentPlayer.getMoney() >= wateringCanType.getPrice()) {
                inventory.Items.put(barsAndOres,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }

}
