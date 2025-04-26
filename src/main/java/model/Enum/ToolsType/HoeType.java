package model.Enum.ToolsType;

public enum HoeType {
    primary{
        @Override
        public int costEnergy() {
            return 5;
        }
    },

    coppery{
        @Override
        public int costEnergy() {
            return 4;
        }
    },

    iron{
        @Override
        public int costEnergy() {
            return 3;
        }
    },

    golden{
        @Override
        public int costEnergy() {
            return 2;
        }
    },

    iridium{
        @Override
        public int costEnergy() {
            return 1;
        }
    };

    public abstract int costEnergy();


}
