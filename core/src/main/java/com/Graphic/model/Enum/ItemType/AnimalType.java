package com.Graphic.model.Enum.ItemType;

import java.util.List;

public enum AnimalType {
    hen("Hen",800 , 1 , List.of(BarnORCageType.Coop , BarnORCageType.BigCoop , BarnORCageType.DeluxeCoop) ,
        2 , "Mohamadreza/hen.png" , 16,16),
    duck("Duck",1200 , 2 , List.of(BarnORCageType.BigCoop , BarnORCageType.DeluxeCoop) ,
        2 , "Mohamadreza/duck.png" , 16,16),
    rabbit("Rabbit",8000 , 4 , List.of(BarnORCageType.DeluxeCoop) ,
        2,"Mohamadreza/rabbit.png" , 16,16),
    dino("Dinosaur",14000 , 7 , List.of(BarnORCageType.BigCoop) ,
        2 , "Mohamadreza/dino.png" , 32,32),


    cow("Cow",1500 , 1 , List.of(BarnORCageType.Barn , BarnORCageType.BigBarn , BarnORCageType.DeluxeCoop) ,
        2 , "Mohamadreza/Cow.png" , 32,32),
    goat("Goat",4000 , 2 , List.of(BarnORCageType.BigBarn , BarnORCageType.DeluxeBarn) ,
        2 , "Mohamadreza/Goat.png" , 32 , 32),
    sheep("Sheep", 8000 , 3 , List.of(BarnORCageType.DeluxeBarn) ,
        2 , "Mohamadreza/Sheep.png",32,32),
    pig("Pig" , 16000 , 1 , List.of(BarnORCageType.DeluxeBarn) ,
        2 , "Mohamadreza/Pig.png" , 32 , 32);//خوک کاری به روز نداره اگر truffle جمع آوری کرده باشه محصول میده و دوره منظم محصول دادن نداره

    private final String type;
    private final int price;//قیمتی که میریم از فروشگاه میخریمش
    private final int period;
    private final List <BarnORCageType> barnorcages;
    private final int initialLimit=2;
    private int remindInShop;
    private String Icon;
    private final int x;
    private final int y;

    AnimalType(String type,int price, int period , List <BarnORCageType> barnorcages , int remindInShop , String Icon , int x , int y) {
        this.type = type;
        this.price = price;
        this.period = period;
        this.barnorcages = barnorcages;
        this.remindInShop = remindInShop;
        this.Icon = Icon;
        this.x = x;
        this.y = y;
    }

    public int getPrice() {
        return price;
    }

    public int getPeriod() {
        return period;
    }

    public int getRemindInShop() {
        return remindInShop;
    }
    public void setRemindInShop() {
        this.remindInShop = initialLimit;
    }
    public void increaseRemindInShop(int amount) {
        this.remindInShop += amount;
    }

    public List<BarnORCageType> getBarnorcages() {
        return barnorcages;
    }

    public String getIcon() {
        return Icon;
    }

    public String getType() {
        return type;
    }

    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
}
