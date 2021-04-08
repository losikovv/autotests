package ru.instamart.core.testdata.ui;

import ru.instamart.ui.common.pagesdata.StaticPageData;

public class StaticPages {

    public static StaticPageData newStaticPage(){
        return new StaticPageData(
                "AAA",
                "testURL",
                "textAAA",
                "1",
                "я маленькая тестовая страничка, которую должен видеть только селен"
            );
        }

    public static StaticPageData editedStaticPage(){
        return new StaticPageData(
                "BBB",
                "testURLB",
                "textBBB",
                "2",
                "я большая тестовая страничка, очень очень большая и отредактированная"
            );
        }
    }
