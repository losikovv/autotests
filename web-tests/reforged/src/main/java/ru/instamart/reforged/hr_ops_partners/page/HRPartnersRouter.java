package ru.instamart.reforged.hr_ops_partners.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.hr_ops_partners.page.home.HomePage;
import ru.instamart.reforged.hr_ops_partners.page.vacancy.VacancyPage;

public final class HRPartnersRouter extends Router {

    public static HomePage home() {
        return (HomePage) getPage(HomePage.class);
    }

    public static VacancyPage vacancyPage() {
        return (VacancyPage) getPage(VacancyPage.class);
    }
    private HRPartnersRouter() {
    }
}
