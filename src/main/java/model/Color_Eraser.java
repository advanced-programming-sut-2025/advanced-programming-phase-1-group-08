package model;

public class Color_Eraser {

    // Reset
    public static final String RESET = "\u001B[0m";

    // Regular Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String ORANGE = "\u001B[38;2;255;165;0m";
    public static final String Brown = "\u001B[38;2;165;42;42m";
    public static final String BROWN = "\u001B[38;5;94m";


    public static final String BRIGHT_BLACK   = "\u001B[90m";  // معمولاً نمایانگر خاکستری تیره
    public static final String BRIGHT_RED     = "\u001B[91m";
    public static final String BRIGHT_GREEN   = "\u001B[92m";
    public static final String BRIGHT_YELLOW  = "\u001B[93m";
    public static final String BRIGHT_BROWN   = "\u001B[38;2;205;133;63m";
    public static final String BRIGHT_BLUE    = "\u001B[94m";
    public static final String BRIGHT_PURPLE  = "\u001B[95m";
    public static final String BRIGHT_CYAN    = "\u001B[96m";
    public static final String BRIGHT_WHITE   = "\u001B[97m";


    // Bold
    public static final String BLACK_BOLD = "\u001B[1;30m";
    public static final String RED_BOLD = "\u001B[1;31m";
    public static final String GREEN_BOLD = "\u001B[1;32m";
    public static final String YELLOW_BOLD = "\u001B[1;33m";
    public static final String BLUE_BOLD = "\u001B[1;34m";
    public static final String PURPLE_BOLD = "\u001B[1;35m";
    public static final String CYAN_BOLD = "\u001B[1;36m";
    public static final String WHITE_BOLD = "\u001B[1;37m";

    // Background Colors
    public static final String GRAY     = "\u001B[40m"; // فقط مملی استفاده کنه
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";

    public static final String BG_BRIGHT_BLACK  = "\u001B[100m";
    public static final String BG_BRIGHT_RED    = "\u001B[101m";
    public static final String BG_BRIGHT_GREEN  = "\u001B[102m";
    public static final String BG_BRIGHT_YELLOW = "\u001B[103m";
    public static final String BG_BRIGHT_BLUE   = "\u001B[104m";
    public static final String BG_BRIGHT_PURPLE = "\u001B[105m";
    public static final String BG_BRIGHT_CYAN   = "\u001B[106m";
    public static final String BG_BRIGHT_WHITE  = "\u001B[107m";
    public static final String BG_BRIGHT_BROWN = "\u001B[48;2;205;133;63m";

    public static final String REVERSED = "\u001B[7m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";

    public static final String BLUEBERRY = "\u001B[38;2;54;72;173m"; // رنگ بلوبری


    public static final String eraser = "\033[H\033[2J";
}
