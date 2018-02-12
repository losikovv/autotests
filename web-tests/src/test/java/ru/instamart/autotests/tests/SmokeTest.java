package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTest extends TestBase {

    // TODO запилить проверки на корректное отображение страниц
    // TODO добавить чек витрин всех ретейлеров по списку ретейлеров
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
    // чекаем страницы раздела Профиль
    public void checkProfilePages() throws Exception {
        app.getNavigationHelper().getPageProfileAccount();
        app.getNavigationHelper().getPageProfileOrders();
        app.getNavigationHelper().getPageProfileAddresses();
    }

    @Test
    // чекаем статические страницы
    // TODO сделать переходы по статическим страницам циклом по списку страниц
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
    // чекаем лендинг "Много.ру"
    public void checkMnogoruLanding() throws Exception {
        app.getNavigationHelper().getMnogoruLandingPage();
    }

    @Test
    // логаут
    public void logout() throws Exception {
        //app.getNavigationHelper().getLandingPage();
        app.getAuthorisationHelper().doLogout();
    }

}

