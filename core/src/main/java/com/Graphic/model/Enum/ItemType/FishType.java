package com.Graphic.model.Enum.ItemType;

import com.Graphic.model.Enum.WeatherTime.Season;

public enum FishType {

    Salmon("Salmon",75,Season.Fall,false),
    Sardine("Sardine",40,Season.Fall,false),
    Shad("Shad",60,Season.Fall,false),
    Blue_Discus("Blue Discus",120,Season.Fall,false),
    Midnight_Carp("Midnight Carp",150,Season.Winter,false),
    Squid("Squid",80,Season.Winter,false),
    Tuna(  "Tuna",100,Season.Winter,false),
    Perch("Perch",55,Season.Winter,false),
    Flounder("Flounder",100,Season.Spring,false),
    Lionfish("Lionfish",100,Season.Spring,false),
    Herring("Herring",30,Season.Spring,false),
    Ghostfish("Ghostfish",45,Season.Spring,false),
    Tilapia("Tilapia",75,Season.Summer,false),
    Dorado("Dorado",100,Season.Summer,false),
    Sunfish("Sunfish",30,Season.Summer,false),
    Rainbow_Trout("Rainbow Trout",65,Season.Summer,false),
    Legend("Legend",5000,Season.Spring,true),
    Glacierfish("Glacierfish",1000,Season.Winter,true),
    Angler("Angler",900,Season.Fall,true),
    Crimsonfish("Crimsonfish",1500,Season.Summer,true),

    ;
    private final String name;
    private final int price;
    private final Season season;
    private final boolean legendary;


    FishType(String name, int price, Season season, boolean legendary){
        this.name = name;
        this.price = price;
        this.season = season;
        this.legendary = legendary;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public Season getSeason() {
        return season;
    }
    public boolean isLegendary() {
        return legendary;
    }


}
