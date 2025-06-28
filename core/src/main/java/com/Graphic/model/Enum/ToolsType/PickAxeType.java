package com.Graphic.model.Enum.ToolsType;

import model.App;
import model.Enum.ItemType.BarsAndOreType;
import model.Inventory;
import model.OtherItem.BarsAndOres;
import model.ToolsPackage.Axe;
import model.ToolsPackage.PickAxe;
import model.Enum.ItemType.BarsAndOreType.*;

import static model.Enum.ItemType.BarsAndOreType.*;

public enum PickAxeType {

    primaryPickAxe  ("Primary PickAxe", -5 , 0 , null),

    copperyPickAxe  ("Coppery PickAxe", -4 , 2000 , CopperBar),

    ironPickAxe     ("Iron PickAxe",    -3 , 5000 , IronBar),

    goldenPickAxe   ("Golden PickAxe",  -2 , 10000 , GoldBar),

    iridiumPickAxe  ("Iridium PickAxe", -1 , 25000 , IridiumBar);

    private final int energyCost;
    private final String displayName;
    private final int price;
    private final BarsAndOreType barsAndOreType;

    PickAxeType (String displayName, int energyCost, int price , BarsAndOreType barsAndOreType) {
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

    public static PickAxeType getPickAxeType(PickAxeType pickAxeType) {
        return switch (pickAxeType) {
            case primaryPickAxe -> copperyPickAxe;
            case copperyPickAxe -> ironPickAxe;
            case ironPickAxe -> goldenPickAxe;
            case goldenPickAxe -> iridiumPickAxe;
            case null, default -> null;
        };
    }

    public static boolean checkIngredient(PickAxeType pickAxeType) {
        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
        BarsAndOres barsAndOres= new BarsAndOres(pickAxeType.barsAndOreType);
        if (inventory.Items.containsKey(barsAndOres)) {
            Integer value=inventory.Items.get(barsAndOres);
            if (value >= 5 && App.currentGame.currentPlayer.getMoney() >= pickAxeType.getPrice()) {
                inventory.Items.put(barsAndOres,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }
}
