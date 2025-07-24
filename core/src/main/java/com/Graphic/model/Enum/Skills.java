package com.Graphic.model.Enum;

public enum Skills {

    Foraging ("this is Foraging ability"),
    Farming  ("this is Farming ability"),
    Fishing  ("this is Fishing ability"),
    Mining   ("this is Mining ability");

    private final String discription;

    Skills(String discription) {
        this.discription = discription;
    }

    public String getPath() {
        return "Erfan/Skill/" + this.name() + " Skill Icon.png";
    }
    public String getDiscription() {
        return discription;
    }
}
