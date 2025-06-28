package com.Graphic.model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MarketMenuCommands implements Command {

    showAllProducts("(?i)show\\s+all\\s+products\\s*"),
    showAvailableProducts("(?i)show\\s+all\\s+available\\s+products\\s*"),
    purchase("^(?i)purchase\\s+(.+?)(?:\\s+-n\\s+(\\d+))?$"),
    buyAnimal("(?i)buy\\s+animal\\s+-a\\s+(?<animal>\\S.*)\\s+-n\\s+(?<name>\\S.*)\\s*"),
    buildBarnOrCage("build\\s+-a\\s+(?<name>\\S.*)\\s+-l\\s+(?<X>\\d+),(?<Y>\\d+)\\s*"),
    toolsUpgrade  ("(?i)\\s*tools\\s*upgrade\\s*(?<name>\\S+)\\s*");

    private final String pattern;

    MarketMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
