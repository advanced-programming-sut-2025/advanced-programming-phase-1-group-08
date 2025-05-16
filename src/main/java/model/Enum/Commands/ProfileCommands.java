package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileCommands implements Command{
    userInfo ("\\s*(?i)user\\s+info\\s*"),
    changeUsername ("\\s*(?i)change\\s+username\\s+-u\\s+(?<username>.+)\\s*"),
    changePass ("\\s*(?i)change\\s+password\\s+-p\\s+(?<password>.+)\\s+-o\\s*(?<oldPassword>.+)\\s*"),
    changeEmail ("\\s*(?i)change\\s+email\\s+-e\\s+(?<email>.+)\\s*"),
    changeNickname("\\s*(?i)change\\s+nickname\\s+-n\\s+(?<nickname>.+)\\s*"),
    back ("\\s*(?i)back\\s*");
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
