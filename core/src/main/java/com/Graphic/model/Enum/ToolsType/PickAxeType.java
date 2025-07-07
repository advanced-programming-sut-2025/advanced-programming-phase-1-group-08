package com.Graphic.model.Enum.ToolsType;

import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.BarsAndOreType;
import com.Graphic.model.Inventory;
import com.Graphic.model.OtherItem.BarsAndOres;
import com.Graphic.model.ToolsPackage.Axe;
import com.Graphic.model.ToolsPackage.PickAxe;
import com.Graphic.model.Enum.ItemType.BarsAndOreType.*;

import static com.Graphic.model.Enum.ItemType.BarsAndOreType.*;

public enum PickAxeType {

    primaryPickAxe  ("Primary PickAxe", -5 , 0 , null, "Erfan/Tools/Pickaxe/Pickaxe.png"),

    copperyPickAxe  ("Coppery PickAxe", -4 , 2000 , CopperBar, "Erfan/Tools/Pickaxe/Copper_Pickaxe.png"),

    ironPickAxe     ("Iron PickAxe",    -3 , 5000 , IronBar, "Erfan/Tools/Pickaxe/Steel_Pickaxe.png"),

    goldenPickAxe   ("Golden PickAxe",  -2 , 10000 , GoldBar, "Erfan/Tools/Pickaxe/Gold_Pickaxe.png"),

    iridiumPickAxe  ("Iridium PickAxe", -1 , 25000 , IridiumBar, "Erfan/Tools/Pickaxe/Iridium_Pickaxe.png");

    private final int energyCost;
    private final String displayName;
    private final int price;
    private final String iconPath;
    private final BarsAndOreType barsAndOreType;

    PickAxeType (String displayName, int energyCost, int price , BarsAndOreType barsAndOreType, String iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
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
