package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;
import com.Graphic.model.Items;

import java.util.ArrayList;
import java.util.List;

import static com.Graphic.model.Color_Eraser.*;

public enum ForagingCropsType {

    CommonMushroom  ("Common Mushroom", 40, 38, BG_RED+BLACK+BOLD+"C "+RESET,
            List.of(Season.Spring, Season.Fall, Season.Summer, Season.Winter) , true),
    Daffodil        ("Daffodil",        30, 0,  BG_RED+BLACK+BOLD+"D "+RESET,
            List.of(Season.Spring) , false),
    Dandelion       ("Dandelion",       40, 25, BG_RED+BROWN+BOLD+"D "+RESET,
            List.of(Season.Spring) , false),
    Leek            ("Leek",            60, 40, BG_RED+BLACK+BOLD+"L "+RESET,
            List.of(Season.Spring) , true),
    Morel           ("Morel",           150,20, BG_RED+BLACK+BOLD+"M "+RESET,
            List.of(Season.Spring) , true),
    Salmonberry     ("Salmonberry",     5,  25, BG_RED+BLACK+BOLD+"S "+RESET,
            List.of(Season.Spring) , false),
    SpringOnion     ("Spring Onion",    8,  13, BG_RED+BROWN+BOLD+"S "+RESET,
            List.of(Season.Spring) , false),
    WildHorseradish ("Wild Horseradish",50, 13, BG_RED+BLACK+BOLD+"W "+RESET,
            List.of(Season.Spring) , false),
    FiddleheadFern  ("Fiddlehead Fern", 90, 25, BG_RED+BLACK+BOLD+"F "+RESET,
            List.of(Season.Summer) , false),
    Grape           ("Grape",           80, 38, BG_RED+BLACK+BOLD+"G "+RESET,
            List.of(Season.Summer) , false),
    RedMushroom     ("Red Mushroom",    75, -50,BG_RED+BLACK+BOLD+"R "+RESET,
            List.of(Season.Summer) , true),
    SpiceBerry      ("Spice Berry",     80, 25, BG_RED+CYAN+BOLD+"S "+RESET,
            List.of(Season.Summer) , false),
    SweetPea        ("Sweet Pea",       50, 0,  BG_RED+BRIGHT_WHITE+BOLD+"S "+RESET,
            List.of(Season.Summer) , false),
    Blackberry      ("Blackberry",      25, 25, BG_RED+BLACK+BOLD+"B "+RESET,
            List.of(Season.Fall) , false),
    Chanterelle     ("Chanterelle",     160,75, BG_RED+BROWN+BOLD+"C "+RESET,
            List.of(Season.Fall) , false),
    Hazelnut        ("Hazelnut",        40, 38, BG_RED+BLACK+BOLD+"H "+RESET,
            List.of(Season.Fall) , false),
    PurpleMushroom  ("Purple Mushroom", 90, 30, BG_RED+BLACK+BOLD+"P "+RESET,
            List.of(Season.Fall) , true),
    WildPlum        ("Wild Plum",       80, 25, BG_RED+BROWN+BOLD+"W "+RESET,
            List.of(Season.Fall) , false),
    Crocus          ("Crocus",          60, 0,  BG_RED+BRIGHT_WHITE+BOLD+"C "+RESET,
            List.of(Season.Winter) , false),
    CrystalFruit    ("Crystal Fruit",   150,63, BG_RED+WHITE+BOLD+"C "+RESET,
            List.of(Season.Winter) , false),
    Holly           ("Holly",           80, -37,BG_RED+BLACK+BOLD+"H "+RESET,
            List.of(Season.Winter) , false),
    SnowYam         ("Snow Yam",        100,30, BG_RED+WHITE+BOLD+"S "+RESET,
            List.of(Season.Winter) , false),
    WinterRoot      ("Winter Root",     70, 25, BG_RED+WHITE+BOLD+"W "+RESET,
            List.of(Season.Winter) , false),
    Fiber           ("Fiber",            0,  0, BG_RED+WHITE+BOLD+"F "+RESET,
            List.of(Season.Winter) , false);


    private final String displayName;
    private final int price;
    private final int energy;
    private final String icon;
    private final List<Season> seasons;
    private boolean isMushroom ;

    ForagingCropsType (String displayName, int price, int energy, String icon, List<Season> seasons , boolean isMushroom) {
        this.displayName = displayName;
        this.price = price;
        this.energy = energy;
        this.icon = icon;
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
    public String getIcon() {
        return icon;
    }
    public ArrayList<Season> getSeason() {
        return new ArrayList<>(seasons);
    }

    public boolean isMushroom() {
        return isMushroom;
    }

    public static String getInformation (ForagingCropsType type) {

        StringBuilder builder = new StringBuilder();
        builder.append(BLUE+"Name : "+BRIGHT_GREEN).append(type.displayName)
                .append(BLUE+"\nPrice  : "+BRIGHT_RED).append(type.price)
                .append(BLUE+"\nEnergy : "+BRIGHT_RED).append(type.energy)
                .append(BLUE+"\nIcon : "+RESET).append(type.icon)
                .append(BLUE+"\nSeasons : "+RESET);

        for (Season season : type.seasons)
            builder.append(season.getDisplayName()).append(" ").append(RESET);

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    public static ForagingCropsType fromDisplayName(String displayName) {
        for (ForagingCropsType type : ForagingCropsType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
