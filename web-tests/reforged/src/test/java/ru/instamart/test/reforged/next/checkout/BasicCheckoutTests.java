package ru.instamart.test.reforged.next.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.next.page.StfRouter.checkout;
import static ru.instamart.reforged.next.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Базовые тесты чекаута")
public final class BasicCheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2939)
    @Test(description = "Тест доступности чекаута по прямой ссылке", groups = "regression")
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
