package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.application.*;

import static ru.instamart.autotests.application.BonusPrograms.aeroflot;
import static ru.instamart.autotests.application.BonusPrograms.mnogoru;


// Тесты функционала чекаута


// TODO перенести проверку добавления карты Вкусвилл в тесты чекаута
// TODO добавить проверку проставления галки согласия на акционную рассылку


public class Checkout extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void preparingForCheckout() throws Exception {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }


    @Test(
            description = "Тест применения промокода в чекауте",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void successAddPromocode(){
        kraken.reach().checkout();
        kraken.checkout().addPromocode("unicorn");

        Assert.assertTrue(kraken.detect().isPromocodeApplied(),
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

        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "Не удаляется промокод в чекауте\n");
    }


    @Test(
            description = "Тест недобавления промокода при нажатии кнопки Отмена",
            groups = {"regression"},
            priority = 703
    )
    public void noPromocodeAddedOnCancel(){
        kraken.reach().checkout();
        kraken.perform().click(Elements.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Checkout.PromocodeModal.cancelButton());

        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
                "При отмене добавления промокода, промокод все равно применяется\n");
    }

    @Test(
            description = "Тест недобавления промокода при закрытии модалки промокода",
            groups = {"regression"},
            priority = 704
    )
    public void noPromocodeAddedOnClose(){
        kraken.reach().checkout();
        kraken.perform().click(Elements.Checkout.addPromocodeButton());
        kraken.perform().fillField(Elements.Checkout.PromocodeModal.field(), "unicorn");
        kraken.perform().click(Elements.Checkout.PromocodeModal.closeButton());

        Assert.assertFalse(kraken.detect().isPromocodeApplied(),
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

        Assert.assertTrue(kraken.detect().isBonusAdded(mnogoru()),
                "Не добавляется бонусная программа " + mnogoru().getName() + " в чекауте\n");

        kraken.checkout().addBonus(aeroflot());

        Assert.assertTrue(kraken.detect().isBonusAdded(aeroflot()),
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
        Assert.assertFalse(kraken.detect().isBonusAdded(mnogoru()),
                "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        kraken.checkout().clearBonus(aeroflot());
        Assert.assertFalse(kraken.detect().isBonusAdded(aeroflot()),
                "Не удаляется бонусная программа " + aeroflot().getName() + " в чекауте");
    }

    //TODO successAddRetailerBonusProgram

    //TODO successClearRetailerBonusProgram
}
