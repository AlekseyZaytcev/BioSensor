package Time;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by root on 20.07.2017.
 */
public class MyTime {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Timestamp currentTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Timestamp time = new Timestamp(now.getTime());
        return time;
    }

    public Timestamp weeks2AgoTime() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Timestamp time = new Timestamp(now.getTime() - 2 * 7 * 24 * 60 * 60 * 1000);
        return time;
    }

    public Timestamp secondsAgoTime(Integer seconds) {
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Timestamp time = new Timestamp(now.getTime() - seconds * 1000);
        return time;
    }

    public Timestamp timeStart(String time) throws ParseException {
        Timestamp timestamp = new Timestamp(format.parse(time).getTime());
        return timestamp;
    }

    public Timestamp timeEnd(String time) throws ParseException {
        Timestamp timestamp = new Timestamp(format.parse(time).getTime());
        return timestamp;
    }
}
