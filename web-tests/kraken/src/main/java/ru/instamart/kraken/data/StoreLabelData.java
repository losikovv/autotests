package ru.instamart.kraken.data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public final class StoreLabelData {

    @Setter(AccessLevel.PUBLIC)
    private String title;
    private final String subtitle;
    private final String code;
    private final String icon;
    private final int position;
    private final int level;

}
