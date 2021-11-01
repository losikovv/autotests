package ru.instamart.kraken.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class TimeUtil {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter dtd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getDeliveryDateFrom() {
        return dt.format(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
    }

    public static String getDeliveryDateTo() {
        return dt.format(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
    }

    public static String getDateWithoutTime() {
        return dtd.format(LocalDateTime.now());
    }

    public static String getFutureDateWithoutTime(Long days) {
        return dtd.format(LocalDateTime.now().plusDays(days));
    }

    private TimeUtil() {}
}
