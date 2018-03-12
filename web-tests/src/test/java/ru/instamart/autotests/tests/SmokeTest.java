package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTest extends TestBase {



    // Smoke-test сайта и админки
    // TODO запилить тестовый набор в TestRail с признаком automated
    // TODO запилить автоматическое создание и заполнение рана в TestRail результатами теста



    @Test
    public void authorisation() throws Exception {

        // идем и чекаем лендинг
        getPageAndAssertAvailability("https://instamart.ru/");
        //  проверка на авторизованность
        //  if (app.getSessionHelper().userIsAuthorised) {
        //    app.getSessionHelper().doLogout();
        //    app.getNavigationHelper().getLandingPage();
        //  }
        // логинимся
        app.getSessionHelper().doLogin(new UserData("autotestuser@instamart.ru", "DyDrasLipMeibe7"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getSessionHelper().userIsAuthorised(), "User wasn't successfully authorised"+"\n");
    }

    @Test
    // чекаем страницы ритейлеров
    // TODO переделать чек страниц всех ретейлеров по списку ретейлеров
    // TODO забирать список ритейлеров из БД или из админки с признаком активности
    // TODO проверять витрины активных ритейлеров на доступность, витрины неактивных - на недоступность
    public void checkRetailerPages() throws Exception {
        getPageAndAssertAvailability("https://instamart.ru/metro");
        getPageAndAssertAvailability("https://instamart.ru/lenta");

        // неактивный ритейлер
        getPageAndAssertIts404("https://instamart.ru/selgros");

        getPageAndAssertAvailability("https://instamart.ru/vkusvill");
        getPageAndAssertAvailability("https://instamart.ru/karusel");
    }

    @Test
    // чекаем недоступность пустого чекаута при ненабранной корзине
    public void emptyCheckoutUnreachable() throws Exception {
        // TODO добавить проверку на текущую сумму корзины и если она выше суммы минимального заказа - очищать корзину
        assertPageIsUnreachable("https://instamart.ru/checkout/edit?");
    }

    @Test
    // чекаем страницы профиля
    // TODO переделать чек страниц циклом по списку
    public void checkProfilePages() throws Exception {
        getPageAndAssertAvailability("https://instamart.ru/user/edit");
        getPageAndAssertAvailability("https://instamart.ru/user/orders");
        getPageAndAssertAvailability("https://instamart.ru/user/addresses");
    }

    @Test
    // чекаем статические страницы
    // TODO переделать чек страниц циклом по списку
    // TODO забирать список страниц из БД или из админки
    public void checkStaticPages() throws Exception {
        getPageAndAssertAvailability("https://instamart.ru/about");
        getPageAndAssertAvailability("https://instamart.ru/delivery");
        getPageAndAssertAvailability("https://instamart.ru/rules");
        getPageAndAssertAvailability("https://instamart.ru/payment");
        getPageAndAssertAvailability("https://instamart.ru/faq");
        getPageAndAssertAvailability("https://instamart.ru/terms");
        getPageAndAssertAvailability("https://instamart.ru/contacts");
    }

    @Test
    // чекаем партнерские лендинги
    // TODO переделать чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkPartnerLandings() throws Exception {
        getPageAndAssertAvailability("https://instamart.ru/mnogoru");
    }

    @Test
    // чекаем корневые страницы админки
    // TODO переделать чек лендингов циклом по списку
    public void checkAdminPages() throws AssertionError {
        getPageAndAssertAvailability("https://instamart.ru/admin/shipments");
        getPageAndAssertAvailability("https://instamart.ru/admin/retailers");
        getPageAndAssertAvailability("https://instamart.ru/admin/products");
        getPageAndAssertAvailability("https://instamart.ru/admin/imports");
        getPageAndAssertAvailability("https://instamart.ru/admin/reports");
        getPageAndAssertAvailability("https://instamart.ru/admin/general_settings/edit");
        getPageAndAssertAvailability("https://instamart.ru/admin/promo_cards");
        getPageAndAssertAvailability("https://instamart.ru/admin/users");
        getPageAndAssertAvailability("https://instamart.ru/admin/pages");
    }

    @Test
    // логаут
    public void logout() throws Exception {
        app.getSessionHelper().doLogout();
        assertPageIsAvailable();
    }

}

