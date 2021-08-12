package ru.instamart.kraken.helper;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
public class DateTimeHelper {

    public static LocalDate getDateFromMSK() {
        return OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toLocalDate();
    }

    public static String getDateFromUTC() {
        return OffsetDateTime.now(ZoneOffset.UTC).toLocalDate().toString();
    }
}
