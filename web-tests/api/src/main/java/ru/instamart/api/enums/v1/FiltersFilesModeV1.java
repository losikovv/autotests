package ru.instamart.api.enums.v1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public enum FiltersFilesModeV1 {

    REPLACE_FOR_FILTER_GROUPS("replace_for_filter_groups"),
    UPDATE_ONLY("update_only"),
    REPLACE_FOR_CATEGORY("replace_for_category");

    private final String value;
}
