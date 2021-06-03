package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.page.StfRouter;
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

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("9999919613");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().openAddressFrame();
//        Rout.shop().interactAddress().clear();
//        Rout.shop().interactAddress().setAddress("Москва проспект Мира, 211к1");
//        Rout.shop().interactAddress().selectFirstAddress();
//        Rout.shop().interactAddress().clickOnSave();
        StfRouter.shop().interactAddress().selectSelfDelivery();
        StfRouter.shop().interactAddress().selectCity("Воронеж");
        StfRouter.shop().interactAddress().selectCity("Самара");
//        Rout.shop().interactAddress().changeStore();
//        Rout.shop().interactAddress().selectFirstStore();
//        Rout.shop().interactAddress().clickViewOtherRetailers();
//        Rout.shop().interactAddress().selectRetailerByName(retailerName);
//        Rout.shop().interactAddress().selectFirstStoreByRetailerName(retailerName);



    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
            CustomReport.takeScreenshot();
    }
}
