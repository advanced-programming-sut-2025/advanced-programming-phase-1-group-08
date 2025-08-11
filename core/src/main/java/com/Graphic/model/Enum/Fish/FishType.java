package com.Graphic.model.Enum.Fish;

import com.Graphic.model.Enum.WeatherTime.Season;

public enum FishType {

    Salmon("Salmon",75,Season.Fall,false, "Ariyo/Fish/Salmon.png"),
    Sardine("Sardine",40,Season.Fall,false, "Ariyo/Fish/Sardine.png"),
    Shad("Shad",60,Season.Fall,false, "Ariyo/Fish/Shad.png"),
    Blue_Discus("Blue Discus",120,Season.Fall,false, "Ariyo/Fish/Blue_Discus.png"),
    Midnight_Carp("Midnight Carp",150,Season.Winter,false, "Ariyo/Fish/Midnight_Carp.png"),
    Squid("Squid",80,Season.Winter,false, "Ariyo/Fish/Squid.png"),
    Tuna(  "Tuna",100,Season.Winter,false, "Ariyo/Fish/Tuna.png"),
    Perch("Perch",55,Season.Winter,false, "Ariyo/Fish/Perch.png"),
    Flounder("Flounder",100,Season.Spring,false, "Ariyo/Fish/Flounder.png"),
    Lionfish("Lionfish",100,Season.Spring,false, "Ariyo/Fish/Lionfish.png"),
    Herring("Herring",30,Season.Spring,false, "Ariyo/Fish/Herring.png"),
    Ghostfish("Ghostfish",45,Season.Spring,false, "Ariyo/Fish/Ghostfish.png"),
    Tilapia("Tilapia",75,Season.Summer,false, "Ariyo/Fish/Tilapia.png"),
    Dorado("Dorado",100,Season.Summer,false, "Ariyo/Fish/Dorado.png"),
    Sunfish("Sunfish",30,Season.Summer,false, "Ariyo/Fish/Sunfish.png"),
    Rainbow_Trout("Rainbow Trout",65,Season.Summer,false, "Ariyo/Fish/Rainbow_Trout.png"),
    Legend("Legend",5000,Season.Spring,true, "Ariyo/Fish/Legend.png"),
    Glacierfish("Glacierfish",1000,Season.Winter,true, "Ariyo/Fish/Glacierfish.png"),
    Angler("Angler",900,Season.Fall,true, "Ariyo/Fish/Angler.png"),
    Crimsonfish("Crimsonfish",1500,Season.Summer,true, "Ariyo/Fish/Crimsonfish.png"),

    ;
    private final String name;
    private final int price;
    private final Season season;
    private final boolean legendary;
    private final String iconPath;



    FishType(String name, int price, Season season, boolean legendary, String iconPath){
        this.name = name;
        this.price = price;
        this.season = season;
        this.legendary = legendary;
        this.iconPath = iconPath;
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
    public String getIconPath() {
        return iconPath;
    }


}
