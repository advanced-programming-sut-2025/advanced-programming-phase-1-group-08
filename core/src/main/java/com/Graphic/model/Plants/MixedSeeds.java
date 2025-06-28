package com.Graphic.model.Plants;

import com.Graphic.model.Enum.AllPlants.ForagingSeedsType;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Items;

public class MixedSeeds extends Items {

    public MixedSeeds() {

    }

    public ForagingSeedsType getSeeds (Season season) {

        double probability = Math.random();

        if (season.equals(Season.Spring)) {

            if (probability < 0.2)
                return ForagingSeedsType.PotatoSeeds;

            if (probability < 0.4)
                return ForagingSeedsType.JazzSeeds;

            if (probability < 0.6)
                return ForagingSeedsType.TulipBulb;

            if (probability < 0.8)
                return ForagingSeedsType.ParsnipSeeds;

            else
                return ForagingSeedsType.CauliflowerSeeds;
        }
        else if (season.equals(Season.Summer)) {

            if (probability < 0.14)
                return ForagingSeedsType.CornSeeds;

            if (probability < 0.28)
                return ForagingSeedsType.PepperSeeds;

            if (probability < 0.42)
                return ForagingSeedsType.RadishSeeds;

            if (probability < 0.56)
                return ForagingSeedsType.WheatSeeds;

            if (probability < 0.70)
                return ForagingSeedsType.PoppySeeds;

            if (probability < 0.84)
                return ForagingSeedsType.SunflowerSeeds;

            else
                return ForagingSeedsType.SpangleSeeds;
        }
        else if (season.equals(Season.Fall))   {

            if (probability < 0.17)
                return ForagingSeedsType.ArtichokeSeeds;

            if (probability < 0.34)
                return ForagingSeedsType.CornSeeds;

            if (probability < 0.5)
                return ForagingSeedsType.EggplantSeeds;

            if (probability < 0.67)
                return ForagingSeedsType.PumpkinSeeds;

            if (probability < 0.84)
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
    public int getSellPrice() {
        return 0;
    }
}
