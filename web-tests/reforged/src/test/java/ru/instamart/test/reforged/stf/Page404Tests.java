package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;

import static ru.instamart.reforged.stf.page.StfRouter.notfound;

@Epic("STF UI")
@Feature("Страница 404")
public final class Page404Tests {

    @CaseId(1763)
    @Test(description = "Тест перехода на главную по одноименной кнопке на странице 404", groups = "regression")
    public void successGoToHomepage() {
        notfound().goToPage();
        notfound().clickToGoToMainPage();
        notfound().checkPageIsAvailable();
    }
}
