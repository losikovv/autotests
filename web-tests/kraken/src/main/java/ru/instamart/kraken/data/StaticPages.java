package ru.instamart.kraken.data;

public final class StaticPages {

    public static StaticPageData newStaticPage() {
        return new StaticPageData(
                "AAA_" + Generate.literalString(6),
                "testUrl_" + Generate.literalString(6),
                "textAAA",
                "1",
                "я маленькая тестовая страничка, которую должен видеть только селен"
        );
    }

    public static StaticPageData editedStaticPage() {
        return new StaticPageData(
                "BBB_" + Generate.literalString(6),
                "testUrlEdited_" + Generate.literalString(6),
                "textBBB",
                "2",
                "я большая тестовая страничка, очень очень большая и отредактированная"
        );
    }

    private StaticPages() {
    }
}
