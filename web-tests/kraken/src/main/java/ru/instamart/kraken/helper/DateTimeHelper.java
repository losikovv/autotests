package ru.instamart.kraken.helper;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateTimeHelper {

    private static final DateTimeFormatter dtd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getDateFromMSK() {
        return dtd.format(ZonedDateTime.now(ZoneId.of("Europe/Moscow")));
    }

    public static String getDateFromUTC() {
        return dtd.format(ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
