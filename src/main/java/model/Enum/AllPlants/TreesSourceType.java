package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

public enum TreesSourceType {

    Apricot_Sapling     (TreeType.ApricotTree,    0.0, "Apricot Sapling"){

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }

        @Override
        public int getPrice() {
            return 2000;
        }
    }, // TODO    probability
    Cherry_Sapling      (TreeType.CherryTree,     0.0, "Cherry Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring));
        }

        @Override
        public int getPrice() {
            return 3400;
        }
    },
    Banana_Sapling      (TreeType.BananaTree,     0.0, "Banana Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mango_Sapling       (TreeType.MangoTree,      0.0, "Mango Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Orange_Sapling      (TreeType.OrangeTree,     0.0, "Orange Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 4000;
        }
    },
    Peach_Sapling       (TreeType.PeachTree,      0.0, "Peach Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Summer));
        }

        @Override
        public int getPrice() {
            return 6000;
        }
    },
    Apple_Sapling       (TreeType.AppleTree,      0.0, "Apple Sapling")  {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }

        @Override
        public int getPrice() {
            return 4000;
        }
    },
    Acorns              (TreeType.OakTree,        0.0, "Acorns")         {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Maple_Seeds         (TreeType.MapleTree,      0.0, "Maple Seeds")         {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Pomegranate_Sapling (TreeType.PomegranateTree,0.0, "Pomegranate Sapling") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }

        @Override
        public int getPrice() {
            return 6000;
        }
    },
    Pine_Cones          (TreeType.PineTree,       0.0, "Pine Cones")          {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mahogany_Seeds      (TreeType.MahoganyTree,   0.0, "Mahogany Seeds")      {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mushroom_Tree_Seeds (TreeType.MushroomTree,   0.0, "Mushroom Tree Seeds") {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    },
    Mystic_Tree_Seeds   (TreeType.MysticTree,     0.0, "Mystic Tree Seeds")   {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of( Season.Spring, Season.Summer, Season.Fall, Season.Winter));
        }

        @Override
        public int getPrice() {
            return 0;
        }
    };

    private final String displayName;
    private final TreeType treeType;
    private final double probability;
    public abstract int getPrice();

    TreesSourceType(TreeType treeType,double probability, String displayName) {
        this.treeType = treeType;
        this.probability = probability;
        this.displayName = displayName;
    }

    public TreeType getTreeType() {
        return treeType;
    }
    public double getProbability() {
        return probability;
    }
    public String getDisplayName() {
        return displayName;
    }
    public abstract ArrayList<Season> getSeason();
}