package model.Enum.ItemType;

import model.App;
import model.Enum.ToolsType.AxeType;
import model.Enum.ToolsType.PickAxeType;
import model.Inventory;
import model.OtherItem.BarsAndOres;
import model.ToolsPackage.Axe;
import model.ToolsPackage.TrashCan;
import static model.Enum.ItemType.BarsAndOreType.*;

public enum TrashCanType {

    primarytTrashCan(0 , null) {
        @Override
        public int getPercent() {
            return 0;
        }
    },
    CopperTrashCan(1000 , CopperBar) {
        @Override
        public int getPercent() {
            return 15;
        }
    },
    SteelTrashCan(2500 , IronBar) {
        @Override
        public int getPercent() {
            return 30;
        }
    },
    GoldTrashCan(5000 , GoldBar) {
        @Override
        public int getPercent() {
            return 45;
        }
    },
    IridiumTrashCan(12500 , IridiumBar) {
        @Override
        public int getPercent() {
            return 60;
        }
    };

    public abstract int getPercent();
    private final int Price;
    private final BarsAndOreType BarsAndOreType;

    private TrashCanType(int price , BarsAndOreType BarsAndOreType) {
        this.Price = price;
        this.BarsAndOreType = BarsAndOreType;
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
