package ru.instamart.autotests.tests;

import org.testng.annotations.Test;



    // Проверка страниц



public class CheckPages extends TestBase {


    @Test(
            enabled = false,
            description = "Тест доступности отдельной страницы"
    )
    public void checkPage() throws Exception, AssertionError {
        assertPageIsAvailable("https://instamart.ru/metro");
    }


    @Test(
            description = "Тест доступности витрин рителйеров",
            groups = {"smoke","acceptance","regression"},
            priority = 801
    )
    // TODO переделать чек страниц всех ретейлеров по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    // TODO проверять витрины активных ритейлеров на доступность, витрины неактивных - на недоступность
    public void checkRetailerPages() throws Exception, AssertionError {
        assertPageIsAvailable("https://instamart.ru/metro");
        assertPageIsAvailable("https://instamart.ru/lenta");
        assertPageIs404("https://instamart.ru/selgros"); // неактивный ритейлер
        assertPageIsAvailable("https://instamart.ru/vkusvill");
        assertPageIsAvailable("https://instamart.ru/karusel");
    }


    @Test(
            description = "Тест доступности лендингов",
            groups = {"smoke","acceptance","regression"},
            priority = 802
    )
    // TODO переделать чек лендингов циклом по списку
    public void checkLandings() throws Exception, AssertionError {
        assertPageIsAvailable("https://instamart.ru/mnogoru");
        assertPageIsAvailable("https://instamart.ru/sovest");
        assertPageIsAvailable("https://instamart.ru/halva");
        assertPageIsAvailable("https://instamart.ru/landings/feedback");
        assertPageIsAvailable("https://vkusvill.instamart.ru/");
    }


    @Test(
            description = "Тест доступности статических страниц",
            groups = {"smoke","acceptance","regression"},
            priority = 803
    )
    // TODO переделать чек страниц циклом по списку
    public void checkStaticPages() throws Exception, AssertionError {
        assertPageIsAvailable("https://instamart.ru/about");
        assertPageIsAvailable("https://instamart.ru/delivery");
        assertPageIsAvailable("https://instamart.ru/rules");
        assertPageIsAvailable("https://instamart.ru/payment");
        assertPageIsAvailable("https://instamart.ru/return");
        assertPageIsAvailable("https://instamart.ru/faq");
        assertPageIsAvailable("https://instamart.ru/terms");
        assertPageIsAvailable("https://instamart.ru/contacts");
    }


    @Test(
            description = "Тест доступности страниц профиля пользователя",
            groups = {"smoke","acceptance","regression"},
            priority = 804
    )
    public void checkProfilePages() throws Exception, AssertionError {
        app.getHelper().getBaseUrl();
        if (!app.getSessionHelper().isUserAuthorised()){
            app.getSessionHelper().doLoginAsAdmin();
        }
        assertPageIsAvailable("https://instamart.ru/user/edit");
        assertPageIsAvailable("https://instamart.ru/user/orders");
        assertPageIsAvailable("https://instamart.ru/user/addresses");
    }


    @Test(
            description = "Тест доступности корневых разделов админки",
            groups = {"smoke","acceptance","regression"},
            priority = 805
    )
    public void checkAdminPages() throws Exception, AssertionError {
        app.getSessionHelper().getUrlAsAdmin("https://instamart.ru/admin/retailers");

        assertPageIsAvailable("https://instamart.ru/admin/shipments");
        assertPageIsAvailable("https://instamart.ru/admin/retailers");
        assertPageIsAvailable("https://instamart.ru/admin/products");
        assertPageIsAvailable("https://instamart.ru/admin/imports");
        assertPageIsAvailable("https://instamart.ru/admin/reports");
        assertPageIsAvailable("https://instamart.ru/admin/general_settings/edit");
        assertPageIsAvailable("https://instamart.ru/admin/promo_cards");
        assertPageIsAvailable("https://instamart.ru/admin/users");
        assertPageIsAvailable("https://instamart.ru/admin/pages");
    }

}
