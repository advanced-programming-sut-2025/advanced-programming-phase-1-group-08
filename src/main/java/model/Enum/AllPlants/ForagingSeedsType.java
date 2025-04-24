package model.Enum.AllPlants;

import model.Enum.WeatherTime.Season;

import java.util.ArrayList;
import java.util.List;

import static model.Color_Eraser.*;
import static model.Enum.AllPlants.CropsType.*;

public enum ForagingSeedsType {

    JazzSeeds       ( "Jazz Seeds",       true, BlueJazz,
            4, false, new int[]{1, 2, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    CarrotSeeds     ( "Carrot Seeds",     true, Carrot,
            3, false, new int[]{1, 1, 1,}) {

        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }
        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }
        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
        @Override
        public String getSymbolByLevel (int level) {

            if (level == 1)
                return YELLOW+"c"+RESET;
            if (level == 2)
                return YELLOW+"C"+RESET;
            else
                return ORANGE
        }
    },
    CauliflowerSeeds( "Cauliflower Seeds",true, Cauliflower,
            5, true, new int[]{1, 2, 4, 4, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    CoffeeBean      ( "CoffeeBean",       false, CoffeeBeanProduct,
            5, false, new int[]{1, 2, 2, 3, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    GarlicSeeds     ( "JGarlic Seeds",    true, Garlic,
            4, false, new int[]{1, 1, 1, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    BeanStarter     ( "Bean Starter",     false, GreenBean,
            5, false, new int[]{1, 1, 1, 3, 4}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    KaleSeeds       ( "Kale Seeds",       true, Kale,
            4, false, new int[]{1, 2, 2, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    ParsnipSeeds    ( "Parsnip Seeds",    true, Parsnip,
            4, false, new int[]{1, 1, 1, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    PotatoSeeds     ( "Potato Seeds",     true, Potato,
            5, false, new int[]{1, 1, 1, 2, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    RhubarbSeeds    ( "Rhubarb Seeds",    true, Rhubarb,
            5, false, new int[]{2, 2, 2, 3, 4}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    StrawberrySeeds ( "Strawberry Seeds", false, Strawberry,
            5, false, new int[]{1, 1, 2, 2, 2,}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    TulipBulb       ( "Tulip Bulb",       true, Tulip,
            4, false, new int[]{1, 1, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    RiceShoot       ( "Rice Shoot",       true, UnmilledRice,
            4, false, new int[]{1, 2, 2, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    BlueberrySeeds  ( "Blueberry Seeds",  false, Blueberry,
            5, false, new int[]{1, 3, 3, 4, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    CornSeeds       ( "Corn Seeds",       false, Corn,
            5, false, new int[]{2, 3, 3, 3, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    HopsStarter     ( "Hops Starter",     false, Hops,
            5, false, new int[]{1, 1, 2, 3, 4}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    PepperSeeds     ( "Pepper Seeds",     false, HotPepper,
            5, false, new int[]{1, 1, 1, 1, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    MelonSeeds      ( "Melon Seeds",      true, Melon,
            5, true, new int[] {1, 2, 3, 3, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    PoppySeeds      ( "Poppy Seeds",      true, Poppy,
            4, false, new int[]{1, 2, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    RadishSeeds     ("Radish Seeds",      true, Radish,
            4, false, new int[]{2, 1, 2, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    RedCabbageSeeds ( "Red Cabbage Seeds",true, RedCabbage,
            5, false, new int[]{2, 1, 2, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    StarfruitSeeds  ( "Starfruit Seeds",  true, Starfruit,
            5, false, new int[]{2, 3, 2, 3, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    SpangleSeeds    ( "Spangle Seeds",    true, SummerSpangle,
            4, false, new int[]{1, 2, 3, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    SummerSquashSeeds( "Summer Squash Seeds",false, SummerSquash,
            5, false, new int[]{1, 1, 1, 2, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    SunflowerSeeds  ( "Sunflower Seeds",     true, Sunflower,
            4, false, new int[]{1, 2, 3, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    TomatoSeeds     ( "Tomato Seeds",        false, Tomato,
            5, false, new int[]{2, 2, 2, 2, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    WheatSeeds      ( "Wheat Seeds",         true, Wheat,
            4, false, new int[]{1, 1, 1, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    AmaranthSeeds   ( "Amaranth Seeds",      true, Amaranth,
            4, false, new int[]{1, 2, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    ArtichokeSeeds  ( "Artichoke Seeds",     true, Artichoke,
            5, false, new int[]{2, 2, 1, 2, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    BeetSeeds       ( "Beet Seeds",          true, Beet,
            4, false, new int[]{1, 1, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    BokChoySeeds    ("BokChoy Seeds",        true, BokChoy,
            4, false, new int[]{1, 1, 1, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    BroccoliSeeds   ( "Broccoli Seeds",      false, Broccoli,
            4, false, new int[]{2, 2, 2, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    CranberrySeeds  ( "Cranberry Seeds",     false, Cranberries,
            5, false, new int[]{1, 2, 1, 1, 2}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    EggplantSeeds   ( "Eggplant Seeds",      false, Eggplant,
            4, false, new int[]{1, 1, 1, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    FairySeeds      ( "Fairy Seeds",         true, FairyRose,
            4, false, new int[]{1, 4, 4, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    GrapeStarter    ( "Grape Starter",       false, Grape,
            5, false, new int[]{1, 1, 2, 3, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    PumpkinSeeds    ( "Pumpkin Seeds",       true, Pumpkin,
            5, true, new int[] {1, 2, 3, 4, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    YamSeeds        ( "Yam Seeds",           true, Yam,
            4, false, new int[]{1, 3, 3, 3}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    RareSeed        ( "Rare Seed",           true, SweetGemBerry,
            5, false, new int[]{2, 4, 6, 6, 6}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    PowdermelonSeeds("Powdermelon Seeds",    true, Powdermelon,
            5, true, new int[]{1, 2, 1, 2, 1}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    },
    AncientSeeds    ( "Ancient Seeds",       false, AncientFruit,
            5, false, new int[]{2, 7, 7, 7, 5}) {
        @Override
        public List<String> getCustomStrings() {
            return List.of();
        }

        @Override
        public int getDaysForStage(int stage) {
            return 0;
        }

        @Override
        public ArrayList<Season> getSeason() {
            return null;
        }
    };


    private final String displayName;
    private final boolean isOneTimeUse;
    private final CropsType productType;
    private final int growthStages;
    private final boolean canGrowGiant;
    private final int[] stageDays; // مدت زمان پیش‌فرض برای هر مرحله رشد

    ForagingSeedsType(String displayName, boolean isOneTimeUse, CropsType productType, int growthStages, boolean canGrowGiant, int[] stageDays) {
        this.displayName = displayName;
        this.isOneTimeUse = isOneTimeUse;
        this.productType = productType;
        this.growthStages = growthStages;
        this.canGrowGiant = canGrowGiant;
        this.stageDays = stageDays;
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
    public int getStageDate (int Stage) {
        return stageDays[Stage];
    }
    // متدهای ابسترکت
    public abstract String getSymbolByLevel (int level);
    public abstract List<String> getCustomStrings();
    public abstract int getDaysForStage(int stage);
    public abstract ArrayList<Season> getSeason();


}
