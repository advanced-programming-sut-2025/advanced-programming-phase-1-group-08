package model.Enum.WeatherTime;

// برای فصل ها و مشخص کردن اب و هوا

public enum Season {

    Winter{
        public void setWeather () {
            // TODO random int
        }
    },
    Fall{
        public void setWeather () {

        }
    },
    Summer{
        public void setWeather () {

        }
    },
    Spring{
        public void setWeather () {

        }
    };

    private int dayOfSeason;




    public int getDayOfSeason() {
        return dayOfSeason;
    }

    public void setDayOfSeason(int dayOfSeason) {
        this.dayOfSeason = dayOfSeason;
    }
}
