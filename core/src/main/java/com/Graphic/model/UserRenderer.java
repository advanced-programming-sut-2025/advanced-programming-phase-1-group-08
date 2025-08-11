package com.Graphic.model;

import com.Graphic.Main;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.HelpersClass.TextureManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class UserRenderer {

    private HashMap<Direction , Animation<Texture> > animations = new HashMap<>();


    public void addToAnimations(Direction direction , ArrayList<String> frames) {
        Animation<Texture> animation = new Animation<>(0.1f ,
            TextureManager.get(frames.get(0)) , TextureManager.get(frames.get(1)));

        animations.put(direction , animation);
    }

    public void render(User user) {
        float x = 0;
        float y = 0;

        if (user.isInFarmExterior()) {
            x = TEXTURE_SIZE;
            y = TEXTURE_SIZE * 1.5f;
        }
        if (user.isInMarket()) {
            x = TEXTURE_SIZE/2;
            y = TEXTURE_SIZE * 0.75f;
        }
        Animation<Texture> animation = animations.get(user.getDirection());
        Main.getBatch().draw(animation.getKeyFrame(user.getTimer() , true) ,
            user.getPositionX() , user.getPositionY() , x , y);

        if ( animation.isAnimationFinished(user.getTimer())) {
            user.setTimer(0.0f);
        }
    }
}
