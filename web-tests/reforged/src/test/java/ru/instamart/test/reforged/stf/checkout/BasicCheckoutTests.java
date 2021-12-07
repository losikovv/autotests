package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Базовые тесты чекаута")
public final class BasicCheckoutTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2939)
    @Test(description = "Тест доступности чекаута по прямой ссылке", groups = {"acceptance", "regression"})
    public void testCheckoutAccessForUserWithCart() {
        final var userData = UserManager.getQaUser();
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        this.helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().checkPageIsAvailable();
    }
}
