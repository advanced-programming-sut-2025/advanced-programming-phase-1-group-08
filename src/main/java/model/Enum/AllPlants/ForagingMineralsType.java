package model.Enum.AllPlants;

import static model.Color_Eraser.*;

public enum ForagingMineralsType {

    RUBY            ("Ruby",            RED_BOLD+"★ "+RESET,     250,
            WHITE+"A precious stone that is sought after for its rich color and beautiful luster."+RESET, 0.1),
    COAL            ("Coal",            Brown+"C "+RESET,        15,
            WHITE+"A combustible rock that is useful for crafting and smelting."+RESET,1),
    IRON            ("Iron",            BRIGHT_BLACK+"I "+RESET, 10,
            WHITE+"A fairly common ore that can be smelted into bars."+RESET,       0.8),
    TOPAZ           ("Topaz",           BRIGHT_YELLOW+"T "+RESET,80,
            WHITE+"Fairly common but still prized for its beauty."+RESET,           0.2),
    GOLD            ("Gold",            BRIGHT_YELLOW+"G "+RESET,25,
            WHITE+"A precious ore that can be smelted into bars."+RESET,            0.1),
    JADE            ("Jade",            GREEN_BOLD+"◆ "+RESET,   200,
            WHITE+"A pale green ornamental stone."+RESET,                           0.19),
    IRIDIUM         ("Iridium",         BRIGHT_BLACK+"♕ "+RESET, 100,
            WHITE+"An exotic ore with many curious properties. Can be smelted into bars."+RESET, 0.22),
    QUARTZ          ("Quartz",          BRIGHT_WHITE+"◇ "+RESET, 25,
            WHITE+"A clear crystal commonly found in caves and mines."+RESET,       0.9),
    EMERALD         ("Emerald",         GREEN_BOLD+"★ "+RESET,   250,
            WHITE+"A precious stone with a brilliant green color."+RESET,           0.2),
    COPPER          ("Copper",          BRIGHT_BLACK+"C "+RESET, 5,
            WHITE+"A common ore that can be smelted into bars."+RESET,              0.95),
    DIAMOND         ("Diamond",         CYAN_BOLD+"★ "+RESET,    750,
            WHITE+"A rare and valuable gem."+RESET,                                 0.05),
    AMETHYST        ("Amethyst",        BRIGHT_PURPLE+"◆ "+RESET,100,
            WHITE+"A purple variant of quartz."+RESET,                              0.2),
    AQUAMARINE      ("Aquamarine",      BRIGHT_CYAN+"Å "+RESET,  180,
            WHITE+"A shimmery blue-green gem."+RESET,                               0.2),
    FROZEN_TEAR     ("Frozen Tear",     BRIGHT_CYAN+"◆ "+RESET,  75,
            WHITE+"A crystal fabled to be the frozen tears of a yeti."+RESET,       0.52),
    FIRE_QUARTZ     ("Fire Quartz",     BRIGHT_RED+"⚡"+RESET,   100,
            WHITE+"A glowing red crystal commonly found near hot lava."+RESET,      0.45),
    EARTH_CRYSTAL   ("Earth Crystal",   BRIGHT_BLUE+"◆ "+RESET,  50,
            WHITE+"A resinous substance found near the surface."+RESET,             0.7),
    PRISMATIC_SHARD ("Prismatic Shard", PURPLE_BOLD+"★ "+RESET,  2000,
            WHITE+"A very rare and powerful substance with unknown origins."+RESET, 0.01);


    private final String symbol;
    private final int price;
    private final String description;
    private final double probability;
    private final String displayName;

    ForagingMineralsType (String displayName, String symbol, int price, String description, double probability) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.price = price;
        this.description = description;
        this.probability = probability;
    }

    public String getDisplayName() {return displayName;};
    public String getCharacter()   {return symbol;}
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
