package ru.instamart.api.enums.shopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public enum ItemStateSHP {

    APPROVED("approved");

    @Getter
    private final String state;
}
