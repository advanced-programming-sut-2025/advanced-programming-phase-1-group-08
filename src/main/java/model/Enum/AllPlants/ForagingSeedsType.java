package model.Enum.AllPlants;

import model.App;
import model.Enum.ItemType.MarketType;
import model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.Color_Eraser.*;
import static model.Enum.AllPlants.CropsType.*;

public enum ForagingSeedsType {

    JazzSeeds       ( "Jazz Seeds",       true, BlueJazz,10000,
            4, false, new int[]{1, 2, 2, 2},5,5 ,
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore )  ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 3)
                return YELLOW+"j"+RESET;
            else
                return GREEN+"j"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 37;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 2)
                return YELLOW+"c"+RESET;
            else
                return ORANGE+"c"+RESET;
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 4)
                return WHITE+"f"+RESET;
            else
                return GREEN+"f"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 100;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring, Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 4)
                return GREEN+"f"+RESET;
            else
                return BRIGHT_BROWN+"f"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 200;}
                default -> {return -1 ;}
            }
        }
    },
    GarlicSeeds     ( "JGarlic Seeds",    true, Garlic,10000,
            4, false, new int[]{1, 1, 1, 1} ,0 , 5 ,
            List.of( MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 3)
                return BRIGHT_BROWN+"g"+RESET;
            else
                return WHITE+"g"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 4)
                return YELLOW+"b"+RESET;
            else
                return GREEN+"b"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 75;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
                        return 60;
                    }
                    return 90;
                }
                default -> {return -1 ;}
            }
        }
    },
    KaleSeeds       ( "Kale Seeds",       true, Kale,10000,
            4, false, new int[]{1, 2, 2, 1} ,5,5 ,
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 3)
                return YELLOW+"k"+RESET;
            else
                return GREEN+"k"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 87;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 3)
                return GREEN+"p"+RESET;
            else
                return WHITE+"p"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 25;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level <= 4)
                return YELLOW+"p"+RESET;
            else
                return BRIGHT_BROWN+"p"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() <= level)
                return WHITE+"p"+RESET;
            else
                return PURPLE+"p"+RESET;
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return GREEN+"s"+RESET;
            else
                return RED+"s"+RESET;
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return GREEN+"t"+RESET;
            else
                return RED+"t"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 25;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Spring));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return YELLOW+"r"+RESET;
            else
                return GREEN+"r"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
                        return 40;
                    }
                    return 60;
                }
                default -> {return -1 ;}
            }
        }
    },
    BlueberrySeeds  ( "Blueberry Seeds",  false, Blueberry,4,
            5, false, new int[]{1, 3, 3, 4, 2} , 0 , 5 ,
            List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return YELLOW+"b"+RESET;
            else
                return BLUEBERRY+"b"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return WHITE+"o"+RESET;
            else
                return YELLOW+"o"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 187;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return WHITE+"h"+RESET;
            else
                return GREEN+"h"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 75;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BLUE+"h"+RESET;
            else
                return RED+"h"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 50;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return WHITE+"m"+RESET;
            else
                return GREEN+"m"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 100;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_YELLOW+BRIGHT_RED+"p"+RESET;
            else
                return BG_BRIGHT_YELLOW+RED+"p"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 125;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return GREEN+"r"+RESET;
            else
                return BRIGHT_RED+"r"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 50;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return CYAN+"r"+RESET;
            else
                return RED+"r"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_WHITE+WHITE+"s"+RESET;
            else
                return BG_BRIGHT_WHITE+BRIGHT_YELLOW+"s"+RESET;
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_WHITE+BRIGHT_BLUE+"s"+RESET;
            else
                return BG_BRIGHT_WHITE+BLUE+"s"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_PURPLE+"s"+RESET;
            else
                return PURPLE+"s"+RESET;
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_YELLOW+BLACK+"s"+RESET;
            else
                return BG_YELLOW+BLACK+"s"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 125;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Summer)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_WHITE+BRIGHT_RED+"t"+RESET;
            else
                return BG_WHITE+RED+"t"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Spring)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(Arrays.asList(Season.Summer, Season.Fall));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_YELLOW+"w"+RESET;
            else
                return YELLOW+"w"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 12;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_PURPLE+"a"+RESET;
            else
                return PURPLE+"a"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 87;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_GREEN+"a"+RESET;
            else
                return GREEN+"a"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart ) ){

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_PURPLE+"b"+RESET;
            else
                return RED+"b"+RESET;
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_WHITE+BRIGHT_GREEN+"b"+RESET;
            else
                return BG_BRIGHT_WHITE+GREEN+"b"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 62;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart ) ){
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_BLACK+BRIGHT_GREEN+"b"+RESET;
            else
                return BG_BRIGHT_BLACK+GREEN+"b"+RESET;
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_CYAN+BRIGHT_RED+"b"+RESET;
            else
                return BG_BRIGHT_CYAN+RED+"b"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 300;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_PURPLE+"e"+RESET;
            else
                return RED+"e"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 25;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_BLACK+BRIGHT_WHITE+"f"+RESET;
            else
                return BG_BRIGHT_BLACK+WHITE+"f"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 250;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.PierreGeneralStore ) ) {
        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_BLACK+BRIGHT_YELLOW+"g"+RESET;
            else
                return BG_BRIGHT_BLACK+YELLOW+"g"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_BLACK+YELLOW+"p"+RESET;
            else
                return BG_BRIGHT_BLACK+ORANGE+"p"+RESET;
        }


        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart,MarketType.PierreGeneralStore ) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_YELLOW+WHITE+"y"+RESET;
            else
                return BG_BRIGHT_YELLOW+BROWN+"y"+RESET;
        }

        @Override
        public int getPrice( MarketType marketType) {
            switch (marketType) {
                case JojaMart -> {return 75;}
                case PierreGeneralStore -> {
                    if (App.currentDate.getSeason().equals(Season.Fall)){
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Fall));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_BLACK+GREEN+"r"+RESET;
            else
                return BG_BRIGHT_BLACK+ORANGE+"r"+RESET;
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
            return new ArrayList<>(List.of(Season.Winter));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BG_BRIGHT_PURPLE+BRIGHT_CYAN+"p"+RESET;
            else
                return BG_BRIGHT_PURPLE+CYAN+"p"+RESET;
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
            List.of(MarketType.JojaMart) ) {

        @Override
        public ArrayList<Season> getSeason() {
             return new ArrayList<>(Arrays.asList(Season.Fall, Season.Summer, Season.Spring));
        }
        public String getSymbolByLevel (int level) {

            if (this.getGrowthStages() < level)
                return BRIGHT_CYAN+"a"+RESET;
            else
                return CYAN+"a"+RESET;
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
                      int JojaMartLimit, int PierrGeneralLimit , List<MarketType> marketTypes) {
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


    public abstract String getSymbolByLevel (int level);
    public abstract ArrayList<Season> getSeason();
    public abstract int getPrice(MarketType marketType);

    public void increaseJojaMartLimit(int amount) {
        JojaMartLimit = Math.max(0 , JojaMartLimit + amount);
    }
    public void increasePierrGeneralLimit(int amount) {
        PierrGeneralLimit = Math.max(0 , JojaMartLimit + amount);
    }

    public List<MarketType> getMarketTypes() {
        return marketTypes;
    }
}