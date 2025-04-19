package model;

import model.Enum.Season;
import model.Enum.WeekDay;

public class Date {

    private WeekDay weekDay;
    private Season season;
    private int seasonDay; // چندمین روز فصل ( تاریخ )
    private int hour;

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

    public void advanceTime (int hour) {

        // advanceDay(hour/24);
        // حواست به عدد بزرگا هم باشه مثلا اگه اندازه ۲ فصل رفت جلو
        // ساعت جلو میزه بیشتر از ۲۴ هم نشه
    }

    public void advanceDay (int number) {

        // changeSeason(number/28);

        // تابع برای افزایش روز
        // بعد تموم شدن ماه بیاد بریم فصل بعد
    }
    public void changeSeason (int number) {
        // حواست به جلو رفتن دو تا فصل باشه
    }
}
