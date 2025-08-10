package com.Graphic.model.ToolsPackage;

import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.ToolsType.PickAxeType;

import static com.Graphic.model.App.*;

public class PickAxe extends Tools {

    private PickAxeType type;

    public PickAxe(PickAxeType type){
        super("PickAxe");
        this.type = type;
    }

    public PickAxeType getType() {

        return type;
    }
    public void setType(PickAxeType type) {

        this.type = type;
    }

    @Override
    public int healthCost() {

        double x = Main.getClient(null).getLocalGameState().currentWeather.getEnergyCostCoefficient();

        if (Main.getClient(null).getPlayer().getLevelMining() == 4)
            return Math.min((int) (this.type.getEnergyCost()*x)+1, 0);

        return Math.min((int) (this.type.getEnergyCost()*x), 0);
    }

    @Override
    public String getInventoryIconPath() {
        return type.getIconPath();
    }

    @Override
    public String getName() {
        return type.getDisplayName();
    }

    @Override
    public int getSellPrice() {
        return 0;
    }

    @Override
    public String getIcon() {
        return type.getIconPath();
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
        return type.getPrice();
    }
}
