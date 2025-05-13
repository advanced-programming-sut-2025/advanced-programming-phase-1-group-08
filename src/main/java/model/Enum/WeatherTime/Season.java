package model.Enum.WeatherTime;


import static model.Color_Eraser.*;

public enum Season {

    Winter("❄️Winter❄️") {

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

            double randomNumber = Math.random();

            if (randomNumber < 0.15 )
                return Weather.Snowy;

            return Weather.Sunny;
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

            double randomNumber = Math.random();

            if (randomNumber < 0.2)
                return Weather.Rainy;
            if (randomNumber > 0.2 && randomNumber < 0.4)
                return Weather.Rainy;

            return Weather.Sunny;
        }
    },
    Summer("☀️Summer☀️") {

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

            double randomNumber = Math.random();

            if (randomNumber < 0.1)
                return Weather.Rainy;
            if (randomNumber > 0.1 && randomNumber < 0.2)
                return Weather.Stormy;

            return Weather.Sunny;
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

            double randomNumber = Math.random();

            if (randomNumber < 0.2)
                return Weather.Rainy;
            if (randomNumber > 0.2 && randomNumber < 0.3)
                return Weather.Stormy;

            return Weather.Sunny;
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
