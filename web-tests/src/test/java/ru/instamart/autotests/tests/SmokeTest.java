package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.models.UserData;

public class SmokeTest extends TestBase {

    // TODO запилить проверки на корректное отображение страниц
    // TODO сделать переходы по страницам циклом по списку страниц
    // TODO использовать в смоук-тесте уже готовые тесты из класса TestAuthorisation

    @Test
    // авторизуемся на лендинге
    public void authOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().getLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().getLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
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
        app.getNavigationHelper().getRetailerPage(new RetailerData("lenta"));
        app.getNavigationHelper().getRetailerPage(new RetailerData("selgros"));
        app.getNavigationHelper().getRetailerPage(new RetailerData("vkusvill"));
        app.getNavigationHelper().getRetailerPage(new RetailerData("karusel"));
    }

    @Test
    // чекаем страницы профиля
    public void checkProfilePages() throws Exception {
        app.getNavigationHelper().getPage("user/profile");
        app.getNavigationHelper().getPage("user/orders");
        app.getNavigationHelper().getPage("user/addresses");
    }

    @Test
    // чекаем статические страницы
    public void checkStaticPages() throws Exception {
        app.getNavigationHelper().getPage("about");
        app.getNavigationHelper().getPage("delivery");
        app.getNavigationHelper().getPage("rules");
        app.getNavigationHelper().getPage("payment");
        app.getNavigationHelper().getPage("faq");
        app.getNavigationHelper().getPage("terms");
        app.getNavigationHelper().getPage("contacts");
    }

    @Test
    // чекаем лендинги
    // TODO добавить чек лендингов циклом по списку
    // TODO забирать список лендингов из БД или из админки
    public void checkLandings() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getNavigationHelper().getMnogoruLandingPage();
    }

    @Test
    // логаут
    public void logout() throws Exception {
        app.getAuthorisationHelper().doLogout();
    }

}

