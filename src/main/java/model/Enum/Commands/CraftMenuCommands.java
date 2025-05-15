package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CraftMenuCommands implements Command {
    craftingRecipe("(?i)crafting\\s+show\\s+recipes\\s*"),
    craftingCraft("(?i)crafting\\s+craft\\s+(?<name>\\S.*)\\s*"),
    exit("\\s*(?i)exit\\s*");


    private final String string;
    CraftMenuCommands(String string) {
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
