package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTestAdmin extends TestBase {

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
    // чекаем страницы админки
    public void checkAdminPages() throws Exception {
        app.getNavigationHelper().getAdminPage("shipments");
        app.getNavigationHelper().getAdminPage("retailers");
        app.getNavigationHelper().getAdminPage("products");
        app.getNavigationHelper().getAdminPage("imports");
        app.getNavigationHelper().getAdminPage("reports");
        app.getNavigationHelper().getAdminPage("general_dettings/edit");
        app.getNavigationHelper().getAdminPage("promo-cards");
        app.getNavigationHelper().getAdminPage("users");
        app.getNavigationHelper().getAdminPage("pages");
    }

    @Test
    // логаут
    public void logout() throws Exception {
        app.getAuthorisationHelper().doLogout();
    }
}
