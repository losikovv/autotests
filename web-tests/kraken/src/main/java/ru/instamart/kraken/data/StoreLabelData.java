package ru.instamart.kraken.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class StoreLabelData {

    private String title;
    private final String subtitle;
    private final String code;
    private final String icon;
    private final int position;
    private final int level;

}
