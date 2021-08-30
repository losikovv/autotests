package ru.instamart.kraken.testdata;

import ru.instamart.kraken.testdata.pagesdata.StaticPageData;

public final class StaticPages {

    public static StaticPageData newStaticPage() {
        return new StaticPageData(
                "AAA",
                "testUrl_" + Generate.literalString(6),
                "textAAA",
                "1",
                "я маленькая тестовая страничка, которую должен видеть только селен"
        );
    }

    public static StaticPageData editedStaticPage() {
        return new StaticPageData(
                "BBB",
                "testUrlEdited_" + Generate.literalString(6),
                "textBBB",
                "2",
                "я большая тестовая страничка, очень очень большая и отредактированная"
        );
    }

    private StaticPages() {
    }
}
