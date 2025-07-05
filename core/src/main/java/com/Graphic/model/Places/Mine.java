package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.model.MapThings.GameObject;

import static com.Graphic.model.HelpersClass.Color_Eraser.*;

public class Mine extends GameObject {

    private final int startX;
    private final int startY;
    private final int width;
    private final int height;
    private String Path;

    public Mine(int x, int y , int width, int height) {
        this.startX = x;
        this.startY = y;
        this.width = width;
        this.height = height;
    }

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
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

        for (int i = startX ; i < startX + width ; i++) {
            for (int j = startY ; j < startY + height ; j++) {
                if (GameControllerLogic.getTileByCoordinates(i, j).getGameObject().equals(this)) {

                    if (i == startX + width - 1 && j == startY + (height / 2)) {
                        return Path = "Places/Mine1.png";
                    }
                    if (i == startX + width - 2 && j == startY + (height / 2)) {
                        return Path = "Places/Mine2.png";
                    }
                    if (i == startX + width -1 && j == startY) {
                        return Path = "Places/Mine5.png";
                    }
                    if (i == startX + width - 1 && j == startY + height - 1) {
                        return Path = "Places/Mine6.png";
                    }
                    if (i == startX + width - 1) {
                        return Path="Places/Mine3.png";
                    }
                    if (i == startX + width - 2 && j == startY + height - 1) {
                        return Path = "Places/Mine7.png";
                    }
                    if (i == startX + width -2 && j == startY) {
                        return Path = "Places/Mine8.png";
                    }
                    if (i == startX + width -2) {
                        return Path = "Places/Mine9.png";
                    }
                    if (i > startX && i < startX + width - 2 && j > startY && j < startY + height - 1 ) {
                        return Path = "Places/Mine10.png";
                    }
                    if (i == startX + width - 3 && j == startY ) {
                        return Path = "Places/Mine11.png";
                    }
                    if (i == startX + width - 1 && j == startY + height - 3) {
                        return Path = "Places/Mine12.png";
                    }
                    if (i == startX && j == startY + height - 1) {
                        return Path = "Places/Mine16.png";
                    }
                    if (i == startX && j == startY) {
                        return Path = "Places/Mine17.png";
                    }
                    if (j == startY){
                        return Path = "Places/Mine15.png";
                    }
                    if (j == startY + height - 1) {
                        return Path = "Places/Mine14.png";
                    }

                    if (i == startX) {
                        return Path = "Places/Mine13.png";
                    }
                }
            }
        }
        return Path;
    }



}
