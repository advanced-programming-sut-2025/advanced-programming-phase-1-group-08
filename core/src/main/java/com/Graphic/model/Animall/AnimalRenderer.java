package com.Graphic.model.Animall;

import com.Graphic.Main;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.HelpersClass.TextureManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;

import static com.Graphic.model.HelpersClass.TextureManager.TEXTURE_SIZE;

public class AnimalRenderer {

    private Animal animal;
    private HashMap<Direction, Animation<Texture>> animations;

    public AnimalRenderer(Animal animal) {
        this.animal = animal;
        animations = new HashMap<>();
        addToAnimations(Direction.Up , animal.getUp());
        addToAnimations(Direction.Down , animal.getDown());
        addToAnimations(Direction.Left , animal.getLeft());
        addToAnimations(Direction.Right , animal.getRight());
    }
    public void addToAnimations(Direction direction , ArrayList<String> frames) {
        Animation<Texture> animation = new Animation<>(0.1f ,
            TextureManager.get(frames.get(0)) ,
            TextureManager.get(frames.get(1)) ,
            TextureManager.get(frames.get(2)) ,
            TextureManager.get(frames.get(3)));

        animations.put(direction , animation);
    }

    public void render() {
        if (! animal.isSold()) {
            Animation<Texture> animation = animations.get(animal.getDirection());
            if (!animal.isOut()) {
                Main.getBatch().draw(animation.getKeyFrame(animal.getTimer(), true),
                    animal.getPositionX(), animal.getPositionY(), animal.getType().getX(), animal.getType().getY());
            } else {
                if (Main.getClient().getPlayer().isInFarmExterior()) {
                    //float px = animal.getPositionX() * TEXTURE_SIZE;
                    //float py = (90 - animal.getPositionY()) * TEXTURE_SIZE;
                    Main.getBatch().draw(animation.getKeyFrame(animal.getTimer(), true),
                        animal.getPositionX() * TEXTURE_SIZE, (90 - animal.getPositionY() ) * TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);

                    //System.out.println(px + "hh"+py);
                }
            }

            if (!animation.isAnimationFinished(animal.getTimer())) {
                animal.setTimer(animal.getTimer() + Gdx.graphics.getDeltaTime());
            } else {
                animal.setTimer(0);
            }
        }
    }

    public AnimalRenderer() {
    }

    public Animal getAnimal() {
        return animal;
    }
}
