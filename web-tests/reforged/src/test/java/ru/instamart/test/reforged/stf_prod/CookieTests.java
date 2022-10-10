package ru.instamart.test.reforged.stf_prod;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.reforged.core.annotation.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.STF_PROD_S;
import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка cookie")
public final class CookieTests {

    @CaseId(2282)
    @CookieProvider(cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Отображение предупреждения без записи в cookies", groups = {STF_PROD_S})
    public void checkVisibilityCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
    }

    @CaseId(2283)
    @CookieProvider(cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Проверка верстки предупреждения", groups = {STF_PROD_S})
    public void checkElementCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().checkAlertButton();
        home().interactCookieAlert().checkAlertLink();
    }

    @CaseId(2284)
    @CookieProvider(cookies = {"EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    @Test(description = "Скрытие предупреждения о сборе cookies", groups = {STF_PROD_S})
    public void checkCookieAlertWasHide() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().clickToOk();
        home().interactCookieAlert().checkAlertNotVisible();
    }
}
