package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;

import static ru.instamart.autotests.application.BonusPrograms.aeroflot;
import static ru.instamart.autotests.application.BonusPrograms.mnogoru;

public class Checkout extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    //TODO successValidateCheckout (все элементы во всех шагах и модалках)

    @Test(
            description = "Тест применения промокода в чекауте",
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
            description = "Тест добавления программ лояльности в чекауте",
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
            description = "Тест выбора программы лояльности в чекауте",
            groups = {"regression"},
            priority = 706
    )
    public void successSelectBonusPrograms(){
        kraken.reach().checkout();
        kraken.checkout().selectBonus(mnogoru());
        kraken.checkout().selectBonus(aeroflot());
        // TODO добавить проверки на наличие модалок после выбора
    }

    @Test(
            description = "Тест удаления программ лояльности в чекауте",
            groups = {"acceptance","regression"},
            priority = 707
    )
    public void successClearBonusPrograms(){
        skipOn("metro");
        kraken.reach().checkout();

        kraken.checkout().clearBonus(mnogoru());
        Assert.assertFalse(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        kraken.checkout().clearBonus(aeroflot());
        Assert.assertFalse(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не удаляется бонусная программа " + aeroflot().getName() + " в чекауте");
    }

    //TODO successAddRetailerBonusProgram

    //TODO successClearRetailerBonusProgram
}
