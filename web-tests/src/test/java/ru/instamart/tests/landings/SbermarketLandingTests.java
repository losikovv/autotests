package ru.instamart.tests.landings;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.application.Tenants;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.User;
import ru.instamart.tests.TestBase;

public class SbermarketLandingTests extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setup() {
        User.Logout.quickly();
    }

    @BeforeMethod(alwaysRun = true)
    public void quickLogout() {
        User.Logout.quickly();
    }

    @Test(
            description = "Тест валидности и наличия элемнтов лендинга Сбермаркет",
            priority = 51,
            groups = {"smoke","acceptance","regression"}
    )
    public void successValidateInstamartLanding() {
        runTestOnlyOn(Tenants.sbermarket());

        assertPageIsAvailable();

        assertPresence(Elements.Footer.container());
    }


    // todo тесты наличия всех элементов
    // todo тесты переходов вкаталог магазинов
    // todo тест авторизации на лендосе
}
