package ru.instamart.kraken.data;

public final class StaticPages {

    public static StaticPageData newStaticPage() {
        return new StaticPageData(
                "Auto_AAA_" + Generate.literalString(6),
                "Auto_testUrl_" + Generate.literalString(6),
                "Auto_textAAA",
                "1",
                "я маленькая тестовая страничка, которую должен видеть только селен"
        );
    }

    public static StaticPageData editedStaticPage() {
        return new StaticPageData(
                "Auto_BBB_" + Generate.literalString(6),
                "Auto_testUrlEdited_" + Generate.literalString(6),
                "Auto_textBBB",
                "2",
                "я большая тестовая страничка, очень очень большая и отредактированная"
        );
    }

    private StaticPages() {
    }
}
