package model;

import model.Enum.WeatherTime.Season;
import model.Enum.WeatherTime.WeekDay;

public class DateHour implements Cloneable {

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

        increaseSeason(((number+this.date)-1) / 28);
        this.date = (this.date + number) % 28;
        if (this.date == 0)
            this.date = 28;
    } // TODO ساعت از ۹ به بعد باید برن بخوابن و عملا الکیه و دستور افزایش ساعتم حواست باشه
    public void increaseSeason (int number) {

        checkYearPassed(number);
        this.season = this.season.passedSeason(number);

    }
    private void checkYearPassed (int SeasonPassed) {

        if (this.season == Season.Spring)
            this.year += SeasonPassed / 4;
        else if (this.season == Season.Summer)
            this.year += (SeasonPassed + 1) / 4;
        else if (this.season == Season.Fall)
            this.year += (SeasonPassed + 2) / 4;
        else if (this.season == Season.Winter)
            this.year += (SeasonPassed + 3) / 4;
    }

    public static DateHour decreaseDay (int day, DateHour dateHour) { // بهش عدد بزرگ ندیم

        DateHour result = dateHour.clone();

        if (result.date > day)
            result.date -= day;
        else {

            if (result.season.equals(Season.Spring)) {
                result.season = Season.Winter;
                result.year--;
            }
            else if (result.season.equals(Season.Summer))
                result.season = Season.Spring;
            else if (result.season.equals(Season.Fall))
                result.season = Season.Summer;
            else if (result.season.equals(Season.Winter))
                result.season = Season.Fall;
            result.date = 28+(result.date - day);
        }
        return result;
    }

    @Override
    public DateHour clone() {
        try {
            return (DateHour) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


    private static int getDayDifferentBySeason (Season season1, Season season2) {

        if (season2.equals(Season.Spring)) {
            if (season1.equals(Season.Spring))
                return 0;
            if (season1.equals(Season.Summer))
                return -28;
            if (season1.equals(Season.Fall))
                return -56;
            else
                return -84;
        }
        if (season2.equals(Season.Summer)) {
            if (season1.equals(Season.Spring))
                return 28;
            if (season1.equals(Season.Summer))
                return 0;
            if (season1.equals(Season.Fall))
                return -28;
            else
                return -56;
        }
        if (season2.equals(Season.Fall)) {
            if (season1.equals(Season.Spring))
                return 56;
            if (season1.equals(Season.Summer))
                return 28;
            if (season1.equals(Season.Fall))
                return 0;
            else
                return -28;
        }
        else {
            if (season1.equals(Season.Spring))
                return 84;
            if (season1.equals(Season.Summer))
                return 56;
            if (season1.equals(Season.Fall))
                return 28;
            else
                return 0;
        }
    }
    public static int getDayDifferent (DateHour first, DateHour older) {

        int day = 0;

        day += (older.getYear() - first.getYear()) * 28 * 4;
        day += getDayDifferentBySeason(first.getSeason(), older.getSeason());
        day += older.getDate() - first.getDate();

        return day;
    }

    public static int getHourDiffrent(DateHour first) {
        int firstSeason=0;
        switch (first.getSeason()) {
            case Spring -> {
                firstSeason=1;
            }
            case Summer -> {
                firstSeason=2;
            }
            case Fall -> {
                firstSeason=3;
            }
            case Winter -> {
                firstSeason=4;
            }
        }
        int nowSeason =0;
        switch (App.currentGame.currentDate.getSeason()) {
            case Spring -> {
                nowSeason =1;
            }
            case Summer -> {
                nowSeason =2;
            }
            case Fall -> {
                nowSeason =3;
            }
            case Winter -> {
                nowSeason =4;
            }
        }

        int firstHour= first.year * 2688 + firstSeason * 672 + (first.getDate() -1)*24 + first.getHour();
        int secondHour= App.currentGame.currentDate.year * 2688 + nowSeason * 672 + (App.currentGame.currentDate.getDate() -1 ) * 24 + App.currentGame.currentDate.getHour();

        return secondHour - firstHour;
    }
}