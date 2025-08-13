package com.Graphic.model;

import com.Graphic.Controller.MainGame.InputGameController;
import com.Graphic.Main;
import com.Graphic.model.Enum.ItemType.ArtisanType;
import com.Graphic.model.HelpersClass.TextureManager;
import com.Graphic.model.ToolsPackage.CraftingItem;
import com.Graphic.model.Weather.DateHour;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class CraftingRenderer {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private CraftingItem craftingItem;

    public static Animation<Texture> bombAnimation = new Animation<>(0.2f,
            TextureManager.get("Mohamadreza/Bomb1.png") , TextureManager.get("Mohamadreza/Bomb2.png") ,
            TextureManager.get("Mohamadreza/Bomb3.png") , TextureManager.get("Mohamadreza/Bomb4.png"));

    public static Animation<Texture> sprinklerAnimation = new Animation<>(0.3f ,
        TextureManager.get("Mohamadreza/Sprinkler1.png") , TextureManager.get("Mohamadreza/Sprinkler2.png") ,
        TextureManager.get("Mohamadreza/Sprinkler3.png") , TextureManager.get("Mohamadreza/Sprinkler4.png") ,
        TextureManager.get("Mohamadreza/Sprinkler5.png") , TextureManager.get("Mohamadreza/Sprinkler6.png") ,
        TextureManager.get("Mohamadreza/Sprinkler7.png") , TextureManager.get("Mohamadreza/Sprinkler8.png") ,
        TextureManager.get("Mohamadreza/Sprinkler9.png") , TextureManager.get("Mohamadreza/Sprinkler10.png"));

    public CraftingRenderer(CraftingItem craftingItem) {
        this.craftingItem = craftingItem;
    }

    public void render() {
        // ==== مرحله 1: Filled Rectangles ====
        shapeRenderer.setProjectionMatrix(Main.getBatch().getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1); // سبز برای Filled

        for (int i = 0; i < craftingItem.getItems().size(); i++) {
            for (ArtisanType artisanType : ArtisanType.values()) {
                if (artisanType.getName().equalsIgnoreCase(craftingItem.getItems().get(i).getName())) {
                    if (!craftingItem.getItems().get(i).isCollected()) {
                        float x = (float) DateHour.getHourDifferent(craftingItem.getDateHours().get(i))
                            / artisanType.getTakesTime();
                        if (x >= 1) x = 1;

                        shapeRenderer.rect(
                            TEXTURE_SIZE * craftingItem.getX() + 6,
                            TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE / 2) * i + 3,
                            (TEXTURE_SIZE * 2) * x - 10,
                            TEXTURE_SIZE / 2 - 7
                        );
                    }
                    break;
                }
            }
        }
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1); // سفید برای Line

        for (int i = 0; i < craftingItem.getItems().size(); i++) {
            for (ArtisanType artisanType : ArtisanType.values()) {
                if (artisanType.getName().equalsIgnoreCase(craftingItem.getItems().get(i).getName())) {
                    if (!craftingItem.getItems().get(i).isCollected()) {
                        float x = (float) DateHour.getHourDifferent(craftingItem.getDateHours().get(i))
                            / artisanType.getTakesTime();
                        if (x >= 1) x = 1;

                        shapeRenderer.rect(
                            TEXTURE_SIZE * craftingItem.getX() + 6,
                            TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE / 2) * i + 3,
                            (TEXTURE_SIZE * 2) * x - 10,
                            TEXTURE_SIZE / 2 - 7
                        );
                    }
                    break;
                }
            }
        }
        shapeRenderer.end();
    }

    public void renderBg() {
        for (int i = 0 ; i < craftingItem.getItems().size() ; i ++) {
            if (! craftingItem.getItems().get(i).isCollected()) {
                Main.getBatch().draw(TextureManager.get("Mohamadreza/bgProgress.png") ,
                    TEXTURE_SIZE * craftingItem.getX() , TEXTURE_SIZE * (90 - craftingItem.getY()) + TEXTURE_SIZE + (TEXTURE_SIZE / 2) * i ,
                    TEXTURE_SIZE * 2 , TEXTURE_SIZE / 2);
            }
        }
    }

}
