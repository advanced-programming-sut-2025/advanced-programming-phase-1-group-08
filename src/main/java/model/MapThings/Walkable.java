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
                return " ";
            }
            case "Grass" -> {
                return BG_BRIGHT_GREEN + " " + RESET;
            }
            case "Fiber" -> {
                return BG_GREEN + " " + RESET;
            }
        }
        return " ";
    }
}
