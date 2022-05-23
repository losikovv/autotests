package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.listener.UiExecutionListener;
import ru.instamart.reforged.core.listener.UiListener;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.home;

@Epic("STF UI")
@Feature("Проверка cookie")
public final class CookieTests {

    @CaseId(2282)
    @Test(description = "Отображение предупреждения без записи в cookies", groups = "regression")
    public void checkVisibilityCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
    }

    @CaseId(2283)
    @Test(description = "Проверка верстки предупреждения", groups = "regression")
    public void checkElementCookieAlert() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().checkAlertButton();
        home().interactCookieAlert().checkAlertLink();
    }

    @CaseId(2284)
    @Test(description = "Скрытие предупреждения о сборе cookies", groups = "regression")
    public void checkCookieAlertWasHide() {
        home().goToPage();
        home().interactCookieAlert().checkAlertVisible();
        home().interactCookieAlert().clickToOk();
        home().interactCookieAlert().checkAlertNotVisible();
    }
}
