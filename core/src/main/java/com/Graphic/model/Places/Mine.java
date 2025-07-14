package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;

public class Mine extends GameObject {

    private final int startX;
    private final int startY;
    private final int width;
    private final int height
        ;
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

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                if (GameControllerLogic.getTileByCoordinates(i, j).getGameObject().equals(this)) {

                    if (j == startY + height - 1 && i == startX + (width / 2)) {
                        return Path = "Places/Mine1.png";
                    }
                    if (j == startY + height - 2 && i == startX + (width / 2)) {
                        return Path = "Places/Mine2.png";
                    }
                    if (j == startY + height -1 && i == startX) {
                        return Path = "Places/Mine5.png";
                    }
                    if (j == startY + height - 1 && i == startX + width - 1) {
                        return Path = "Places/Mine6.png";
                    }
                    if (j == startY + height - 1) {
                        return Path="Places/Mine3.png";
                    }
                    if (j == startY + height - 2 && i == startX + width - 1) {
                        return Path = "Places/Mine7.png";
                    }
                    if (j == startY + height -2 && i == startX) {
                        return Path = "Places/Mine8.png";
                    }
                    if (j == startY + height -2) {
                        return Path = "Places/Mine9.png";
                    }
                    if (j > startY && j < startY + height - 2 && i > startX && i < startX + width - 1 ) {
                        return Path = "Places/Mine10.png";
                    }
                    if (j == startY + height - 3 && i == startX ) {
                        return Path = "Places/Mine11.png";
                    }
                    if (j == startY + height - 1 && i == startX + width - 3) {
                        return Path = "Places/Mine12.png";
                    }
                    if (j == startY && i == startX + width - 1) {
                        return Path = "Places/Mine16.png";
                    }
                    if (j == startY && i == startX) {
                        return Path = "Places/Mine17.png";
                    }
                    if (i == startX){
                        return Path = "Places/Mine15.png";
                    }
                    if (i == startX + width - 1) {
                        return Path = "Places/Mine14.png";
                    }

                    if (j == startY) {
                        return Path = "Places/Mine13.png";
                    }
                }
            }
        }
        return Path;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return 0;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }
}
