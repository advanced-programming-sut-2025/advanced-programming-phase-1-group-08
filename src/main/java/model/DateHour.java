package model;

import model.Enum.WeatherTime.Season;
import model.Enum.WeatherTime.WeekDay;

public class DateHour {

    private WeekDay weekDay;
    private Season season;
    private int seasonDay; // چندمین روز فصل ( تاریخ )
    private int hour;


    public DateHour(WeekDay weekDay, Season season, int seasonDay, int hour) {
        this.weekDay = weekDay;
        this.season = season;
        this.seasonDay = seasonDay;
        this.hour = hour;
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

        // advanceDay(hour/24);
        // حواست به عدد بزرگا هم باشه مثلا اگه اندازه ۲ فصل رفت جلو
        // ساعت جلو میزه بیشتر از ۲۴ هم نشه
    }
    public void decreaseHour (int hour) {

        // advanceDay(hour/24);
        // حواست به عدد بزرگا هم باشه مثلا اگه اندازه ۲ فصل رفت جلو
        // ساعت جلو میزه بیشتر از ۲۴ هم نشه
    }
    public void increaseDay (int number) {

        // changeSeason(number/28);

        // تابع برای افزایش روز
        // بعد تموم شدن ماه بیاد بریم فصل بعد
    }
    public void decreaseDay (int number) {

        // changeSeason(number/28);

        // تابع برای افزایش روز
        // بعد تموم شدن ماه بیاد بریم فصل بعد
    }
    public void changeSeason (Season season) {
        // حواست به جلو رفتن دو تا فصل باشه
    }
    public void increaseSeason () {

    }
}
