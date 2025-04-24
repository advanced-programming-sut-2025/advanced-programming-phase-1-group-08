package model.Plants;

import model.Enum.AllPlants.CropsType;
import model.Items;

public class AllCrops extends Items {

    private CropsType type;


    public void setType(CropsType type) {
        this.type = type;
    }
}
