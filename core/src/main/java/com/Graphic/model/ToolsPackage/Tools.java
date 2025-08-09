package com.Graphic.model.ToolsPackage;


import com.Graphic.model.Items;


public class Tools extends Items {

    private String name;

    public Tools(){}
    public Tools(String name) {

        this.name = name;
    }


    @Override
    public String getName() {

        return name;
    }
    @Override
    public int getSellPrice() {

        return 0;
    }


    public void setName(String name) {

        this.name = name;
    }
    public int healthCost () {
        return 0;
    };
}

