package ru.instamart.kraken.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum ScheduleType {

    DISPATCH("dispatch"),
    YANDEX("yandex"),
    LIST("list");

    private final String name;
}
