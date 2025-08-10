package com.Graphic.model.Enum.ToolsType;

import com.Graphic.Main;

import static com.Graphic.model.App.currentGame;


public enum FishingPoleType {

    TrainingRod (1 , -1, "Erfan/Tools/Fishing_Pole/Training_Rod.png"){
        @Override
        public int getPrice() {
            return 25;
        }
        //TODO نوع مغازه ای که باید بریم ازش بخریم
        //TODO ارزان ترین ماهی های هر فصل رو میتونه بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            boolean a = Main.getClient(null).getPlayer().Buff_fishing_hoursLeft > 0;
            if (Fishing && a) {
                return 6;
            }
            if (Fishing) {
                return 7;
            }
            return 8;
        }

        @Override
        public String getName() {
            return "Training Rod";
        }

        @Override
        public double getCoefficient() {
            return 0.1;
        }
    },
    BambooPole (1 , -1, "Erfan/Tools/Fishing_Pole/Bamboo_Pole.png"){
        @Override
        public int getPrice() {
            return 500;
        }
        //TODO نوع مغازه ای که باید ازش بخریم
        //TODO میتونه هر ماهی ای رو بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing && Main.getClient(null).getPlayer().Buff_fishing_hoursLeft > 0)
                return 6;
            if (Fishing) {
                return 7;
            }
            return 8;
        }

        @Override
        public String getName() {
            return "Bamboo Pole";
        }
        public double getCoefficient() {
            return 0.5;
        }
    },
    FiberglassRod (1 , 2, "Erfan/Tools/Fishing_Pole/Fiberglass_Rod.png"){
        @Override
        public int getPrice() {
            return 1800;
        }
        //TODO نوع مغازه ای که باید ازش بخریم
        //TODO میتونه هر ماهی رو بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing && Main.getClient(null).getPlayer().Buff_fishing_hoursLeft > 0)
                return 4;
            if (Fishing) {
                return 5;
            }
            return 6;
        }

        @Override
        public String getName() {
            return "Fiberglass Rod";
        }
        public double getCoefficient() {
            return 0.9;
        }
    },
    IridiumRod (1 , 4, "Erfan/Tools/Fishing_Pole/Iridium_Rod.png"){
        @Override
        public int getPrice() {
            return 7500;
        }
        //TODO نوع مغازه ای که باید ازش بخریم
        //TODO در داک نیومده اما هر ماهی ای رو میتونه بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing && Main.getClient(null).getPlayer().Buff_fishing_hoursLeft > 0)
                return 2;
            if (Fishing) {
                return 3;
            }
            return 4;
        }

        @Override
        public String getName() {
            return "Iridium Rod";
        }

        @Override
        public double getCoefficient() {
            return 1.2;
        }
    };

    public int initialShopLimit;
    private final String iconPath;
    private final int level;
    public int shopLimit=1;

    FishingPoleType(int InitialShopLimit , int level, String iconPath) {
        this.initialShopLimit = InitialShopLimit;
        this.level = level;
        this.iconPath = iconPath;
    }



    public abstract int getPrice();
    public abstract int costEnergy(boolean Fishing);
    public abstract String getName();
    public abstract double getCoefficient();

    public int getInitialShopLimit() {
        return initialShopLimit;
    }
    public void setshopLimit() {
        this.shopLimit = initialShopLimit;
    }

    public void incrementShopLimit(int amount) {
        this.shopLimit += amount;
    }

    public int getLevel() {
        return level;
    }
    public String getIconPath() {

        return iconPath;
    }

    public boolean checkAbility(int amount){
        if (Main.getClient(null).getPlayer().getLevelFishing()>=amount){
            return true;
        }
        return false;
    }

    public static FishingPoleType getFishingPoleType(String name){
        return switch (name) {
            case "Training Rod" -> TrainingRod;
            case "Bamboo Pole" -> BambooPole;
            case "Fiberglass Rod" -> FiberglassRod;
            case "Iridium Rod" -> IridiumRod;
            default -> null;
        };

    }
}
