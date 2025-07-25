package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.CropsType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;

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
    public String getInventoryIconPath() {
        return type.getIconPath();
    }

    @Override
    public int getSellPrice() {
        return type.getPrice();
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return -1;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 0;
    }
}
