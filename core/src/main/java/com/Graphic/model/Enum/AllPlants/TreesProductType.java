package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.Enum.WeatherTime.Season;

import java.util.ArrayList;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public enum TreesProductType {

    Apricot     ("Apricot",   59,  true,  38,  "apricot/Apricot") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.ApricotTree.getSeason();
        }
    },
    Cherry      ("Cherry",    80,  true,  38,  "cherry/Cherry") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.CherryTree.getSeason();
        }
    },
    Banana      ("Banana",    150, true,  75,  "banana/Banana") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.BananaTree.getSeason();
        }
    },
    Mango       ("Mango",     130, true,  100, "mango/Mango") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.MangoTree.getSeason();
        }
    },
    Orange      ("Orange",    100, true,  38,  "orange/Orange") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.OrangeTree.getSeason();
        }
    },
    Peach       ("Peach",     140, true,  38,  "peach/Peach") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.PeachTree.getSeason();
        }
    },
    Apple       ("Apple",     100, true,  38,  "apple/Apple") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.AppleTree.getSeason();
        }
    },
    OakResin    ("Oak Resin", 150, false, 0,   "oak/Acorn") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.OakTree.getSeason();
        }
    },
    MapleSyrup  ("Maple Syrup",200, false, 0,  "maple/Maple_Seed") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.MapleTree.getSeason();
        }
    },
    PineTar     ("Pine Tar",  100, false, 0,   "pine/Pine_Tar") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.PineTree.getSeason();
        }
    },
    Sap         ("Sap",       2,   false, -2,  "Sap") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.MahoganyTree.getSeason();
        }
    },
    MysticSyrup ("Mystic Syrup",1000,false, 500, "mystic/Mystic_Syrup") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.MysticTree.getSeason();
        }
    },
    Pomegranate ("Pomegranate",140, true,  38, "pomegranate/Pomegranate") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.PomegranateTree.getSeason();
        }
    },
    CommonMushroom("Common Mushroom",40,true,  38, "mushroom/Common_Mushroom") {

        @Override
        public ArrayList<Season> getSeason() {
            return TreeType.MushroomTree.getSeason();
        }
    };

    private final String inventoryIconPath;
    private final String displayName;
    private final boolean edible;
    private final int price;
    private final int energy;

    TreesProductType (String displayName, int price, boolean edible, int energy, String inventoryIconPath) {
        this.inventoryIconPath = inventoryIconPath;
        this.price = price;
        this.edible = edible;
        this.energy = energy;
        this.displayName = displayName;
    }

    public int getPrice() {
        return price;
    }
    public boolean isEdible() {
        return edible;
    }
    public int getEnergy() {
        return energy;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getInventoryIconPath() {
        return "Erfan/plants/Trees/" + inventoryIconPath + ".png";
    }

    public static String getInformation (TreesProductType type) {

        StringBuilder builder = new StringBuilder();

        builder.append(BLUE+"Name: "+RESET).append(type.displayName)
                .append(BLUE+"\nStages: "+RESET).append("7-7-7-7")
                .append(BLUE+"\nTotal Harvest Time: "+RESET).append("28")
                .append(BLUE+"\nOne Time: "+RESET).append("False")
                .append(BLUE+"\nRegrowth Time: "+RESET+"28")
                .append(BLUE+"\nBase Sell Price: "+RESET).append(type.getPrice())
                .append(BLUE+"\nIs Edible: "+RESET).append(type.isEdible())
                .append(BLUE+"\nBase Energy: "+RESET).append(type.energy)
                .append(BLUE+"\nSeason: "+RESET);

        ArrayList<Season> seasons = type.getSeason();
        for (Season season : seasons) builder.append(season.getDisplayName()).append(" ");

        builder.append(BLUE+"\nCan Become Giant: "+RESET).append("No");
        return builder.toString();
    }
    public static TreesProductType fromDisplayName(String displayName) {
        for (TreesProductType type : TreesProductType.values())
            if (type.getDisplayName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
    public abstract ArrayList<Season> getSeason ();
}

