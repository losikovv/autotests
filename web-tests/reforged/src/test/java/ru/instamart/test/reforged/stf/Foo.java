package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.frame.AddCompanyModal;
import ru.instamart.reforged.stf.frame.EditPhoneNumber;
import ru.instamart.reforged.stf.page.Checkout;
import ru.instamart.reforged.stf.page.StfRouter;
import ru.instamart.reforged.stf.page.checkoutSteps.ContactsStep;
import ru.instamart.reforged.stf.page.checkoutSteps.DeliveryOptionsStep;
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
        //9999919613
        Checkout checkout = new Checkout();
        DeliveryOptionsStep firstStep = checkout.setDeliveryOptions();
        ContactsStep secondStep = checkout.setContacts();
        AddCompanyModal addCompanyModal = checkout.interactAddCompanyModal();
        EditPhoneNumber editPhoneNumber = checkout.interactEditPhoneNumberModal();

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("9999919613");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        //ожидание загрузки страницы
        checkout.goToPage();
        firstStep.clickSubmitForDelivery();
        secondStep.clickToChangePhone();
        editPhoneNumber.clickToDelete();
        secondStep.clickToSubmit();
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        CustomReport.takeScreenshot();
    }
}
