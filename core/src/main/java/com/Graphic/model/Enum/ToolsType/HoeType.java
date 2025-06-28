package com.Graphic.model.Enum.ToolsType;

import model.App;
import model.Enum.ItemType.BarsAndOreType;
import model.Inventory;
import model.OtherItem.BarsAndOres;



import static model.Enum.ItemType.BarsAndOreType.*;

public enum HoeType {

    primaryHoe  ("Primary Hoe", -5 , 0 , null),

    copperyHoe  ("Coppery Hoe", -4 , 2000 , CopperBar),

    ironHoe     ("Iron Hoe",    -3 , 5000 , IronBar),

    goldenHoe   ("Golden Hoe",  -2 , 10000 , GoldBar),

    iridiumHoe  ("Iridium Hoe", -1 , 25000 , IridiumBar);

    private final int energyCost;
    private final String displayName;
    private final int price;
    private final BarsAndOreType barsAndOreType;

    HoeType(String displayName, int energyCost , int price , BarsAndOreType barsAndOreType) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.price = price;
        this.barsAndOreType = barsAndOreType;
    }

    public int getEnergyCost() {

        return energyCost;
    }
    public String getDisplayName() {

        return displayName;
    }
    public int getPrice() {
        return price;
    }

    public static HoeType getNextType(HoeType type) {
        if (type == HoeType.primaryHoe) {
            return HoeType.copperyHoe;
        }
        if (type == HoeType.copperyHoe) {
            return HoeType.ironHoe;
        }
        if (type == HoeType.ironHoe) {
            return HoeType.goldenHoe;
        }
        if (type == HoeType.goldenHoe) {
            return HoeType.iridiumHoe;
        }
        return null;
    }

    public static boolean checkIngredient(HoeType hoeType) {
        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
        BarsAndOres barsAndOres= new BarsAndOres(hoeType.barsAndOreType);
        if (inventory.Items.containsKey(barsAndOres)) {
            Integer value=inventory.Items.get(barsAndOres);
            if (value >= 5 && App.currentGame.currentPlayer.getMoney() >= hoeType.getPrice()) {
                inventory.Items.put(barsAndOres,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }

}
