package model.Plants;

import model.Enum.AllPlants.ForagingSeedsType;
import model.Items;

public class ForagingSeeds extends Items {

    private ForagingSeedsType type;


    public void setType(ForagingSeedsType type) {
        this.type = type;
    }
}
