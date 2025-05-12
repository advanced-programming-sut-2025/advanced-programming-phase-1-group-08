package model.MapThings;

import static model.Color_Eraser.*;

public class Walkable extends GameObject {

    private String grassOrFiber;

    public Walkable() {
        this.grassOrFiber = "Walk";
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
                return ".";
            }
            case "Grass" -> {
                return BG_BRIGHT_GREEN + "." + RESET;
            }
            case "Fiber", "JojaMart" -> {
                return BG_GREEN + "." + RESET;
            }
            case "PLowed", "Marnie's Ranch" -> {
                return BG_BRIGHT_BROWN + "." + RESET;
            }
            case "Blacksmith" , "Sebastian" -> {
                return BG_BRIGHT_BLACK + "." + RESET;
            }
            case "The Stardrop Saloon" , "Abigail" -> {
                return BG_BRIGHT_YELLOW + "." + RESET;
            }
            case "Fish shop" , "Harvey" -> {
                return BG_BRIGHT_BLUE + "." + RESET;
            }
            case "Carpenter's shop" , "Lia" -> {
                return BG_BRIGHT_RED + "." + RESET;
            }
            case "Pierre's General" , "Robin" -> {
                return BG_BRIGHT_PURPLE + "." + RESET;
            }
        }
        return " ";
    }
}
