package model.ToolsPackage;

import model.Enum.ItemType.BackPackType;
import model.Inventory;

public class BaⅽkPaⅽk {

    private BackPackType Type = BackPackType.primary;
    private int capacity=12;

    public Inventory inventory;

    public void addItem (){}
    public void removeItem (){}

    public BackPackType getType() {
        return Type;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setType(BackPackType type) {
        Type = type;
    }

    public void reduceCapacity(int amount) {
        capacity += amount;
    }

}
