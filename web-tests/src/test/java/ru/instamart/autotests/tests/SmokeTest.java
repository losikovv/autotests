package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class SmokeTest extends TestBase {



    // Smoke-test сайта и админки
    // TODO запилить проверки на корректное отображение страниц
    // TODO запилить тестовый набор в TestRail с признаком automated
    // TODO запилить автоматическое создание и заполнение рана в TestRail результатами теста



    @Test
    // авторизуемся
    public void authorisation() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
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
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        assertPageAvailability();
        app.getNavigationHelper().getRetailerPage(new RetailerData("lenta"));
        assertPageAvailability();
        app.getNavigationHelper().getRetailerPage(new RetailerData("selgros"));
        assertPageAvailability();
        app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        assertPageAvailability();
        app.getNavigationHelper().getRetailerPage(new RetailerData("karusel"));
        assertPageAvailability();
    }

    @Test
    // чекаем страницы профиля
    public void checkProfilePages() throws Exception {
        app.getNavigationHelper().getPage("user/edit");
        assertPageAvailability();
        app.getNavigationHelper().getPage("user/orders");
        assertPageAvailability();
        app.getNavigationHelper().getPage("user/addresses");
        assertPageAvailability();
    }

    @Test
    // чекаем статические страницы
    // TODO добавить чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        app.getNavigationHelper().getPage("about");
        assertPageAvailability();
        app.getNavigationHelper().getPage("delivery");
        assertPageAvailability();
        app.getNavigationHelper().getPage("rules");
        assertPageAvailability();
        app.getNavigationHelper().getPage("payment");
        assertPageAvailability();
        app.getNavigationHelper().getPage("faq");
        assertPageAvailability();
        app.getNavigationHelper().getPage("terms");
        assertPageAvailability();
        app.getNavigationHelper().getPage("contacts");
        assertPageAvailability();
    }

    @Test
    // чекаем партнерские лендинги
    // TODO добавить чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkPartnerLandings() throws Exception {
        app.getNavigationHelper().getMnogoruLandingPage();
        assertPageAvailability();
    }

    @Test
    // чекаем корневые страницы админки
    // TODO добавить чек лендингов циклом по списку
    public void checkAdminPages() throws Exception {
        app.getNavigationHelper().getAdminPage("shipments");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("retailers");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("products");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("imports");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("reports");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("general_settings/edit");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("promo_cards");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("users");
        assertPageAvailability();
        app.getNavigationHelper().getAdminPage("pages");
        assertPageAvailability();
    }

    @Test
    // логаут
    public void logout() throws Exception {
        app.getAuthorisationHelper().doLogout();
        assertPageAvailability();
    }

}

