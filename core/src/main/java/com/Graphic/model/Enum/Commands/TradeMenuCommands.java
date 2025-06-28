package com.Graphic.model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands implements Command{
    tradeP      ("(?i)\\s*Trade\\s*-u\\s*(?<username>\\S+)\\s*-t\\s*(?<type>\\S.+\\S)\\s*-i\\s*(?<item>\\S.+\\S)\\s*-a\\s*(?<amount>\\S+)\\s*-p\\s*(?<price>.+)"),
    tradeTI     ("(?i)\\s*trade\\s*-u\\s*(?<username>\\S+)\\s*-t\\s*(?<type>\\S.+\\S)\\s*-i\\s*(?<item>\\S.+\\S)\\s*-a\\s*(?<amount>\\S+)\\s*-ti\\s*(?<targetItem>\\S.+\\S)\\s*-ta\\s*(?<targetAmount>.+)"),
    tradeResponse   ("\\s*(?i)trade\\s*response\\s*(?<response>\\S+)\\s*(?<id>.+)")

    ;

    private final String string;
    TradeMenuCommands(String string) {
        this.string = string;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.string).matcher(input);
        if (matcher.matches())
            return matcher;
        else
            return null;
    }
}
