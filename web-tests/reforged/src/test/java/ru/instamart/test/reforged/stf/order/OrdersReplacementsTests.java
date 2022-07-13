package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.data_provider.ReplacePolicyProvider;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.stf.page.StfRouter.*;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersReplacementsTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @BeforeMethod(alwaysRun = true, description = "Создание юзера и наполнение корзины")
    public void beforeTest() {
        this.ordersUser = UserManager.getQaUser();
        this.helper.dropAndFillCart(ordersUser, UiProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        this.helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseIDs(value = {@CaseId(1634), @CaseId(1635), @CaseId(1636), @CaseId(1637)})
    @Story("Тест заказа с политикой Звонить / Заменять")
    @Test(description = "Тест заказа с политикой Звонить / Заменять",
            groups = "regression",
            dataProviderClass = ReplacePolicyProvider.class,
            dataProvider = "replacementPolicy")
    public void successOrderWithReplacementPolicy(final String replacementPolicy) {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToPolicy(replacementPolicy);
        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().clickToDetails();
        userShipments().checkReplacementMethod(replacementPolicy);
    }
}
