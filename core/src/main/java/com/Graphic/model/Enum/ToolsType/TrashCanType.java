package com.Graphic.model.Enum.ToolsType;

import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.BarsAndOreType;
import com.Graphic.model.Inventory;
import com.Graphic.model.OtherItem.BarsAndOres;

import static com.Graphic.model.Enum.ItemType.BarsAndOreType.*;

public enum TrashCanType {

    primaryTrashCan("Primary Trash Can",    0, null, "Erfan/Tools/Trash_Can/Trash_Can_Copper.png") {
        @Override
        public int getPercent() {
            return 0;
        }
    },
    CopperTrashCan ("Copper Trash Can",  1000, CopperBar, "Erfan/Tools/Trash_Can/Trash_Can_Copper.png") {
        @Override
        public int getPercent() {
            return 15;
        }
    },
    SteelTrashCan  ("Steel Trash Can",   2500, IronBar, "Erfan/Tools/Trash_Can/Trash_Can_Steel.png") {
        @Override
        public int getPercent() {
            return 30;
        }
    },
    GoldTrashCan   ("Gold Trash Can",    5000, GoldBar, "Erfan/Tools/Trash_Can/Trash_Can_Gold.png") {
        @Override
        public int getPercent() {
            return 45;
        }
    },
    IridiumTrashCan("Iridium Trash Can",12500, IridiumBar, "Erfan/Tools/Trash_Can/Trash_Can_Iridium.png") {
        @Override
        public int getPercent() {
            return 60;
        }
    };

    private final int Price;
    private final String iconPath;
    private final String displayName;
    private final BarsAndOreType BarsAndOreType;

    private TrashCanType(String displayName, int price , BarsAndOreType BarsAndOreType, String iconPath) {
        this.displayName = displayName;
        this.iconPath = iconPath;
        this.Price = price;
        this.BarsAndOreType = BarsAndOreType;
    }


    public int getPrice() {
        return Price;
    }
    public String getDisplayName() {

        return displayName;
    }
    public String getIconPath() {
        return iconPath;

    }

    public abstract int getPercent();

    public static TrashCanType nextTrashCanType(TrashCanType trashCanType) {
        return switch (trashCanType) {
            case primaryTrashCan -> CopperTrashCan;
            case CopperTrashCan -> SteelTrashCan;
            case SteelTrashCan -> GoldTrashCan;
            case GoldTrashCan -> IridiumTrashCan;
            case null, default -> null;
        };
    }
    public static boolean checkIngredient(TrashCanType trashCanType) {
        Inventory inventory = App.currentGame.currentPlayer.getBackPack().inventory;
        BarsAndOres barsAndOres= new BarsAndOres(trashCanType.BarsAndOreType);
        if (inventory.Items.containsKey(barsAndOres)) {
            Integer value=inventory.Items.get(barsAndOres);
            if (value >= 5 && App.currentGame.currentPlayer.getMoney() >= trashCanType.getPrice()) {
                inventory.Items.put(barsAndOres,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }
}
