package com.Graphic.model;

import com.Graphic.Main;
import com.Graphic.View.GameMenus.GameMenu;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.Enum.NPC.NPC;
import com.Graphic.model.HelpersClass.TextureManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.model.HelpersClass.TextureManager.EQUIP_THING_SIZE;
import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class UserRenderer {

    private HashMap<Direction , Animation<Texture> > animations = new HashMap<>();



    public void addToAnimations(Direction direction , ArrayList<String> frames) {
        Animation<Texture> animation = new Animation<>(0.1f ,
            TextureManager.get(frames.get(0)) , TextureManager.get(frames.get(1)));

        animations.put(direction , animation);
    }

    public void render(User user) {
        if (Main.getClient().getPlayer().isFishing || Main.getClient().getPlayer().doingMinigame)
            return;

        float x = 0;
        float y = 0;

        if (user.isInFarmExterior()) {
            x = TEXTURE_SIZE;
            y = TEXTURE_SIZE * 1.5f;
        }
        if (user.isInMarket() || user.isInBarnOrCage()) {
            x = TEXTURE_SIZE/2f;
            y = TEXTURE_SIZE * 0.75f;
        }

        Animation<Texture> animation = animations.get(user.getDirection());
        Main.getBatch().draw(animation.getKeyFrame(user.getTimer() , true) ,
            user.getPositionX() , user.getPositionY() , x , y);


        if ( animation.isAnimationFinished(user.getTimer()))
            user.setTimer(0.0f);

        drawCurrentItem();

    }
    private void drawCurrentItem() {

        Items currentItem = Main.getClient().getPlayer().currentItem;
        if (currentItem == null) return;

        GameMenu gameMenu = GameMenu.getInstance();

        Direction direction = Main.getClient().getPlayer().getDirection();
        float x = getXForHands(direction), y = getYForHands(direction);

        if (gameMenu.getCurrentItemSprite() == null ||
            !gameMenu.getCurrentItemSprite().getTexture().equals(TextureManager.get(currentItem.getInventoryIconPath()))) {
            gameMenu.setCurrentItemSprite(new Sprite(TextureManager.get(currentItem.getInventoryIconPath())));
        }


        gameMenu.getCurrentItemSprite().flip(Direction.lastDir != null && Direction.lastDir != direction &&
            (direction == Direction.Left || Direction.lastDir == Direction.Left), false);

        if (direction == Direction.Left) {
            gameMenu.getCurrentItemSprite().setOrigin(gameMenu.getCurrentItemSprite().getWidth(), 0);
        } else {
            gameMenu.getCurrentItemSprite().setOrigin(0, 0);
        }

        gameMenu.getCurrentItemSprite().setRotation(gameMenu.getCurrentRotation());

        gameMenu.getCurrentItemSprite().setPosition(x, y);
        gameMenu.getCurrentItemSprite().setSize(EQUIP_THING_SIZE, EQUIP_THING_SIZE);
        Direction.lastDir = direction;
        gameMenu.getCurrentItemSprite().draw(Main.getBatch());
    }
    private float getYForHands(Direction direction) {

        if (direction == Direction.Up)
            return (90 - Main.getClient().getPlayer().getPositionY()) * TEXTURE_SIZE + 12;

        return (90 - Main.getClient().getPlayer().getPositionY()) * TEXTURE_SIZE + 8;
    }
    private float getXForHands(Direction direction) {

        return switch (direction) {
            case Right -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE + 20;
            case Left -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE - 10;
            case Up -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE + 25;
            case Down -> Main.getClient().getPlayer().getPositionX() * TEXTURE_SIZE + 23;
        };
    }
}
