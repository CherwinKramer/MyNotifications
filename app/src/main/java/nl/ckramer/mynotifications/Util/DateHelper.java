package nl.ckramer.mynotifications.Util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    private static final String TAG = "DateHelper";

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static String formatDate(SimpleDateFormat dateFormat, Date date) {
        if(date != null) {
            return dateFormat.format(date);
        }
        return "";
    }

    public static Date parseDate(SimpleDateFormat dateFormat, String date) {
        if(date != null && !date.isEmpty()) {
            try {
                return dateFormat.parse(date);
            } catch (ParseException e) {
                Log.e(TAG, "parseDate: " + date, e);
                return null;
            }
        }
        return null;
    }

    public static boolean isDate(String date) {
        if(date != null && !date.isEmpty()) {
            try {
                dateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                Log.e(TAG, "parseDate: " + date, e);
                return false;
            }
        }
        return false;
    }

}
