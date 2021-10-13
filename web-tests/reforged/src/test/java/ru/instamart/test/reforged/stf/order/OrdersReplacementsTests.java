package ru.instamart.test.reforged.stf.order;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.*;
import static ru.instamart.reforged.stf.page.StfRouter.userShipments;

@Epic("STF UI")
@Feature("Покупка товара")
public class OrdersReplacementsTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();
    private UserData ordersUser;

    @DataProvider(name = "replacementPolicy")
    public static Object[][] getReplacementPolicyName() {
        return new Object[][]{
                {"Позвонить мне. Подобрать замену, если не смогу ответить"},
                {"Позвонить мне. Убрать из заказа, если не смогу ответить"},
                {"Не звонить мне. Подобрать замену"},
                {"Не звонить мне. Убрать из заказа"}
        };
    }

    @AfterMethod(alwaysRun = true, description = "Отмена ордера")
    public void afterTest() {
        helper.cancelAllActiveOrders(ordersUser);
    }

    @CaseIDs(value = {@CaseId(1634), @CaseId(1635), @CaseId(1636), @CaseId(1637)})
    @Story("Тест заказа с политикой Звонить / Заменять")
    @Test(description = "Тест заказа с политикой Звонить / Заменять",
            groups = {"metro-acceptance", "metro-regression",
                    "sbermarket-acceptance", "sbermarket-regression"},
                     dataProvider = "replacementPolicy"
    )
    public void successOrderWithReplacementPolicy(final String replacementPolicy) {
        var company = UserManager.juridical();

        ordersUser = UserManager.getUser();
        helper.dropAndFillCart(ordersUser, 1);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(ordersUser);
        shop().interactHeader().checkProfileButtonVisible();
        shop().addCookie(CookieFactory.COOKIE_ALERT);

        checkout().goToPage();
        checkout().setDeliveryOptions().clickToForSelf();

        checkout().setDeliveryOptions().fillApartment(company.getJuridicalAddress());
        checkout().setDeliveryOptions().clickToSubmitForDelivery();

        checkout().setContacts().fillFirstName(Generate.literalString(8));
        checkout().setContacts().fillLastName(Generate.literalString(8));
        checkout().setContacts().fillEmail(Generate.email());
        checkout().setContacts().clickToSubmit();

        checkout().setReplacementPolicy().clickToPolicy(replacementPolicy);
        checkout().setReplacementPolicy().clickToSubmit();

        checkout().setSlot().setFirstActiveSlot();

        checkout().setPayment().clickToByCardToCourier();

        checkout().setPayment().clickToSubmitFromCheckoutColumn();

        userShipments().checkPageContains(userShipments().pageUrl());

        userShipments().checkStatusShipmentReady();
        userShipments().clickToDetails();
        userShipments().checkReplacementMethod(replacementPolicy);
    }
}
