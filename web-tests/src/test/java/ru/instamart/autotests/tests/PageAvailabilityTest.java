package ru.instamart.autotests.tests;

import org.testng.annotations.Test;

public class PageAvailabilityTest extends TestBase {

    /**
     * тест на доступность страницы
    */

    @Test
    public void checkPage() throws Exception {

        getPageAndAssertAvailability("https://instamart.ru/metro");

    }

}
