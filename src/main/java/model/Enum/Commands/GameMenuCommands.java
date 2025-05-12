package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements Command{


//    makeNewGame   ("^\\s*game\\s+new\\s+-u\\s+(?<username1>\\S+)(?:\\s+" +
//            "(?<username2>\\S+))?(?:\\s+(?<username3>\\S+))?\\s*$"),
    makeNewGame ("\\s*game\\s+new\\s*"),
    openHomeMenu("\\s*(?i)menu\\s*home\\s*menu\\s*"),
    nextTurn      ("\\s*(?i)next\\s*turn\\s*"),

                                                        //  relation
    friendships   ("\\s*(?i)friendships\\s*"),
    hug           ("\\s*(?i)hug\\s*-u\\s*(?<username>.+)\\s*"),
    giveFlower    ("\\s*(?i)flower\\s*-u\\s*(?<username>.+)\\s*"),
    talkHistory   ("\\s*(?i)talk\\s*history\\s*(?<username>.+)\\s*"),
    talking       ("\\s*(?i)talk\\s*-u\\s*(?<username>\\S+)-m\\s*(?<message>.+)\\s*"),
    propose       ("\\s*(?i)ask\\s*marriage\\s*-u\\s*(?<username>\\S)-r\\s*(?<ring>.+)"),
    sendGift      ("\\s*(?i)gift\\s*-u\\s*(?<username>\\S+)-i\\s*(?<item>\\S+)-a\\s*(?<amount>.+)\\s*"),
    trade         ("\\s*(?i)start\\s*trade\\s*"),
    proposalRespond("\\s*respond\\s*(?<response>\\S+)-u\\s*(?<username>.+)\\s*"),

                                                        //  Date Time weather
    showTomorrowWeather("\\s*(?i)weather\\s*forecast\\s*"),
    showTime      ("\\s*(?i)time\\s*"),
    showDate      ("\\s*(?i)date\\s*"),
    showSeason    ("\\s*(?i)season\\s*"),
    showWeather   ("\\s*(?i)weather\\s*"),
    showDateTime  ("(?i)\\s*date\\s*Time\\s*"),
    showDayOfWeek ("(?i)\\s*day\\s+of\\s+the\\s+week\\s*"),
    setWeather    ("(?i)\\s*cheat\\s*weather\\s*set\\s*(?<Weather>.+)\\s*"),
    advanceTime   ("(?i)\\s*cheat\\s+advance\\s+time\\s+(?<hour>.+)\\s*h\\s*"),
    advanceDate   ("(?i)\\s*cheat\\s+advance\\s+date\\s+(?<date>.+)\\s*d\\s*"),

                                                        // energy
    showEnergy    ("(?i)\\s*energy\\s*show\\s*"),
    energyUnlimit ("(?i)\\s*energy\\s*unlimited\\s*"),
    setEnergy     ("(?i)\\s*energy\\s*set\\s*-v\\s*(?<amount>\\d+)\\s*"),

                                                        // plants
    buildGreenHouse    ("(?i)\\s*greenhouse\\s*build\\s*"),
    showTreeInfo  ("(?i)\\s*info\\s*-n\\s*(?<name>.+)\\s*"),
    wateringPlant ("(?i)\\s*water\\s*(?<direction>\\d+)\\s*"),
    showFruitInfo ("(?i)\\s*craft\\s*info\\s*-n\\s*(?<name>.+)\\s*"),
    createThor    ("(?i)\\s*cheat\\s*thor\\s*-l(?<x>\\d+),(?<y>\\d+)\\s*"),
    showPlant     ("(?i)\\s*show\\s*plant\\s*-l\\s*(?<x>\\d+),\\s*(?<y>\\d+)\\s*"),
    planting      ("(?i)\\s*plant\\s*-s\\s*(?<seed>.+)\\s*-d\\s*(?<direction>\\d+)\\s*"),
    fertilize     ("(?i)\\s*fertilize\\s*-f\\s*(?<fertilizer>.+)\\s*-d\\s*(?<direction>\\d+)\\s*"),

                                                        // Tools
    howMuchWater  ("(?i)\\s*how\\s*much\\s*water\\s*"),
    showTool      ("(?i)\\s*tools\\s*show\\s*current\\s*"),
    toolsAvailable("(?i)\\s*tools\\s*show\\s*available\\s*"),
    toolsEquip    ("(?i)\\s*tools\\s*equip\\s*(?<name>\\S+)\\s*"),
    toolsUpgrade  ("(?i)\\s*tools\\s*upgrade\\s*(?<name>\\S+)\\s*"),
    toolsUse      ("(?i)\\s*tools\\s*use\\s*-d\\s*(?<direction>\\d+)\\s*"),

                                                        // NPC
    questsList   ("(?i)\\s*quests\\s*list\\s*"),
    meetNPC      ("(?i)\\s*meet\\s*NPC\\s*(?<name>\\S+)\\s*"),
    giftNPC      ("(?i)\\s*gift\\s*NPC\\s*(?<name>\\S+)\\s*-i\\s*(?<item>.+)\\s*"),
    questsFinish ("(?i)\\s*quests\\s*-n\\s*(?<name>\\S+)\\s*finish\\s*-i\\s*(?<index>\\d+)\\s*"),
    friendshipNPCList ("(?i)\\s*friendship\\s*NPC\\s*list\\s*"),



    walk("walk\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s*"),
    printMap("(?i)print\\s+map\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s+-s\\s+(?<size>\\d+)\\s*"),
    inventoryShow("inventory\\s+show\\s*"),
    removeItem("inventory\\s+trash\\s+-i\\s+(?<item>\\S.*)\\s*"),
    removeItemFlagn("inventory\\s+trash\\s+-i\\s+(?<item>\\S.*)\\s+-n\\s+(?<amount>\\d+)\\s*"),
    currentTool("tools\\s+show\\s+current\\s*"),
    availableTool("tools\\s+show\\s+available\\s*"),
    fishing("fishing\\s+-p\\s+(?<name>\\S.*)\\s*"),
    buyAnimal("(?i)buy\\s+animal\\s+-a\\s+(?<animal>\\S.*)\\s+-n\\s+(?<name>\\S.*)\\s*"),
    buildBarnOrCage("build\\s+-a\\s+(?<name>\\S.*)\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s*"),
    pet("pet\\s+-n\\s+(?<name>\\S.*)\\s*"),
    animals("\\s*animals\\s*"),
    shepherdAnimals("shepherd\\s+animals\\s+-n\\s+(?<name>\\S.*)\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s*"),
    feedHay("feed\\s+hay\\s+-n\\s+(?<name>\\S.*)\\s*"),
    produces("\\s*produces\\s*"),
    collectProduct("collect\\s+product\\s+-n\\s+(?<name>\\S.*)\\s*"),
    sellAnimal("sell\\s+animal\\s+-n\\s+(?<name>\\S.*)\\s*"),
    showAllProducts("(?i)show\\s+all\\s+products\\s*"),
    showAvailableProducts("(?i)show\\s+all\\s+available\\s+products\\s*"),
    purchase("^(?i)purchase\\s+(.+?)(?:\\s+-n\\s+(\\d+))?$"),
    craftingRecipe("(?i)crafting\\s+show\\s+recipes\\s*"),
    craftingCraft("(?i)crafting\\s+craft\\s+(?<name>\\S.*)\\s*"),
    placeItem("place\\s+item\\s+-n\\s+(?<name>\\S.*)\\s+-d\\s+(?<direction>\\d+)\\s*"),
    artisan("(?i)artisan\\s+use\\s+(?<artisanName>\\S+)\\s+(?<first>\\S+)\\s*"),
    artisanUse("(?i)artisan\\s+use\\s+(?<artisanName>\\S+)\\s+(?<first>\\S+)\\s+(?<second>\\S+)\\s*"),
    artisanGet("(?i)artisan\\s+get\\s+(?<name>\\S.*)\\s*"),
    sell("(?i)sell\\s+(?<name>\\S.*)\\s*"),
    sellByCount("(?i)sell\\s+(?<name>\\S.*)\\s+-n\\s+(?<count>\\d+)\\s*"),
    cheatSetFriendship("(?i)cheat\\s+set\\s+\\friendship\\s+-n\\s+(?<name>\\S.*)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    addDollar("(?i)cheat\\s+add\\s+(?<amount>\\d+)\\s+dollars\\s*"),
    addItem("cheat\\s+add\\s+item\\s+-n\\s+(?<name>\\S.*)\\s+-c\\s+(?<amount>\\d+)\\s*"),
    // todo Eat!
    ;


    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
