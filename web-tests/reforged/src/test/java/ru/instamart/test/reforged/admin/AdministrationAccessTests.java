package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.main;

@Epic("Админка STF")
@Feature("Доступ в админку")
public class AdministrationAccessTests {

    @CaseId(417)
    @Story("Тест недоступности админки пользователю без админ. прав")
    @Test(description = "Тест недоступности админки пользователю без админ. прав",
            groups = "")
    //перенести в класс AdministrationLoginTests
    public void loginWithoutAdminPermission(){
        login().goToPage();
        login().setUsername("testSam@instamart.ru");
        login().setPassword("12345678");
        login().submit();
        login().checkPermissionError();
    }

    @CaseId(418)
    @Story("Тест доступности админки пользователю c админ. правми")
    @Test(description = "Тест доступности админки пользователю c админ. правми",
            groups = "")
    //дублирует тест successAuthOnAdminLoginPage из класса AdministrationLoginTests
    public void loginWithAdminPermission(){
        login().goToPage();
        login().setUsername("");
        login().setPassword("");
        login().submit();
        main().checkAuth();
    }
}
