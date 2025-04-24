package model.Enum.AllPlants;

import static model.Color_Eraser.*;

public enum ForagingMineralsType {

    RUBY            ("R", 250,  WHITE+"A precious stone that is sought after for its rich color and beautiful luster."+RESET,Math.random() < 0.005),
    COAL            ("C", 15,   WHITE+"A combustible rock that is useful for crafting and smelting."+RESET,                  Math.random() < 0.07),
    IRON            ("I", 10,   WHITE+"A fairly common ore that can be smelted into bars."+RESET,                            Math.random() < 0.08),
    TOPAZ           ("T", 80,   WHITE+"Fairly common but still prized for its beauty."+RESET,                                Math.random() < 0.008),
    GOLD            ("G", 25,   WHITE+"A precious ore that can be smelted into bars."+RESET,                                 Math.random() < 0.005),
    JADE            ("J", 200,  WHITE+"A pale green ornamental stone."+RESET,                                                Math.random() < 0.006),
    IRIDIUM         ("♕", 100,  WHITE+"An exotic ore with many curious properties. Can be smelted into bars."+RESET,         Math.random() < 0.008),
    QUARTZ          ("◇", 25,   WHITE+"A clear crystal commonly found in caves and mines."+RESET,                            Math.random() < 0.01),
    EMERALD         ("✦", 250,  WHITE+"A precious stone with a brilliant green color."+RESET,                                Math.random() < 0.05),
    COPPER          ("C", 5,    WHITE+"A common ore that can be smelted into bars."+RESET,                                   Math.random() < 0.12),
    DIAMOND         ("◆", 750,  WHITE+"A rare and valuable gem."+RESET,                                                      Math.random() < 0.002),
    AMETHYST        ("α", 100,  WHITE+"A purple variant of quartz."+RESET,                                                   Math.random() < 0.009),
    AQUAMARINE      ("Å", 180,  WHITE+"A shimmery blue-green gem."+RESET,                                                    Math.random() < 0.007),
    FROZEN_TEAR     ("❄", 75,   WHITE+"A crystal fabled to be the frozen tears of a yeti."+RESET,                            Math.random() < 0.009),
    FIRE_QUARTZ     ("⚡", 100,  WHITE+"A glowing red crystal commonly found near hot lava."+RESET,                           Math.random() < 0.008),
    EARTH_CRYSTAL   ("⊕", 50,   WHITE+"A resinous substance found near the surface."+RESET,                                  Math.random() < 0.01),
    PRISMATIC_SHARD ("☼", 2000, WHITE+"A very rare and powerful substance with unknown origins."+RESET,                      Math.random() < 0.001);

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

    public String getCharacter() {
        return symbol;
    }
    public int getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }


    public boolean beCreated() {
        // پیاده‌سازی خود را اینجا قرار دهید (فعلاً مقدار پیش‌فرض false برمی‌گرداند)
        return probability;
    }
}
