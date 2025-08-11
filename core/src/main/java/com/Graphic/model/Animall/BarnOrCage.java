package com.Graphic.model.Animall;

import com.Graphic.Controller.MainGame.GameControllerLogic;
import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.BarnORCageType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.MapThings.GameObject;
import com.Graphic.model.collisionRect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

import java.util.ArrayList;

import static com.Graphic.model.HelpersClass.Color_Eraser.BROWN;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class BarnOrCage extends GameObject {

    private BarnORCageType barnORCageType;
    public ArrayList<Animal> animals = new ArrayList<>();
    public int topLeftX;
    public int topLeftY;
    private collisionRect door;


    public BarnOrCage(){}

    public BarnOrCage(BarnORCageType barnORCageType , int topLeftX , int topLeftY) {
        this.barnORCageType = barnORCageType;
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        door = new collisionRect((topLeftX + barnORCageType.getDoorX() ) * TEXTURE_SIZE ,
                             (TEXTURE_SIZE * 3) / 2 ,
                                (90 - topLeftY - barnORCageType.getDoorY()) * TEXTURE_SIZE ,
                            (TEXTURE_SIZE * 3) / 2 );
    }

    public int getReminderCapacity(){
        return this.barnORCageType.getInitialCapacity() - animals.size();
    }

    public BarnORCageType getBarnORCageType() {
        return barnORCageType;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String getIcon() {
        if (Path != null) {
            return Path;
        }
        for (int i = topLeftX ; i < topLeftX + barnORCageType.getWidth() ; i++) {
            for (int j = topLeftY ; j < topLeftY + barnORCageType.getHeight() ; j++) {
                if (GameControllerLogic.getTileByCoordinates(i , j , Main.getClient().getLocalGameState()).getGameObject().equals(this)) {
                    int l = barnORCageType.getWidth() * (j - topLeftY) + i - topLeftX + 1;
                    System.out.println(l);
                    return Path = "Places/"+barnORCageType.getName()+l+".png";
                }
            }
        }
        return Path;
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return barnORCageType.getShopLimit();
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {
        barnORCageType.setShopLimit(amount);
    }

    public collisionRect getDoor() {
        return door;
    }
}

