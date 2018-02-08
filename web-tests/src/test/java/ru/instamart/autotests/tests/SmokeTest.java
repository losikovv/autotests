package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.UserData;

public class SmokeTest extends TestBase {

    // TODO запилить проверки на корректное отображение страниц
    // TODO сделать переходы по статическим страницам циклом по списку страниц
    // TODO использовать в смоук-тесте уже готовые тесты из класса TestAuthorisation

    @Test
    // авторизуемся на лендинге
    public void authOnLanding() throws Exception {
        // идем на лендинг
        app.getNavigationHelper().goToLandingPage();
        // проверка на авторизованность
        if (app.getAuthorisationHelper().userIsAuthorised) {
            app.getAuthorisationHelper().doLogout();
            app.getNavigationHelper().goToLandingPage();
        }
        // логинимся на лендинге
        app.getAuthorisationHelper().doLogin(new UserData("instatestuser@yandex.ru", "instamart"));
        // проверяем что авторизованы
        Assert.assertTrue(app.getAuthorisationHelper().userIsAuthorised());
    }

    @Test
    // чекаем страницы раздела Профиль
    public void checkProfilePages() throws Exception {
        app.getNavigationHelper().goToProfileAccount();
        app.getNavigationHelper().goToProfileOrders();
        app.getNavigationHelper().goToProfileAddresses();
    }

    @Test
    // чекаем статические страницы
    public void checkStaticPages() throws Exception {
        app.getNavigationHelper().goToAbout();
        app.getNavigationHelper().goToDelivery();
        app.getNavigationHelper().goToRules();
        app.getNavigationHelper().goToPayment();
        app.getNavigationHelper().goToFAQ();
        app.getNavigationHelper().goToTerms();
        app.getNavigationHelper().goToContacts();
    }

    @Test
    // чекаем лендинг "Много.ру"
    public void checkMnogoruLanding() throws Exception {
        app.getNavigationHelper().goToMnogoruLandingPage();
    }

    @Test
    // логаут
    public void logout() throws Exception {
        //app.getNavigationHelper().goToLandingPage();
        app.getAuthorisationHelper().doLogout();
    }

}

