package model;

import model.Enum.WeatherTime.Season;
import model.Enum.WeatherTime.WeekDay;

public class DateHour {

    private int year;
    private Season season;
    private int seasonDay; // چندمین روز فصل ( تاریخ )
    private int hour;


    public DateHour(Season season, int seasonDay, int hour, int year) {
        this.season = season;
        this.seasonDay = seasonDay;
        this.hour = hour;
        this.year = year;
    }

    public int getTime () {
        return -1;
    }
    public String getDate () {
        return null;
    }
    public String getDateTime () {
        return null;
    }
    public WeekDay getDayOfWeek () {
        return null;
    }


    public void increaseHour (int hour) {

        increaseDay((hour+this.hour) / 24);
        this.hour = (hour+this.hour) % 24;
    }
    public void increaseDay (int number) {

        increaseSeason((number+this.seasonDay) / 28);
        this.seasonDay = (this.seasonDay + number) % 28;
    }
    public void increaseSeason (int number) {

        checkYearPassed(number);
        this.season = this.season.passedSeason(number);

    }
    private void checkYearPassed (int SeasonPassed) {

        if (this.season == Season.Spring)
            this.year += SeasonPassed % 4;
        else if (this.season == Season.Summer)
            this.year += (SeasonPassed + 1) % 4;
        else if (this.season == Season.Fall)
            this.year += (SeasonPassed + 2) % 4;
        else if (this.season == Season.Winter)
            this.year += (SeasonPassed + 3) % 4;
    }
    public void changeSeason (Season season) {

    }
    public WeekDay getDayOfTheWeek () {

        if (this.seasonDay % 7 == 0)
            return WeekDay.saturday;
        else if (this.seasonDay % 7 == 1)
            return WeekDay.sunday;
        else if (this.seasonDay % 7 == 2)
            return WeekDay.monday;
        else if (this.seasonDay % 7 == 3)
            return WeekDay.tuesday;
        else if (this.seasonDay % 7 == 4)
            return WeekDay.wednesday;
        else if (this.seasonDay % 7 == 5)
            return WeekDay.thursday;
        else
            return WeekDay.friday;
    }
}
