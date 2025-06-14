package model.Plants;

import model.Enum.AllPlants.CropsType;
import model.Items;

public class AllCrops extends Items {

    private CropsType type;

    public AllCrops(CropsType type) {
        this.type = type;
    }

//    public void setType(CropsType type) {
//        this.type = type;
//        this.price = type.getPrice();
//    }

    public CropsType getType() {
        return type;
    }


    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }
}