package com.Graphic.model.Enum;

public enum Direction {
    Right("Mohamadreza/PlayerSpriteIdle/Right" ,1 , 0),
    Left ("Mohamadreza/PlayerSpriteIdle/Left" , -1 , 0),
    Up   ("Mohamadreza/PlayerSpriteIdle/Up" , 0 , -1 ),
    Down ("Mohamadreza/PlayerSpriteIdle/Down" , 0 , 1);

    private final String Path;
    private final int x;
    private final int y;
    Direction(String path , int x, int y) {
        this.Path = path;
        this.x = x;
        this.y = y;
    }

    public String getPath() {
        return Path;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
