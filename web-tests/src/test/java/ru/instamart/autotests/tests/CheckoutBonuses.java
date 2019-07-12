package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static ru.instamart.autotests.application.BonusPrograms.aeroflot;
import static ru.instamart.autotests.application.BonusPrograms.mnogoru;
import static ru.instamart.autotests.application.Config.testCheckoutBonuses;

public class CheckoutBonuses extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void prepareForCheckout() {
        kraken.get().baseUrl();
        kraken.perform().loginAs(kraken.session.admin);
    }

    @Test(  enabled = testCheckoutBonuses,
            description = "Тест успешного добавления всех доступных бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 771
    )
    public void successAddBonusPrograms(){
        skipOn("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();

        kraken.checkout().addBonus(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не добавляется бонусная программа " + mnogoru().getName() + " в чекауте\n");

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    "Бонусная программа " + mnogoru().getName() + " неактивна после добавления в чекауте\n");

        kraken.checkout().addBonus(aeroflot());

        softAssert.assertTrue(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не добавляется бонусная программа " + aeroflot().getName() + " в чекауте\n");

        softAssert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    "Бонусная программа " + aeroflot().getName() + " неактивна после добавления в чекауте\n");

        softAssert.assertAll();
    }

    @Test(  enabled = testCheckoutBonuses,
            description = "Тест выбора добавленных бонусных программ в чекауте",
            groups = {"regression"},
            priority = 772
    )
    public void successSelectBonusPrograms(){
        skipOn("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();

        kraken.checkout().addBonus(mnogoru());
        kraken.checkout().addBonus(aeroflot());

        kraken.checkout().selectBonus(mnogoru());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(mnogoru()),
                    "Не выбирается бонусная программа " + mnogoru().getName() + " в чекауте\n");

        kraken.checkout().selectBonus(aeroflot());

        softAssert.assertTrue(
                kraken.detect().isBonusActive(aeroflot()),
                    "Не выбирается бонусная программа " + aeroflot().getName() + " в чекауте\n");

        softAssert.assertAll();
    }

    @Test(  enabled = testCheckoutBonuses,
            description = "Тест удаления всех бонусных программ в чекауте",
            groups = {"acceptance","regression"},
            priority = 773
    )
    public void successDeleteBonusPrograms(){
        skipOn("metro");
        SoftAssert softAssert = new SoftAssert();
        kraken.reach().checkout();

        kraken.checkout().addBonus(mnogoru());
        kraken.checkout().deleteBonus(mnogoru());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(mnogoru()),
                    "Не удаляется бонусная программа " + mnogoru().getName() + " в чекауте");

        kraken.checkout().addBonus(aeroflot());
        kraken.checkout().deleteBonus(aeroflot());

        softAssert.assertFalse(
                kraken.detect().isBonusAdded(aeroflot()),
                    "Не удаляется бонусная программа " + aeroflot().getName() + " в чекауте");

        softAssert.assertAll();
    }

    // TODO расширить тесты бонусных программ:

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
