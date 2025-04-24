package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegisterCommands implements Command {
    Register   ("\\s*register\\s+" +
            "-u\\s+(?<username>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)\\s+(?<confirm>\\S+)\\s+" +
            "-n\\s+(?<nickname>\\S+)\\s+" +
            "-e\\s+(?<email>\\S+)\\s+" +
            "-g\\s+(?<gender>\\S+)"),
    GoToLogin("\\s*go\\s+to\\s+login\\s+menu\\s*");
    private final String string;

    RegisterCommands(String string) {
        this.string = string;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.string).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
