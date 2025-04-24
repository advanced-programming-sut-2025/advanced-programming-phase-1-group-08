package model.Plants;

import model.Enum.ItemType.TreesProductType;
import model.Items;

public class TreesProdct extends Items {

    private TreesProductType type;


    public void setType(TreesProductType type) {
        this.type = type;
    }

    public TreesProductType getType() {
        return type;
    }
}
