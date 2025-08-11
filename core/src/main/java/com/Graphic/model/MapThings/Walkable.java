package com.Graphic.model.MapThings;

import com.Graphic.model.Enum.ItemType.MarketType;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class Walkable extends GameObject {

    private String grassOrFiber;


    public Walkable() {
        this.grassOrFiber = "Walk";
    }

    public Walkable(String grassOrFiber) {
        this.grassOrFiber = grassOrFiber;
    }

    public String getGrassOrFiber() {

        return grassOrFiber;
    }
    public void setGrassOrFiber(String grassOrFiber) {

        this.grassOrFiber = grassOrFiber;
    }
    public String getIcon () {

        switch (this.grassOrFiber) {

            case "Walk" -> {
                return "Places/Walkable.png";
            }
            case "Grass" -> {
                return "Places/Grass.png";
            }
            case "Fiber" -> {
                return "Crops/Fiber.png";
            }
            case "Plowed", "Marnie's Ranch" -> {
                return "Erfan/Floor.png";
            }
//            case "Blacksmith" , "Sebastian" -> {
//                return BG_BRIGHT_BLACK + ". " + RESET;
//            }
//            case "The Stardrop Saloon" , "Abigail" -> {
//                return BG_BRIGHT_YELLOW + ". " + RESET;
//            }
//            case "Fish shop" , "Harvey" -> {
//                return BG_BRIGHT_BLUE + ". " + RESET;
//            }
//            case "Carpenter's shop" , "Lia" -> {
//                return BG_BRIGHT_RED + ". " + RESET;
//            }
//            case "Pierre's General" , "Robin" -> {
//                return BG_BRIGHT_PURPLE + ". " + RESET;
//            }
        }
        return "  ";
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
