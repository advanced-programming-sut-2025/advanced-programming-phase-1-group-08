package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    showTime      ("\\s*(?i)time\\s*"),
    showDate      ("\\s*(?i)data\\s*"),
    showDateTime  ("\\s*(?i)dataTime\\s*"),
    showDayOfWeek ("\\s*(?i)day\\s+(?i)of\\s+(?i)the\\s+(?i)week\\s*"),

    advanceTime ("\\s*(?i)cheat\\s+(?i)advance\\s+(?i)time\\s+(?<hour>.+)\\s*h\\s*"),
    advanceDate ("\\s*(?i)cheat\\s+(?i)advance\\s+(?i)date\\s+(?<date>.+)\\s*d\\s*"),

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
