package model.MapThings;

public class WaterTank extends GameObject {

        private int amount;

        public WaterTank(int waterTank) {
            this.amount = waterTank;
        }

        public void increaseAmount(int waterTank) {
        this.amount += waterTank;
    }
        public void setAmount(int waterTank) {
            this.amount = waterTank;
        }
        public int getAmount() {
            return amount;
        }
}
