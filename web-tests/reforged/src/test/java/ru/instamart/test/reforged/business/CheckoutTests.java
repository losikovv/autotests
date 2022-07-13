package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.CookieProvider;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.business.page.BusinessRouter.checkout;
import static ru.instamart.reforged.business.page.BusinessRouter.shop;

@Epic("SMBUSINESS UI")
@Feature("Чекаут B2B")
public final class CheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(738)
    @Test(description = "Способ оплаты корп. картой в чекауте", groups = {"smoke", "regression"})
    public void addBusinessCardInCheckout() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();
        var card = PaymentCards.testBusinessCard();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), user.getEmail());
        helper.dropAndFillCart(user, UiProperties.DEFAULT_METRO_MOSCOW_SID);

        shop().goToPage();
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        shop().interactHeader().checkProfileButtonVisible();
        shop().interactHeader().checkEnteredAddressIsVisible();

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
