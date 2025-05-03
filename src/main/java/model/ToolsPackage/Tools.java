package model.ToolsPackage;


import model.Items;

public abstract class Tools extends Items {


    private int level = 0;
    private final String name;

    public Tools(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }


    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public abstract int healthCost ();
}
