package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.core.page.Tab;
import ru.instamart.reforged.stf.frame.auth.AuthMail;
import ru.instamart.reforged.stf.frame.checkout.EditLoyaltyPromoCode;
import ru.instamart.reforged.stf.frame.checkout.EditPaymentCard;
import ru.instamart.reforged.stf.page.checkout.Checkout;
import ru.instamart.reforged.stf.page.StfRouter;
import ru.instamart.reforged.stf.page.checkout.*;
import ru.instamart.ui.listener.UiExecutionListener;
import ru.instamart.ui.report.CustomReport;

@Listeners(UiExecutionListener.class)
public class Foo {

    @SneakyThrows
    @Test
    public void bar() {
        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("79999999999");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().interactHeader().clickToProfile();
        StfRouter.home().goToPage();
    }

    @SneakyThrows
    @Test
    public void bar2() {
        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
        StfRouter.home().interactAuthModal().checkForBusiness();
        StfRouter.home().interactAuthModal().uncheckForBusiness();
    }

    @SneakyThrows
    @Test
    public void bar3() {
        String retailerName = "METRO";
        String callAndReplace = "Позвонить мне. Подобрать замену, если не смогу ответить";
        String callAndRemove = "Позвонить мне. Убрать из заказа, если не смогу ответить";
        String noCallAndReplace = "Не звонить мне. Подобрать замену";
        String noCallAndRemove = "Не звонить мне. Убрать из заказа";
        //9999919613
        AuthMail mail = new AuthMail();
        Checkout checkout = new Checkout();
        DeliveryOptionsStep deliveryOptionsStep = checkout.setDeliveryOptions();
        ContactsStep contactStep = checkout.setContacts();
        ReplacementPolicyStep policyStep = checkout.setReplacementPolicy();
        SlotStep slotStep = checkout.setSlot();
        PaymentStep paymentStep = checkout.paymentStep();
        EditLoyaltyPromoCode loyaltyBonusModal = checkout.interactEditLoyaltyPromoCodeModal();
        EditPaymentCard editPaymentCardModal = checkout.interactEditPaymentCardModal();

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
//        StfRouter.home().interactAuthModal().fillPhone("9999919613");
//        StfRouter.home().interactAuthModal().sendSms();
//        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.home().interactAuthModal().authViaMail();
        mail.closeAndSwitchToNextWindow();
        mail.fillName("vanek_samara");
        mail.clickToEnterPassword();
        mail.fillPassword("A0pc47gh");
        //mail.clickToEnterPassword();
        mail.clickToSubmit();
        mail.closeAndSwitchToPrevWindow();
        StfRouter.home().interactAuthModal().sendSms();
        //ожидание загрузки страницы
        StfRouter.home().interactAuthModal().fillSMS("1111");
        //ожидание загрузки страницы
        checkout.goToPage();
//        deliveryOptionsStep.clickToSubmitForDelivery();
//        contactStep.clickToSubmit();
//        policyStep.clickToPolicy(noCallAndReplace);
//        policyStep.clickToSubmit();
//        slotStep.setFirstActiveSlot();
//        paymentStep.clickToByCardOnline();
//        paymentStep.clickToAddNewPaymentCard();
//        editPaymentCardModal.fillCardNumber("4242424242424242");
//        editPaymentCardModal.fillExpMonth("12");
//        editPaymentCardModal.fillExpYear("2024");
//        editPaymentCardModal.fillCvv("123");
//        editPaymentCardModal.fillHolderName("TEST TESTOV");
//        editPaymentCardModal.clickToSave();
//        checkout.clickToAddLoyaltyCard("Много");
//        loyaltyBonusModal.fillValue("11600350");
//        loyaltyBonusModal.clickToSaveModal();
//        loyaltyBonusModal.clickToCloseModal();
//        checkout.clickToAddLoyaltyCard("Много");
//        loyaltyBonusModal.fillValue("11600350");
//        loyaltyBonusModal.clickToSaveModal();
//        loyaltyBonusModal.clickToCloseModal();
//        checkout.clickToSubmitFromSidebar();
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        CustomReport.takeScreenshot();
    }
}
