package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.Page;

public final class Faq implements Page {

    private static final String PAGE = "/faq-sbermarket";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
