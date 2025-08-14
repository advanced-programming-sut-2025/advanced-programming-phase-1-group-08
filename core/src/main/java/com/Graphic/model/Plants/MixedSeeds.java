package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Items;

public class MixedSeeds extends Items {

    public MixedSeeds() {}

    public ForagingSeedsType getSeeds (Season season) {

        double probability = Math.random();

        if (season.equals(Season.Spring)) {

            if (probability < 0.2)
                return ForagingSeedsType.PotatoSeeds;

            else if (probability < 0.4)
                return ForagingSeedsType.JazzSeeds;

            else if (probability < 0.6)
                return ForagingSeedsType.TulipBulb;

            else if (probability < 0.8)
                return ForagingSeedsType.ParsnipSeeds;

            else
                return ForagingSeedsType.CauliflowerSeeds;
        }
        else if (season.equals(Season.Summer)) {

            if (probability < 0.14)
                return ForagingSeedsType.CornSeeds;

            else if (probability < 0.28)
                return ForagingSeedsType.PepperSeeds;

            else if (probability < 0.42)
                return ForagingSeedsType.RadishSeeds;

            else if (probability < 0.56)
                return ForagingSeedsType.WheatSeeds;

            else if (probability < 0.70)
                return ForagingSeedsType.PoppySeeds;

            else if (probability < 0.84)
                return ForagingSeedsType.SunflowerSeeds;

            else
                return ForagingSeedsType.SpangleSeeds;
        }
        else if (season.equals(Season.Fall))   {

            if (probability < 0.17)
                return ForagingSeedsType.ArtichokeSeeds;

            else if (probability < 0.34)
                return ForagingSeedsType.CornSeeds;

            else if (probability < 0.5)
                return ForagingSeedsType.EggplantSeeds;

            else if (probability < 0.67)
                return ForagingSeedsType.PumpkinSeeds;

            else if (probability < 0.84)
                return ForagingSeedsType.SunflowerSeeds;

            else
                return ForagingSeedsType.FairySeeds;
        }
        else if (season.equals(Season.Winter)) {
            return ForagingSeedsType.PowdermelonSeeds;
        }

        return ForagingSeedsType.CornSeeds;
    }

    @Override
    public String getName() {
        return "Mixed Seeds";
    }

    @Override
    public String getInventoryIconPath() {
        return "Erfan/plants/crops/Mixed/Mixed_Seeds.png";
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
