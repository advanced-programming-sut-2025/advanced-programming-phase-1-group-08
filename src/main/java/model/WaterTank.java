package model;

import model.Places.Farm;

public class WaterTank extends GameObject {

        private int waterTank;

        public WaterTank(int waterTank) {
            this.waterTank = waterTank;
        }

        public void setWaterTank(int waterTank) {
            this.waterTank = waterTank;
        }
        public int getWaterTank() {
            return waterTank;
        }
}
