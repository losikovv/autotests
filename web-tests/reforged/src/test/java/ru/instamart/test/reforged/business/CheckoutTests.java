package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Addresses;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.business.page.BusinessRouter.*;

@Epic("SMBUSINESS UI")
@Feature("Чекаут B2B")
public final class CheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(738)
    @Test(description = "Способ оплаты корп. картой в чекауте", groups = {"smoke", "regression"})
    @CookieProvider(cookieFactory = CookieFactory.class, cookies = {"COOKIE_ALERT", "EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE"})
    public void addBusinessCardInCheckout() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();
        var card = PaymentCards.testBusinessCard();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), user.getEmail());

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();

        shop().interactHeader().clickToSelectAddress();
        shop().interactAddress().checkYmapsReady();
        shop().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        shop().interactAddress().selectFirstAddress();
        shop().interactAddress().checkMarkerOnMapInAdviceIsNotVisible();
        shop().interactAddress().clickOnSave();
        shop().interactAddress().checkAddressModalIsNotVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

        shop().plusFirstItemToCart();
        shop().interactHeader().checkCartNotificationIsVisible();
        shop().interactHeader().clickToCart();
        shop().interactCart().increaseFirstItemCountToMin();

        checkout().goToPage();
        checkout().checkCheckoutLoaderNotVisible();
        checkout().setDeliveryOptions().checkSubmitButtonVisible();
        checkout().setDeliveryOptions().clickToSubmitForDelivery();
        checkout().setDeliveryOptions().checkSubmitButtonNotVisible();

        checkout().setContacts().checkSubmitButtonVisible();
        checkout().setContacts().fillContactInfo();
        checkout().setContacts().clickToSubmit();
        checkout().setContacts().checkSubmitButtonNotVisible();

        checkout().setReplacementPolicy().checkSubmitButtonVisible();
        checkout().setReplacementPolicy().clickToSubmit();
        checkout().setReplacementPolicy().checkReplacementSpinnerNotVisible();

        checkout().setSlot().checkSubmitButtonVisible();
        checkout().setSlot().setFirstActiveSlot();
        checkout().setSlot().checkSubmitButtonNotVisible();

        checkout().setPayment().checkPaymentByCardOnlineVisible();
        checkout().setPayment().clickToByCardOnline();
        checkout().setPayment().clickToAddNewPaymentCard();

        checkout().interactEditPaymentCardModal().fillCardData(card);
        checkout().interactEditPaymentCardModal().clickToSaveModal();
        checkout().checkCheckoutLoaderNotVisible();

        checkout().setPayment().checkCardNameContains(checkout().setPayment().getFirstCardName(), "Бизнеc-карта");

        checkout().setPayment().fillRequisitesName(company.getJuridicalName());
        checkout().setPayment().fillRequisitesAddress(company.getJuridicalAddress());
        checkout().setPayment().fillRequisitesKPP(company.getKpp());
        checkout().setPayment().clickToSave();
        checkout().checkSubmitFromCheckoutVisible();
        checkout().setPayment().checkRequisitesContains(checkout().setPayment().getFirstRequisites(), company.getJuridicalName());
        checkout().checkSubmitFromCheckoutActive();
    }
}
