package model.Plants;

import model.Enum.AllPlants.TreesSourceType;
import model.Items;

public class TreeSource extends Items {

    private TreesSourceType type;
    private String name;

    public TreeSource (TreesSourceType type) {
        this.type = type;
        this.name = type.getDisplayName();
    }
}
