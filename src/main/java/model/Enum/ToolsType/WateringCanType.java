package model.Enum.ToolsType;

import model.App;
import model.Inventory;
import model.ToolsPackage.Axe;
import model.ToolsPackage.WateringCan;

public enum WateringCanType {

    PrimaryWateringCan  ("Primary Watering Can",-5, 40 , 0),

    CopperyWateringCan  ("Coppery Watering Can",-4, 55 , 2000),

    IronWateringCan     ("Iron Watering Can",   -3, 70 , 5000),

    GoldenWateringCan   ("Golden Watering Can", -2, 85 , 10000),

    IridiumWateringCan  ("Iridium Watering Can",-1, 100 , 25000);

    private final String displayName;
    private final int energyCost;
    private final int capacity;
    private final int Price;

    WateringCanType(String displayName, int energyCost, int capacity , int Price) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.capacity = capacity;
        this.Price = Price;
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
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        WateringCan wateringCan=new WateringCan(wateringCanType);
        if (inventory.Items.containsKey(wateringCan)) {
            Integer value=inventory.Items.get(wateringCan);
            if (value >= 5) {
                inventory.Items.put(wateringCan,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }
}
