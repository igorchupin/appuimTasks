package core;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {
    public String getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd");
        String date = simpleDateFormat.format(calendar.getTime());
        return date;
    }

    public String getTme () {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm");
        String time = simpleDateFormat.format(calendar.getTime());
        return time;

    }
    public String [] getTimeForEventStartAndEnd () {
        String time = getTme() + "-" + getTme();
        String[] timeArray = time.split("-");

        Integer minutesFromDayStarted = Integer.parseInt(timeArray[0]) * 60 + Integer.parseInt(timeArray[1]);
        Integer minutesForBegin = minutesFromDayStarted + 60;

        Integer hoursFromBegin = minutesForBegin / 60;
        Integer minutesLeftNextHour = minutesForBegin - hoursFromBegin * 60;
        if (hoursFromBegin > 23) {
            timeArray[0] = "00";
        }
        else {
            timeArray[0] = hoursFromBegin.toString();
        }
        timeArray[1] = minutesLeftNextHour.toString();

        Integer minutesForEnd = Integer.parseInt(timeArray[0]) * 60 + Integer.parseInt(timeArray[1]) + 90;

        Integer hoursForEnd = minutesForEnd / 60;
        Integer minutesLeftNextHourEnd = minutesForEnd - hoursForEnd * 60;
        if (hoursFromBegin > 23) {
            timeArray[2] = "00";
        }
        else {
            timeArray[2] = hoursForEnd.toString();
        }
        timeArray[3] = minutesLeftNextHourEnd.toString();

        return timeArray;
    }
}
