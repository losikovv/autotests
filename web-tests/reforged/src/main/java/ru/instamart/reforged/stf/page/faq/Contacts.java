package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.StfPage;

public final class Contacts implements StfPage {

    private static final String PAGE = "/contacts";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
