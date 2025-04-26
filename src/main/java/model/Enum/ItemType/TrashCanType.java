package model.Enum.ItemType;

public enum TrashCanType {

    primary{
        @Override
        public int getPercent() {
            return 0;
        }
    },
    coppery{
        @Override
        public int getPercent() {
            return 15;
        }
    },
    iron{
        @Override
        public int getPercent() {
            return 30;
        }
    },
    golden{
        @Override
        public int getPercent() {
            return 45;
        }
    },
    iridium{
        @Override
        public int getPercent() {
            return 60;
        }
    };

    public abstract int getPercent();

}
