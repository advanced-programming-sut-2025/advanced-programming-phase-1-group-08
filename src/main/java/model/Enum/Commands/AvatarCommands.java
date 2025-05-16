package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AvatarCommands implements Command{
    back   ("\\s*(?i)back\\s*");

    private final String string;

    AvatarCommands(String string) {
        this.string = string;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.string).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
