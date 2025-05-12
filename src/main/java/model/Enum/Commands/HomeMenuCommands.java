package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HomeMenuCommands implements Command {
    startCooking     ("\\s*start\\s*cooking\\s*"),
    fridgePut        ("\\s*cooking\\s*refrigerator\\s*put\\s*(?<item>.+)"),
    fridgePick       ("\\s*cooking\\s*refrigerator\\s*pick\\s*(?<item>.+)"),
    showRecipes      ("\\s*cooking\\s*show\\s*recipes\\s*"),
    foodPrepare      ("\\s*cooking\\s*prepare\\s*(?<food>.+)");


    private final String string;
    HomeMenuCommands(String string) {
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
