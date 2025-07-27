package com.Graphic.model.Places;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.model.Enum.AllPlants.ForagingMineralsType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.Plants.ForagingMinerals;
import com.Graphic.model.collisionRect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class Mine extends GameObject {

    private final int startX;
    private final int startY;
    private final int width;
    private final int height;
    private String Path;
    private collisionRect door;
    private ArrayList<ForagingMinerals> foragingMinerals;
    private ArrayList<Point> positions;
    private ArrayList<Point> taken = new ArrayList<>();

    public Mine(int x, int y , int width, int height) {
        this.startX = x;
        this.startY = y;
        this.width = width;
        this.height = height;
        door = new collisionRect((startX + width/2) * TEXTURE_SIZE , (TEXTURE_SIZE * 3) / 2 ,
            (90 - startY - height) * TEXTURE_SIZE , (TEXTURE_SIZE * 3)/2 );

        foragingMinerals = new ArrayList<ForagingMinerals>();
        positions = new ArrayList<>();
        positions.add(new Point(119,139));
        positions.add(new Point(159,139));
        positions.add(new Point(196,139));
        positions.add(new Point(241,139));
        positions.add(new Point(149,104));
        positions.add(new Point(95,106));
        positions.add(new Point(127,106));
        positions.add(new Point(159,106));
        positions.add(new Point(192,106));
        positions.add(new Point(224,106));
        positions.add(new Point(255,106));
        positions.add(new Point(108,74));
        positions.add(new Point(145,74));
        positions.add(new Point(172,74));
        positions.add(new Point(202,74));
        positions.add(new Point(238,74));
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

    public collisionRect getDoor() {
        return door;
    }

    public ArrayList<ForagingMinerals> getForagingMinerals() {
        return foragingMinerals;
    }

    public boolean checkPositionForMineral(Point point) {
        for (Point p : taken) {
            if (p.getX() == point.getX() && p.getY() == point.getY()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Point> getPositions() {
        return positions;
    }

    public ArrayList<Point> getTaken() {
        return taken;
    }
}
