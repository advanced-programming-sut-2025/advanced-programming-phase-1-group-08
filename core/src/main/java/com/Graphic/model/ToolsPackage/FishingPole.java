package com.Graphic.model.ToolsPackage;

import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.ToolsType.FishingPoleType;

public class FishingPole extends Tools {

    public FishingPole() {
        super("FishingPole");
        this.type = FishingPoleType.BambooPole;
    }

    @Override
    public String getIcon() {
        return type.getIconPath();
    }

    public FishingPoleType type = null;

    public void use (){}

    @Override
    public int healthCost() { // TODO
        return 0;
    }
    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public int getSellPrice() {
        return 0;
    }
    @Override
    public String getInventoryIconPath() {
        return type.getIconPath();
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return type.shopLimit;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        type.shopLimit = amount;
    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return type.getPrice();
    }
}
