package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.action.SwitchAction;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.stf.drawer.AccountMenu;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.frame.Address;
import ru.instamart.reforged.stf.frame.ProductCard;
import ru.instamart.reforged.stf.page.Shop;
import ru.instamart.reforged.stf.frame.auth.AuthFacebook;
import ru.instamart.reforged.stf.frame.auth.AuthMail;
import ru.instamart.reforged.stf.frame.auth.AuthModal;
import ru.instamart.reforged.stf.frame.auth.AuthVk;
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
        //9999919613
        AuthModal authModal = new AuthModal();
        AuthVk authVk = authModal.interactAuthVkWindow();
        SwitchAction switchAction = new SwitchAction();

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().authViaVk();
        switchAction.switchToWindowIndex(1);
        authVk.setEmail("");
        authVk.setPassword("");
        authVk.clickToLogin();
//        authMail.clickToEnterPassword();
//        authMail.setPassword("");
//        authMail.clickToSubmit();
        switchAction.switchToWindowIndex(0);


    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        CustomReport.takeScreenshot();
    }
}
