package model.Enum.ItemType;

public enum TrashCanType {

    primarytTrashCan {
        @Override
        public int getPercent() {
            return 0;
        }
    },
    CopperTrashCan {
        @Override
        public int getPercent() {
            return 15;
        }
    },
    SteelTrashCan {
        @Override
        public int getPercent() {
            return 30;
        }
    },
    GoldTrashCan {
        @Override
        public int getPercent() {
            return 45;
        }
    },
    IridiumTrashCan {
        @Override
        public int getPercent() {
            return 60;
        }
    };

    public abstract int getPercent();

}
