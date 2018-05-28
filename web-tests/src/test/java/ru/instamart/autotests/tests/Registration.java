package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.autotests.models.RetailerData;
import ru.instamart.autotests.testdata.Generate;



    // Тесты регистрации



public class Registration extends TestBase {


    @Test(
            description = "Регистрация нового пользователя на лендинге",
            groups = {"acceptance","regression"}
    )
    public void successRegOnLandingPage() throws Exception {
        app.getNavigationHelper().getLandingPage();
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser(Generate.testUserData());

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve the registration was performed correctly"+"\n");

        // TODO добавить проверку на то что авторизованы под верным юзером
        // TODO добавить проверку наличия пользователя в админке
        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Регистрация нового пользователя на витрине магазина",
            groups = {"regression"}
    )
    public void successRegOnRetailerPage() throws Exception {
        app.getNavigationHelper().getRetailerPage(new RetailerData("metro"));
        app.getSessionHelper().dropAuth();
        app.getSessionHelper().regNewUser(Generate.testUserData());

        // Assert user is authorised
        Assert.assertTrue(app.getSessionHelper().isUserAuthorised(),
                "Can't approve the registration was performed correctly"+"\n");

        // TODO добавить проверку на то что авторизованы под верным юзером
        // TODO добавить проверку наличия пользователя в админке
        app.getSessionHelper().doLogout();
    }

}
