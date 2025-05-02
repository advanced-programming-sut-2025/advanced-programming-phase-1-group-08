package model.Enum.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands implements Command{
    Login("\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+--stay-logged-in)?\\s*"), //TODO Stay-Logged-In Flag
    ForgotPass("\\s*forget\\s+password\\s+-u\\s+(?<username>.+)\\s*"),
    GoToSignUp("\\s*go\\s+to\\s+signup\\s+menu\\s*");

    private final String string;
    LoginCommands(String string) {
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
