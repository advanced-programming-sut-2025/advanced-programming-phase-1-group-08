package model.Enum.AllPlants;

import static model.Color_Eraser.*;

public enum ForagingMineralsType {

    RUBY            (RED_BOLD+"★"+RESET,     250,  WHITE+"A precious stone that is sought after for its rich color and beautiful luster."+RESET,Math.random() < 0.005),
    COAL            (Brown+"C"+RESET,        15,   WHITE+"A combustible rock that is useful for crafting and smelting."+RESET,                  Math.random() < 0.07),
    IRON            (BRIGHT_BLACK+"I"+RESET, 10,   WHITE+"A fairly common ore that can be smelted into bars."+RESET,                            Math.random() < 0.08),
    TOPAZ           (BRIGHT_YELLOW+"T"+RESET,80,   WHITE+"Fairly common but still prized for its beauty."+RESET,                                Math.random() < 0.008),
    GOLD            (BRIGHT_YELLOW+"G"+RESET,25,   WHITE+"A precious ore that can be smelted into bars."+RESET,                                 Math.random() < 0.005),
    JADE            (GREEN_BOLD+"◆"+RESET,   200,  WHITE+"A pale green ornamental stone."+RESET,                                                Math.random() < 0.006),
    IRIDIUM         (BRIGHT_BLACK+"♕"+RESET, 100,  WHITE+"An exotic ore with many curious properties. Can be smelted into bars."+RESET,         Math.random() < 0.008),
    QUARTZ          (BRIGHT_WHITE+"◇"+RESET, 25,   WHITE+"A clear crystal commonly found in caves and mines."+RESET,                            Math.random() < 0.01),
    EMERALD         (GREEN_BOLD+"★"+RESET,   250,  WHITE+"A precious stone with a brilliant green color."+RESET,                                Math.random() < 0.05),
    COPPER          (BRIGHT_BLACK+"C"+RESET, 5,    WHITE+"A common ore that can be smelted into bars."+RESET,                                   Math.random() < 0.12),
    DIAMOND         (CYAN_BOLD+"★"+RESET,    750,  WHITE+"A rare and valuable gem."+RESET,                                                      Math.random() < 0.002),
    AMETHYST        (BRIGHT_PURPLE+"◆"+RESET,100,  WHITE+"A purple variant of quartz."+RESET,                                                   Math.random() < 0.009),
    AQUAMARINE      (BRIGHT_CYAN+"Å"+RESET,  180,  WHITE+"A shimmery blue-green gem."+RESET,                                                    Math.random() < 0.007),
    FROZEN_TEAR     (BRIGHT_CYAN+"◆"+RESET,  75,   WHITE+"A crystal fabled to be the frozen tears of a yeti."+RESET,                            Math.random() < 0.009),
    FIRE_QUARTZ     (BRIGHT_RED+"⚡"+RESET,   100,  WHITE+"A glowing red crystal commonly found near hot lava."+RESET,                           Math.random() < 0.008),
    EARTH_CRYSTAL   (BRIGHT_BLUE+"◆"+RESET,  50,   WHITE+"A resinous substance found near the surface."+RESET,                                  Math.random() < 0.01),
    PRISMATIC_SHARD (PURPLE_BOLD+"★"+RESET,  2000, WHITE+"A very rare and powerful substance with unknown origins."+RESET,                      Math.random() < 0.001);

    private final String symbol;
    private final int price;
    private final String description;
    private final boolean probability;

    ForagingMineralsType (String symbol, int price, String description, boolean probability) {
        this.symbol = symbol;
        this.price = price;
        this.description = description;
        this.probability = probability;
    }

    public String getCharacter()   {return symbol;}
    public int getPrice()          {return price;}
    public String getDescription() {return description;}
    public boolean beCreated()     {return probability;}
}
