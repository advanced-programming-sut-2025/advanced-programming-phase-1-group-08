package com.Graphic.model.Plants;

import com.Graphic.model.Enum.ItemType.AnimalProductType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.ItemType.Quantity;
import com.Graphic.model.Items;

public class Animalproduct extends Items {

    private final AnimalProductType type;
    private final Quantity quantity;

    public Animalproduct(AnimalProductType animalProductType, Quantity quantity) {
        this.type = animalProductType;
        this.quantity = quantity;
    }

    public AnimalProductType getType() {
        return type;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() {
        return this.type.getIconPath();
    }

    @Override
    public int getSellPrice() {
        return (int) (type.getInitialPrice() * quantity.getValue() );
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
