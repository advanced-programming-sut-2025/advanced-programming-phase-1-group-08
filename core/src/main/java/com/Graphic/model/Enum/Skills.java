package com.Graphic.model.Enum;

import static com.Graphic.model.App.currentGame;

public enum Skills {

    Foraging ("this is Foraging ability \n Level : "),
    Farming  ("this is Farming ability \n Level : "),
    Fishing  ("this is Fishing ability \n Level : "),
    Mining   ("this is Mining ability \n Level : ");

    private final String discription;

    Skills(String discription) {
        this.discription = discription;
    }

    public String getPath() {
        return "Erfan/Skill/" + this.name() + " Skill Icon.png";
    }
    public String getDiscription() {

        if (this.equals(Foraging)) {
            return discription + currentGame.currentPlayer.getForagingAbility();
        } else if (this.equals(Farming)) {
            return discription + currentGame.currentPlayer.getFarmingAbility();
        } else if (this.equals(Fishing)) {
            return discription + currentGame.currentPlayer.getFishingAbility();
        } else
            return discription + currentGame.currentPlayer.getMiningAbility();
    }
}
