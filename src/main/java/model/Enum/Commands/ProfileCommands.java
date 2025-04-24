package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileCommands implements Command{
    userInfo ("\\s*user\\s+info\\s*"),
    changeUsername ("\\s*change\\s+username\\s+-u\\s+(?<username>.+)\\s*"),
    changePass ("\\s*change\\s+password\\s+-p\\s+(?<password>.+)\\s+-o(?<oldPassword>.+)\\s*"),
    changeEmail ("\\s*change\\s+email\\s+-e\\s+(?<email>.+)\\s*"),
    changeNickname("\\s*change\\s+nickname\\s+-n\\s+(?<nickname>.+)\\s*");
    private final String string;

    ProfileCommands(String string) {
        this.string = string;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.string).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
