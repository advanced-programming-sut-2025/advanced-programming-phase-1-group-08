package model;

import model.Plants.AllCrops;
import model.Plants.ForagingCrops;
import model.Plants.ForagingMinerals;
import model.Plants.ForagingSeeds;
import model.ToolsPackage.Tools;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {

    private BasicRock basicRock=null;
    private Wood wood=null;
    private HashMap<ForagingMinerals,Integer> ForagingMinerals=new HashMap<>();
    private HashMap<ForagingSeeds,Integer> ForagingSeeds=new HashMap<>();
    //TODO راجب Mixed Seeds نمیدونم که میتونم داشته باشم یا نه
    private HashMap<AllCrops,Integer> AllCrops=new HashMap<>();
    //TODO غذاهای پخته شده
    private HashMap<ForagingCrops,Integer> ForagingCrops=new HashMap<>();
    //TODO نهال های درختانی که نیاز به مراقبت دارن
    private ArrayList<Tools> tools=new ArrayList<>();
    // TODO arrayList Item


    public BasicRock getBasicRock() {
        return basicRock;
    }

    public HashMap<AllCrops, Integer> getAllCrops() {
        return AllCrops;
    }

    public ArrayList<Tools> getTools() {
        return tools;
    }

    public HashMap<ForagingCrops, Integer> getForagingCrops() {
        return ForagingCrops;
    }

    public HashMap<ForagingMinerals, Integer> getForagingMinerals() {
        return ForagingMinerals;
    }

    public HashMap<ForagingSeeds, Integer> getForagingSeeds() {
        return ForagingSeeds;
    }

    public Wood getWood() {
        return wood;
    }
}

