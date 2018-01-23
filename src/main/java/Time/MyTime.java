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

