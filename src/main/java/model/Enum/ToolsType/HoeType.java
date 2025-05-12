package model.Enum.ToolsType;

import model.App;
import model.Inventory;
import model.ToolsPackage.Axe;
import model.ToolsPackage.Hoe;

public enum HoeType {

    primaryHoe  ("Primary Hoe", -5 , 0),

    copperyHoe  ("Coppery Hoe", -4 , 2000),

    ironHoe     ("Iron Hoe",    -3 , 5000),

    goldenHoe   ("Golden Hoe",  -2 , 10000),

    iridiumHoe  ("Iridium Hoe", -1 , 25000);

    private final int energyCost;
    private final String displayName;
    private final int price;

    HoeType(String displayName, int energyCost , int price) {
        this.displayName = displayName;
        this.energyCost = energyCost;
        this.price = price;
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

    public HoeType getNextType(HoeType type) {
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
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        Hoe hoe =new Hoe(hoeType);
        if (inventory.Items.containsKey(hoe)) {
            Integer value=inventory.Items.get(hoe);
            if (value >= 5) {
                inventory.Items.put(hoe,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }

}
