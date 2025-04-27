package model.Enum.AllPlants;

public enum CropsType {

    BlueJazz        (true,   50,  ForagingSeedsType.JazzSeeds),
    Carrot          (true,   35,  ForagingSeedsType.CarrotSeeds),
    Cauliflower     (true,   175, ForagingSeedsType.CauliflowerSeeds),
    CoffeeBeanProduct(false, 15,  ForagingSeedsType.CoffeeBean),
    Garlic          (true,   60,  ForagingSeedsType.GarlicSeeds),
    GreenBean       (true,   40,  ForagingSeedsType.BeanStarter),
    Kale            (true,   110, ForagingSeedsType.KaleSeeds),
    Parsnip         (true,   35,  ForagingSeedsType.ParsnipSeeds),
    Potato          (true,   80,  ForagingSeedsType.PotatoSeeds),
    Rhubarb         (false,  220, ForagingSeedsType.RhubarbSeeds),
    Strawberry      (true,   120, ForagingSeedsType.StrawberrySeeds),
    Tulip           (true,   30,  ForagingSeedsType.TulipBulb),
    UnmilledRice    (true,   30,  ForagingSeedsType.RiceShoot),
    Blueberry       (true,   50,  ForagingSeedsType.BlueberrySeeds),
    Corn            (true,   50,  ForagingSeedsType.CornSeeds),
    Hops            (true,   25,  ForagingSeedsType.HopsStarter),
    HotPepper       (true,   40,  ForagingSeedsType.PepperSeeds),
    Melon           (true,   250, ForagingSeedsType.MelonSeeds),
    Poppy           (true,   140, ForagingSeedsType.PoppySeeds),
    Radish          (true,   90,  ForagingSeedsType.RadishSeeds),
    RedCabbage      (true,   260, ForagingSeedsType.RedCabbageSeeds),
    Starfruit       (true,   750, ForagingSeedsType.StarfruitSeeds),
    SummerSpangle   (true,   90,  ForagingSeedsType.SpangleSeeds),
    SummerSquash    (true,   45,  ForagingSeedsType.SummerSquashSeeds),
    Sunflower       (true,   80,  ForagingSeedsType.SunflowerSeeds),
    Tomato          (true,   60,  ForagingSeedsType.TomatoSeeds),
    Wheat           (false,  25,  ForagingSeedsType.WheatSeeds),
    Amaranth        (true,   150, ForagingSeedsType.AmaranthSeeds),
    Artichoke       (true,   160, ForagingSeedsType.ArtichokeSeeds),
    Beet            (true,   100, ForagingSeedsType.BeetSeeds),
    BokChoy         (true,   80,  ForagingSeedsType.BokChoySeeds),
    Broccoli        (true,   70,  ForagingSeedsType.BroccoliSeeds),
    Cranberries     (true,   75,  ForagingSeedsType.CranberrySeeds),
    Eggplant        (true,   60,  ForagingSeedsType.EggplantSeeds),
    FairyRose       (true,   290, ForagingSeedsType.FairySeeds),
    Grape           (true,   80,  ForagingSeedsType.GrapeStarter),
    Pumpkin         (false,  320, ForagingSeedsType.PumpkinSeeds),
    Yam             (true,   160, ForagingSeedsType.YamSeeds),
    SweetGemBerry   (false,  3000,ForagingSeedsType.RareSeed),
    Powdermelon     (true,   60,  ForagingSeedsType.PowdermelonSeeds),
    AncientFruit    (false,  550, ForagingSeedsType.AncientSeeds);

    private final boolean isEdible;
    private final int price;
    private final ForagingSeedsType seedsType;

    CropsType (boolean isEdible, int price, ForagingSeedsType seedsType) {
        this.isEdible = isEdible;
        this.price = price;
        this.seedsType = seedsType;
    }

    public boolean isEdible() {
        return isEdible;
    }
    public int getPrice() {
        return price;
    }
    public ForagingSeedsType getSeedsType() {
        return seedsType;
    }
}
