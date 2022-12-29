package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.reforged.core.annotation.CookieProvider;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка cookie")
public final class CookieTests {

    @TmsLink("2282")
    @CookieProvider(cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение предупреждения без записи в cookies", groups = {STF_PROD_S})
    public void checkVisibilityCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
    }

    @TmsLink("2283")
    @CookieProvider(cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Проверка верстки предупреждения", groups = {STF_PROD_S})
    public void checkElementCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().checkAlertButton();
        home().interactCookieAlert().checkAlertLink();
    }

    @TmsLink("2284")
    @CookieProvider(cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Скрытие предупреждения о сборе cookies", groups = {STF_PROD_S})
    public void checkCookieAlertWasHide() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().clickToOk();
        home().interactCookieAlert().checkAlertNotVisible();
    }
}
