package com.Graphic.model.Enum.Commands;

import java.util.regex.Matcher;

public interface Command {
    Matcher getMatcher(String input);
}
