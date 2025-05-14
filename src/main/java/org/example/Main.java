package org.example;

import View.AppView;

import model.Enum.AllPlants.TreesSourceType;

public class Main {

    public static void main(String[] args)  {

        for (TreesSourceType t : TreesSourceType.values())
            System.out.println(t.name() + t.getTreeType());

        (new AppView()).run();
    }
}