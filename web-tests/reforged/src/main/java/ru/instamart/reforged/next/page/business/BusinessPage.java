package ru.instamart.reforged.next.page.business;

import ru.instamart.reforged.next.page.StfPage;

public final class BusinessPage implements StfPage, BusinessCheck {

    @Override
    public String pageUrl() {
        return "/business";
    }
}
