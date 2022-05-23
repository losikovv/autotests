package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.data.LoyaltiesData;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.data_provider.BonusProvider;

import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersBonusesTests {

    private final ApiHelper helper = new ApiHelper();
    private UserData userData;

    @AfterMethod(alwaysRun = true,
            description = "Отмена ордера")
    public void cancelOrder() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseIDs(value = {@CaseId(1666), @CaseId(1667)})
    @Test(  description = "Тест заказа с добавлением бонусов",
            groups = {"smoke","acceptance", "regression"},
            dataProviderClass = BonusProvider.class, dataProvider = "bonus" )
    public void successOrderWithBonus(final LoyaltiesData loyaltiesData) {
        userData = UserManager.getQaUser();
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setLastActiveSlot();

        checkout().clickToAddLoyaltyCard(loyaltiesData.getName());
        checkout().interactEditLoyaltyCardModal()
                .fillValue(loyaltiesData.getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                .clickToSaveModal();

        checkout().setPayment().clickToByCardToCourier();
        checkout().setPayment().clickToSubmitFromCheckoutColumn();
    }
}
