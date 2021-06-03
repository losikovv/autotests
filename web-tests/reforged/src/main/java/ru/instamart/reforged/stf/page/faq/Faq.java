package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.StfPage;

public final class Faq implements StfPage {

    private static final String PAGE = "/faq-sbermarket";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
