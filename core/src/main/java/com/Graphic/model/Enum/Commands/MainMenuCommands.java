package com.Graphic.model.Enum.Commands;

import View.RegisterMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Command{
    logout   ("\\s*user\\s+logout\\s*"),
    goToProfileMenu("\\s*go\\s+to\\s+profile\\s+menu\\s*"),
    goToGameMenu("\\s*go\\s+to\\s+game\\s+menu\\s*"),
    goToAvatarMenu("\\s*go\\s+to\\s+avatar\\s+menu\\s*");
    private final String string;

    MainMenuCommands(String string) {
        this.string = string;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.string).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
