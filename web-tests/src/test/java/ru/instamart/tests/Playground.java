package ru.instamart.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.application.AppManager;
import ru.instamart.application.Config;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.lib.PaymentTypes;
import ru.instamart.application.lib.ReplacementPolicies;
import ru.instamart.application.models.ElementData;
import ru.instamart.application.models.OrderDetailsData;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.platform.modules.User;

import java.io.IOException;

public class Playground extends TestBase {


    @Test
    public void vibory() throws IOException {
        if(1>0) {
            vote();
            vibory();
        }

    }

    public void vote() throws IOException {
        kraken.rise();
        kraken.get().url("https://docs.google.com/forms/d/e/1FAIpQLScl1Lji8-tG3r355VL4WYWJ68KVS9ShcFvnjOCjVj0NlefAOA/viewform");
        kraken.perform().fillField(Elements.Social.Gmail.AuthForm.loginField(), "devices@instamart.ru");
        kraken.perform().click(Elements.Social.Gmail.AuthForm.loginNextButton());
        kraken.await().simply(1); // Ожидание загрузки страницы ввода пароля Gmail
        kraken.perform().fillField(Elements.Social.Gmail.AuthForm.passwordField(),"hex78.Berwyn");
        kraken.perform().click(Elements.Social.Gmail.AuthForm.passwordNextButton());
        kraken.await().simply(2); // Ожидание авторизации в Gmail

        // кек
        kraken.await().simply(1); //

        kraken.perform().click(By.xpath("//*[text()='Отправить']"));
        kraken.await().simply(1); //
        kraken.stop();
    }

    @Test
    public void restOrder() {
        kraken.rest().order(AppManager.session.user, 1);
        kraken.rest().cancelCurrentOrder();
    }

    @Test
    public void regUser() {
        User.Do.registration();
    }

    @Test
    public void regUserAndSetAddress() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
    }

    @Test
    public void regUserAndPrepareForCheckout() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        Shop.Cart.collect();
    }

    @Test
    public void regUserAndPrepareForOrder() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().fillOrderDetails(Config.TestVariables.testOrderDetails());
    }

    @Test
    public void regUserAndMakeOrder() {
        User.Do.registration();
        User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeOrder() {
        User.Do.loginAs(AppManager.session.user);
        kraken.reach().checkout();
        kraken.checkout().complete();
    }

    @Test
    public void makeNewOrder() {
        User.Do.loginAs(AppManager.session.user);
        kraken.reach().checkout();
        kraken.checkout().complete(PaymentTypes.cash());
    }

    @Test
    public void testNewOrder() {

        User.Do.loginAs(AppManager.session.user);
        kraken.get().page(Pages.Retailers.metro());
        User.ShippingAddress.set(Addresses.Moscow.testAddress());
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();

        OrderDetailsData details = new OrderDetailsData();
        details.setAddressDetails(
                "1234",
                "2345",
                "3456",
                false,
                "4567",
                "5678",
                "обожди ебана"
        );
        details.setContactsDetails(
                null,
                null,
                null,
                false,
                null,
                false
        );
        details.setReplacementPolicy(
                ReplacementPolicies.callAndReplace()
        );
        details.setPaymentDetails(
                PaymentTypes.cardCourier()

        );
        details.setDeliveryTime(
                7,
                7
        );
        //orderDetails.setPromocode("unicorn");
        //orderDetails.setBonus(BonusPrograms.aeroflot());
        //orderDetails.setLoyalty(LoyaltyPrograms.vkusvill());

        kraken.checkout().fillOrderDetails(details);
        //kraken.checkout().complete(orderDetails);

        //kraken.perform().cancelLastOrder();
    }

    @Test
    public void cancelTestOrders() {
        kraken.cleanup().orders();
        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder()),
                "Отменились не все тестовые заказы\n");
    }

    @Test
    public void deleteTestUsers() {
        kraken.cleanup().users();
        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Administration.UsersSection.userlistFirstRow()),
                "Удалились не все тестовые юзеры\n");
    }

    @Test
    public void testMultikraken() {
        User.Do.loginAs(AppManager.session.admin);
        kraken.perform().order();
        //kraken.perform().loginAs(session.user);
        //kraken.perform().order();
    }

    @Test
    public void catalog() {
        kraken.get().page("metro");
        //kraken.await().simply(5);
        Shop.CatalogDrawer.open();
        //kraken.await().simply(5);
        Shop.CatalogDrawer.close();
       // kraken.await().simply(5);
    }


    @Test
    public void slackWipeout() {
        String email = "";

        ElementData emailField = new ElementData(By.xpath("//input[@placeholder='Search current members']"));
        ElementData optionsButton = new ElementData(By.xpath("//button[contains(@class,'p-admin_member_table__menu_button')]"));
        ElementData deactivateButton = new ElementData(By.xpath("//div[@class='c-menu_item__label' and text()='Deactivate account']"));
        ElementData deactivateApproveButton = new ElementData(By.xpath("//button[@data-qa='dialog_go']"));

        loginSlack();
        searchSlackUser(email, emailField);
        deactivateSlackUser(optionsButton, deactivateButton, deactivateApproveButton);

        if(kraken.detect().isElementPresent(optionsButton)) {
            throw new AssertionError("Пользователь " + email + " не был деактивирован");
        }
    }

    private void loginSlack() {
        kraken.get().url("https://instamart.slack.com/admin");
        ElementData emailLoginField = new ElementData(By.xpath("//input[@id='email']"));
        ElementData passwordLoginField = new ElementData(By.xpath("//input[@id='password']"));
        ElementData loginSubmitButton = new ElementData(By.xpath("//button[@id='signin_btn']"));

        String loginEmail = "stanislav.klimov@instamart.ru";
        String loginPassword = "";

        kraken.perform().fillField(emailLoginField,loginEmail);
        kraken.perform().fillField(passwordLoginField,loginPassword);
        kraken.perform().click(loginSubmitButton);
        kraken.await().simply(2);
    }

    private void searchSlackUser(String email, ElementData emailField) {
        kraken.get().url("https://instamart.slack.com/admin");
        kraken.perform().fillField(emailField,email);
        kraken.await().simply(1);
    }

    private void deactivateSlackUser(ElementData optionsButton, ElementData deactivateButton, ElementData deactivateApproveButton) {
        kraken.perform().click(optionsButton);
        kraken.await().simply(2);
        kraken.perform().click(deactivateButton);
        kraken.perform().click(deactivateApproveButton);
        kraken.await().simply(3);
    }
}
