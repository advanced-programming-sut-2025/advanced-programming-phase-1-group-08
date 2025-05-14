package model.Enum.ToolsType;

import model.App;
import model.Enum.ItemType.BarsAndOreType;
import model.Inventory;
import model.OtherItem.BarsAndOres;

import static model.Enum.ItemType.BarsAndOreType.*;

public enum TrashCanType {

    primaryTrashCan("Primary Trash Can",    0, null) {
        @Override
        public int getPercent() {
            return 0;
        }
    },
    CopperTrashCan ("Copper Trash Can",  1000, CopperBar) {
        @Override
        public int getPercent() {
            return 15;
        }
    },
    SteelTrashCan  ("Steel Trash Can",   2500, IronBar) {
        @Override
        public int getPercent() {
            return 30;
        }
    },
    GoldTrashCan   ("Gold Trash Can",    5000, GoldBar) {
        @Override
        public int getPercent() {
            return 45;
        }
    },
    IridiumTrashCan("Iridium Trash Can",12500, IridiumBar) {
        @Override
        public int getPercent() {
            return 60;
        }
    };

    public abstract int getPercent();
    private final int Price;
    private final String displayName;
    private final BarsAndOreType BarsAndOreType;

    private TrashCanType(String displayName, int price , BarsAndOreType BarsAndOreType) {
        this.displayName = displayName;
        this.Price = price;
        this.BarsAndOreType = BarsAndOreType;
    }
    public int getPrice() {
        return Price;
    }

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
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        BarsAndOres barsAndOres= new BarsAndOres(trashCanType.BarsAndOreType);
        if (inventory.Items.containsKey(barsAndOres)) {
            Integer value=inventory.Items.get(barsAndOres);
            if (value >= 5 && App.currentPlayer.getMoney() >= trashCanType.getPrice()) {
                inventory.Items.put(barsAndOres,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }

}
