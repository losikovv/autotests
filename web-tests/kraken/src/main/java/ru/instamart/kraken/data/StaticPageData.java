package ru.instamart.kraken.data;

import lombok.Data;

@Data
public final class StaticPageData {

    private final String pageName;
    private final String pageURL;
    private final String text;
    private final String position;
    private final String description;
}
