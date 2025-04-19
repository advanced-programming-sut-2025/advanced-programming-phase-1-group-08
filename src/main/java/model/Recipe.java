package model;

import java.util.HashMap;

public class Recipe {

    private String name;
    private HashMap<Items, Integer> ingredient = new HashMap<>(); // مقدار لازم از هر آیتم
    private int energyNeeded;
    private String source;
    private int sellPrice_golds;
    private String description;
    private int processingTime_hour;
}
