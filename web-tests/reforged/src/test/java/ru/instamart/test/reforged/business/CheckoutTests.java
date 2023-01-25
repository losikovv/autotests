package ru.instamart.test.reforged.business;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.Group.REGRESSION_BUSINESS;
import static ru.instamart.reforged.Group.SMOKE_B2B;
import static ru.instamart.reforged.business.page.BusinessRouter.*;
import static ru.instamart.reforged.core.config.UiProperties.DEFAULT_SID;

@Epic("SMBUSINESS UI")
@Feature("Чекаут B2B")
public final class CheckoutTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("738")
    @Test(description = "Способ оплаты корп. картой в чекауте", groups = {SMOKE_B2B, REGRESSION_BUSINESS})
    public void addBusinessCardInCheckout() {
        var company = JuridicalData.juridical();
        var user = UserManager.getQaUser();
        //TODO: Сейчас сделали Бизнес картой карту 41111
        // По идее насколько я понял, можно сделать любую через рельсу таску, но этот момент надо будет проговаривать с командой
        /*
            B2b::BankIdentificationNumber.all
            B2b::BankIdentificationNumber.create(value: 411111)
            B2b::CardChecker.new('4111111111111111').corporate?
            Rails.cache.clear
         */
        var card = PaymentCards.testCard();
        helper.addCompanyForUser(company.getInn(), company.getJuridicalName(), user.getEmail());
        helper.dropAndFillCart(user, DEFAULT_SID);

        shop().goToPage(DEFAULT_SID);
        shop().interactHeader().clickToLogin();
        shop().interactAuthModal().authViaPhone(user);
        business().interactHeaderMultisearch().checkUserActionsButtonVisible();

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
        checkout().setPayment().fillRequisitiesConsigneeName(company.getJuridicalName());
        checkout().setPayment().fillRequisitiesConsigneeAddress(company.getJuridicalAddress());
        checkout().setPayment().fillRequisitiesConsigneeKpp(company.getKpp());
        checkout().setPayment().clickToSave();
        checkout().checkSubmitFromCheckoutVisible();
        checkout().setPayment().checkRequisitesContains(checkout().setPayment().getFirstRequisites(), company.getJuridicalName());
        checkout().checkSubmitFromCheckoutActive();
    }
}
