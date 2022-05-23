package ru.instamart.test.reforged.next;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.next.page.StfRouter.home;
import static ru.instamart.reforged.next.page.StfRouter.notfound;

@Epic("STF UI")
@Feature("Страница 404")
public final class Page404Tests {

    @CaseId(1763)
    @Test(description = "Тест перехода на главную по одноименной кнопке на странице 404", groups = {"acceptance", "regression"})
    public void successGoToHomepage() {
        notfound().goToPage();
        notfound().clickToGoToMainPage();
        home().checkPageOpened();
    }
}
