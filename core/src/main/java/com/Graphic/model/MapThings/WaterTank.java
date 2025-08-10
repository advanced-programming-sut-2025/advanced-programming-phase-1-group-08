package com.Graphic.model.MapThings;


import com.Graphic.model.Enum.ItemType.MarketType;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class WaterTank extends GameObject {

        public WaterTank() {
        }
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

    @Override
    public String getIcon() {
        return BRIGHT_BLUE+"@ ";
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
