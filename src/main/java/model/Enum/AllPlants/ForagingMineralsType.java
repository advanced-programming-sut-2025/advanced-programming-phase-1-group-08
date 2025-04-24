package model.Enum.AllPlants;

import static model.Color_Eraser.*;

public enum ForagingMineralsType {

    RUBY("R", 250) {
        @Override
        public String getDescription() {
            return WHITE + "A precious stone that is sought after for its rich color and beautiful luster." + RESET;  // می‌توانید پیاده‌سازی مورد نظر خود را تغییر دهید.
        }
        @Override
        public boolean beCreated() {
            // پیاده‌سازی خود را اینجا قرار دهید.
            return false;
        }
    },
    COAL("C", 15) {
        @Override
        public String getDescription() {
            return WHITE + "A combustible rock that is useful for crafting and smelting." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    IRON("I", 10) {
        @Override
        public String getDescription() {
            return WHITE + "A fairly common ore that can be smelted into bars." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    TOPAZ("T", 80) {
        @Override
        public String getDescription() {
            return WHITE + "Fairly common but still prized for its beauty." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    GOLD("G", 25) {
        @Override
        public String getDescription() {
            return  WHITE + "A precious ore that can be smelted into bars." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    JADE("J", 200) {
        @Override
        public String getDescription() {
            return  WHITE + "A pale green ornamental stone." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    IRIDIUM("♕", 100) {
        @Override
        public String getDescription() {
            return  WHITE + "An exotic ore with many curious properties. Can be smelted into bars." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    QUARTZ("◇", 25) {
        @Override
        public String getDescription() {
            return WHITE + "A clear crystal commonly found in caves and mines." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    EMERALD("✦", 250) {
        @Override
        public String getDescription() {
            return WHITE + "A precious stone with a brilliant green color." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    COPPER("C", 5) {
        @Override
        public String getDescription() {
            return WHITE + "A common ore that can be smelted into bars." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    DIAMOND("◆", 750) {
        @Override
        public String getDescription() {
            return WHITE + "A rare and valuable gem." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    AMETHYST("α", 100) {
        @Override
        public String getDescription() {
            return WHITE + "A purple variant of quartz." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    AQUAMARINE("Å", 180) {
        @Override
        public String getDescription() {
            return WHITE + "A shimmery blue-green gem." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    FROZEN_TEAR("❄", 75) {
        @Override
        public String getDescription() {
            return WHITE + "A crystal fabled to be the frozen tears of a yeti." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    FIRE_QUARTZ("⚡", 100) {
        @Override
        public String getDescription() {
            return  WHITE + "A glowing red crystal commonly found near hot lava." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    EARTH_CRYSTAL("⊕", 50) {
        @Override
        public String getDescription() {
            return  WHITE + "A resinous substance found near the surface." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    },
    PRISMATIC_SHARD("☼", 2000) {
        @Override
        public String getDescription() {
            return  WHITE + "A very rare and powerful substance with unknown origins." + RESET;
        }
        @Override
        public boolean beCreated() {
            return false;
        }
    };

    protected final String symbol;
    protected final int price;

    ForagingMineralsType(String symbol, int price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getCharacter() {
        return symbol;
    }

    public int getPrice() {
        return price;
    }

    // اعلام متدهای ابسترکت برای الزام پیاده‌سازی در هر مقدار از enum
    public abstract String getDescription();
    public abstract boolean beCreated();
}
