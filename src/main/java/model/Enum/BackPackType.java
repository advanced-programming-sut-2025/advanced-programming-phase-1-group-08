package model.Enum;

public enum BackPackType {
    primary{

        final int capacity = 12;

        public int getCapacity() {
            return capacity;
        }
    },
    giant{
        final int capacity = 24;

        public int getCapacity() {
            return capacity;
        }

        },
    deluxe{
        final int capacity = Integer.MAX_VALUE;
        public int getCapacity() {
            return capacity;
        }
    };

 }
