package model.ToolsPackage;

import model.Enum.ItemType.BackPackType;
import model.Inventory;
import model.Items;

public class BaⅽkPaⅽk {

    private BackPackType Type = BackPackType.primary;
    private int capacity=12;
    public final String name="BackPack";

    public Inventory inventory=new Inventory();

    public void addItem (){}
    public void removeItem (Items item){

    }

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
