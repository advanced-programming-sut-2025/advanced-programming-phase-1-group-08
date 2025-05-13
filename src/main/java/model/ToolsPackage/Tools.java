package model.ToolsPackage;


import model.Items;

public abstract class Tools extends Items {

    private final String name;

    public Tools(String name) {
        this.name = name;
    }

    public String getName() {
        System.out.println(" hala madrid ");
        return name;
    }

    public abstract int healthCost ();
}
