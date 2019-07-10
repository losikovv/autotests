package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.instamart.autotests.application.BonusPrograms.aeroflot;
import static ru.instamart.autotests.application.BonusPrograms.mnogoru;

public class CheckoutBonuses extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    @Test(
            description = "Тест успешного добавления всех доступных бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 771
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
            priority = 772
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
            priority = 773
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
}
