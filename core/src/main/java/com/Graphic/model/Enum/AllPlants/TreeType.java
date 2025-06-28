package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

import static com.Graphic.model.Color_Eraser.*;

public enum TreeType {

    ApricotTree     ("Apricot Tree", TreesProductType.Apricot, "Apricot Sapling",
            1,BG_WHITE+BLACK+BOLD+"A "+RESET, BG_WHITE+BRIGHT_GREEN+BOLD+"A "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }
    },
    CherryTree      ("Cherry Tree", TreesProductType.Cherry, "Cherry Sapling",
            1,BG_WHITE+BLACK+BOLD+"C "+RESET, BG_WHITE+GREEN+BOLD+"C "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }
    },
    BananaTree      ("Banana Tree", TreesProductType.Banana, "Banana Sapling",
            1,BG_WHITE+BLACK+BOLD+"B "+RESET, BG_WHITE+GREEN+BOLD+"B "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }
    },
    MangoTree       ("Mango Tree", TreesProductType.Mango, "Mango Sapling",
            1,BG_WHITE+BLACK+BOLD+"M "+RESET, BG_WHITE+GREEN+BOLD+"M "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

    },
    OrangeTree      ("Orange Tree", TreesProductType.Orange, "Orange Sapling",
            1,BG_WHITE+BLACK+BOLD+"O "+RESET, BG_WHITE+GREEN+BOLD+"O "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

    },
    PeachTree       ("Peach Tree", TreesProductType.Peach, "Peach Sapling",
            1,BG_WHITE+BLACK+BOLD+"P "+RESET, BG_WHITE+GREEN+BOLD+"P "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

    },
    AppleTree       ("Apple Tree", TreesProductType.Apple, "Apple Sapling",
            1,BG_WHITE+BLACK+BOLD+"A "+RESET, BG_WHITE+GREEN+BOLD+"A "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }

    },
    OakTree         ("Oak Tree", TreesProductType.OakResin, "Acorns",
            7,BG_WHITE+BLACK+BOLD+"O "+RESET, BG_WHITE+GREEN+BOLD+"O "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

    },
    MapleTree       ("Maple Tree", TreesProductType.MapleSyrup, "Maple Seeds",
            9,BG_WHITE+BLACK+BOLD+"M "+RESET, BG_WHITE+GREEN+BOLD+"M "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

    },
    PineTree        ("Pine Tree", TreesProductType.PineTar, "Pine Cones",
            5,BG_WHITE+BLACK+BOLD+"P "+RESET, BG_WHITE+GREEN+BOLD+"P "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }

    },
    MahoganyTree    ("Mahogany Tree", TreesProductType.Sap, "Mahogany Seeds",
            1,BG_WHITE+BRIGHT_WHITE+BOLD+"M "+RESET, BG_WHITE+BROWN+BOLD+"M "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

    },
    MysticTree      ("Mystic Tree", TreesProductType.MysticSyrup, "Mystic Tree Seeds",
            7,BG_WHITE+PURPLE+BOLD+"M "+RESET, BG_WHITE+BROWN+BOLD+"M "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

    },
    MushroomTree    ("Mushroom Tree", TreesProductType.CommonMushroom, "Mushroom Tree Seeds",
            1,BG_BLUE+BLACK+BOLD+"M "+RESET, BG_BLUE+GREEN+BOLD+"M "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

    },
    PomegranateTree ("Pomegranate Tree", TreesProductType.Pomegranate, "Pomegranate Sapling",
            1,BG_WHITE+BROWN+BOLD+"P "+RESET, BG_WHITE+RED+BOLD+"P "+RESET) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

    };

    private final String displayName;
    private final TreesProductType productType;
    private final String sourceName;
    private final int harvestYield;
    private final String icon1;
    private final String icon2;

    TreeType (String displayName, TreesProductType productType, String sourceName,
              int harvestYield, String icon1, String icon2) {

        this.displayName = displayName;
        this.productType = productType;
        this.sourceName = sourceName;
        this.harvestYield = harvestYield;
        this.icon1 = icon1;
        this.icon2 = icon2;
    }

    public String getIcon1() {

        return icon1;
    }
    public String getIcon2() {

        return icon2;
    }
    public String getDisplayName() {return displayName;}
    public String getSourceName() {

        return sourceName;
    }
    public TreesProductType getProductType() {return productType;}
    public int getHarvestYield() {return harvestYield;}
    public static String getInformation (TreeType type) {

        return BLUE+"Name : "+BRIGHT_PURPLE + type.displayName +
                BLUE+"\nFruit : "+BRIGHT_PURPLE + type.productType.getDisplayName() +
                BLUE+"\nMature Icon   : "+BRIGHT_PURPLE + type.icon2 +
                BLUE+"\nSeedling Icon : "+BRIGHT_PURPLE + type.icon1 +
                BLUE+"\nFruit Harvest Cycle : "+BRIGHT_PURPLE + type.harvestYield;
    }

    public static TreeType fromDisplayName(String displayName) {
        for (TreeType type : TreeType.values())
            if (type.getDisplayName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
    public abstract ArrayList<Season> getSeason();

}
