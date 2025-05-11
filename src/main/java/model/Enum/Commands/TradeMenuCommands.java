package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands implements Command{
    tradeP      ("\\s*(?i)trade\\s*-u\\s*(?<username>\\S+)-t\\s*(?<type>\\S+)-i\\s*(?<item>\\S+)-a\\s*(?<amount>\\S+)-p\\s*(?<price>.+)"),
    tradeTI     ("\\s*(?i)trade\\s*-u\\s*(?<username>\\S+)-t\\s*(?<type>\\S+)-i\\s*(?<item>\\S+)-a\\s*(?<amount>\\S+)-ti\\s*(?<targetItem>\\S+)-ta\\s*(?<targetAmount>.+)"),
    tradeResponse   ("\\s*(?i)trade\\s*response\\s*(?<response>\\S+)(?<id>.+)")

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
