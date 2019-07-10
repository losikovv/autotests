package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;

import static ru.instamart.autotests.application.BonusPrograms.aeroflot;
import static ru.instamart.autotests.application.BonusPrograms.mnogoru;

public class Checkout extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void preparingForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    //TODO successValidateCheckout (все элементы во всех шагах и модалках)

    @Test(
            description = "Тест успешного применения промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void successAddPromocode(){
        kraken.reach().checkout();
        
        kraken.checkout().addPromocode(Promo.freeOrderDelivery());

        Assert.assertTrue(
                kraken.detect().isPromocodeApplied(),
                    "Не применяется промокод в чекауте\n");
    }

    @Test(
            description = "Тест удаления промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 702
    )
    public void successClearPromocode(){
        kraken.reach().checkout();
        kraken.checkout().clearPromocode();

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "Не удаляется промокод в чекауте\n");
    }

    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 703
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();
        kraken.perform().click(Elements.Checkout.Promocode.addButton());
        kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), "unicorn");
        kraken.perform().click(Elements.Checkout.Promocode.Modal.cancelButton());

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 704
    )
    public void noPromocodeAddedOnModalClose(){
        kraken.reach().checkout();
        kraken.perform().click(Elements.Checkout.Promocode.addButton());
        kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), "unicorn");
        kraken.perform().click(Elements.Checkout.Promocode.Modal.closeButton());

        Assert.assertFalse(
                kraken.detect().isPromocodeApplied(),
                    "При закрытии модалки добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест успешного добавления всех доступных бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 705
    )
    public void successAddBonusPrograms(){
        skipOn("metro");
        kraken.reach().checkout();

        kraken.checkout().addBonus(mnogoru());

        Assert.assertTrue(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не добавляется бонусная программа " + mnogoru().getName() + " в чекауте\n");

        kraken.checkout().addBonus(aeroflot());

        Assert.assertTrue(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не добавляется бонусная программа " + aeroflot().getName() + " в чекауте\n");
    }

    @Test(
            description = "Тест выбора добавленных бонусных программ в чекауте",
            groups = {"regression"},
            priority = 706
    )
    public void successSelectBonusPrograms(){
        skipOn("metro");
        kraken.reach().checkout();

        kraken.checkout().addBonus(mnogoru());
        kraken.checkout().addBonus(aeroflot());

        kraken.checkout().selectBonus(mnogoru());

        Assert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    "Не выбирается бонусная программа " + mnogoru().getName() + " в чекауте\n");

        kraken.checkout().selectBonus(aeroflot());

        Assert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    "Не выбирается бонусная программа " + aeroflot().getName() + " в чекауте\n");
    }

    @Test(
            description = "Тест удаления всех бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 707
    )
    public void successDeleteBonusPrograms(){
        skipOn("metro");
        kraken.reach().checkout();

        kraken.checkout().deleteBonus(mnogoru());

        Assert.assertFalse(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        kraken.checkout().deleteBonus(aeroflot());

        Assert.assertFalse(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не удаляется бонусная программа " + aeroflot().getName() + " в чекауте");
    }

    // TODO расширить тесты бонусных программ

        // TODO public void noAddBonusProgramOnCancel() {}

        // TODO public void noAddBonusProgramOnModalClose() {}

        // TODO public void noAddBonusProgramWithEmptyCardNumber() {}

        // TODO public void noAddBonusProgramWithWrongCardNumber() {}

        // TODO public void successEditBonusProgram() {}

        // TODO public void noEditBonusProgramOnCancel() {}

        // TODO public void noEditBonusProgramOnModalClose() {}

        // TODO public void noEditBonusProgramWithSameCardNumber() {}

        // TODO public void noEditBonusProgramWithEmptyCardNumber() {}

        // TODO public void noDeleteBonusProgramOnCancel() {}

        // TODO public void noDeleteBonusProgramOnModalClose() {}


    // TODO написать тесты добавления карты ритейлера

        // TODO public void successAddRetailerCard {}

        // TODO public void noAddRetailerCardOnCancel {}

        // TODO public void noAddRetailerCardOnModalClose {}

        // TODO public void noAddRetailerCardWithWrongNumber {}

        // TODO public void noAddRetailerCardWithEmptyNumber {}

        // TODO public void successEditRetailerCard {}

        // TODO public void noEditRetailerCardOnCancel {}

        // TODO public void noEditRetailerCardOnModalClose {}

        // TODO public void noEditRetailerCardWithSameCardNumber() {}

        // TODO public void noEditRetailerCardWithEmptyCardNumber() {}

        // TODO public void successDeleteRetailerCard {}

        // TODO public void noDeleteRetailerCardOnCancel() {}

        // TODO public void noDeleteRetailerCardOnModalClose() {}
}
