package ru.instamart.test.reforged.stf;

import lombok.SneakyThrows;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.instamart.reforged.stf.block.AuthoredHeader;
import ru.instamart.reforged.stf.drawer.AccountMenu;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.frame.Address;
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
        AuthoredHeader header = new AuthoredHeader();
        Cart cart = header.interactCart();
        Address address = header.interactAddress();
        AccountMenu accountMenu = header.interactAccountMenu();


        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("9999919613");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
        header.clickToPickup();
        address.close();
        header.clickToProfile();
        accountMenu.clickToProfile();
        StfRouter.home().goToPage();
        header.clickToFavorite();
        StfRouter.home().goToPage();
        header.clickToCart();
        cart.clearCart();
        cart.confirmClearCart();
        cart.returnToCatalog();
        header.clickToStoreSelector();
        header.interactStoreSelector().clickToCloseButton();

//        header.clickToCart();
//        header.interactCart().clearCart();
//        header.interactCart().confirmClearCart();
//        header.interactAddress().selectSelfDelivery();
//        header.interactAddress().selectCity("Воронеж");
//        header.interactAddress().selectCity("Самара");
    }

    @AfterMethod(alwaysRun = true, description = "Прикрепляем скриншот интерфейса, если UI тест упал")
    public void screenShot(final ITestResult result) {
        CustomReport.takeScreenshot();
    }
}
