package com.Graphic.model.Animall;

import com.Graphic.Main;
import com.Graphic.model.Enum.Direction;
import com.Graphic.model.HelpersClass.TextureManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.HashMap;

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
        Animation<Texture> animation = animations.get(animal.getDirection());
        Main.getBatch().draw(animation.getKeyFrame(animal.getTimer() , true), animal.getX(), animal.getY());
    }

    public AnimalRenderer() {
    }

    public Animal getAnimal() {
        return animal;
    }
}
