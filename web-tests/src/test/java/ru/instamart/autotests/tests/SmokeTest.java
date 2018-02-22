package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class SmokeTest extends TestBase {



    // Smoke-test сайта и админки
    // TODO запилить тестовый набор в TestRail с признаком automated
    // TODO запилить автоматическое создание и заполнение рана в TestRail результатами теста



    @Test
    // авторизуемся
    public void authorisation() throws Exception {

        // идем и чекаем лендинг
        assertPageAvailability("https://instamart.ru/");
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся
        app.getAuthorisationHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
    }

    @Test
    // чекаем страницы ритейлеров
    // TODO добавить чек страниц всех ретейлеров по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    // TODO проверять витрины активных ритейлеров на доступность, витрины неактивных - на недоступность
    public void checkRetailerPages() throws Exception {
        assertPageAvailability("https://instamart.ru/metro");
        assertPageAvailability("https://instamart.ru/lenta");
        assertPageAvailability("https://instamart.ru/selgros");
        assertPageAvailability("https://instamart.ru/vkusvill");
        assertPageAvailability("https://instamart.ru/karusel");
    }

    @Test
    // чекаем чекаут
    public void checkCheckoutPage() throws Exception {
        assertPageAvailability("https://instamart.ru/checkout/edit?");
    }

    @Test
    // чекаем страницы профиля
    // TODO добавить чек страниц циклом по списку
    public void checkProfilePages() throws Exception {
        assertPageAvailability("https://instamart.ru/user/edit");
        assertPageAvailability("https://instamart.ru/user/orders");
        assertPageAvailability("https://instamart.ru/user/addresses");
    }

    @Test
    // чекаем статические страницы
    // TODO добавить чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        assertPageAvailability("https://instamart.ru/about");
        assertPageAvailability("https://instamart.ru/delivery");
        assertPageAvailability("https://instamart.ru/rules");
        assertPageAvailability("https://instamart.ru/payment");
        assertPageAvailability("https://instamart.ru/faq");
        assertPageAvailability("https://instamart.ru/terms");
        assertPageAvailability("https://instamart.ru/contacts");
    }

    @Test
    // чекаем партнерские лендинги
    // TODO добавить чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkPartnerLandings() throws Exception {
        //app.getNavigationHelper().getMnogoruLandingPage();
        assertPageAvailability("https://instamart.ru/mnogoru");
    }

    @Test
    // чекаем корневые страницы админки
    // TODO добавить чек лендингов циклом по списку
    public void checkAdminPages() throws Exception {
        //app.getNavigationHelper().getAdminPage("shipments");
        assertPageAvailability("https://instamart.ru/admin/shipments");
        //app.getNavigationHelper().getAdminPage("retailers");
        assertPageAvailability("https://instamart.ru/admin/retailers");
        //app.getNavigationHelper().getAdminPage("products");
        assertPageAvailability("https://instamart.ru/admin/products");
        //app.getNavigationHelper().getAdminPage("imports");
        assertPageAvailability("https://instamart.ru/admin/imports");
        //app.getNavigationHelper().getAdminPage("reports");
        assertPageAvailability("https://instamart.ru/admin/reports");
        //app.getNavigationHelper().getAdminPage("general_settings/edit");
        assertPageAvailability("https://instamart.ru/admin/general_settings/edit");
        //app.getNavigationHelper().getAdminPage("promo_cards");
        assertPageAvailability("https://instamart.ru/admin/promo_cards");
        //app.getNavigationHelper().getAdminPage("users");
        assertPageAvailability("https://instamart.ru/admin/users");
        //app.getNavigationHelper().getAdminPage("pages");
        assertPageAvailability("https://instamart.ru/admin/pages");
    }

    @Test
    // логаут
    public void logout() throws Exception {
        app.getAuthorisationHelper().doLogout();
        //assertPageAvailability();
    }

}

