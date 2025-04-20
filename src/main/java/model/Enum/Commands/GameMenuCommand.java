package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommand {

    showTime      ("\\s*(?i)time\\s*"),
    showDate      ("\\s*(?i)data\\s*"),
    showDateTime  ("\\s*(?i)dataTime\\s*"),
    showDayOfWeek ("\\s*(?i)day\\s+(?i)of\\s+(?i)the\\s+(?i)week\\s*"),

    ;


    private final String pattern;

    GameMenuCommand (String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMather(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
