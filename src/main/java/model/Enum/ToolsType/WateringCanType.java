package model.Enum.ToolsType;

public enum WateringCanType {

    PrimaryWateringCan{
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

    CopperyWateringCan{
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

    IronWateringCan{
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

    GoldenWateringCan{
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

    IridiumWateringCan{
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
