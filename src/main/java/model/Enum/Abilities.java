package model.Enum;

public enum Abilities {

    Forming,
    mining,
    fishing,
    foraging;

    private int scale;
    private int level = 0;



    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public void increaseScale (int amount) {
        scale += amount;
    }
    public int getScale() {
        return scale;
    }
    public void setScale(int scale) {
        this.scale = scale;
    }
}