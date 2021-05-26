package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.Page;

public final class About implements Page {

    private static final String PAGE = "/about-sbermarket";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
