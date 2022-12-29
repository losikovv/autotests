package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.reforged.core.annotation.CookieProvider;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.Group.SMOKE_STF;
import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка cookie")
public final class CookieTests {

    @TmsLink("167481")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Отображение предупреждения без записи в cookies", groups = {REGRESSION_STF, SMOKE_STF})
    public void checkVisibilityCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
    }

    @TmsLink("2283")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Проверка верстки предупреждения", groups = {REGRESSION_STF, SMOKE_STF})
    public void checkElementCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().checkAlertButton();
        home().interactCookieAlert().checkAlertLink();
    }

    @TmsLink("2284")
    @CookieProvider(cookies = {"FORWARD_FEATURE_STF", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_GUEST"})
    @Test(description = "Скрытие предупреждения о сборе cookies", groups = {REGRESSION_STF, SMOKE_STF})
    public void checkCookieAlertWasHide() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().clickToOk();
        home().interactCookieAlert().checkAlertNotVisible();
    }
}
