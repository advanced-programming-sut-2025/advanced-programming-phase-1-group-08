package model;

import model.Enum.WeatherTime.Season;
import model.Enum.WeatherTime.WeekDay;

public class DateHour {

    private int year;
    private Season season;
    private int date; // چندمین روز فصل ( تاریخ )
    private int hour;


    public DateHour(Season season, int seasonDay, int hour, int year) {
        this.season = season;
        this.date = seasonDay;
        this.hour = hour;
        this.year = year;
    }

    public int getHour () {return this.hour;}
    public int getDate () {return this.date;}
    public int getYear () {return this.year;}
    public Season getSeason () {return this.season;}
    public String getNameSeason () {return this.season.getDisplayName();}

    public void increaseHour (int hour) {

        increaseDay((hour+this.hour) / 24);
        this.hour = (hour+this.hour) % 24;
    }
    public void increaseDay (int number) {

        increaseSeason((number+this.date) / 28);
        this.date = (this.date + number) % 28;
    } // TODO ساعت از ۹ به بعد باید برن بخوابن و عملا الکیه و دستور افزایش ساعتم حواست باشه
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
    public WeekDay getDayOfTheWeek () {

        if (this.date % 7 == 0)
            return WeekDay.saturday;
        else if (this.date % 7 == 1)
            return WeekDay.sunday;
        else if (this.date % 7 == 2)
            return WeekDay.monday;
        else if (this.date % 7 == 3)
            return WeekDay.tuesday;
        else if (this.date % 7 == 4)
            return WeekDay.wednesday;
        else if (this.date % 7 == 5)
            return WeekDay.thursday;
        else
            return WeekDay.friday;
    }
}
