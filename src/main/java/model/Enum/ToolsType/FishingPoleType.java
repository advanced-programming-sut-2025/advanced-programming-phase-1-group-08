package model.Enum.ToolsType;

import model.App;

import java.util.ArrayList;

public enum FishingPoleType {

    TrainingRod{
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

        @Override
        public String getName() {
            return "Training Rod";
        }
    },
    BambooPole{
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

        @Override
        public String getName() {
            return "Bamboo Pole";
        }
    },
    FiberglassRod{
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

        @Override
        public String getName() {
            return "Fiberglass Rod";
        }
    },
    IridiumRod{
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

        @Override
        public String getName() {
            return "Iridium Rod";
        }
    };

    public abstract int getPrice();
    public abstract int costEnergy(boolean Fishing);
    public abstract String getName();

    public boolean checkAbility(int amount){
        if (App.currentPlayer.getFishingAbility()>=amount){
            return true;
        }
        return false;
    }
}
