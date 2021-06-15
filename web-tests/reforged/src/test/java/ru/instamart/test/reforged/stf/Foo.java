package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.page.StfRouter;
import ru.instamart.ui.listener.UiExecutionListener;
import ru.instamart.ui.report.CustomReport;

import static ru.instamart.reforged.stf.page.StfRouter.home;

@Listeners(UiExecutionListener.class)
public class Foo {

    @SneakyThrows
    @Test
    public void bar() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone("79999999999");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillSMS("111111");
        StfRouter.shop().interactHeader().clickToProfile();
        home().goToPage();
    }

    @SneakyThrows
    @Test
    public void bar2() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().checkForBusiness();
        home().interactAuthModal().uncheckForBusiness();
        home().interactAuthModal().checkForBusiness();
        home().interactAuthModal().uncheckForBusiness();
        home().interactAuthModal().checkForBusiness();
        home().interactAuthModal().uncheckForBusiness();
    }

    @SneakyThrows
    @Test
    public void bar3() {
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().authViaVk();
        home().switchToNextTab();
        home().interactAuthModal().interactAuthVkWindow().setEmail("asd");
        home().interactAuthModal().interactAuthVkWindow().setPassword("asddddd");
        home().interactAuthModal().interactAuthVkWindow().clickToLogin();
        home().closeAndSwitchToPrevTab();
        home().interactAuthModal().fillPhone("79999999999");
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillSMS("111111");
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        CustomReport.takeScreenshot();
    }
}
