package model.ToolsPackage;


import model.Items;

public abstract class Tools extends Items {

    private final String name;

    public Tools(String name) {
        this.name = name;
    }


    public abstract int healthCost ();

    @Override
    public int getSellPrice() {
        return 0;
    }
}
