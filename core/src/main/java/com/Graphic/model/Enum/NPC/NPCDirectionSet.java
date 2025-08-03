package com.Graphic.model.Enum.NPC;

import com.Graphic.model.Enum.Direction;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class NPCDirectionSet {
    private final Map<Direction, String> directionTexturePaths = new HashMap<>();

    public NPCDirectionSet(String basePath) {
        directionTexturePaths.put(Direction.Right, basePath + ",Right1.png");
        directionTexturePaths.put(Direction.Left, basePath + ",Left1.png");
        directionTexturePaths.put(Direction.Up, basePath + ",Up1.png");
        directionTexturePaths.put(Direction.Down, basePath + ",Down1.png");
    }

    public String getPath(Direction direction) {
        return directionTexturePaths.get(direction);
    }

    public HashMap<Direction, Texture> loadAllTextures() {
        HashMap<Direction, Texture> textures = new HashMap<>();
        for (Direction direction : Direction.values()) {
            textures.put(direction, new Texture(Gdx.files.internal(getPath(direction))));
        }
        return textures;
    }

}
