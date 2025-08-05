package com.Graphic.model.ToolsPackage;

import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.Weather.DateHour;
import com.Graphic.model.Enum.ItemType.CraftType;
import com.Graphic.model.Enum.ItemType.MarketType;
import com.Graphic.model.Items;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.math.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class CraftingItem extends Items {
    private final CraftType type;
    private int x;
    private int y;
    private ArrayList<Items> items = new ArrayList<>();
    private ArrayList<DateHour> dateHours = new ArrayList<>();
    private boolean isWaiting;
    private float Timer;// for showing animation of Bombs And Sprinklers
    private ArrayList<String> Bomb;
    private ArrayList<String> Sprinkler;
    private ArrayList<ShapeRenderer> shapeRenderers = new ArrayList<>();
    private ArrayList<Rectangle> rectangles = new ArrayList<>();

    public static CraftingItem Bombing;
    public static CraftingItem currentSprinkler;

    public CraftingItem(CraftType craftType) {
        this.type = craftType;
        isWaiting = false;
        Timer = 0f;
        Bomb = new ArrayList<>();
        Sprinkler = new ArrayList<>();
        Bomb.add("Mohamadreza/Bomb1.png");
        Bomb.add("Mohamadreza/Bomb2.png");
        Bomb.add("Mohamadreza/Bomb3.png");
        Bomb.add("Mohamadreza/Bomb4.png");
//        Bomb = new Animation<>(0.2f ,
//            new Texture("Mohamadreza/Bomb1.png") , new Texture("Mohamadreza/Bomb2.png"),
//            new Texture("Mohamadreza/Bomb3.png") , new Texture("Mohamadreza/Bomb4.png"));

        Sprinkler.add("Mohamadreza/Sprinker1.png");
        Sprinkler.add("Mohamadreza/Sprinker2.png");
        Sprinkler.add("Mohamadreza/Sprinker3.png");
        Sprinkler.add("Mohamadreza/Sprinker4.png");
        Sprinkler.add("Mohamadreza/Sprinker5.png");
        Sprinkler.add("Mohamadreza/Sprinker6.png");
        Sprinkler.add("Mohamadreza/Sprinker7.png");
        Sprinkler.add("Mohamadreza/Sprinker8.png");
        Sprinkler.add("Mohamadreza/Sprinker9.png");
        Sprinkler.add("Mohamadreza/Sprinker10.png");
//        Sprinkler = new Animation<>(0.3f ,
//            new Texture("Mohamadreza/Sprinkler1.png") , new Texture("Mohamadreza/Sprinkler2.png") ,
//            new Texture("Mohamadreza/Sprinkler3.png") , new Texture("Mohamadreza/Sprinkler4.png"),
//            new Texture("Mohamadreza/Sprinkler5.png") , new Texture("Mohamadreza/Sprinkler6.png") ,
//            new Texture("Mohamadreza/Sprinkler7.png") , new Texture("Mohamadreza/Sprinkler8.png") ,
//            new Texture("Mohamadreza/Sprinkler9.png") , new Texture("Mohamadreza/Sprinkler10.png"));
    }


    public CraftType getType() {
        return type;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Items> getItems() {
        return items;
    }
    public ArrayList<DateHour> getDateHours() {
        return dateHours;
    }

    @Override
    public void turnByTurnAutomaticTask() {

        if (this.type.equals(CraftType.Scarecrow))
            this.setProtect(8);
        if (this.type.equals(CraftType.DeluxeScarecrow))
            this.setProtect(12);
    }
    public void setProtect (int r) {


    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getInventoryIconPath() { // TODO
        return this.type.getAddress();
    }

    @Override
    public int getSellPrice() {
        return type.getSellPrice();
    }

    @Override
    public String getIcon() {
        return type.getIcon();
    }

    @Override
    public int getRemindInShop(MarketType marketType) {
        return -1;
    }

    @Override
    public void setRemindInShop(int amount, MarketType marketType) {

    }

    @Override
    public int getMarketPrice(MarketType marketType) {
        return 0;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }

    public float getTimer() {
        return Timer;
    }
    public void setTimer(float timer) {
        Timer = timer;
    }

    public ArrayList<String> getBomb() {
        return Bomb;
    }

    public ArrayList<String> getSprinkler() {
        return Sprinkler;
    }



    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }
//    public ArrayList<ShapeRenderer> getShapeRenderers() {
//        return shapeRenderers;
//    }
//
//    public void createAnotherShapeRender() {
//        ShapeRenderer shapeRenderer = new ShapeRenderer();
//        shapeRenderers.add(shapeRenderer);
//
//    }



}
