package model;

public class Tile { // TODO

    private int x;
    private int y;

    private GameObject gameObject;


    public Tile(int x, int y, GameObject gameObject) {
        this.x = x;
        this.y = y;
        this.gameObject = gameObject;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameObject getGameObject() {
        return gameObject;
    }

    public void setGameObject(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}