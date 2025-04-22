package model.Enum.WeatherTime;

// برای فصل ها و مشخص کردن اب و هوا

import static model.Color_Eraser.*;

public enum Season {

    Winter("❄\uFE0FWinter❄\uFE0F") {

        public Season passedSeason(int number) {

            if (number % 4 == 1)
                return Spring;
            else if (number % 4 == 2)
                return Summer;
            else if (number % 4 == 3)
                return Fall;
            else
                return Winter;
        }
        public Weather getWeather() {
            // TODO random int
            return null;
        }
    },
    Fall  ("\uD83C\uDF42Fall\uD83C\uDF42") {

        public Season passedSeason(int number) {

            if (number % 4 == 1)
                return Winter;
            else if (number % 4 == 2)
                return Spring;
            else if (number % 4 == 3)
                return Summer;
            else
                return Fall;
        }
        public Weather getWeather() {
            return null;
        }
    },
    Summer("☀\uFE0FSummer☀\uFE0F") {

        public Season passedSeason(int number) {

            if (number % 4 == 1)
                return Fall;
            else if (number % 4 == 2)
                return Winter;
            else if (number % 4 == 3)
                return Spring;
            else
                return Summer;
        }
        public Weather getWeather() {
            return null;
        }
    },
    Spring("\uD83C\uDF38Spring\uD83C\uDF38") {

        public Season passedSeason(int number) {

            if (number % 4 == 1)
                return Summer;
            else if (number % 4 == 2)
                return Fall;
            else if (number % 4 == 3)
                return Winter;
            else
                return Spring;
        }
        public Weather getWeather() {
            return null;
        }
    };

    private final String displayName;

    Season(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {

        if (this.equals(Summer))
            return YELLOW+displayName+RESET;
        if (this.equals(Spring))
            return PURPLE+displayName+RESET;
        if (this.equals(Winter))
            return CYAN+displayName+RESET;
        else
            return RED+displayName+RESET;
    }

    public abstract Season passedSeason   (int number);
    public abstract Weather getWeather();
}
