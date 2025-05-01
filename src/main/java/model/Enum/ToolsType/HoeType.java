package model.Enum.ToolsType;

public enum HoeType {
    primaryHoe{
        @Override
        public int costEnergy() {
            return -5;
        }
    },

    copperyHoe{
        @Override
        public int costEnergy() {
            return -4;
        }
    },

    ironHoe{
        @Override
        public int costEnergy() {
            return -3;
        }
    },

    goldenHoe{
        @Override
        public int costEnergy() {
            return -2;
        }
    },

    iridiumHoe{
        @Override
        public int costEnergy() {
            return -1;
        }
    };

    public abstract int costEnergy();


}
