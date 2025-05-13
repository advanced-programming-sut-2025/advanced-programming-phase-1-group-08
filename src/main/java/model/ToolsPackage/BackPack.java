package model.ToolsPackage;

import model.Enum.ItemType.BackPackType;
import model.Inventory;
import model.Items;

public class BackPack {

    private BackPackType Type;
    public Inventory inventory;
    private int capacity = 12;
    public final String name = "BackPack";

    public BackPack() {
        this.Type = BackPackType.primary;
        this.inventory = new Inventory();
    }

    public BackPackType getType() {
        return Type;
    }
    public void setType(BackPackType type) {
        Type = type;
    }
}
