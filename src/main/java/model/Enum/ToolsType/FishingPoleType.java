package model.Enum.ToolsType;

import model.App;

public enum FishingPoleType {

    primary{
        @Override
        public int getPrice() {
            return 25;
        }
        //TODO نوع مغازه ای که باید بریم ازش بخریم
        //TODO ارزان ترین ماهی های هر فصل رو میتونه بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing) {
                return 7;
            }
            return 8;
        }
    },
    Bamboo{
        @Override
        public int getPrice() {
            return 500;
        }
        //TODO نوع مغازه ای که باید ازش بخریم
        //TODO میتونه هر ماهی ای رو بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing) {
                return 7;
            }
            return 8;
        }
    },
    FiberGlass{
        @Override
        public int getPrice() {
            return 1800;
        }
        //TODO نوع مغازه ای که باید ازش بخریم
        //TODO میتونه هر ماهی رو بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing) {
                return 5;
            }
            return 6;
        }
    },
    iridium{
        @Override
        public int getPrice() {
            return 7500;
        }
        //TODO نوع مغازه ای که باید ازش بخریم
        //TODO در داک نیومده اما هر ماهی ای رو میتونه بگیره

        @Override
        public int costEnergy(boolean Fishing) {
            if (Fishing) {
                return 3;
            }
            return 4;
        }
    };

    public abstract int getPrice();
    public abstract int costEnergy(boolean Fishing);

    public boolean checkAbility(int amount){
        if (App.currentPlayer.getFishingAbility()>=amount){
            return true;
        }
        return false;
    }
}
