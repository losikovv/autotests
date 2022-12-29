package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.SMOKE_STF;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.notfound;

@Epic("STF UI")
@Feature("Страница 404")
public final class Page404Tests {

    @TmsLink("145700")
    @Test(description = "Тест перехода на главную по одноименной кнопке на странице 404", groups = {REGRESSION_STF, SMOKE_STF})
    public void successGoToHomepage() {
        notfound().goToPage();
        notfound().clickToGoToMainPage();
        home().checkPageOpened();
    }
}
