package model.Enum.ToolsType;

public enum WateringCanType {

    primary{
        @Override
        public int costEnergy(boolean Farming) {
            if (Farming){
                return 4;
            }
            return 5;
        }

        @Override
        public int getInitialCapacity() {
            return 40;
        }
    },

    coppery{
        @Override
        public int costEnergy(boolean Farming) {
            if (Farming){
                return 3;
            }
            return 4;
        }

        @Override
        public int getInitialCapacity() {
            return 55;
        }
    },

    iron{
        @Override
        public int costEnergy(boolean Farming) {
            if (Farming){
                return 2;
            }
            return 3;
        }

        @Override
        public int getInitialCapacity() {
            return 70;
        }
    },

    golden{
        @Override
        public int costEnergy(boolean Farming) {
            if (Farming){
                return 1;
            }
            return 2;
        }

        @Override
        public int getInitialCapacity() {
            return 85;
        }
    },

    iridium{
        @Override
        public int costEnergy(boolean Farming) {
            if (Farming){
                return 1;
            }
            return 1;
        }

        @Override
        public int getInitialCapacity() {
            return 100;
        }
    };

    public abstract int costEnergy(boolean Farming);
    public abstract int getInitialCapacity();
}
