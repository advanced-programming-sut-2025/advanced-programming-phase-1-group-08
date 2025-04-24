package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    showTime      ("\\s*(?i)time\\s*"),
    showDate      ("\\s*(?i)date\\s*"),
    showSeason    ("\\s*(?i)season\\s*"),
    showWeather   ("\\s*(?i)weather\\s*"),
    showDateTime  ("(?i)\\s*date\\s*Time\\s*"),
    showEnergy    ("(?i)\\s*energy\\s*show\\s*"),
    energyUnlimit ("(?i)\\s*energy\\s*unlimited\\s*"),
    showDayOfWeek ("(?i)\\s*day\\s+of\\s+the\\s+week\\s*"),
    createThor    ("(?i)\\s*cheat\\s*thor\\s*-l(?<x>.+),(?<y>.+)\\s*"),
    setEnergy     ("(?i)\\s*energy\\s*set\\s*-v\\s*(?<amount>\\d+)\\s*"),
    showTomorrowWeather("\\s*(?i)weather\\s*forecast\\s*"),
    buildGreenHouse    ("(?i)\\s*greenhouse\\s*build\\s*"),

    setWeather  ("\\s*(?i)cheat\\s*weather\\s*set\\s*(?<Weather>.+)\\s*"),
    advanceTime ("(?i)\\s*cheat\\s+advance\\s+time\\s+(?<hour>.+)\\s*h\\s*"),
    advanceDate ("(?i)\\s*cheat\\s+advance\\s+date\\s+(?<date>.+)\\s*d\\s*"),
    walk("walk\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s*"),
    inventoryShow("inventory\\s+show\\s*"),
    removeItem("^inventory trash -i (?<item>.+?)(?= -n|$)(?: -n (?<number>\\d+))?$"),

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
