package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public enum ForagingCropsType {

    CommonMushroom  ("Common Mushroom", 40, 38,
        "Erfan/plants/ForagingCrops/Common_Mushroom.png",
            List.of(Season.Spring, Season.Fall, Season.Summer, Season.Winter) , true),
    Daffodil        ("Daffodil",        30, 0,
        "Erfan/plants/ForagingCrops/Daffodil.png",
            List.of(Season.Spring) , false),
    Dandelion       ("Dandelion",       40, 25,
        "Erfan/plants/ForagingCrops/Dandelion.png",
            List.of(Season.Spring) , false),
    Leek            ("Leek",            60, 40,
        "Erfan/plants/ForagingCrops/Leek.png",
            List.of(Season.Spring) , true),
    Morel           ("Morel",           150,20,
        "Erfan/plants/ForagingCrops/Moral.png",
            List.of(Season.Spring) , true),
    Salmonberry     ("Salmonberry",     5,  25,
        "Erfan/plants/ForagingCrops/Salmonberry.png",
            List.of(Season.Spring) , false),
    SpringOnion     ("Spring Onion",    8,  13,
        "Erfan/plants/ForagingCrops/Spring_Onion_Mastery.png",
            List.of(Season.Spring) , false),
    WildHorseradish ("Wild Horseradish",50, 13,
        "Erfan/plants/ForagingCrops/Wild_Horseradish.png",
            List.of(Season.Spring) , false),
    FiddleheadFern  ("Fiddlehead Fern", 90, 25,
        "Erfan/plants/ForagingCrops/Fiddlehead_Fern.png",
            List.of(Season.Summer) , false),
    Grape           ("Grape",           80, 38,
        "Erfan/plants/ForagingCrops/Grape.png",
            List.of(Season.Summer) , false),
    RedMushroom     ("Red Mushroom",    75, -50,
        "Erfan/plants/ForagingCrops/Red_Mushroom.png",
            List.of(Season.Summer) , true),
    SpiceBerry      ("Spice Berry",     80, 25,
        "Erfan/plants/ForagingCrops/Spice_Berry.png",
            List.of(Season.Summer) , false),
    SweetPea        ("Sweet Pea",       50, 0,
        "Erfan/plants/ForagingCrops/Sweet_Pea.png",
            List.of(Season.Summer) , false),
    Blackberry      ("Blackberry",      25, 25,
        "Erfan/plants/ForagingCrops/Blackberry.png",
            List.of(Season.Fall) , false),
    Chanterelle     ("Chanterelle",     160,75,
        "Erfan/plants/ForagingCrops/Chanterelle.png",
            List.of(Season.Fall) , false),
    Hazelnut        ("Hazelnut",        40, 38,
        "Erfan/plants/ForagingCrops/Hazelnut.png",
            List.of(Season.Fall) , false),
    PurpleMushroom  ("Purple Mushroom", 90, 30,
        "Erfan/plants/ForagingCrops/Purple_Mushroom.png",
            List.of(Season.Fall) , true),
    WildPlum        ("Wild Plum",       80, 25,
        "Erfan/plants/ForagingCrops/Wild_Plum.png",
            List.of(Season.Fall) , false),
    Crocus          ("Crocus",          60, 0,
        "Erfan/plants/ForagingCrops/Crocus.png",
            List.of(Season.Winter) , false),
    CrystalFruit    ("Crystal Fruit",   150,63,
        "Erfan/plants/ForagingCrops/Crystal_Fruit.png",
            List.of(Season.Winter) , false),
    Holly           ("Holly",           80, -37,
        "Erfan/plants/ForagingCrops/Holly.png",
            List.of(Season.Winter) , false),
    SnowYam         ("Snow Yam",        100,30,
        "Erfan/plants/ForagingCrops/Snow_Yam.png",
            List.of(Season.Winter) , false),
    WinterRoot      ("Winter Root",     70, 25,
        "Erfan/plants/ForagingCrops/Winter_Root.png",
            List.of(Season.Winter) , false),
    Fiber           ("Fiber",            0,  0,
        "Erfan/plants/ForagingCrops/Fiber.png",
            List.of(Season.Winter) , false);


    private final String displayName;
    private final int price;
    private final int energy;
    private final String texturePath;
    private final List<Season> seasons;
    private boolean isMushroom ;

    ForagingCropsType (String displayName, int price, int energy, String icon,
                       List<Season> seasons, boolean isMushroom) {
        this.displayName = displayName;
        this.price = price;
        this.energy = energy;
        this.texturePath = icon;
        this.seasons = seasons;
        this.isMushroom = isMushroom;
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
    public String getTexturePath() {
        return texturePath;
    }
    public ArrayList<Season> getSeason() {
        return new ArrayList<>(seasons);
    }

    public boolean isMushroom() {
        return isMushroom;
    }

    public static String getInformation (ForagingCropsType type) {

        StringBuilder builder = new StringBuilder();
        builder.append("Name : ").append(type.displayName)
                .append("\nPrice  : ").append(type.price)
                .append("\nEnergy : ").append(type.energy)
                .append("\nSeasons : ");

        for (Season season : type.seasons)
            builder.append(season.getDisplayName()).append(" ");

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
    public static ForagingCropsType fromDisplayName(String displayName) {
        for (ForagingCropsType type : ForagingCropsType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException("wrong name!");
    }
}
