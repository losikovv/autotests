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
import ru.instamart.reforged.stf.frame.ProductCard;
import ru.instamart.reforged.stf.page.Shop;
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
        Shop shop = new Shop();
        ProductCard productCard = shop.interactProductCard();
//        ProductCard productCard = new ProductCard();
        Cart cart = shop.interactCart();
        Address addressModal = shop.interactAddress();

        StfRouter.home().goToPage();
        StfRouter.home().openLoginModal();
        StfRouter.home().interactAuthModal().fillPhone("9999919613");
        StfRouter.home().interactAuthModal().sendSms();
        StfRouter.home().interactAuthModal().fillSMS("111111");
//        shop.openAddressFrame();
//        addressModal.close();
//        shop.openFirstProductCard();
//        productCard.close();
//        shop.plusFirstItemToCart();
//        shop.plusFirstItemToCart();
//        shop.minusFirstItemFromCart();
//        shop.addFirstItemToFavorite();
//        shop.deleteFirstItemFromFavorite();
//        shop.plusFirstItemToCart();
//        cart.removeItem();
//        cart.closeCart();

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
