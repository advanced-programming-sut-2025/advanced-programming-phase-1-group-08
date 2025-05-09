package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    makeNewGame   ("^\\s*game\\s+new\\s+-u\\s+(?<username1>\\S+)(?:\\s+(?<username2>\\S+))?(?:\\s+(?<username3>\\S+))?\\s*$"),
    nextTurn      ("\\s(?i)*next\\s*turn\\s*"),
    friendships   ("\\s*(?i)friendships\\s*"),
    talking       ("\\s*(?i)talk\\s*-u\\s*(?<username>\\S+)-m\\s*(?<message>.+)\\s*"),
    talkHistory   ("\\s*(?i)talk\\s*history\\s*(?<username>.+)\\s*"),
    hug           ("\\s*(?i)hug\\s*-u\\s*(?<username>.+)\\s*"),
    giveFlower    ("\\s*(?i)flower\\s*-u\\s*(?<username>.+)\\s*"),
    sendGift      ("\\s*(?i)gift\\s*-u\\s*(?<username>\\S+)-i\\s*(?<item>\\S+)-a\\s*(?<amount>.+)\\s*"),
    propose       ("\\s*(?i)ask\\s*marriage\\s*-u\\s*(?<username>\\S)-r\\s*(?<ring>.+)"),
    showTime      ("\\s*(?i)time\\s*"),
    showDate      ("\\s*(?i)date\\s*"),
    showSeason    ("\\s*(?i)season\\s*"),
    showWeather   ("\\s*(?i)weather\\s*"),
    showDateTime  ("(?i)\\s*date\\s*Time\\s*"),
    showEnergy    ("(?i)\\s*energy\\s*show\\s*"),
    energyUnlimit ("(?i)\\s*energy\\s*unlimited\\s*"),
    howMuchWater  ("(?i)\\s*how\\s*much\\s*water\\s*"),
    showDayOfWeek ("(?i)\\s*day\\s+of\\s+the\\s+week\\s*"),
    showFruitInfo ("(?i)\\s*craft\\s*info\\s*-n\\s*(?<name>\\S+)\\s*"),
    setEnergy     ("(?i)\\s*energy\\s*set\\s*-v\\s*(?<amount>\\d+)\\s*"),
    createThor    ("(?i)\\s*cheat\\s*thor\\s*-l(?<x>\\d+),(?<y>\\d+)\\s*"),
    setWeather    ("\\s*(?i)cheat\\s*weather\\s*set\\s*(?<Weather>.+)\\s*"),
    advanceTime   ("(?i)\\s*cheat\\s+advance\\s+time\\s+(?<hour>.+)\\s*h\\s*"),
    advanceDate   ("(?i)\\s*cheat\\s+advance\\s+date\\s+(?<date>.+)\\s*d\\s*"),
    showPlant     ("(?i)\\s*show\\s*plant\\s*-l\\s*(?<x>\\d+),\\s*(?<y>\\d+)\\s*"),
    planting      ("\\s*(?i)plant\\s*-s\\s*(?<seed>.+)\\s*-d\\s*(?<direction>\\d+)\\s*"),
    showTomorrowWeather("\\s*(?i)weather\\s*forecast\\s*"),
    buildGreenHouse    ("(?i)\\s*greenhouse\\s*build\\s*"),


    walk("walk\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s*"),
    inventoryShow("inventory\\s+show\\s*"),
    removeItem("^inventory\\s+trash\\s+-i\\s+(?<item>.+?)(?= -n|$)(?: -n (?<number>\\d+))?$"),
    toolsEquip("tools\\s+equip\\s+(?<name>\\S.*)\\s*"),
    currentTool("tools\\s+show\\s+current\\s*"),
    availableTool("tools\\s+show\\s+available\\s*"),
    toolsUpgrade("tools\\s+upgrade\\s+(?<name>\\S.*)\\s*"),
    toolsUse("tools\\s+use\\s+-d\\s+(?<direction>\\S.*)\\s*"),
    fishing("fishing\\s+-p\\s+(?<name>\\S.*)\\s*"),
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

    ;


    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMather(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
