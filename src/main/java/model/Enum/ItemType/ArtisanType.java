package model.Enum.ItemType;

import model.App;
import model.ArtisanProduct;
import model.Enum.AllPlants.CropsType;
import model.Enum.AllPlants.TreesProductType;
import model.Items;

public enum ArtisanType {
    Honey("Honey") {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public int takeTime() {
            return 96;
        }

        @Override
        public void creatArtesian() {
            ArtisanProduct artisanProduct=new ArtisanProduct(Honey , App.currentDate);
            App.currentPlayer.
        }
    },
    Cheese("Cheese") {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public int takeTime() {
            return 3;
        }
    },
    Goat_Cheese("Goat Cheese") {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public int takeTime() {
            return 3;
        }
    },
    Beer("Beer") {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public int takeTime() {
            return 24;
        }
    },
    Vinegar("Vinegar") {
        @Override
        public int getEnergy(String name) {
            return 13;
        }

        @Override
        public int takeTime() {
            return 10;
        }
    },
    Coffee("Coffee") {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public int takeTime() {
            return 2;
        }
    },
    Juice("Juice") {
        @Override
        public int getEnergy(String name) {
            CropsType cropsType = CropsType.valueOf(name);
            return 2 * cropsType.getEnergy();
        }

        @Override
        public int takeTime() {
            return 96;
        }
    },
    Mead("Mead") {
        @Override
        public int getEnergy(String name) {
            return 100;
        }

        @Override
        public int takeTime() {
            return 10;
        }
    },
    Pale_Ale("Pale Ale") {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public int takeTime() {
            return 72;
        }
    },
    Wine("Wine") {
        @Override
        public int getEnergy(String name) {
            TreesProductType type = TreesProductType.valueOf(name);
            return (7 * type.getEnergy() ) / 4;
        }
        @Override
        public int takeTime() {
            return 168;
        }


    },
    Dried_Mushrooms("Dried Mushrooms") {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public int takeTime() {
            return 12;
        }
    },
    Dried_Fruit("Dried Fruit") {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public int takeTime() {
            return 12;
        }
    },
    Raisins("Raisins") {
        @Override
        public int getEnergy(String name) {
            return 125;
        }

        @Override
        public int takeTime() {
            return 12;
        }
    },
    Coal("Coal") {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public int takeTime() {
            return 1;
        }
    },
    Cloth("Cloth") {
        @Override
        public int getEnergy(String name) {
            return 0;
        }

        @Override
        public int takeTime() {
            return 4;
        }
    },
    Mayonnaise("Mayonnaise") {
        @Override
        public int getEnergy(String name) {
            return 50;
        }

        @Override
        public int takeTime() {
            return 3;
        }
    },
    Duck_Mayonnaise("Duck Mayonnaise") {
        @Override
        public int getEnergy(String name) {
            return 75;
        }

        @Override
        public int takeTime() {
            return 3;
        }
    },
    Dinosaur_Mayonnaise("Dinosaur Mayonnaise") {
        @Override
        public int getEnergy(String name) {
            return 125;
        }

        @Override
        public int takeTime() {
            return 3;
        }
    },
    Truffle_Oil("Truffle Oil") {
        @Override
        public int getEnergy(String name) {
            return 6;
        }
    },
    Oil("Oil") {
        @Override
        public int getEnergy(String name) {
            return 13;
        }

        @Override
        public int takeTime() {
            return 6;
        }
    },
    Pickles("Pickles") {
        @Override
        public int getEnergy(String name) {
            CropsType cropsType = CropsType.valueOf(name);
            return (7 * cropsType.getEnergy() ) / 4;
        }

        @Override
        public int takeTime() {
            return 6;
        }
    },
    Jelly("Jelly") {
        @Override
        public int getEnergy(String name) {
            TreesProductType type = TreesProductType.valueOf(name);
            return 2 * type.getEnergy();
        }

        @Override
        public int takeTime() {
            return 72;
        }
    },
    Smoked_Fish("Smoked Fish") {
        @Override
        public int getEnergy(String name) {
            return 15;
        }

        @Override
        public int takeTime() {
            return 1;
        }
    };

    private final String name;
    public abstract int getEnergy(String name);
    public abstract int takeTime();
    public abstract void creatArtesian();

}
