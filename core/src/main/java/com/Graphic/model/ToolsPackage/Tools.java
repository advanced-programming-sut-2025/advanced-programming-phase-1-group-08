package com.Graphic.model.ToolsPackage;


import com.Graphic.lombok.Setter;
import com.Graphic.model.Items;

@Setter
public abstract class Tools extends Items {

    private String name;

    public Tools(){}

    public Tools(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract int healthCost ();

    @Override
    public int getSellPrice() {
        return 0;
    }
}
