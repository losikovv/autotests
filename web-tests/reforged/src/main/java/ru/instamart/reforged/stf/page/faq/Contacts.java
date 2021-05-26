package ru.instamart.reforged.stf.page.faq;

import ru.instamart.reforged.stf.page.Page;

public final class Contacts implements Page {

    private static final String PAGE = "/contacts";

    @Override
    public String pageUrl() {
        return PAGE;
    }
}
