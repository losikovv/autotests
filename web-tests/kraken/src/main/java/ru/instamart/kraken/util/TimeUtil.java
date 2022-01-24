package ru.instamart.kraken.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public final class TimeUtil {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter dtd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter zdt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateTimeFormatter dtdb = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");

    public static String getDeliveryDateFrom() {
        return dt.format(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZONE_ID));
    }

    public static String getDeliveryDateTo() {
        return dt.format(ZonedDateTime.of(LocalDate.now(), LocalTime.MAX, ZONE_ID));
    }

    public static String getDateWithoutTime() {
        return dtd.format(ZonedDateTime.now(ZONE_ID));
    }

    public static String getFutureDateWithoutTime(Long days) {
        return dtd.format(ZonedDateTime.now(ZONE_ID).plusDays(days));
    }

    public static String getPastDateWithoutTime(Long days) {
        return dtd.format(ZonedDateTime.now(ZONE_ID).minusDays(days));
    }

    public static String getZonedDate() {
        return zdt.format(ZonedDateTime.now(ZONE_ID));
    }

    public static String getZonedFutureDate(Long days) {
        return zdt.format(ZonedDateTime.now(ZONE_ID).plusDays(days));
    }

    public static String getDbDeliveryDateFrom(Long days) {
        return dtdb.format(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZONE_ID).plusDays(days));
    }

    public static String getDbDeliveryDateTo(Long days) {
        return dtdb.format(ZonedDateTime.of(LocalDate.now(), LocalTime.MAX, ZONE_ID).plusDays(days));
    }

    public static String getPastZoneDbDate(Long days) {
        return dtdb.format(ZonedDateTime.now(ZONE_ID).minusDays(days));
    }

    public static String getFutureZoneDbDate(Long days) {
        return dtdb.format(ZonedDateTime.now(ZONE_ID).plusDays(days));
    }

    public static Date returnDateFromString(final String str) throws ParseException {
        String[] months = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
        Locale ru = new Locale("ru");
        DateFormatSymbols symbols = DateFormatSymbols.getInstance(ru);
        symbols.setMonths(months);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", ru);
        format.setDateFormatSymbols(symbols);
        return format.parse(str);
    }

    private TimeUtil() {
    }
}
