package model.Enum.ItemType;

import java.util.List;

public enum AnimalType {
    hen("Chicken",800 , 1 , List.of(BarnORCageType.Coop , BarnORCageType.BigCoop , BarnORCageType.DeluxeCoop) , 2),
    duck("Duck",1200 , 2 , List.of(BarnORCageType.BigCoop , BarnORCageType.DeluxeCoop) , 2),
    rabbit("Rabbit",8000 , 4 , List.of(BarnORCageType.DeluxeCoop) , 2),
    dino("Dinosaur",14000 , 7 , List.of(BarnORCageType.BigCoop) , 2),


    cow("Cow",1500 , 1 , List.of(BarnORCageType.Barn , BarnORCageType.BigBarn , BarnORCageType.DeluxeCoop) , 2),
    goat("Goat",4000 , 2 , List.of(BarnORCageType.BigBarn , BarnORCageType.DeluxeBarn) , 2),
    sheep("Sheep", 8000 , 3 , List.of(BarnORCageType.DeluxeBarn) , 2),
    pig("Pig" , 16000 , 1 , List.of(BarnORCageType.DeluxeBarn) , 2);//خوک کاری به روز نداره اگر truffle جمع آوری کرده باشه محصول میده و دوره منظم محصول دادن نداره

    private final String type;
    private final int price;//قیمتی که میریم از فروشگاه میخریمش
    private final int period;
    private final List <BarnORCageType> barnorcages;
    private final int initialLimit=2;
    private int remindInShop;

    AnimalType(String type,int price, int period , List <BarnORCageType> barnorcages , int remindInShop) {
        this.type = type;
        this.price = price;
        this.period = period;
        this.barnorcages = barnorcages;
        this.remindInShop = remindInShop;
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
}
