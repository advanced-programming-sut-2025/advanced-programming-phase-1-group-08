package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

public enum ForagingCropsType {

    CommonMushroom  ("Common Mushroom", 40, 38, "Icon_CommonMushroom",
            List.of(Season.Spring, Season.Fall, Season.Summer, Season.Winter)),
    Daffodil        ("Daffodil",        30, 0,  "Icon_Daffodil",
            List.of(Season.Spring)),
    Dandelion       ("Dandelion",       40, 25, "Icon_Dandelion",
            List.of(Season.Spring)),
    Leek            ("Leek",            60, 40, "Icon_Leek",
            List.of(Season.Spring)),
    Morel           ("Morel",           150,20, "Icon_Morel",
            List.of(Season.Spring)),
    Salmonberry     ("Salmonberry",     5,  25, "Icon_Salmonberry",
            List.of(Season.Spring)),
    SpringOnion     ("Spring Onion",    8,  13, "Icon_SpringOnion",
            List.of(Season.Spring)),
    WildHorseradish ("Wild Horseradish",50, 13, "Icon_WildHorseradish",
            List.of(Season.Spring)),
    FiddleheadFern  ("Fiddlehead Fern", 90, 25, "Icon_FiddleheadFern",
            List.of(Season.Summer)),
    Grape           ("Grape",           80, 38, "Icon_Grape",
            List.of(Season.Summer)),
    RedMushroom     ("Red Mushroom",    75, -50,"Icon_RedMushroom",
            List.of(Season.Summer)),
    SpiceBerry      ("Spice Berry",     80, 25, "Icon_SpiceBerry",
            List.of(Season.Summer)),
    SweetPea        ("Sweet Pea",       50, 0,  "Icon_SweetPea",
            List.of(Season.Summer)),
    Blackberry      ("Blackberry",      25, 25, "Icon_Blackberry",
            List.of(Season.Fall)),
    Chanterelle     ("Chanterelle",     160,75, "Icon_Chanterelle",
            List.of(Season.Fall)),
    Hazelnut        ("Hazelnut",        40, 38, "Icon_Hazelnut",
            List.of(Season.Fall)),
    PurpleMushroom  ("Purple Mushroom", 90, 30, "Icon_PurpleMushroom",
            List.of(Season.Fall)),
    WildPlum        ("Wild Plum",       80, 25, "Icon_WildPlum",
            List.of(Season.Fall)),
    Crocus          ("Crocus",          60, 0,  "Icon_Crocus",
            List.of(Season.Winter)),
    CrystalFruit    ("Crystal Fruit",   150,63, "Icon_CrystalFruit",
            List.of(Season.Winter)),
    Holly           ("Holly",           80, -37,"Icon_Holly",
            List.of(Season.Winter)),
    SnowYam         ("Snow Yam",        100,30, "Icon_SnowYam",
            List.of(Season.Winter)),
    WinterRoot      ("Winter Root",     70, 25, "Icon_WinterRoot",
            List.of(Season.Winter));

    private final String displayName;
    private final int price;
    private final int energy;
    private final String icon;
    private final List<Season> seasons;

    ForagingCropsType (String displayName, int price, int energy, String icon, List<Season> seasons) {
        this.displayName = displayName;
        this.price = price;
        this.energy = energy;
        this.icon = icon;
        this.seasons = seasons;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

    public int getEnergy() {
        return energy;
    }

    public String getIcon() {
        return icon;
    }

    public ArrayList<Season> getSeason() {
        return new ArrayList<>(seasons);
    }
}
