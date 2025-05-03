package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

import static model.Color_Eraser.*;

public enum ForagingCropsType {

    CommonMushroom  ("Common Mushroom", 40, 38, BG_RED+BLACK+BOLD+"C"+RESET,
            List.of(Season.Spring, Season.Fall, Season.Summer, Season.Winter)),
    Daffodil        ("Daffodil",        30, 0,  BG_RED+BLACK+BOLD+"D"+RESET,
            List.of(Season.Spring)),
    Dandelion       ("Dandelion",       40, 25, BG_RED+BROWN+BOLD+"D"+RESET,
            List.of(Season.Spring)),
    Leek            ("Leek",            60, 40, BG_RED+BLACK+BOLD+"L"+RESET,
            List.of(Season.Spring)),
    Morel           ("Morel",           150,20, BG_RED+BLACK+BOLD+"M"+RESET,
            List.of(Season.Spring)),
    Salmonberry     ("Salmonberry",     5,  25, BG_RED+BLACK+BOLD+"S"+RESET,
            List.of(Season.Spring)),
    SpringOnion     ("Spring Onion",    8,  13, BG_RED+BROWN+BOLD+"S"+RESET,
            List.of(Season.Spring)),
    WildHorseradish ("Wild Horseradish",50, 13, BG_RED+BLACK+BOLD+"W"+RESET,
            List.of(Season.Spring)),
    FiddleheadFern  ("Fiddlehead Fern", 90, 25, BG_RED+BLACK+BOLD+"F"+RESET,
            List.of(Season.Summer)),
    Grape           ("Grape",           80, 38, BG_RED+BLACK+BOLD+"G"+RESET,
            List.of(Season.Summer)),
    RedMushroom     ("Red Mushroom",    75, -50,BG_RED+BLACK+BOLD+"R"+RESET,
            List.of(Season.Summer)),
    SpiceBerry      ("Spice Berry",     80, 25, BG_RED+CYAN+BOLD+"S"+RESET,
            List.of(Season.Summer)),
    SweetPea        ("Sweet Pea",       50, 0,  BG_RED+BRIGHT_WHITE+BOLD+"S"+RESET,
            List.of(Season.Summer)),
    Blackberry      ("Blackberry",      25, 25, BG_RED+BLACK+BOLD+"B"+RESET,
            List.of(Season.Fall)),
    Chanterelle     ("Chanterelle",     160,75, BG_RED+BROWN+BOLD+"C"+RESET,
            List.of(Season.Fall)),
    Hazelnut        ("Hazelnut",        40, 38, BG_RED+BLACK+BOLD+"H"+RESET,
            List.of(Season.Fall)),
    PurpleMushroom  ("Purple Mushroom", 90, 30, BG_RED+BLACK+BOLD+"P"+RESET,
            List.of(Season.Fall)),
    WildPlum        ("Wild Plum",       80, 25, BG_RED+BROWN+BOLD+"W"+RESET,
            List.of(Season.Fall)),
    Crocus          ("Crocus",          60, 0,  BG_RED+BRIGHT_WHITE+BOLD+"C"+RESET,
            List.of(Season.Winter)),
    CrystalFruit    ("Crystal Fruit",   150,63, BG_RED+WHITE+BOLD+"C"+RESET,
            List.of(Season.Winter)),
    Holly           ("Holly",           80, -37,BG_RED+BLACK+BOLD+"H"+RESET,
            List.of(Season.Winter)),
    SnowYam         ("Snow Yam",        100,30, BG_RED+WHITE+BOLD+"S"+RESET,
            List.of(Season.Winter)),
    WinterRoot      ("Winter Root",     70, 25, BG_RED+WHITE+BOLD+"W"+RESET,
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

    public static String getInformation (ForagingCropsType type) {

        StringBuilder builder = new StringBuilder();
        builder.append(BLUE+"Name : "+RESET).append(type.displayName)
                .append(BLUE+"\nPrice : "+RESET).append(type.price)
                .append(BLUE+"\nEnergy : "+RESET).append(type.energy)
                .append(BLUE+"\nIcon : "+RESET).append(type.icon)
                .append(BLUE+"\nSeasons : "+RESET);

        for (Season season : type.seasons)
            builder.append(season).append(",");

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
