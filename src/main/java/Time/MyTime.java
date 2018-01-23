package Time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 20.07.2017.
 */
public class MyTime {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//2018-01-23 10:31:00.279

    public Date currentTime() {
        Date now = new Date();
        return now;
    }

    public Date weeks2AgoTime() {
        Date now = new Date();
        Date time = new Date(now.getTime() - 2 * 7 * 24 * 60 * 60 * 1000);
        return time;
    }

    public Date secondsAgoTime(Integer seconds) {
        Date now = new Date();
        Date time = new Date(now.getTime() - seconds * 1000);
        return time;
    }
}

