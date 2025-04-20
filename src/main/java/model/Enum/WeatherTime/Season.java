package model.Enum.WeatherTime;

// برای فصل ها و مشخص کردن اب و هوا

public enum Season {

    Winter {

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
        }
    },
    Fall {

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

        }
    },
    Summer {

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

        }
    },
    Spring {

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

        }
    };

    private int dayOfSeason;




    public int getDayOfSeason  () {
        return dayOfSeason;
    }
    public abstract Season passedSeason   (int number);
    public abstract Weather getWeather();
    public void setDayOfSeason (int dayOfSeason) {
        this.dayOfSeason = dayOfSeason;
    }
}
