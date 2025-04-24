package model.Plants;

import model.Enum.AllPlants.ForagingMineralsType;
import model.Items;

public class ForagingMinerals extends Items {

    private final ForagingMineralsType type;
    private final int sellPrice;
    private final String description;
    private final String symbol;


    public ForagingMinerals(ForagingMineralsType type) {
        this.type = type;
        this.sellPrice = type.getPrice();
        this.description = type.getDescription();
        this.symbol = type.getDescription();
    }

}
