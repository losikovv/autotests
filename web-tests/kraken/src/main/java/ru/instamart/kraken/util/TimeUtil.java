package ru.instamart.kraken.util;

import com.google.protobuf.Timestamp;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public final class TimeUtil {

    private static final DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter dtd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dts = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter dtds = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter zdt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateTimeFormatter zdtz = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    private static final DateTimeFormatter dtdb = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId ZONE_ID = ZoneId.of("Europe/Moscow");
    private static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("dd MMMM yyyy")
            .toFormatter(new Locale("ru"));
    //Mon Feb 14 22:55:00 MSK 2022
    private static final DateTimeFormatter formatterFullDate = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("EEE MMM dd HH:mm:ss zzz yyyy")
            .toFormatter(Locale.ENGLISH);

    private TimeUtil() {
    }

    public static String getDateTimeViaSlash() {
        return dts.format(ZonedDateTime.now());
    }

    public static String getDeliveryDateFrom() {
        return dt.format(ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZONE_ID));
    }

    public static String getDeliveryDateTo() {
        return dt.format(ZonedDateTime.of(LocalDate.now(), LocalTime.MAX, ZONE_ID));
    }

    public static String getDateWithoutTime() {
        return dtd.format(ZonedDateTime.now(ZONE_ID));
    }

    public static String getDateWithoutTimeViaSlash() {
        return dtds.format(ZonedDateTime.now(ZONE_ID));
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

    public static String getZonedUTCDate() {
        return zdt.format(ZonedDateTime.now(ZONE_UTC));
    }

    public static String getZonedUTCFutureDate(Long days) {
        return zdt.format(ZonedDateTime.now(ZONE_UTC).plusDays(days));
    }

    public static String getDbDeliveryDateFrom(Long days) {

        return dtdb.format(ZonedDateTime.ofInstant(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).atZone(ZONE_ID).toInstant(), ZoneId.of("UTC")).plusDays(days));
    }

    public static String getDbDeliveryDateTo(Long days) {
        return dtdb.format(ZonedDateTime.ofInstant(LocalDateTime.of(LocalDate.now(), LocalTime.MAX).atZone(ZONE_ID).toInstant(), ZoneId.of("UTC")).plusDays(days));
    }

    public static String getDbDate(LocalDateTime date) {
        return dtdb.format(ZonedDateTime.ofInstant(date.atZone(ZONE_ID).toInstant(), ZoneId.of("UTC")));
    }

    public static String getDbDate() {
        return dtdb.format(ZonedDateTime.now(ZONE_UTC));
    }

    public static String getPastZoneDbDate(Long days) {
        return dtdb.format(ZonedDateTime.now(ZONE_ID).minusDays(days));
    }

    public static String getFutureZoneDbDate(Long days) {
        return dtdb.format(ZonedDateTime.now(ZONE_ID).plusDays(days));
    }

    public static Long getDbDateMinusMinutes(Long minutes) {
        return ZonedDateTime.now(ZONE_ID).minusMinutes(minutes).toInstant().toEpochMilli();
    }

    public static String getDateTime() {
        return zdtz.format(ZonedDateTime.now(ZONE_ID));
    }

    public static String getFutureDateTime(Long days) {
        return zdtz.format(ZonedDateTime.now(ZONE_ID).plusDays(days));
    }

//    public static String getNearestDateTime(){
//        java.util.Date date = Date.from(ZonedDateTime.now(ZONE_ID).toInstant());
//        return DateUtils.round(date.getTime())
//    }

    /**
     * Парсит строку типа "15 ноября 2016" в (ZoneDateTime) 2016-11-15T00:00+07:00[Asia/Novosibirsk]
     */
    public static ZonedDateTime convertStringToDate(final String str) {
        return LocalDate.parse(str, formatter).atStartOfDay(ZoneId.systemDefault());
    }

    public static String convertFullDateToDtd(final String date) {
        return dtd.format(LocalDate.parse(date, formatterFullDate).atStartOfDay(ZONE_ID));
    }

    public static String convertFullDateToDt(final String date) {
        return dt.format(ZonedDateTime.parse(date, formatterFullDate).withZoneSameInstant(ZONE_ID));
    }

    public static Timestamp getTimestampFromString(String dateTime) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateTime, zdtz);
        Instant instant = zonedDateTime.toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Long getTimestamp() {
        Instant instant = Instant.now();
        return instant.toEpochMilli();
    }

    public static Timestamp getDateWithSec(int sec) {
        Instant instant = Instant.now().minusSeconds(sec);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
