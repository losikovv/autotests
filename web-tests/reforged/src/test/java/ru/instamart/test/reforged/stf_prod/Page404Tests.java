package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import io.qameta.allure.TmsLink;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.notfound;

@Epic("STF UI")
@Feature("Страница 404")
public final class Page404Tests {

    @TmsLink("1763")
    @Test(description = "Тест перехода на главную по одноименной кнопке на странице 404", groups = {STF_PROD_S})
    public void successGoToHomepage() {
        notfound().goToPage();
        notfound().clickToGoToMainPage();
        home().checkPageContains(home().pageUrl());
        home().checkPageOpened();
    }
}
