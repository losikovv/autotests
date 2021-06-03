package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.StfPage;

public final class About implements StfPage {

    private static final String PAGE = "/about-sbermarket";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
