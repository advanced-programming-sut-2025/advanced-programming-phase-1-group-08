package com.Graphic.model.ToolsPackage;

import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.MarketType;

import static com.Graphic.model.App.currentGame;
public class Scythe extends Tools {

    public Scythe() {
        super("Scythe");
    }

    @Override
    public String getIcon() {
        return "Erfan/Tools/Scythe.png";
    }


    @Override
    public int healthCost() {

        return Math.min((int) (-2 * Main.getClient(null).getLocalGameState().currentWeather.getEnergyCostCoefficient()), 0);
    }

    @Override
    public String getInventoryIconPath() {
        return "Erfan/Tools/Scythe.png";
    }

    @Override
    public String getName() {
        return "Scythe";
    }

    @Override
    public int getSellPrice() {
        return 0;
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
