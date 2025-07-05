package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.model.MapThings.GameObject;

import java.util.Random;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class Lake extends GameObject {
    private final int topLeftX;
    private final int topLeftY;
    private final int width;
    private final int height;
    private String Path;

    public Lake(int x, int y, int width, int height) {
        this.topLeftX = x;
        this.topLeftY = y;
        this.width = width;
        this.height = height;
    }
    public int getTopLeftX() {
        return topLeftX;
    }
    public int getTopLeftY() {
        return topLeftY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public String getIcon() {
        if (Path != null) {
            return Path;
        }


        for (int i = topLeftX ; i < topLeftX + width ; i++) {
            for (int j = topLeftY ; j < topLeftY + height ; j++) {
                if (GameControllerLogic.getTileByCoordinates(i , j).getGameObject().equals(this)) {
                    if (i == topLeftX && j == topLeftY) {
                        return Path = "Places/Lake6.png";
                    }
                    if (i == topLeftX + width - 1 && j == topLeftY + height - 1) {
                        return Path = "Places/Lake8.png";
                    }
                    if (i == topLeftX && j == topLeftY + height - 1) {
                        return Path = "Places/Lake7.png";
                    }
                    if (i == topLeftX + width - 1 && j == topLeftY ) {
                        return Path = "Places/Lake11.png";
                    }
                    if (i == topLeftX) {
                        return Path="Places/Lake1.png";
                    }
                    if (j == topLeftY + height - 1) {
                        return Path="Places/Lake3.png";
                    }
                    if (i == topLeftX + width - 1) {
                        return Path="Places/Lake4.png";
                    }
                    if (j == topLeftY) {
                        return Path="Places/Lake5.png";
                    }
                    else {
                        Random random = new Random();
                        int x = random.nextInt();
                        if (x % 2 == 0) {
                            return Path = "Places/Lake9.png";
                        }
                        return Path = "Places/Lake10.png";
                    }
                }
            }
        }

        return Path;
    }
}
