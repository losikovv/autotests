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
        Assert.assertFalse(app.getErrorPageHelper().its404());
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
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getRetailerPage(new RetailerData("lenta"));
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getRetailerPage(new RetailerData("selgros"));
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getRetailerPage(new RetailerData("karusel"));
        Assert.assertFalse(app.getErrorPageHelper().its404());
    }

    @Test
    // чекаем страницы профиля
    public void checkProfilePages() throws Exception {
        app.getNavigationHelper().getPage("user/profile");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("user/orders");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("user/addresses");
        Assert.assertFalse(app.getErrorPageHelper().its404());
    }

    @Test
    // чекаем статические страницы
    // TODO добавить чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        app.getNavigationHelper().getPage("about");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("delivery");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("rules");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("payment");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("faq");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("terms");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getPage("contacts");
        Assert.assertFalse(app.getErrorPageHelper().its404());
    }

    @Test
    // чекаем партнерские лендинги
    // TODO добавить чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkPartnerLandings() throws Exception {
        app.getNavigationHelper().getMnogoruLandingPage();
    }

    @Test
    // чекаем корневые страницы админки
    // TODO добавить чек лендингов циклом по списку
    public void checkAdminPages() throws Exception {
        app.getNavigationHelper().getAdminPage("shipments");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("retailers");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("products");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("imports");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("reports");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("general_settings/edit");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("promo-cards");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("users");
        Assert.assertFalse(app.getErrorPageHelper().its404());
        app.getNavigationHelper().getAdminPage("pages");
        Assert.assertFalse(app.getErrorPageHelper().its404());
    }

    @Test
    // логаут
    public void logout() throws Exception {
        app.getAuthorisationHelper().doLogout();
    }

}

