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
        Animation<Texture> animation = animations.get(user.getDirection());
        Main.getBatch().draw(animation.getKeyFrame(user.getTimer() , true) ,
            user.getPositionX() , user.getPositionY() , TEXTURE_SIZE , TEXTURE_SIZE * 1.5f);

        if ( animation.isAnimationFinished(user.getTimer())) {
            user.setTimer(0.0f);
        }
    }
}
