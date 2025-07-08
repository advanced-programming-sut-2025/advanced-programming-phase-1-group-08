package com.Graphic.model.Enum.AllPlants;

import com.Graphic.model.App;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Enum.WeatherTime.Season;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.Graphic.model.App.currentGame;
import static com.Graphic.model.HelpersClass.Color_Eraser.*;
import static com.Graphic.model.Enum.AllPlants.CropsType.*;

public enum ForagingSeedsType {

    JazzSeeds       ( "Jazz Seeds",       true, BlueJazz,10000,
            4, false, new int[]{1, 2, 2, 2},5,5 ,
            "Erfan/plants/crops/blueJazz/Jazz_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore )  ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/blueJazz/Blue_Jazz_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/blueJazz/Blue_Jazz_Stage_" + this.getGrowthStages() + ".png";
        }
        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 37;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 30;
                    }
                    return 45;
                }
            }
            return -1;
        }
    },
    CarrotSeeds     ( "Carrot Seeds",     true, Carrot,  10000,
            3, false, new int[]{1, 1, 1,} ,10,0 ,
            "Erfan/plants/crops/carrot/Carrot_Seeds.png", List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/carrot/Carrot_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/carrot/Carrot_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 50;}
                case PierreGeneralStore -> {return -1;}
            }
            return -1;
        }
    },
    CauliflowerSeeds( "Cauliflower Seeds",true, Cauliflower,10000,
            5, true, new int[]{1, 2, 4, 4, 1} , 5,5 ,
            "Erfan/plants/crops/Cauliflower/Cauliflower_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }

        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/Cauliflower/Cauliflower_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/Cauliflower/Cauliflower_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public String getGiantTexturePath() {
            return "Erfan/plants/crops/giant/Giant_Cauliflower.png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 100;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 80;
                    }
                    return 120;
                }
            }
            return -1;
        }
    },
    CoffeeBean      ( "Coffee Bean",       false, CropsType.CoffeeBean,2,
            5, false, new int[]{1, 2, 2, 3, 2} , 1, 5 ,
            "Erfan/plants/crops//.png", List.of(MarketType.JojaMart ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/coffeeBean/Coffee_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/coffeeBean/Coffee_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 200;}
                default -> {return -1 ;}
            }
        }
    },             // TODO
    GarlicSeeds     ( "Garlic Seeds",    true, Garlic,10000,
            4, false, new int[]{1, 1, 1, 1} ,0 , 5 ,
            "Erfan/plants/crops/Garlic/Garlic_Seeds.png", List.of( MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/Garlic/Garlic_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/Garlic/Garlic_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 40;
                    }
                    return 60;
                }
                default -> {return -1 ;}
            }
        }
    },
    BeanStarter     ( "Bean Starter",     false, GreenBean,3,
            5, false, new int[]{1, 1, 1, 3, 4} , 5 , 5 ,
            "Erfan/plants/crops/GreenBean/.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/GreenBean/Green_Bean_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/GreenBean/Green_Bean_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 75;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 60;
                    }
                    return 90;
                }
                default -> {return -1 ;}
            }
        }
    }, // TODO
    KaleSeeds       ( "Kale Seeds",       true, Kale,10000,
            4, false, new int[]{1, 2, 2, 1} ,5,5 ,
            "Erfan/plants/crops/kale/Kale_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/kale/Kale_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/kale/Kale_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 87;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 70;
                    }
                    return 105;
                }
                default -> {return -1 ;}
            }
        }
    },
    ParsnipSeeds    ( "Parsnip Seeds",    true, Parsnip,10000,
            4, false, new int[]{1, 1, 1, 1} ,5, 5 ,
            "Erfan/plants/crops/parsnip/Parsnip_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/parsnip/Parsnip_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/parsnip/Parsnip_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 25;}
                case PierreGeneralStore -> {
                    if (App.currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 20;
                    }
                    return 30;
                }
                default -> {return -1 ;}
            }
        }
    },
    PotatoSeeds     ( "Potato Seeds",     true, Potato,10000,
            5, false, new int[]{1, 1, 1, 2, 1} , 5 , 5 ,
            "Erfan/plants/crops/potato/Potato_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/potato/Potato_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/potato/Potato_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 50;
                    }
                    return 75;
                }
                default -> {return -1 ;}
            }
        }
    },
    RhubarbSeeds    ( "Rhubarb Seeds",    true, Rhubarb,10000,
            5, false, new int[]{2, 2, 2, 3, 4} , 5 , 0 ,
            "Erfan/plants/crops/rhubarb/Rhubarb_Seeds.png", List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/rhubarb/Rhubarb_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/rhubarb/Rhubarb_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 100;}
                default -> {return -1 ;}
            }
        }
    },
    StrawberrySeeds ( "Strawberry Seeds", false, Strawberry,4,
            5, false, new int[]{1, 1, 2, 2, 2,} , 5 , 0 ,
            "Erfan/plants/crops/strawberry/Strawberry_Seeds.png", List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/strawberry/Strawberry_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/strawberry/Strawberry_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 100;}
                default -> {return -1 ;}
            }
        }
    },
    TulipBulb       ( "Tulip Bulb",       true, Tulip,10000,
            4, false, new int[]{1, 1, 2, 2} , 5 , 5 ,
            "Erfan/plants/crops/tulip/Tulip_Bulb.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/tulip/Tulip_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/tulip/Tulip_Stage_" + this.getGrowthStages() + ".png";
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 25;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 20;
                    }
                    return 30;
                }
                default -> {return -1 ;}
            }
        }
    },
    RiceShoot       ( "Rice Shoot",       true, UnmilledRice,10000,
            4, false, new int[]{1, 2, 2, 3} , 0 , 5 ,
            "Erfan/plants/crops/unmilledRice/.png", List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/unmilledRice/Unmilled_Rice_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/unmilledRice/Unmilled_Rice_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 40;
                    }
                    return 60;
                }
                default -> {return -1 ;}
            }
        }
    }, // TODO
    BlueberrySeeds  ( "Blueberry Seeds",  false, Blueberry,4,
            5, false, new int[]{1, 3, 3, 4, 2} , 0 , 5 ,
            "Erfan/plants/crops/blueberry/Blueberry_Seeds.png", List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/blueberry/Blueberry_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/blueberry/Blueberry_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 80;
                    }
                    return 120;
                }
                default -> {return -1 ;}
            }
        }
    },
    CornSeeds       ( "Corn Seeds",       false, Corn,4,
            5, false, new int[]{2, 3, 3, 3, 3} ,5 , 5 ,
            "Erfan/plants/crops/corn/Corn_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/corn/Corn_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/corn/Corn_Stage_" + this.getGrowthStages() + ".png";
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 187;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 150;
                    }
                    return 225;
                }
                default -> {return -1 ;}
            }
        }
    },
    HopsStarter     ( "Hops Starter",     false, Hops,1,
            5, false, new int[]{1, 1, 2, 3, 4} , 5 , 5 ,
            "Erfan/plants/crops/hops/Hops_Starter.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/hops/Hops_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/hops/Hops_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 75;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 60;
                    }
                    return 90;
                }
                default -> {return -1 ;}
            }
        }
    },
    PepperSeeds     ( "Pepper Seeds",     false, HotPepper,3,
            5, false, new int[]{1, 1, 1, 1, 1} , 5 , 5 ,
            "Erfan/plants/crops/hotPepper/Pepper_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }

        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/hotPepper/Hot_Pepper_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/hotPepper/Hot_Pepper_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 50;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 40;
                    }
                    return 60;
                }
                default -> {return -1 ;}
            }
        }
    },
    MelonSeeds      ( "Melon Seeds",      true, Melon,10000,
            5, true, new int[] {1, 2, 3, 3, 3} , 5 , 5 ,
            "Erfan/plants/crops/melon/Melon_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }

        @Override
        public String getGiantTexturePath() {
            return "Erfan/plants/crops/giant/Giant_Melon.png";
        }

        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/melon/Melon_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/melon/Melon_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 100;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 80;
                    }
                    return 120;
                }
                default -> {return -1 ;}
            }
        }
    },
    PoppySeeds      ( "Poppy Seeds",      true, Poppy,10000,
            4, false, new int[]{1, 2, 2, 2} , 5 , 5 ,
            "Erfan/plants/crops/poppy/Poppy_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/poppy/Poppy_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/poppy/Poppy_Stage_" + this.getGrowthStages() + ".png";
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 125;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 100;
                    }
                    return 150;
                }
                default -> {return -1 ;}
            }
        }
    },
    RadishSeeds     ("Radish Seeds",      true, Radish,10000,
            4, false, new int[]{2, 1, 2, 1} , 5 , 5 ,
            "Erfan/plants/crops/radish/Radish_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/radish/Radish_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/radish/Radish_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 50;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 40;
                    }
                    return 60;
                }
                default -> {return -1 ;}
            }
        }
    },
    RedCabbageSeeds ( "Red Cabbage Seeds",true, RedCabbage,10000,
            5, false, new int[]{2, 1, 2, 2, 2} ,0 ,5 ,
            "Erfan/plants/crops/redCabbage/Red_Cabbage_Seeds.png", List.of(MarketType.PierreGeneralStore)) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/redCabbage/Red_Cabbage_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/redCabbage/Red_Cabbage_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 100;
                    }
                    return 150;
                }
                default -> {return -1 ;}
            }
        }
    },
    StarfruitSeeds  ( "Starfruit Seeds",  true, Starfruit,10000,
            5, false, new int[]{2, 3, 2, 3, 3} , 5 , 0 ,
            "Erfan/plants/crops/starfruit/Starfruit_Seeds.png", List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/starfruit/Starfruit_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/starfruit/Starfruit_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 400;}
                default -> {return -1 ;}
            }
        }
    },
    SpangleSeeds    ( "Spangle Seeds",    true, SummerSpangle,10000,
            4, false, new int[]{1, 2, 3, 1} , 5 , 5 ,
            "Erfan/plants/crops/summerSpangle/Summer_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/summerSpangle/Summer_Spangle_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/summerSpangle/Summer_Spangle_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 50;
                    }
                    return 75;
                }
                default -> {return -1 ;}
            }
        }
    },
    SummerSquashSeeds( "Summer Squash Seeds",false, SummerSquash,3,
            5, false, new int[]{1, 1, 1, 2, 1} ,10 , 0 ,
            "Erfan/plants/crops/summerSquash/Summer_Squash_Seeds.png", List.of(MarketType.JojaMart ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/summerSquash/Summer_Squash_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/summerSquash/Summer_Squash_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 10;}
                default -> {return -1 ;}
            }
        }
    },
    SunflowerSeeds  ( "Sunflower Seeds",     true, Sunflower,10000,
            4, false, new int[]{1, 2, 3, 2} , 5 , 5 ,
            "Erfan/plants/crops/sunflower/Sunflower_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/sunflower/Sunflower_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/sunflower/Sunflower_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 125;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Summer)){
                        return 200;
                    }
                    return 300;
                }
                default -> {return -1 ;}
            }
        }
    },
    TomatoSeeds     ( "Tomato Seeds",        false, Tomato,4,
            5, false, new int[]{2, 2, 2, 2, 3} , 5 , 5 ,
            "Erfan/plants/crops/tomato/Tomato_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/tomato/Tomato_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/tomato/Tomato_Stage_" + this.getGrowthStages() + ".png";
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Spring)){
                        return 50;
                    }
                    return 75;
                }
                default -> {return -1 ;}
            }
        }
    },
    WheatSeeds      ( "Wheat Seeds",         true, Wheat,10000,
            4, false, new int[]{1, 1, 1, 1} , 10 , 5 ,
            "Erfan/plants/crops/wheat/Wheat_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/wheat/Wheat_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/wheat/Wheat_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 12;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 10;
                    }
                    return 15;
                }
                default -> {return -1 ;}
            }
        }
    },
    AmaranthSeeds   ( "Amaranth Seeds",      true, Amaranth,10000,
            4, false, new int[]{1, 2, 2, 2} , 5 , 5 ,
            "Erfan/plants/crops/amaranth/Amaranth_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/amaranth/Amaranth_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/amaranth/Amaranth_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 87;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 70;
                    }
                    return 105;
                }
                default -> {return -1 ;}
            }
        }
    },
    ArtichokeSeeds  ( "Artichoke Seeds",     true, Artichoke,10000,
            5, false, new int[]{2, 2, 1, 2, 1} ,0 , 5 ,
            "Erfan/plants/crops/artichoke/Artichoke_Seeds.png", List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/artichoke/Artichoke_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/artichoke/Artichoke_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 30;
                    }
                    return 45;
                }
                default -> {return -1 ;}
            }
        }
    },
    BeetSeeds       ( "Beet Seeds",          true, Beet,10000,
            4, false, new int[]{1, 1, 2, 2} ,5 , 0 ,
            "Erfan/plants/crops/beet/Beet_Seeds.png", List.of(MarketType.JojaMart ) ){

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/beet/Beet_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/bett/Beet_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 20;}
                default -> {return -1 ;}
            }
        }
    },
    BokChoySeeds    ("BokChoy Seeds",        true, BokChoy,10000,
            4, false, new int[]{1, 1, 1, 1} , 5 , 5 ,
            "Erfan/plants/crops/bokChoy/Bok_Choy_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/bokChoy/Bok_Choy_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/bokChoy/Bok_Choy_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 50;
                    }
                    return 75;
                }
                default -> {return -1 ;}
            }
        }
    },
    BroccoliSeeds   ( "Broccoli Seeds",      false, Broccoli,4,
            4, false, new int[]{2, 2, 2, 2} ,5 , 0 ,
            "Erfan/plants/crops/Broccoli/Broccoli_Seeds.png", List.of(MarketType.JojaMart ) ){
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/broccoli/Broccoli_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/broccoli/Broccoli_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 15;}
                default -> {return -1 ;}
            }
        }
    },
    CranberrySeeds  ( "Cranberry Seeds",     false, Cranberries,5,
            5, false, new int[]{1, 2, 1, 1, 2} , 5 , 5 ,
            "Erfan/plants/crops/cranberries/Cranberry_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/cranberries/Cranberry_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/cranberries/Cranberry_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 300;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 240;
                    }
                    return 360;
                }
                default -> {return -1 ;}
            }
        }
    },
    EggplantSeeds   ( "Eggplant Seeds",      false, Eggplant,5,
            4, false, new int[]{1, 1, 1, 1} , 5 , 5 ,
            "Erfan/plants/crops/eggplant/Eggplant_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/eggplant/Eggplant_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/eggplant/Eggplant_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 25;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 20;
                    }
                    return 30;
                }
                default -> {return -1 ;}
            }
        }
    },
    FairySeeds      ( "Fairy Seeds",         true, FairyRose,10000,
            4, false, new int[]{1, 4, 4, 3}, 5,5 ,
            "Erfan/plants/crops/fairyRose/Fairy_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/fairyRose/Fairy_Rose_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/fairyRose/Fairy_Rose_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 250;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 200;
                    }
                    return 300;
                }
                default -> {return -1 ;}
            }
        }
    },
    GrapeStarter    ( "Grape Starter",       false, Grape,3,
            5, false, new int[]{1, 1, 2, 3, 3} ,0 , 5 ,
            "Erfan/plants/crops/grape/Grape_Starter.png", List.of(MarketType.PierreGeneralStore ) ) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/grape/Grape_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/grape/Grape_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 60;
                    }
                    return 90;
                }
                default -> {return -1 ;}
            }
        }
    },
    PumpkinSeeds    ( "Pumpkin Seeds",       true, Pumpkin,10000,
            5, true, new int[] {1, 2, 3, 4, 3} , 0 , 5 ,
            "Erfan/plants/crops/pumpkin/Pumpkin_Seeds.png", List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getGiantTexturePath() {
            return "Erfan/plants/crops/giant/Giant_Pumpkin.png";
        }

        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/pumpkin/Pumpkin_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/pumpkin/Pumpkin_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 100;
                    }
                    return 150;
                }
                default -> {return -1 ;}
            }
        }
    },
    YamSeeds        ( "Yam Seeds",           true, Yam,10000,
            4, false, new int[]{1, 3, 3, 3} , 5 , 5 ,
            "Erfan/plants/crops/yam/Yam_Seeds.png", List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/yam/Yam_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/yam/Yam_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 75;}
                case PierreGeneralStore -> {
                    if (currentGame.currentDate.getSeason().equals(Season.Fall)){
                        return 60;
                    }
                    return 90;
                }
                default -> {return -1 ;}
            }
        }
    },
    RareSeed        ( "Rare Seed",           true, SweetGemBerry,10000,
            5, false, new int[]{2, 4, 6, 6, 6} , 1 , 0 ,
            "Erfan/plants/crops/sweetGemBerry/Rare_Seed.png", List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/sweetGemBerry/Sweet_Gem_Berry_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/sweetGemBerry/Sweet_Gem_Berry_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 1000;}
                default -> {return -1 ;}
            }
        }
    },
    PowdermelonSeeds("Powdermelon Seeds",    true, Powdermelon,10000,
            5, true, new int[]{1, 2, 1, 2, 1} , 10 , 0 ,
            "Erfan/plants/crops/Powdermelon/Powdermelon_Seeds.png", List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Winter));
        }

        @Override
        public String getGiantTexturePath() {
            return "Erfan/plants/crops/giant/Giant_Powdermelon.png";
        }

        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/Powdermelon/Powdermelon_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/Powdermelon/Powdermelon_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 20;}
                default -> {return -1 ;}
            }
        }
    },
    AncientSeeds    ( "Ancient Seeds",       false, AncientFruit,7,
            5, false, new int[]{2, 7, 7, 7, 5} , 1 , 0 ,
            "Erfan/plants/crops/ancientFruit/Ancient_Seeds.png", List.of(MarketType.JojaMart) ) {

        public ArrayList<Season> getSeason() {
             return new ArrayList<>(Arrays.asList(Season.Fall, Season.Summer, Season.Spring));
        }

        @Override
        public String getTexturePath(int stage) {

            for (int i = 1; i < this.getGrowthStages(); i++)
                if (i == stage)
                    return "Erfan/plants/crops/ancientFruit/Ancient_Fruit_Stage_" + (i+1) + ".png";

            return "Erfan/plants/crops/ancientFruit/Ancient_Fruit_Stage_" + this.getGrowthStages() + ".png";
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {
                    return 500;
                }
                default -> {return -1 ;}
            }
        }
    };


    private final String inventoryIconPath;
    private final String displayName;
    private final boolean isOneTimeUse;
    private final CropsType productType;
    private final int growthStages;
    private final int regrowthTime;
    private final boolean canGrowGiant;
    private final int[] stageDays; // مدت زمان پیش‌فرض برای هر مرحله رشد
    public int JojaMartLimit;
    public int PierrGeneralLimit;
    private final List<MarketType> marketTypes;

    ForagingSeedsType(String displayName, boolean isOneTimeUse, CropsType productType,
                      int regrowthTime, int growthStages, boolean canGrowGiant, int[] stageDays,
                      int JojaMartLimit, int PierrGeneralLimit,
                      String inventoryIconPath, List<MarketType> marketTypes) {

        this.inventoryIconPath = inventoryIconPath;
        this.displayName = displayName;
        this.isOneTimeUse = isOneTimeUse;
        this.productType = productType;
        this.growthStages = growthStages;
        this.regrowthTime = regrowthTime;
        this.canGrowGiant = canGrowGiant;
        this.stageDays = stageDays;
        this.JojaMartLimit = JojaMartLimit;
        this.PierrGeneralLimit = PierrGeneralLimit;
        this.marketTypes = marketTypes;
    }

    public String getDisplayName() {
        return displayName;
    }
    public boolean isOneTimeUse() {
        return isOneTimeUse;
    }
    public CropsType getProductType() {
        return productType;
    }
    public int getGrowthStages() {
        return growthStages;
    }
    public boolean canGrowGiant() {
        return canGrowGiant;
    }
    public int getRegrowthTime() {
        return regrowthTime;
    }
    public int getStageDate (int Stage) {
        return stageDays[Stage];
    }
    public int[] getStageDays() {
        return stageDays;
    }


    public abstract ArrayList<Season> getSeason();
    public abstract int getPrice(MarketType marketType);
    public abstract String getTexturePath(int stage);

    public void increaseJojaMartLimit(int amount) {
        JojaMartLimit = Math.max(0 , JojaMartLimit + amount);
    }
    public void increasePierrGeneralLimit(int amount) {
        PierrGeneralLimit = Math.max(0 , JojaMartLimit + amount);
    }

    public List<MarketType> getMarketTypes() {
        return marketTypes;
    }
    public String getGiantTexturePath() {
        return null;
    }
    public String getInventoryIconPath() {
        return inventoryIconPath;
    }

    public static ForagingSeedsType fromDisplayName(String displayName) {
        for (ForagingSeedsType type : ForagingSeedsType.values())
            if (type.getDisplayName().equals(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }
}
