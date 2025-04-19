package model;



public class Tools extends Items {


    private int level = 0;


    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int healthCost () {
        return -1;
    }



}
