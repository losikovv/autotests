package ru.instamart.reforged.stf.page.business;

import ru.instamart.reforged.stf.page.StfPage;

public final class BusinessPage implements StfPage, BusinessCheck {

    @Override
    public String pageUrl() {
        return "/business";
    }
}
