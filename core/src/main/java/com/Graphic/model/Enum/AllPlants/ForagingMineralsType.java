package com.Graphic.model.Enum.AllPlants;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public enum ForagingMineralsType {

    RUBY            ("Ruby",            "Erfan/Node/Ruby_Node.png",     250,
            WHITE+"A precious stone that is sought after for its rich color and beautiful luster."+RESET, 0.1),
    COAL            ("Coal",            "Erfan/Node/Coal_Node.png",        15,
            WHITE+"A combustible rock that is useful for crafting and smelting."+RESET,1),
    IRON            ("Iron",            "Erfan/Node/Iron_Node.png", 10,
            WHITE+"A fairly common ore that can be smelted into bars."+RESET,       0.8),
    TOPAZ           ("Topaz",           "Erfan/Node/Topaz_Node.png",80,
            WHITE+"Fairly common but still prized for its beauty."+RESET,           0.2),
    GOLD            ("Gold",            "Erfan/Node/Gold_Node.png",25,
            WHITE+"A precious ore that can be smelted into bars."+RESET,            0.1),
    JADE            ("Jade",            "Erfan/Node/Jade_Node.png",   200,
            WHITE+"A pale green ornamental stone."+RESET,                           0.19),
    IRIDIUM         ("Iridium",         "Erfan/Node/Iridium_Node.png", 100,
            WHITE+"An exotic ore with many curious properties. Can be smelted into bars."+RESET, 0.22),
    QUARTZ          ("Quartz",          "Erfan/Node/Quartz.png", 25,
            WHITE+"A clear crystal commonly found in caves and mines."+RESET,       0.9),
    EMERALD         ("Emerald",         "Erfan/Node/Emerald_Node.png",   250,
            WHITE+"A precious stone with a brilliant green color."+RESET,           0.2),
    COPPER          ("Copper",          "Erfan/Node/Copper_Node.png", 5,
            WHITE+"A common ore that can be smelted into bars."+RESET,              0.95),
    DIAMOND         ("Diamond",         "Erfan/Node/Diamond_Node.png",    750,
            WHITE+"A rare and valuable gem."+RESET,                                 0.05),
    AMETHYST        ("Amethyst",        "Erfan/Node/Amethyst_Node.png",100,
            WHITE+"A purple variant of quartz."+RESET,                              0.2),
    AQUAMARINE      ("Aquamarine",      "Erfan/Node/Aquamarine_Node.png",  180,
            WHITE+"A shimmery blue-green gem."+RESET,                               0.2),
    FROZEN_TEAR     ("Frozen Tear",     "Erfan/Node/Frozen_Geode_Node.png",  75,
            WHITE+"A crystal fabled to be the frozen tears of a yeti."+RESET,       0.52),
    FIRE_QUARTZ     ("Fire Quartz",     "Erfan/Node/Fire_Quartz.png",   100,
            WHITE+"A glowing red crystal commonly found near hot lava."+RESET,      0.45),
    EARTH_CRYSTAL   ("Earth Crystal",   "Erfan/Node/Earth_Crystal.png",  50,
            WHITE+"A resinous substance found near the surface."+RESET,             0.7),
    PRISMATIC_SHARD ("Prismatic Shard", "Erfan/Node/Prismatic_Shard.png",  2000,
            WHITE+"A very rare and powerful substance with unknown origins."+RESET, 0.01);


    private final int price;
    private final String texturePath;
    private final String description;
    private final double probability;
    private final String displayName;

    ForagingMineralsType (String displayName, String symbol, int price, String description, double probability) {
        this.displayName = displayName;
        this.texturePath = symbol;
        this.price = price;
        this.description = description;
        this.probability = probability;
    }

    public String getDisplayName() {return displayName;};
    public String getTexturePath()   {return texturePath;}
    public int getPrice()          {return price;}
    public String getDescription() {return description;}
    public double getProbability()     {return probability;}

    public static ForagingMineralsType fromDisplayName(String displayName) {
        for (ForagingMineralsType type : ForagingMineralsType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
