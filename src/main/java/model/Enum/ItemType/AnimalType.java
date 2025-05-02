package model.Enum.ItemType;

public enum AnimalType {
    hen("hen",800 , 1),
    duck("duck",1200 , 2),
    rabbit("rabbit",8000 , 4),
    dino("dino",14000 , 7),


    cow("cow",1500 , 1),
    goat("goat",4000 , 2),
    sheep("sheep", 8000 , 3),
    pig("pig" , 16000 , 1);//خوک کاری به روز نداره اگر truffle جمع آوری کرده باشه محصول میده و دوره منظم محصول دادن نداره

    private final String name;
    private final int price;//قیمتی که میریم از فروشگاه میخریمش
    private final int period;

    AnimalType(String name,int price, int period) {
        this.name = name;
        this.price = price;
        this.period = period;
    }

    public int getPrice() {
        return price;
    }

    public int getPeriod() {
        return period;
    }






}
