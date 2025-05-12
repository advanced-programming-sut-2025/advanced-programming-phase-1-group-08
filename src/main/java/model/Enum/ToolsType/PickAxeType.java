package model.Enum.ToolsType;

import model.App;
import model.Inventory;
import model.ToolsPackage.Axe;
import model.ToolsPackage.PickAxe;

public enum PickAxeType {

    primaryPickAxe  ("Primary PickAxe", -5 , 0),

    copperyPickAxe  ("Coppery PickAxe", -4 , 2000),

    ironPickAxe     ("Iron PickAxe",    -3 , 5000),

    goldenPickAxe   ("Golden PickAxe",  -2 , 10000),

    iridiumPickAxe  ("Iridium PickAxe", -1 , 25000);

    private final int energyCost;
    private final String displayName;
    private final int price;

    PickAxeType (String displayName, int energyCost, int price) {
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
        Inventory inventory = App.currentPlayer.getBackPack().inventory;
        PickAxe pickAxe=new PickAxe(pickAxeType);
        if (inventory.Items.containsKey(pickAxe)) {
            Integer value=inventory.Items.get(pickAxe);
            if (value >= 5) {
                inventory.Items.put(pickAxe,value-5);
                inventory.Items.entrySet().removeIf(entry -> entry.getValue().equals(0));
                return true;
            }
            return false;
        }
        return false;
    }
}
