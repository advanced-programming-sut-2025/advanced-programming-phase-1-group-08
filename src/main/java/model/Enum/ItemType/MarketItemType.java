package model.Enum.ItemType;

import java.util.List;

public enum MarketItemType {
    JojaCola("Joja_Cola", List.of(MarketType.JojaMart) , Integer.MAX_VALUE , 0 ) {
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

    GrassStarter("Grass_Starter" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE) {
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

    Sugar("Sugar" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE) {
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

    WheatFlour("Wheat_Flour" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE) {
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

    Rice("Rice" , List.of(MarketType.JojaMart , MarketType.PierreGeneralStore) , Integer.MAX_VALUE , Integer.MAX_VALUE) {
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

    Bouquet("Bouquet" , List.of( MarketType.PierreGeneralStore) , 0 , 2) {
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

    WeddingRing("Wedding_Ring" , List.of( MarketType.PierreGeneralStore) , 0 , 2) {
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

    DehydratorRecipe("Dehydrator_Recipe" , List.of( MarketType.PierreGeneralStore) , 0 , 1) {
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

    GrassStarterRecipe("Grass Starter_Recipe" , List.of( MarketType.PierreGeneralStore) , 0 , 1) {
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

    Oil("Oil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE) {
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

    Vinegar("Vinegar" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE) {
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

    DeluxeRetainingSoil("Deluxe_Retaining_Soil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE) {
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

    SpeedGro("Speed-Gro" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE) {
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

    BasicRetainingSoil("Basic_Retaining_Soil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE) {
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

    QuantityRetainingSoil("Quantity_Retaining_Soil" , List.of( MarketType.PierreGeneralStore) , 0 , Integer.MAX_VALUE) {
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

    Hay("Hay" , List.of(MarketType.MarnieRanch) , Integer.MAX_VALUE , 0) {
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

    Beer("Beer" , List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0) {
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
    Salad("Salad", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0) {
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
    Bread("Bread", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0) {
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
    Spaghetti("Spaghetti", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0) {
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
    Pizza("Pizza", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0) {
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
    Coffee("Coffee", List.of(MarketType.StardropSaloon) , Integer.MAX_VALUE , 0) {
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
    HashbrownsRecipe("Hashbrowns_Recipe", List.of(MarketType.StardropSaloon), 1 , 0) {
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
    OmeletRecipe("Omelet_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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
    PancakesRecipe("Pancakes_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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
    BreadRecipe("Bread_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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
    TortillaRecipe("Tortilla_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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
    PizzaRecipe("Pizza_Recipe", List.of(MarketType.StardropSaloon) ,1 , 0) {
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
    MakiRollRecipe("Maki_Roll_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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
    TripleShotEspressoRecipe("Triple_Shot_Espresso_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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
    CookieRecipe("Cookie_Recipe", List.of(MarketType.StardropSaloon) , 1 , 0) {
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


    Wood("Wood" , List.of(MarketType.CarpenterShop) , Integer.MAX_VALUE , 0) {
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

    Stone("Stone" , List.of(MarketType.CarpenterShop), Integer.MAX_VALUE , 0 ) {
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

    FishSmokerRecipe("Fish_Smoker_Recipe" , List.of(MarketType.FishShop), 1 , 0 ) {
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

    TroutSoup("Trout_Soup" , List.of(MarketType.FishShop), 1 , 0 ) {
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


    MarketItemType(String name , List<MarketType> marketTypes , int otherShopsLimit , int PierreShopsLimit ) {
        this.name = name;
        this.marketTypes = marketTypes;
        this.otherShopsLimit = otherShopsLimit;
        this.PierreShopsLimit = PierreShopsLimit;
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


}
