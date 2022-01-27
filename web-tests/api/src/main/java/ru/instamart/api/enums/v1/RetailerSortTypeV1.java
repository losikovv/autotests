package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum RetailerSortTypeV1 {
    NAME_ASC("name asc"),
    NAME_DESC("name desc"),
    CREATED_AT_ASC("created_at asc"),
    CREATED_AT_DESC("created_at desc");

    private final String value;
}
