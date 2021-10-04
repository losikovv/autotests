package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.LoyaltiesData;
import ru.instamart.reforged.core.data_provider.BonusProvider;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.kraken.testdata.BonusPrograms.aeroflot;
import static ru.instamart.kraken.testdata.BonusPrograms.mnogoru;
import static ru.instamart.reforged.stf.page.StfRouter.checkout;
import static ru.instamart.reforged.stf.page.StfRouter.shop;

@Epic("STF UI")
@Feature("Покупка товара")
public final class OrdersBonusesTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private final UserData userData = UserManager.getUser();

    @BeforeMethod(description = "Наполняем корзину")
    public void preconditions() {
        helper.dropAndFillCart(userData, EnvironmentProperties.DEFAULT_SID);
    }

    @AfterMethod(alwaysRun = true,
            description = "Отмена ордера")
    public void cancelOrder() {
        helper.cancelAllActiveOrders(userData);
    }

    @CaseIDs(value = {@CaseId(1666), @CaseId(1667)})
    @Test(
            description = "Тест заказа с добавлением бонусов",
            groups = {"sbermarket-acceptance", "sbermarket-regression"},
            dataProviderClass = BonusProvider.class, dataProvider = "bonus"
    )
    public void successOrderWithBonus(final LoyaltiesData loyaltiesData) {
        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(userData);
        shop().interactHeader().checkProfileButtonVisible();

        checkout().goToPage();
        checkout().setDeliveryOptions().fillApartment(Generate.digitalString(3));
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().clickToAddLoyaltyCard(loyaltiesData.getName());
        checkout().interactEditLoyaltyCardModal()
                .fillValue(loyaltiesData.getCardNumber());
        checkout().interactEditLoyaltyCardModal()
                .clickToSaveModal();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();
    }
}
