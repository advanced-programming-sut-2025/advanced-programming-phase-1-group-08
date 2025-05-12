package model.Enum.ItemType;

import model.App;
import model.Enum.ToolsType.AxeType;
import model.Inventory;
import model.ToolsPackage.Axe;
import model.ToolsPackage.TrashCan;

public enum TrashCanType {

    primarytTrashCan(0) {
        @Override
        public int getPercent() {
            return 0;
        }
    },
    CopperTrashCan(1000) {
        @Override
        public int getPercent() {
            return 15;
        }
    },
    SteelTrashCan(2500) {
        @Override
        public int getPercent() {
            return 30;
        }
    },
    GoldTrashCan(5000) {
        @Override
        public int getPercent() {
            return 45;
        }
    },
    IridiumTrashCan(12500) {
        @Override
        public int getPercent() {
            return 60;
        }
    };

    public abstract int getPercent();
    private final int Price;
    private TrashCanType(int price) {
        this.Price = price;
    }
    public int getPrice() {
        return Price;
    }

    public static TrashCanType nextTrashCanType(TrashCanType trashCanType) {
        return switch (trashCanType) {
            case primarytTrashCan -> CopperTrashCan;
            case CopperTrashCan -> SteelTrashCan;
            case SteelTrashCan -> GoldTrashCan;
            case GoldTrashCan -> IridiumTrashCan;
            case null, default -> null;
        };
    }

    public static boolean checkIngredient(TrashCanType trashCanType) {
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        TrashCan trashCan=new TrashCan(trashCanType);
        if (inventory.Items.containsKey(trashCan)) {
            Integer value=inventory.Items.get(trashCan);
            if (value >= 5) {
                inventory.Items.put(axe,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }

}
