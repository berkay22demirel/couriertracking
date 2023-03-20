package dev.berkaydemirel.couriertracking.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    private static DateUtil instance;

    private DateUtil() {
    }

    public static synchronized DateUtil getInstance() {
        if (instance == null) {
            instance = new DateUtil();
        }
        return instance;
    }

    public long getDiffSeconds(Date startDate, Date endDate) {
        return TimeUnit.MILLISECONDS.toSeconds((endDate.getTime() - startDate.getTime()));
    }
}
