package model.Plants;

import model.Enum.AllPlants.TreesSourceType;
import model.Items;

public class TreeSource extends Items {

    private TreesSourceType type;

    public TreeSource (TreesSourceType type) {
        this.type = type;
    }
}
