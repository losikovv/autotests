package ru.instamart.test.reforged.stf.checkout;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.REGRESSION_STF;
import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Базовые тесты чекаута")
public final class BasicCheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("2939")
    //Старый чекаут теперь не должен быть доступен по прямой ссылке. Почему бы и не проверить
    @Test(description = "Тест недоступности старого чекаута по прямой ссылке", groups = REGRESSION_STF)
    public void testCheckoutAccessForUserWithCart() {
        final var userData = UserManager.getQaUser();
        this.helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());
        this.helper.dropAndFillCart(userData, UiProperties.DEFAULT_AUCHAN_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().checkPageError404();
    }
}
