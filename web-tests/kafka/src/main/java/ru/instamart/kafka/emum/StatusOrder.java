package ru.instamart.kafka.emum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum StatusOrder {
    POSTPONED("Postponed"),
    AUTOMATIC_ROUTING("AutomaticRouting");

    private final String value;
}
