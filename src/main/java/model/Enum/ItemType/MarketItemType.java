package model.Enum.ItemType;

import model.Enum.AllPlants.CropsType;

import java.util.List;

import static model.Color_Eraser.RED;
import static model.Color_Eraser.RESET;

public enum MarketItemType {
    JojaCola("Joja Cola", List.of(MarketType.JojaMart) , Integer.MAX_VALUE , 0 , 0 ) {
        @Override
        public int getPrice(int id) {
            return 750;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },

    GrassStarter("Grass Starter" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            if (id == 1) {
                return 100;
            }
            return 125;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    Sugar("Sugar" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            if (id == 1) {
                return 100;
            }
            return 125;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    WheatFlour("Wheat Flour" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            if (id == 1) {
                return 100;
            }
            return 125;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    Rice("Rice" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            if (id == 1) {
                return 200;
            }
            return 250;
        }
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }

    },

    Bouquet("Bouquet" , List.of( MarketType.PierreGeneralStore) , 0 , 2 , 0) {
        public int getPrice(int id) {
            return 1000;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 2;
        }
    },

    WeddingRing("Wedding Ring" , List.of( MarketType.PierreGeneralStore) , 0 , 2 , 0) {
        @Override
        public int getPrice(int id) {
            return 10000;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 2;
        }
    },

    DehydratorRecipe("Dehydrator Recipe" , List.of( MarketType.PierreGeneralStore) , 0 , 1 , 0) {
        @Override
        public int getPrice(int id) {
            return 10000;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 1;
        }
    },

    GrassStarterRecipe("Grass Starter_Recipe" , List.of( MarketType.PierreGeneralStore) , 0 , 1 , 0) {
        @Override
        public int getPrice(int id) {
            return 1000;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 1;
        }
    },

    Oil("Oil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE , 100) {
        @Override
        public int getPrice(int id) {
            return 200;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    Vinegar("Vinegar" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE , 100) {
        @Override
        public int getPrice(int id) {
            return 200;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    DeluxeRetainingSoil("Deluxe Retaining Soil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            return 150;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    SpeedGro("Speed-Gro" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            return 100;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    BasicRetainingSoil("Basic Retaining Soil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            return 100;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    QuantityRetainingSoil("Quantity Retaining Soil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE , 0) {
        @Override
        public int getPrice(int id) {
            return 150;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return 0;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return Integer.MAX_VALUE;
        }
    },

    Hay("Hay" , List.of(MarketType.MarnieRanch) , Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 50;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },

    Beer("Beer" , List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0 , 200) {
        @Override
        public int getPrice(int id) {
            return 400;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    Salad("Salad", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 220;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    Bread("Bread", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 120;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    Spaghetti("Spaghetti", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 240;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    Pizza("Pizza", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 600;
        }

        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    Coffee("Coffee", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0 , 150) {
        @Override
        public int getPrice(int id) {
            return 300;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    HashbrownsRecipe("Hashbrowns Recipe", List.of(MarketType.StardropSaloon), 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 50;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    OmeletRecipe("Omelet Recipe", List.of(MarketType.StardropSaloon) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 100;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    PancakesRecipe("Pancakes Recipe", List.of(MarketType.StardropSaloon) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 100;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    BreadRecipe("Bread Recipe", List.of(MarketType.StardropSaloon) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 100;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    TortillaRecipe("Tortilla Recipe", List.of(MarketType.StardropSaloon) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 100;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    PizzaRecipe("Pizza Recipe", List.of(MarketType.StardropSaloon) ,1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 150;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    MakiRollRecipe("Maki Roll Recipe", List.of(MarketType.StardropSaloon) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 300;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    TripleShotEspressoRecipe("Triple Shot Espresso Recipe", List.of(MarketType.StardropSaloon ) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 5000;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },
    CookieRecipe("Cookie Recipe", List.of(MarketType.StardropSaloon) , 1 , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 300;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },


    Wood("Wood" , List.of(MarketType.CarpenterShop) , Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 10;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },

    Stone("Stone" , List.of(MarketType.CarpenterShop), Integer.MAX_VALUE , 0 , 0) {
        @Override
        public int getPrice(int id) {
            return 20;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },

    FishSmokerRecipe("Fish Smoker Recipe" , List.of(MarketType.FishShop), 1 , 0 , 0 ) {
        @Override
        public int getPrice(int id) {
            return 10000;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    },

    TroutSoup("Trout Soup" , List.of(MarketType.FishShop), 1 , 0 , 0 ) {
        @Override
        public int getPrice(int id) {
            return 250;
        }
        @Override
        public int getInitialOtherShopsLimit() {
            return 1;
        }

        @Override
        public int getInitialPierreShopsLimit() {
            return 0;
        }
    };



    private final String name;
    private List<MarketType> marketTypes;
    private int otherShopsLimit;
    private int PierreShopsLimit;
    public abstract int getPrice(int id);
    public abstract int getInitialOtherShopsLimit();
    public abstract int getInitialPierreShopsLimit();
    private final int SellPrice;



    MarketItemType(String name , List<MarketType> marketTypes , int otherShopsLimit , int PierreShopsLimit , int SellPrice ) {
        this.name = name;
        this.marketTypes = marketTypes;
        this.otherShopsLimit = otherShopsLimit;
        this.PierreShopsLimit = PierreShopsLimit;
        this.SellPrice = SellPrice;
    }
    public String getName() {
        return name;
    }
    public List<MarketType> getMarketTypes() {
        return marketTypes;
    }
    public int getOtherShopsLimit() {
        return otherShopsLimit;
    }
    public int getPierreShopsLimit() {
        return PierreShopsLimit;
    }
    public void increaseOtherShopsLimit(int amount) {
        otherShopsLimit += amount;
    }
    public void increasePierreShopsLimit(int amount) {
        PierreShopsLimit += amount;
    }
    public void setOtherShopsLimit() {
        otherShopsLimit = getInitialOtherShopsLimit();
    }
    public void setPierreShopsLimit() {
        PierreShopsLimit = getInitialPierreShopsLimit();
    }

    public int getSellPrice() {
        return SellPrice;
    }

    public static MarketItemType fromDisplayName(String displayName) {
        for (MarketItemType type : MarketItemType.values())
            if (type.getName().equalsIgnoreCase(displayName))
                return type;
        throw new IllegalArgumentException(RED+"wrong name!"+RESET);
    }

}
