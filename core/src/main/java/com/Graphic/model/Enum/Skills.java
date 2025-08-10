package com.Graphic.model.Enum;

import com.Graphic.Main;

import static com.Graphic.model.App.currentGame;

public enum Skills {

    Foraging ("this is Foraging ability   Level : "),
    Farming  ("this is Farming ability   Level : "),
    Fishing  ("this is Fishing ability   Level : "),
    Mining   ("this is Mining ability   Level : ");

    private final String discription;

    Skills(String discription) {
        this.discription = discription;
    }

    public String getPath() {
        return "Erfan/Skill/" + this.name() + " Skill Icon.png";
    }
    public String getDiscription() {

        if (this.equals(Foraging)) {
            return discription + Main.getClient(null).getPlayer().getForagingAbility();
        } else if (this.equals(Farming)) {
            return discription + Main.getClient(null).getPlayer().getFarmingAbility();
        } else if (this.equals(Fishing)) {
            return discription + Main.getClient(null).getPlayer().getFishingAbility();
        } else
            return discription + Main.getClient(null).getPlayer().getMiningAbility();
    }
}
